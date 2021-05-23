package com.awei.server.service.impl;

import com.awei.server.mapper.DepartmentMapper;
import com.awei.server.pojo.Department;
import com.awei.server.pojo.RespBean;
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

    /**
     * 添加部门
     * @param dep
     * @return
     */
    @Override
    public RespBean addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
        if (1 == dep.getResult()) {
            return RespBean.success("添加成功！",dep);
        }
        return RespBean.error("添加失败!");
    }

}
