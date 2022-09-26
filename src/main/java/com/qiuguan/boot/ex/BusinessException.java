package com.qiuguan.boot.ex;

import com.qiuguan.boot.enums.ApiCommonCode;

/**
 * @author qiuguan
 * @date 2022/09/09 19:42:26  星期五
 */
public class BusinessException extends RuntimeException {

    private final ApiCommonCode apiCommonCode;

    public BusinessException(String message, ApiCommonCode apiCommonCode) {
        super(message);
        this.apiCommonCode = apiCommonCode;
    }

    public BusinessException(ApiCommonCode apiCommonCode) {
        super(apiCommonCode.getMessage());
        this.apiCommonCode = apiCommonCode;
    }

    public BusinessException(String message, Throwable cause, ApiCommonCode apiCommonCode) {
        super(message, cause);
        this.apiCommonCode = apiCommonCode;
    }

    public BusinessException(Throwable cause, ApiCommonCode apiCommonCode) {
        super(cause);
        this.apiCommonCode = apiCommonCode;
    }

    public ApiCommonCode getApiCommonCode() {
        return apiCommonCode;
    }
}
