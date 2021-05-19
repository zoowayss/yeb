package com.awei.server.service.impl;

import com.awei.server.config.security.JwtTokenUtil;
import com.awei.server.mapper.AdminMapper;
import com.awei.server.pojo.Admin;
import com.awei.server.pojo.Menu;
import com.awei.server.pojo.RespBean;
import com.awei.server.service.IAdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private AdminMapper adminMapper;
    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password,String code,HttpServletRequest request) {
        /*String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
            return RespBean.error("验证码输入错误，请重新输入");
        }*/

        String captcha = (String) request.getSession().getAttribute("captcha");
        // 运行时打开，测试时懒得写验证码
//        if (StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
//            return RespBean.error("验证码填写错误！");
//        }

        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails || !encoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或者密码不正确");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error("账户被禁用，请联系管理员");
        }

        //更新security 登录对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //返回token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功", tokenMap);
    }

    /**
     * 根据用户名获取 admin对象
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
    }


}
