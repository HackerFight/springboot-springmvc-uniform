package com.qiuguan.boot.resolver;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.Conventions;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.ValidationAnnotationUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author qiuguan
 * @date 2022/09/18 17:41:09  星期日
 *
 * 我们知道，如果前端传入使用POST请求传入的是'Content-Type: application/json' 类型的数据，
 * 那么 Controller类需要使用 {@link RequestBody} 注解
 * 去标注，否则无法完成映射，那么我现在不使用 {@link RequestBody} 注解，而使用自定义的 {@link  RequestBodyMapping}
 * 注解完成映射
 */
public class RequestBodyMappingHandlerMethodParamResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBodyMapping.class);
    }

    /**
     *
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        assert nativeRequest != null;
        ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(nativeRequest);

        MediaType contentType;
        try {
            contentType = inputMessage.getHeaders().getContentType();
        } catch (InvalidMediaTypeException e) {
            throw new HttpMediaTypeNotSupportedException(e.getMessage());
        }

        //Controller类
        Class<?> containingClass = parameter.getContainingClass();
        //参数类型
        Type targetType = parameter.getNestedGenericParameterType();
        Class<?> targetClass = (targetType instanceof Class ? (Class<?>) targetType : null);
        if (targetClass == null) {
            ResolvableType resolvableType = ResolvableType.forMethodParameter(parameter);
            targetClass = resolvableType.resolve();
        }

        assert targetClass != null;

        //String jsonBody = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
        //System.out.println("jsonBody = " + jsonBody);

        //TODO:核心逻辑
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        JavaType javaType = objectMapper.constructType(GenericTypeResolver.resolveType(targetType, containingClass));

        //body映射成对象
        Object arg = objectMapper.readValue(inputMessage.getBody(), javaType);
        //参数名
        String name = Conventions.getVariableNameForParameter(parameter);

        //参数校验
        if (binderFactory != null) {
            WebDataBinder binder = binderFactory.createBinder(webRequest, arg, name);
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
                throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
            }
        }

        return arg;
    }


    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation ann : annotations) {
            Object[] validationHints = ValidationAnnotationUtils.determineValidationHints(ann);
            if (validationHints != null) {
                binder.validate(validationHints);
                break;
            }
        }
    }

    /**
     * 方法参数上是否有 {@link org.springframework.validation.BindingResult}
     * @param binder
     * @param parameter
     * @return
     */
    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getExecutable().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
        return !hasBindingResult;
    }

}
