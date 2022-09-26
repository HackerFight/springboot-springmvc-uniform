package com.qiuguan.boot.controller;

import com.qiuguan.boot.bean.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author qiuguan
 */
@RestController
public class CustomValidatorController {

    /**
     * POST: http://localhost:8080/cvc
     * Content type 'application/x-www-form-urlencoded
     * 表单提交就不用{@link RequestBody} 了
     *
     * 使用 {@link Validated} or {@link Valid} 都可以
     */
    @PostMapping("/cvc")
    public User add(@Valid User user) {
        System.out.println("user = " + user);
        return user;
    }
}
