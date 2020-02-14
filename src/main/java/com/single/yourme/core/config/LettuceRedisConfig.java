package com.single.yourme.core.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * LettuceRedis配置类
 * </p>
 *
 * @author 1999single
 * @since 2019-12-02
 */
@Slf4j
@Configuration
public class LettuceRedisConfig {

    public interface CacheName {
        String REDIS_CACHE_MANAGER_5MIN = "REDIS_CACHE_MANAGER_5MIN";
        String REDIS_CACHE_MANAGER_1HOUR = "REDIS_CACHE_MANAGER_1HOUR";
        String REGISTER_CODE_10MIN = "REGISTER_CODE_10MIN";
        String ID_TO_PAIRCODE_10MIN = "ID_TO_PAIRCODE_10MIN";
        String PAIRCODE_TO_ID_10MIN = "PAIRCODE_TO_ID_10MIN";
    }

    public enum CacheNamePair {

        REDIS_CACHE_MANAGER_5MIN("REDIS_CACHE_MANAGER_5MIN", Duration.ofMinutes(5)),
        REDIS_CACHE_MANAGER_1HOUR("REDIS_CACHE_MANAGER_1HOUR", Duration.ofHours(1)),
        REGISTER_CODE_10MIN("REGISTER_CODE_10MIN", Duration.ofMinutes(10)),
        ID_TO_PAIRCODE_10MIN("ID_TO_PAIRCODE_10MIN", Duration.ofDays(1)),
        PAIRCODE_TO_ID_10MIN("PAIRCODE_TO_ID_10MIN", Duration.ofDays(1));


        public String name;

        public Duration ttl;

        CacheNamePair(String name, Duration ttl) {
            this.name = name;
            this.ttl = ttl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Duration getTtl() {
            return ttl;
        }

        public void setTtl(Duration ttl) {
            this.ttl = ttl;
        }
    }

//    @Bean(value = "redisTemplate")
//    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory) {
//        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setConnectionFactory(connectionFactory);
//        return redisTemplate;
//    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        //使用fastjson序列化
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        log.info("加载RedisTemplate成功");
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    // 配置缓存管理器
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 60s缓存失效
                // .entryTtl(Duration.ofHours(1))
                .entryTtl(Duration.ofDays(1))
                // 设置key的序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value的序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 不缓存null值
                .disableCachingNullValues();
        Object[] objs = new Object[2];
        try {
            objs = setCacheNameSpaces(config);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("自定义Redis缓存管理器失败：添加自定义缓存空间失败。");
        }


        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .initialCacheNames((Set<String>) objs[0])// 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations((Map<String, RedisCacheConfiguration>) objs[1])
                .build();

        return redisCacheManager;
    }

    private Object[] setCacheNameSpaces(RedisCacheConfiguration config) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Object[] objs = new Object[2];
        Set<String> cacheNames =  new HashSet<>();

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();

        Class<?> clz = CacheNamePair.class;
        // 2.得到所有枚举常量
        Object[] objects = clz.getEnumConstants();
        Method getName = clz.getMethod("getName");
        Method getTtl = clz.getMethod("getTtl");
        for (Object obj : objects){
            String cacheName = (String) getName.invoke(obj);
            Duration ttl = (Duration) getTtl.invoke(obj);
            cacheNames.add(cacheName);
            configMap.put(cacheName, config.entryTtl(ttl));
        }
        objs[0] = cacheNames;
        objs[1] = configMap;
        return objs;
    }

}


