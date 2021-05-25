package com.awei.server.service.impl;

import com.awei.server.mapper.EmployeeMapper;
import com.awei.server.pojo.Employee;
import com.awei.server.pojo.RespBean;
import com.awei.server.pojo.RespPageBean;
import com.awei.server.service.IEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {


    @Autowired
    private EmployeeMapper employeeMapper;
    /**
     * 获取所有员工（分页）
     *
     * @param currentPage
     * @param size
     * @param emp
     * @param beginDateScope
     * @return
     */
    @Override
    public RespPageBean getEmpByPage(Integer currentPage, Integer size, Employee emp, LocalDate[] beginDateScope) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> empByPage = employeeMapper.getEmpByPage(page, emp, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(empByPage.getTotal(), empByPage.getRecords());
        return respPageBean;
    }

    /**
     * 获取工号
     *
     * @return
     */
    @Override
    public RespBean maxWorkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));

        return new RespBean(200,null,String.format("%08d", Integer.parseInt(maps.get(0).get("max(workID)").toString()) + 1));
    }
}
