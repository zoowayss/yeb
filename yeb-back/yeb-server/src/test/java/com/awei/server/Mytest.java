package com.awei.server;

import com.awei.server.pojo.Admin;
import com.awei.server.pojo.Department;
import com.awei.server.pojo.Menu;
import com.awei.server.service.IAdminService;
import com.awei.server.service.IDepartmentService;
import com.awei.server.service.IMenuService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Description: fjklas
 * @Author: Awei
 * @Create: 2021-03-09 20:33
 **/
@SpringBootTest
public class Mytest {

    @Autowired
    private IMenuService menuService;


    @Autowired
    private IDepartmentService departmentService;
    @Test
    public void irjwqoir() {

        List<Department> menus = departmentService.getAllDepartments();
        menus.forEach(menu -> System.out.println(menu));
        try {
            System.out.println(new ObjectMapper().writeValueAsString(menus));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
