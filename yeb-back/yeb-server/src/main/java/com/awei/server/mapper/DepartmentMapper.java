package com.awei.server.mapper;

import com.awei.server.pojo.Department;
import com.awei.server.pojo.RespBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取所有部门
     * @param i 父部门id
     * @return
     */
    List<Department> getAllDepartments(Integer i);

    /**
     * 添加部门
     * @param dep
     * @return
     */
    RespBean addDep(Department dep);
}
