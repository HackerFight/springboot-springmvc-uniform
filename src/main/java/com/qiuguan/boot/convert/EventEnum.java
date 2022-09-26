package com.qiuguan.boot.convert;

/**
 * @author qiuguan
 * @date 2022/09/18 17:41:49  星期日
 */
public enum EventEnum implements DefaultEnumDescriptor<Integer, EventEnum> {

    /**
     * INIT
     */
    INIT(0, "INIT"),

    START(1, "START"),

    PROCESSING(2, "PROCESSING"),

    FAIL(-1, "FAIL"),

    SUCCESS(3, "SUCCESS");

    private final int code;

    private final String desc;

    EventEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public EventEnum match(Integer code) {
        for (EventEnum value : EventEnum.values()) {
            if (value.code == code) {
                return value;
            }
        }

        return null;
    }



}
