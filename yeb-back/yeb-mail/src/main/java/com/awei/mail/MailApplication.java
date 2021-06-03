package com.awei.mail;

import com.awei.server.pojo.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @Description:
 * @Author: Awei
 * @Create: 2021-05-31 08:57
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MailApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

    @Bean
    public Queue queue() {
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }
}
