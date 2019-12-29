package com.single.yourme.core.aop;

import com.single.yourme.core.exception.ParamException;
import com.single.yourme.core.result.RestResult;
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
@ControllerAdvice(basePackages="com.single.yourme.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public RestResult errorMsg(RuntimeException e){
        return RestResult.fail();
    }

    @ExceptionHandler(ParamException.class)
    @ResponseBody
    public RestResult aramException(ParamException e){
        return RestResult.fail().resetMessage("参数异常");
    }
}
