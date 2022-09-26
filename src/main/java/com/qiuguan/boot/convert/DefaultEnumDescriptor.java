package com.qiuguan.boot.convert;

/**
 * @author qiuguan
 */
public interface DefaultEnumDescriptor<T, E> {

    /**
     *  get code
     * @return
     */
    T getCode();

    /**
     * get desc
     * @return
     */
    Object getDesc();

    /**
     * match
     * @param t
     * @return
     */
    default E match(T t) {
        return null;
    }
}
