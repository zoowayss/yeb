package com.awei.server.controller;


import com.awei.server.pojo.Employee;
import com.awei.server.pojo.RespPageBean;
import com.awei.server.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "查询所有员工（分页）")
    @GetMapping("/")
    public RespPageBean getAllEmps(@RequestParam(defaultValue = "1") Integer currentPage,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   Employee emp, LocalDate[] beginDateScope) {
        return employeeService.getEmpByPage(currentPage, size, emp, beginDateScope);
    }
}
