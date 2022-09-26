package com.qiuguan.boot.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author qiuguan
 * @date 2022/09/14 23:31:21  星期三
 */
@Setter
@Getter
@ToString
public class Student extends BaseBean {

    /**
     * @see NotBlank :它要作用到String类型上
     */
    @NotNull(message = "studentNo must be not null")
    private Integer studentNo;

    @Length(max = 5)
    private String name;

    private String address;

//    @Email
//    private String email;

    @Range(min = 16, max = 100)
    private int age;

    /**
     * {@url https://developer.aliyun.com/article/786719}
     *  只能为true
     */
    @AssertTrue
    private boolean high;

    /**
     * @see Past 得是过去的时间
     * @see JsonFormat 响应格式
     * @see DateTimeFormat 将请求的String类型的日期映射成Date类型，或者通过 @InitBinder 注解 {@link com.qiuguan.boot.controller.BaseController}
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past
    private Date birthDate;
}
