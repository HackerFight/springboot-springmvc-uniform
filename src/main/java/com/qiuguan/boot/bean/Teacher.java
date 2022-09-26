package com.qiuguan.boot.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author qiuguan
 * @date 2022/09/19 23:39:20  星期一
 */
@Data
public class Teacher {

    private Integer id;

    private String name;

    private Double salary;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
