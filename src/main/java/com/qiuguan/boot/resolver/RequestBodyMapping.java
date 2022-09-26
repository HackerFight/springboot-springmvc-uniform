package com.qiuguan.boot.resolver;

import java.lang.annotation.*;

/**
 * @author qiuguan
 * @date 2022/09/18 17:46:19  星期日
 *
 *
 * 我们知道，当前端请求是POST 并且 'Content-Type: application/json' 的时候，必须要使用
 * {@link org.springframework.web.bind.annotation.RequestBody} 注解才可以完成参数到Bean的
 * 映射，没有没有指定这个注解，则无法完成映射
 *
 * 我自定义的注解和参数解析器的作用就是当前端的请求是 POST 并且 'Content-Type: application/json' 的时候
 * 可以完成参数到Bean的映射
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBodyMapping {

}
