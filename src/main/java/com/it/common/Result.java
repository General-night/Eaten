package com.it.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用响应类，将响应结果封装到Result类
 *
 * @author GeneralNight
 * @date 2022/3/27 14:41
 */
@Data
public class Result<T> {

    /**
     * 编码：1成功，0和其它数字为失败
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 动态数据
     */
    private Map<String, Object> map = new HashMap<>();

    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}

