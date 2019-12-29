package com.single.yourme.core.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * AOP验证控制器参数
 * </p>
 *
 * @author 1999single
 * @since 2019-12-26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidParam {
    String value() default "";
}
