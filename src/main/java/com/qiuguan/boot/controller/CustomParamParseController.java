package com.qiuguan.boot.controller;

import com.qiuguan.boot.bean.Apple;
import com.qiuguan.boot.bean.Teacher;
import com.qiuguan.boot.resolver.RequestBodyMapping;
import com.qiuguan.boot.resolver.RequestParamMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author qiuguan
 * @date 2022/09/18 20:17:54  星期日
 */
@Validated
@Slf4j
@RestController
public class CustomParamParseController {


    @PostMapping("/parse")
    public Teacher parse(@Validated @RequestBodyMapping Teacher teacher) {
        log.info("teacher is {}", teacher);
        return teacher;
    }

    /**
     *
     * @param name 使用 {@link RequestParamMethodArgumentResolver} 去解析
     * @param age 使用的也是{@link RequestParamMethodArgumentResolver} 去解析，解析器集合中放了2个
     *         {@link RequestParamMethodArgumentResolver}， 第一个的 useDefaultResolution =false,
     *            第二个的 useDefaultResolution = true
     * @see HandlerMethodArgumentResolverComposite#getArgumentResolver
     * @return
     */
    @GetMapping("parse2")
    public String getParam(@RequestParam(name = "name") String name, int age) {
        log.info("name is {}, age is {}", name, age);
        return "success";
    }


    /**
     * http://localhost:8080/parse3?age=23
     *
     * 说明：单个参数的校验，比如{@link Max} 它是通过通过代理对象调用目标方法之前完成校验的, 请看 {@link MethodValidationPostProcessor}
     * 而对于Bean的映射就相对简单，请看我自定义的两个参数处理器：
     * @see com.qiuguan.boot.resolver.RequestParamMappingAgreementResolver
     * @see com.qiuguan.boot.resolver.RequestBodyMappingHandlerMethodParamResolver
     */
    @GetMapping("parse3")
    public String getParam3(@Max (35) @Min (18) @RequestParamMapping int age){
        log.info("age is {}", age);
        return "success";
    }

    /**
     * http://localhost:8080/parse4?id=1001&name=apple
     *
     * 这个参数是Bean的，使用的是 {@link ModelAttributeMethodProcessor } 处理器,简单的单个参数不管是否
     * 使用 {@link RequestParam} 注解，使用的都是 {@link RequestParamMethodArgumentResolver} 处理器
     */
    @GetMapping("parse4")
    public Apple getParam4(@Validated @RequestParamMapping Apple apple){
        log.info("apple is {}", apple);
        return apple;
    }
}
