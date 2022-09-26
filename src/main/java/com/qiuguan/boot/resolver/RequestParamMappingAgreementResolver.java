package com.qiuguan.boot.resolver;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.ValidationAnnotationUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 * @author qiuguan
 */
public class RequestParamMappingAgreementResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestParamMapping.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        boolean simpleProperty = BeanUtils.isSimpleProperty(parameter.getParameterType());
        if (simpleProperty) {
            //单个参数映射
            return handleSimpleProperty(parameter, webRequest, binderFactory);
        }

        //bean映射
        return handleBeanMapping(parameter, webRequest, binderFactory);
    }

    @SuppressWarnings("all")
    private Object handleSimpleProperty(MethodParameter parameter, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestParamMapping parameterAnnotation = parameter.getParameterAnnotation(RequestParamMapping.class);
        String name = parameterAnnotation.name();
        if ("".equals(name)) {
            name = parameter.getParameterName();
        }

        Object arg = null;
        String[] parameterValues = webRequest.getParameterValues(name);
        if (parameterValues != null) {
            arg = (parameterValues.length == 1 ? parameterValues[0] : parameterValues);
        }

        if (binderFactory != null) {

            WebDataBinder binder = binderFactory.createBinder(webRequest, null, name);
            try {
                //类型转换
                arg = binder.convertIfNecessary(arg, parameter.getParameterType(), parameter);
            } catch (ConversionNotSupportedException ex) {
                throw new MethodArgumentConversionNotSupportedException(arg, ex.getRequiredType(), name, parameter, ex.getCause());
            } catch (TypeMismatchException ex) {
                throw new MethodArgumentTypeMismatchException(arg, ex.getRequiredType(), name, parameter, ex.getCause());
            }

            if (arg == null && parameterAnnotation.required()) {
                throw new ServletRequestBindingException("Missing argument '" + name +
                        "' for method parameter of type " + parameter.getNestedParameterType().getSimpleName());
            }

        }

        /**
         * 单个参数校验是通过代理对象在调用目标方法之前完成的
         * @see org.springframework.validation.beanvalidation.MethodValidationPostProcessor
         */
        return arg;
    }

    private Object handleBeanMapping(MethodParameter parameter, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        MethodParameter nestedParameter = parameter.nestedIfOptional();
        Class<?> clazz = nestedParameter.getNestedParameterType();

        //返回一个可用的构造器，如果有多个则抛出异常
        Constructor<?> ctor = BeanUtils.getResolvableConstructor(clazz);
        /**
         * 简化下，要存在默认的构造器
         * @see org.springframework.web.method.annotation.ModelAttributeMethodProcessor#createAttribute 
         */
        if (ctor.getParameterCount() != 0) {
            throw new RuntimeException("not found default constructor");
        }

        Object object = BeanUtils.instantiateClass(ctor);
        String name = ModelFactory.getNameForParameter(parameter);

        if (binderFactory != null) {
            WebDataBinder binder = binderFactory.createBinder(webRequest, object, name);
            bindParam(webRequest, binder);
            validate(binder, nestedParameter);
            if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(parameter)) {
                throw new BindException(binder.getBindingResult());
            }
        }

        return object;
    }

    protected boolean isBindExceptionRequired(MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getExecutable().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
        return !hasBindingResult;
    }

    private void validate(WebDataBinder binder, MethodParameter nestedParameter) {
        for (Annotation ann : nestedParameter.getParameterAnnotations()) {
            Object[] validationHints = ValidationAnnotationUtils.determineValidationHints(ann);
            if (validationHints != null) {
                binder.validate(validationHints);
                break;
            }
        }
    }

    private void bindParam(NativeWebRequest request, WebDataBinder binder) {
        ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
        Assert.state(servletRequest != null, "No ServletRequest");
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        servletBinder.bind(servletRequest);
    }
}
