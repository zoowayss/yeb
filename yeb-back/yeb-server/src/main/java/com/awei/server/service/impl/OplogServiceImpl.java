package com.awei.server.service.impl;

import com.awei.server.mapper.OplogMapper;
import com.awei.server.pojo.Oplog;
import com.awei.server.service.IOplogService;
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
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements IOplogService {

}
