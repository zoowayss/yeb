package com.awei.server.service;

import com.awei.server.pojo.Admin;
import com.awei.server.pojo.Menu;
import com.awei.server.pojo.RespBean;
import com.awei.server.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
public interface IAdminService extends IService<Admin> {


    RespBean login(String username, String password,String code, HttpServletRequest request);

    Admin getAdminByUsername(String username);

    List<Role> getRoles(Integer adminId);

    /**
     * 获取所有操作员
     * @param keyword
     * @return
     */
    List<Admin> getAllAdmins(String keyword);

}
