package com.qiuguan.boot.controller;

import com.qiuguan.boot.bean.Car;
import com.qiuguan.boot.bean.Student;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @author qiuguan
 * 类上的 {@link Validated} 注解是为了test3方法
 */
@Validated
@Slf4j
@RestController
public class ValidateController extends BaseController {

    /**
     * POST http://localhost:8080/valid/test1
     * Content-Type: application/json
     *
     * 接受前端发送的json数据
     *
     * 用 {@link Validated} 和 {@link Valid} 哪个都可以
     */
    @PostMapping("/valid/test1")
    public Student test1(@Validated @RequestBody Student student){
        log.info("student = {}", student);
        return student;
    }

    /**
     * 表单
     * POST http://localhost:8080/valid/test2
     * Content-Type: application/x-www-form-urlencoded
     * studentNo=1122&name=zxy&age=23
     *
     * 用 {@link Validated} 和 {@link Valid} 哪个都可以
     */
    @PostMapping(value = "/valid/test2")
    public Student test2(@Valid Student student){
        log.info("student is {}", student);
        return student;
    }


    /**
     * POST http://localhost:8080/valid/test3
     * Content-Type: application/x-www-form-urlencoded
     * email=11111122222
     *
     * 单个参数校验
     * TODO: 注意：这种单个参数校验的，必须要在Controller类上添加 @Validate 注解(方法上也不行哦）
     */
    @PostMapping(value = "/valid/test3")
    public String test3(@Email String email){
        log.info("email is {}", email);
        return "email valid success";
    }

    /**
     * GET http://localhost:8080/valid/test4
     * http://localhost:8080/valid/test4?name=zxy&high=true&age=23
     *
     * 如果参数名和url中的key一样，就可以忽略 {@link RequestParam }注解,默认是必须的
     */
    @GetMapping("/valid/test4")
    public String test4(@Length(max = 12, min = 5) String name,
                        @AssertFalse boolean high,
                        @Max(100) int age){
        log.info("name = {}, high = {}, age = {}", name, high, age);
        return "test4 success";
    }


    /**
     * GET http://localhost:8080/valid/test5?id=1001&name=bmw
     *  对于GET 请求直接映射bean的时候，需要在bean的前面添加 {@link Validated} 或者 {@link Valid} 注解，至于
     *  Controller类上是否有{@link Validated } 不关注，像POST请求的单个参数校验是必须有的
     */
    @GetMapping("/valid/test5")
    public String test5(@Validated Car car){
        log.info("car is {}", car);
        return "test5 success";
    }
}
