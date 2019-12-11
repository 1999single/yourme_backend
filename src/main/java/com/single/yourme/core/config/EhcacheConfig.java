package com.single.yourme.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

/**
 * <p>
 * ehcache配置类
 * </p>
 *
 * @author 1999single
 * @since 2019-12-03
 */
@Slf4j
//@Configuration
//@EnableCaching
public class EhcacheConfig {

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

    @Bean
    @Primary
    public EhCacheCacheManager eCacheCacheManager(EhCacheManagerFactoryBean bean) {
        log.info("自定义EhCacheCacheManager加载完成");
        return new EhCacheCacheManager(bean.getObject());
    }
}
