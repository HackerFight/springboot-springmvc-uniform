package com.qiuguan.boot.bean;

import com.qiuguan.boot.custom.GenderChoice;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author qiuguan
 */
@Setter
@Getter
@ToString
public class User {

    @NotNull
    private String name;

    @GenderChoice(value = {"M", "F"}, message = "性别只能是M/F")
    private String male;
}
