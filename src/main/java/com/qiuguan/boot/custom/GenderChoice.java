package com.qiuguan.boot.custom;

/**
 * @author qiuguan
 */

import com.qiuguan.boot.bean.User;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 参考其他注解，比如 {@link javax.validation.constraints.NotNull }
 * @author qiuguan
 * @see User
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(GenderChoice.List.class)
@Constraint(validatedBy = {GenderChoiceValidator.class})
@Documented
public @interface GenderChoice {

    String message() default "value not in enum values.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return date must in this value array
     */
    String[] value();


    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        GenderChoice[] value();
    }
}
