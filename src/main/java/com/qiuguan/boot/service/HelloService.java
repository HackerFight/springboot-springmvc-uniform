package com.qiuguan.boot.service;

import com.qiuguan.boot.enums.ApiCodeEnum;
import com.qiuguan.boot.enums.ApiCommonCode;
import com.qiuguan.boot.ex.BusinessException;
import org.springframework.stereotype.Component;

/**
 * @author qiuguan
 * @date 2022/09/12 10:58:35  星期一
 */
@Component
public class HelloService {

    public String helloService(){
        System.out.println("hello Service .....");
        throw new BusinessException("HelloService 业务异常", ApiCodeEnum.ERROR);
    }
}
