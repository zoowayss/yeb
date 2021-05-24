package com.awei.server.service;

import com.awei.server.pojo.Employee;
import com.awei.server.pojo.RespPageBean;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 获取所有员工（分页）
     * @param currentPage
     * @param size
     * @param emp
     * @param beginDateScope
     * @return
     */
    RespPageBean getEmpByPage(Integer currentPage, Integer size, Employee emp, LocalDate[] beginDateScope);
}
