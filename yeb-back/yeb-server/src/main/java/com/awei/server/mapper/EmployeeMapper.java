package com.awei.server.mapper;

import com.awei.server.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     *  获取所有员工（分页）
     * @param page
     * @param emp
     * @param beginDateScope
     */
    IPage<Employee> getEmpByPage(Page<Employee> page, @Param("emp") Employee emp, @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 查询员工
     * @param id
     * @return
     */
    List<Employee> getEmp(Integer id);

    /**
     * 获取员工账套
     * @param page
     * @return
     */
    IPage<Employee> getEmpWithSalary(Page<Employee> page);
}
