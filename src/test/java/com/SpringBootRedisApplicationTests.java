package com;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.single.yourme.YourMeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.WebSocketClient;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 1999single
 * @since 2019-12-02
 */
@SpringBootTest(classes = YourMeApplication.class)
@RunWith(SpringRunner.class)
public class SpringBootRedisApplicationTests {

    @Test
    public void seleniumTest() {

    }

}
