package com.parkinfo.common;


import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 接口返回对象
 * @author cnyuchu@gmail.com
 * @date 2018/10/19 13:36
 */
@Component
public class ResultMap<T> extends HashMap<String, Object> {
    public ResultMap() {
    }

    public ResultMap success() {
        this.put("result", "success");
        return this;
    }

    public ResultMap fail() {
        this.put("result", "fail");
        return this;
    }

    public ResultMap code(int code) {
        this.put("code", code);
        return this;
    }

    public ResultMap message(Object message) {
        this.put("message", message);
        return this;
    }

    public ResultMap data(T data) {
        this.put("data", data);
        return this;
    }
}

