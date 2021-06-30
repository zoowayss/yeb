package com.awei.mail;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Description: 打war包修改的启动类
 * @Author: Awei
 * @Create: 2021-06-30 09:57
 **/
public class MailWar extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MailApplication.class);
    }
}
