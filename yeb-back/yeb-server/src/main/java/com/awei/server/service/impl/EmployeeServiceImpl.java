package com.awei.server.service.impl;

import com.awei.server.mapper.EmployeeMapper;
import com.awei.server.mapper.MailLogMapper;
import com.awei.server.pojo.*;
import com.awei.server.service.IEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailLogMapper mailLogMapper;
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

    /**
     * 添加员工
     * @param emp
     * @return
     */
    @Override
    public RespBean addEmp(Employee emp) {
        //处理合同期限，保留两位小数
        LocalDate beginContract = emp.getBeginContract();
        LocalDate endContract = emp.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat df = new DecimalFormat("##.00");
        emp.setContractTerm(Double.parseDouble(df.format(days / 365.00)));
        if (1 == employeeMapper.insert(emp)) {
            Employee employee = employeeMapper.getEmp(emp.getId()).get(0);

            //消息入库
            String msgId = UUID.randomUUID().toString();    //数据库记录发送的消息
//            String msgId = "123456";
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEid(employee.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());

            mailLogMapper.insert(mailLog);      //消息落库



            //发送消息
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY, employee,new CorrelationData(msgId));
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 查询员工
     * @param id
     * @return
     */
    @Override
    public List<Employee> getEmp(Integer id) {
        return employeeMapper.getEmp(id);
    }

}
