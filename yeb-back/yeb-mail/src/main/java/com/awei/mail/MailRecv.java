package com.awei.mail;

import com.awei.server.pojo.Employee;
import com.awei.server.pojo.MailConstants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * @Description:
 * @Author: Awei
 * @Create: 2021-05-31 09:08
 **/
@Component
public class MailRecv {


    private static final Logger LOGGER = LoggerFactory.getLogger(MailRecv.class);


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel) {
        Employee emp = (Employee) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        // 消息序号
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // messageId
        String msgId = (String) headers.get("spring_returned_message_correlation");
        // 发件人
        HashOperations hashOperations = redisTemplate.opsForHash();

        try {
            if (hashOperations.entries("mail_log").containsKey(msgId)) {
                LOGGER.error("消息已经被消费=========》{}", msgId);
                /**
                 * 手动确认消息
                 * tag： 消息序号
                 * multiple 一次确认多条
                 */
                channel.basicAck(tag, false);
                return;
            }
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(mailProperties.getUsername());
            // 收件人
            helper.setTo(emp.getEmail());
            helper.setSubject("入职欢迎邮件");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name", emp.getName());
            context.setVariable("posName", emp.getPosition().getName());
            context.setVariable("joblevelName", emp.getJoblevel().getName());
            context.setVariable("departmentName", emp.getDepartment().getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail, true);
            //发送邮件
            javaMailSender.send(mimeMessage);
            hashOperations.put("mail_log", msgId, "OK");
            LOGGER.info("消息已经被消费=========》{}", msgId);
            //手动确认一条消息
            channel.basicAck(tag, false);
        } catch (Exception e) {
            /**
             * requeue：是否退回队列
             */
            try {
                channel.basicNack(tag, false, true);
            } catch (IOException ioException) {
                LOGGER.error("邮件发送失败======》{}", e.getMessage());
            }
            LOGGER.error("邮件发送失败======》{}", e.getMessage());
        }
    }
}
