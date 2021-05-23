package com.awei.server.service;

import com.awei.server.pojo.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     * @param i 父部门id
     * @return
     */
    List<Department> getAllDepartments(Integer i);

}
