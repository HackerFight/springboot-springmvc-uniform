package com.qiuguan.boot.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author qiuguan
 */
@SuppressWarnings("all")
public class IntToEnumConvertFactory implements ConverterFactory<Integer, Enum> {

    @Override
    public <T extends Enum> Converter<Integer, T> getConverter(Class<T> targetType) {
        return new IntToEnumConvert(targetType);
    }

    private static class IntToEnumConvert<T extends Enum> implements Converter<Integer, Enum> {

        private Class<T> targetType;

        public IntToEnumConvert(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public Enum convert(Integer source) {
            if (null == source) return null;
            if (this.targetType.isAssignableFrom(DefaultEnumDescriptor.class)) {
                for (T enumConstant : targetType.getEnumConstants()) {
                    DefaultEnumDescriptor<Integer, T> descriptor = (DefaultEnumDescriptor)enumConstant;
                    return descriptor.match(source);
                }
            }
            return null;
        }
    }
}

