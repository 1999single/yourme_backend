package com.single.yourme.core.aop;

import com.single.yourme.core.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Result errorMsg(RuntimeException e){
        return Result.builder().fail("小Me正在赶来的路上:)").build();
    }

}
