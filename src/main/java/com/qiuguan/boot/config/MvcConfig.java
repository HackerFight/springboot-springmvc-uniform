package com.qiuguan.boot.config;

import com.qiuguan.boot.convert.IntToEnumConvertFactory;
import com.qiuguan.boot.handler.UniformResponseHandler;
import com.qiuguan.boot.message.MyJsonHttpMessageConvert;
import com.qiuguan.boot.resolver.RequestBodyMappingHandlerMethodParamResolver;
import com.qiuguan.boot.resolver.RequestParamMappingAgreementResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author qiuguan
 * @date 2022/09/09 09:25:14  星期五
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    @Bean
    public UniformResponseHandler uniformResponseHandler(RequestMappingHandlerAdapter adapter){
        List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
        assert returnValueHandlers != null;
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(returnValueHandlers);
        for (HandlerMethodReturnValueHandler returnValueHandler : returnValueHandlers) {
            //处理@ResponseBody注解的处理器
            if (returnValueHandler instanceof RequestResponseBodyMethodProcessor) {
                UniformResponseHandler h = new UniformResponseHandler(returnValueHandler);
                int indexOf = returnValueHandlers.indexOf(returnValueHandler);
                handlers.set(indexOf, h);
                adapter.setReturnValueHandlers(handlers);
                return h;
            }
        }

        return null;
    }


    /**
     * 添加类型解析器
     * @param registry
     */
    @Override
    protected void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, Date>() {
            @Override
            public Date convert(String s) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = format.parse(s);
                } catch (ParseException e) {
                    format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        date = format.parse(s);
                    } catch (ParseException ignored) {
                    }
                }

                System.out.println("String ---> Date 解析成功");
                return date;
            }
        });

        registry.addConverterFactory(new IntToEnumConvertFactory());

    }


    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestParamMappingAgreementResolver());
        argumentResolvers.add(new RequestBodyMappingHandlerMethodParamResolver());
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MyJsonHttpMessageConvert());
    }
}
