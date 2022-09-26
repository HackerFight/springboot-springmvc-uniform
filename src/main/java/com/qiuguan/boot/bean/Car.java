package com.qiuguan.boot.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author qiuguan
 */
@Setter
@Getter
@ToString
public class Car {

    @NotNull
    private Integer id;

    @Length(min = 5, max = 32)
    @NotBlank
    private String name;
}
