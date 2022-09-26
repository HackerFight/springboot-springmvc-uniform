package com.qiuguan.boot.result;


import com.qiuguan.boot.enums.ApiCodeEnum;
import com.qiuguan.boot.enums.ApiCommonCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author qiuguan
 * @date 2022/09/08 19:02:22  星期四
 */

@Setter
@Getter
public class ApiResult<T> {

    private int code;

    private String message;

    private T data;

    private boolean success;

    public ApiResult(ApiCommonCode apiCommonCode, T data, boolean success) {
        this.code = apiCommonCode.getCode();
        this.message = apiCommonCode.getMessage();
        this.data = data;
        this.success = success;
    }

    public static <T> ApiResult<T> ok(T t){
        return new ApiResult<>(ApiCodeEnum.SUCCESS, t, true);
    }

    public static <T> ApiResult<T> fail(ApiCommonCode apiCommonCode){
        return new ApiResult<>(apiCommonCode, null, false);
    }
}
