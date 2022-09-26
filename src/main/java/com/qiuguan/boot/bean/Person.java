package com.qiuguan.boot.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author qiuguan
 * @date 2022/09/08 18:54:15  星期四
 */
@Setter
@Getter
public class Person {

    private Integer id;

    private String name;

    private String gender;

    private String idCard;

    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    public Person() {
    }

    public Person(Integer id, String name, String gender, String idCard, String phone, Date birthDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.idCard = idCard;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public static Person getDefaultBean(){
        return new Person(1, "秋官", "男", "150422199112095117", "18368116334", new Date());
    }
}
