package com.qiuguan.boot.bean;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiuguan
 * @date 2022/09/14 23:31:41  星期三
 */
@Getter
public class BaseBean {

    protected Map<String, String> headers = new HashMap<>();

    public void put(String key, String value) {
        this.headers.put(key, value);
    }
}
