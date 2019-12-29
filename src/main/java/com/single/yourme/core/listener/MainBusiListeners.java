package com.single.yourme.core.listener;

import com.single.yourme.core.utils.ApplicationContextUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * <p>
 * 启动监听
 * </p>
 *
 * @author 1999single
 * @since 2019-12-04
 */
public class MainBusiListeners implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContextUtils.setContext(event.getApplicationContext());
    }
}
