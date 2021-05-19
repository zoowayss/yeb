package com.awei.server.config.security;

import com.awei.server.pojo.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 当未登录或者token 失效时访问接口时
 * @Author: Awei
 * @Create: 2021-03-09 19:51
 **/
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        /*httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json");*/
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        RespBean bean = RespBean.error("未登录，请登录");
        bean.setCode(401);
        pw.write(new ObjectMapper().writeValueAsString(bean));
        pw.flush();
        pw.close();

    }
}
