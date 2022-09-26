package com.qiuguan.boot.enums;

/**
 * @author qiuguan
 * @date 2022/09/08 19:03:29  星期四
 */
public enum ApiCodeEnum implements ApiCommonCode {

    SUCCESS(200, "success"),

    ERROR(500, "system error"),

    PARAM_ILLEGAL(201, "param illegal"),

    PARAM_MISS(202, "param miss");

    private final int code;

    private final String message;

    ApiCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
