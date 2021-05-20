package com.awei.server;

import com.awei.server.pojo.Admin;
import com.awei.server.pojo.Menu;
import com.awei.server.service.IAdminService;
import com.awei.server.service.IMenuService;
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

    @Test
    public void irjwqoir() {

        List<Menu> menus = menuService.getMenusWithRole();
        menus.forEach(menu -> System.out.println(menu));
    }

}
