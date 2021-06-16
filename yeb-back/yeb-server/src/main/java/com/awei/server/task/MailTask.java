package com.awei.server.task;

import com.awei.server.pojo.Employee;
import com.awei.server.pojo.MailConstants;
import com.awei.server.pojo.MailLog;
import com.awei.server.service.IEmployeeService;
import com.awei.server.service.IMailLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 邮件发送定时任务
 * @Author: Awei
 * @Create: 2021-06-03 09:25
 **/
@Component
public class MailTask {


    @Autowired
    private IMailLogService mailLogService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/20 * * * * ?")
    public void mailTask() {
        List<MailLog> mailLogList = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));
        mailLogList.forEach(mailLog -> {
            // 如果重试次数超过三次，更新状态为投递失败，不在重试
            if (3 <= mailLog.getCount()) {
                mailLogService.update(new UpdateWrapper<MailLog>().set("status", 2).eq("msgId", mailLog.getMsgId()));
            }
            mailLogService.update(new UpdateWrapper<MailLog>()
                    .set("count", mailLog.getCount() + 1)
                    .set("updateTime", LocalDateTime.now())
                    .set("tryTime", LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT)));
            Employee employee = employeeService.getEmp(mailLog.getEid()).get(0);
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY, employee, new CorrelationData(mailLog.getMsgId()));
        });

    }
}
