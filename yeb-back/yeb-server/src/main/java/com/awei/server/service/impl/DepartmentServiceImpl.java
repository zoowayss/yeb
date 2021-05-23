package com.awei.server.service.impl;

import com.awei.server.mapper.DepartmentMapper;
import com.awei.server.pojo.Department;
import com.awei.server.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取所有部门
     * @param i 父部门id
     * @return
     */
    @Override
    public List<Department> getAllDepartments(Integer i) {
        return departmentMapper.getAllDepartments(i);
    }
}
