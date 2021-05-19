package com.awei.server.service.impl;

import com.awei.server.mapper.MailLogMapper;
import com.awei.server.pojo.MailLog;
import com.awei.server.service.IMailLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
