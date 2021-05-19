package com.awei.server;

import com.awei.server.pojo.Admin;
import com.awei.server.service.IAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: fjklas
 * @Author: Awei
 * @Create: 2021-03-09 20:33
 **/
@SpringBootTest
public class Mytest {

    @Autowired
    private IAdminService adminService;
    @Test
    public void irjwqoir() {

        Admin admin = adminService.getAdminByUsername("admin");
        System.out.println(admin.toString());

    }

}
