package com.single.yourme.core.utils;

import org.springframework.context.ApplicationContext;

/**
 * <p>
 * ApplicaitonContext工具类
 * </p>
 *
 * @author 1999single
 * @since 2019-12-04
 */
public class ApplicationContextUtil {

    private static ApplicationContext context;

    public static void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> t) {
        return context.getBean(t);
    }
}
