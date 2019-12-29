package com.single.yourme.core.aop.aspect;

import com.single.yourme.core.aop.annotation.ValidParam;
import com.single.yourme.core.exception.ParamException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * AOP控制器参数验证
 * </p>
 *
 * @author 1999single
 * @since 2019-12-26
 */
@Aspect
@Component
public class ValidParamAspect {

    @Pointcut("@annotation(validParam)")
    public void annotationPointcut(ValidParam validParam){}

    @Before("annotationPointcut(validParam)")
    public void around(JoinPoint joinPoint, ValidParam validParam) throws Throwable {

        Signature signature = joinPoint.getSignature();
        // 方法名
        String methodName = signature.getName();
        // 类名
        String serviceName = signature.getDeclaringTypeName();
        // 参数名数组
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();
        // 构造参数组集合
        List<Object> argList = new ArrayList<>();
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                BindingResult errors = (BindingResult) arg;
                if (errors.hasErrors()) {
                    List<FieldError> errorList = errors.getFieldErrors();
                    for (FieldError error : errorList) {
                        System.out.println("field :" + error.getField() + "\t" + "msg :" + error.getDefaultMessage());
                    }
                    throw new ParamException("运行错误");
                }
            }
        }
    }

//    @AfterThrowing(throwing = "e", pointcut = "annotationPointcut(validParam)")
//    public void afterThrowing(Throwable e, ValidParam validParam) {
//        e.printStackTrace();
//    }
}
