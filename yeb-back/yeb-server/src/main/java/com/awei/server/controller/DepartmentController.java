package com.awei.server.controller;


import com.awei.server.pojo.Department;
import com.awei.server.pojo.RespBean;
import com.awei.server.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments(-1);
    }


    @ApiOperation(value = "添加部门")
    @PostMapping("/")
    public RespBean addDep(Department dep) {
        return departmentService.addDep(dep);
    }
}
