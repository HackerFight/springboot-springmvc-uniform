package com.qiuguan.boot.config;

import com.qiuguan.boot.convert.IntToEnumConvertFactory;
import com.qiuguan.boot.handler.UniformResponseHandler;
import com.qiuguan.boot.message.MyJsonHttpMessageConvert;
import com.qiuguan.boot.resolver.RequestBodyMappingHandlerMethodParamResolver;
import com.qiuguan.boot.resolver.RequestParamMappingAgreementResolver;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
 *
 * Springmvc文档中关于定制化webmvc, 有提到可以直接继承 {@link DelegatingWebMvcConfiguration} 
 * 这样就可以不用实现 {@link WebMvcConfigurer}
 * 同时也可以忽略 {@link EnableWebMvc} 注解
 * 
 * 但是使用时要注意：webmvc的自定配置类 {@link WebMvcAutoConfiguration}
 * 生效的前提条件是容器中没有 {@link WebMvcConfigurationSupport} bean, 但是定制化webmvc, 继承了 {@link DelegatingWebMvcConfiguration }
 * 同时也继承了 {@link WebMvcConfigurationSupport}, 这样spring的自动配置类 {@link WebMvcAutoConfiguration}
 * 就不会生效了，想要使用相关的功能就要重写方法.
 *
 * 但是在实际开发中，我们并不需要完全接管springmvc, 所以最好不要使用 {@link EnableWebMvc} 或者 实现 {@link DelegatingWebMvcConfiguration}
 *
 * 如果需要定制化springmvc, 可以继承或者实现如下接口
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter  在2.0版本中已废弃
 * @see WebMvcConfigurer 实现这个接口即可
 */
@Configuration
public class MvcConfig extends DelegatingWebMvcConfiguration {


    /**
     * 自定义返回值处理器，它就是对 {@link ResponseBody} 注解返回值的一个进一步处理，其内部
     * 还是交给 {@link ResponseBody} 注解对应的处理器 {@link RequestResponseBodyMethodProcessor}去处理
     * 
     * {@link #addReturnValueHandlers(List)}
     */
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


    /**
     * 添加参数解析器
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestParamMappingAgreementResolver());
        argumentResolvers.add(new RequestBodyMappingHandlerMethodParamResolver());
    }

    /**
     * 添加MessageConverter
     * 注意是实现这个方法，这样spring默认的MessageConverter也可以使用 {@link #configureMessageConverters(List)}
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MyJsonHttpMessageConvert());
    }
}
