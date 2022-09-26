package com.qiuguan.boot.convert;

/**
 * @author qiuguan
 * @date 2022/09/19 23:37:31  星期一
 */
public class Test {

    public static void main(String[] args) {

        Class<EventEnum> eventEnumClass = EventEnum.class;
        for (EventEnum enumConstant : eventEnumClass.getEnumConstants()) {
            System.out.println("enumConstant = " + enumConstant);
        }

        for (EventEnum enumConstant : eventEnumClass.getEnumConstants()) {
            System.out.println("enumConstant.ordinal() = " + enumConstant.ordinal());
        }

        EventEnum init = Enum.valueOf(EventEnum.class, "INIT");
        System.out.println("init = " + init);
    }
}
