package com.single.yourme.core.aop;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 全局异常捕获器
 * </p>
 *
 * @author 1999single
 * @since 2019-12-01
 */
//@ControllerAdvice(basePackages="com.single.yourme.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String, Object> errorMsg(RuntimeException e){
        Map<String, Object> errorMsgResult = new HashMap<>();
        errorMsgResult.put("code", 500);
        errorMsgResult.put("msg", "抛出异常");
        return errorMsgResult;
    }
}
