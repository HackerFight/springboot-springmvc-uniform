package com.qiuguan.boot.handler;

import com.qiuguan.boot.result.ApiResult;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
/**
 * @author qiuguan
 * @date 2022/09/08 21:22:02  星期四
 */
public class UniformResponseHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler handler;

    public UniformResponseHandler(HandlerMethodReturnValueHandler handler) {
        this.handler = handler;
    }

    /**
     * @param methodParameter  返回值类型
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return this.handler.supportsReturnType(methodParameter);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        if (returnValue instanceof ApiResult<?>) {
            this.handler.handleReturnValue(returnValue, returnType, modelAndViewContainer, nativeWebRequest);
            return;
        }

        this.handler.handleReturnValue(ApiResult.ok(returnValue), returnType, modelAndViewContainer, nativeWebRequest);
    }
}
