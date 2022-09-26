package com.qiuguan.boot.controller;

import com.qiuguan.boot.convert.EventBean;
import com.qiuguan.boot.convert.EventEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuguan
 * @date 2022/09/18 23:06:57  星期日
 */
@Slf4j
@RestController
public class CustomEnumMappingPropertiesController {

    /**
     * 表单提交
     * POST: http://localhost:8080/cvc
     * Content type 'application/x-www-form-urlencoded
     *
     *
     *表单提交就不用使用 {@link org.springframework.web.bind.annotation.RequestBody} 了
     * 因为 {@link org.springframework.web.bind.annotation.RequestBody} 是JSON 提交
     *
     * 前面针对枚举参数要传名字，比如INIT， START.... 才可以
     * @see org.springframework.core.convert.support.StringToEnumConverterFactory
     * 这个是默认的 {@link com.qiuguan.boot.convert.Test}
     */
    @PostMapping("/register")
    public EventBean register(EventBean eventBean) {
        log.info("eventBean is {}", eventBean);
        return eventBean;
    }

    /**
     * GET http://localhost:8080/status?eventEnum=INIT
     */
    @GetMapping("/status")
    public Integer map(EventEnum eventEnum) {
        log.info("eventEnum is {}", eventEnum);
        return eventEnum.getCode();
    }

    //----------------- 自定义转换器-------------------//

    /**
     * POST: http://localhost:8080/map2
     * 'Content-Type: application/json'
     *
     * {
     *     "topic": "123",
     *     "taskId": "234",
     *     "eventEnum": 2   //int 类型
     * }
     */
    @PostMapping("/map2")
    public EventBean map2(@RequestBody EventBean eventBean) {
        //eventBean is EventBean(topic=123, taskId=234, eventEnum=PROCESSING)
        log.info("eventBean is {}", eventBean);
        return eventBean;
    }

    /**
     *  POST 'http://localhost:8080/map3'
     * 'Content-Type: application/x-www-form-urlencoded'
     *  表单都是String 类型，所以就算写的是数字2，那么它也会当成 "2", 然后找的是默认的
     *  {@link org.springframework.core.convert.support.StringToEnumConverterFactory }
     */
    @PostMapping("/map3")
    public EventBean map3(EventBean eventBean) {
        log.info("eventBean is {}", eventBean);
        return eventBean;
    }
}

