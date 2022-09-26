package com.qiuguan.boot.controller;

import com.qiuguan.boot.bean.Apple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuguan
 */
@Slf4j
@RestController
public class CustomMessageConvertController {

    /**
     * 使用postman 在headers 中设置：'Content-Type: application/x-www-form-custom'
     *
     */
    @PostMapping(path = "/convert", produces = "application/x-www-form-custom-json")
    public String message(@RequestBody Apple apple){
        log.info("custom message convert producer apple is {}", apple);
        return "convert message success";
    }
}

