package com.awei.server.exception;

import com.awei.server.pojo.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Description: 全局异常处理
 * @Author: Awei
 * @Create: 2021-05-20 13:33
 **/
@RestControllerAdvice
//表示控制器的增强类，如果发生异常，并且符合类中 自定义的拦截异常类的时候就会被拦截
public class GlobalException {


    @ExceptionHandler(SQLException.class)
    public RespBean mySqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据有关联数据，操作失败");
        }
        return RespBean.error("数据库异常，操作失败！");
    }
}
