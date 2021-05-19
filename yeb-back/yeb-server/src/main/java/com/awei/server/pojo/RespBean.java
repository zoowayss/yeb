package com.awei.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Description: 公共返回对象
 * @Author: Awei
 * @Create: 2021-03-09 18:24
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private long code;
    private String msg;
    private Object obj;


    public static RespBean success(String msg) {
        return new RespBean(200, msg, null);
    }

    public static RespBean success(String msg, Object obj) {
        return new RespBean(200, msg, obj);
    }

    public static RespBean error(String msg) {
        return new RespBean(500, msg, null);
    }

    public static RespBean error(String msg, Object obj) {
        return new RespBean(500, msg, obj);
    }
}
