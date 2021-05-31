package com.awei.mail;

import com.awei.server.pojo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

    @RabbitListener(queues = "mail.welcome")
    public void handler(Employee emp) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            // 发件人
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
            helper.setText(mail,true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("邮件发送失败======》{}", e.getMessage());
        }
    }
}
