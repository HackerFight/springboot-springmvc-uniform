package com.qiuguan.boot.resolver;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author qiuguan
 * @date 2022/09/18 17:46:19  星期日
 *
 * 完成和 {@link org.springframework.web.bind.annotation.RequestParam} 一样的功能
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParamMapping {

    @AliasFor("value")
    String name() default "";

    @AliasFor("name")
    String value() default "";

    boolean required() default true;
}
