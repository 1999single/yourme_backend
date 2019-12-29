
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.single.yourme.YourMeApplication;
import com.single.yourme.core.message.MessageHttpUtils;
import com.single.yourme.core.message.SmsUtils;
import com.single.yourme.core.utils.RedisUtils;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author 1999single
 * @since 2019-12-24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {YourMeApplication.class})
public class Message {

    @Test
    public void sendMessage() {

            String host = "http://dingxin.market.alicloudapi.com";
            String path = "/dx/sendSms";
            String method = "POST";
            String appcode = "0fc637b930b14f4f913a07ecee8e1f92";
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("mobile", "17758717836");
            //querys.put("param", "code:1234");
            querys.put("tpl_id", "TP19080213");
            Map<String, String> bodys = new HashMap<String, String>();


            try {
                /**
                 * 重要提示如下:
                 * HttpUtils请从
                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
                 * 下载
                 *
                 * 相应的依赖请参照
                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
                 */
                HttpResponse response = MessageHttpUtils.doPost(host, path, method, headers, querys, bodys);
                System.out.println(response.toString());
                //获取response的body
                //System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Test
    public void send() {
        String str = "595082586";
//        KeyPair pair = SecureUtil.generateKeyPair("RSA");
//
//
//        System.out.println(Base64.encode(pair.getPrivate().getEncoded()));
//        System.out.println(Base64.encode(pair.getPublic().getEncoded()));

        String pri = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAK4ywwZkie9w93l5BJXZYraPII5nqHUUKy6Ayb8ahXwlAr5jWS/lrbpNLuzajd1ke2lJ9YNLi7YoOp0rJAbubxKAgC5UHzd+JFMatSTjXDXP8+KvjaWrALQ6KNw7k7UMZQ7/SPkWtkxdYc8zSFXBXdd5VExvknzn6mS/o7FwfamvAgMBAAECgYBTHxXAbykCgD0WUZDGkDxyMEmwqfXQKBeS0RFzhww6+M4eWvmryUTkqP/DNWaMRe49h3UHeXzpJrkRVgyS4iNNPs3NhlNtI/E80B4Yrr5/o0/M9lxgQzOKUIHhpwIW5esYIJ1WO2fTbK5O5RGYfnFtodkbeXWM9Tc/gHrLFB7nwQJBAOJtIoacpxZ0qr1EtI6Ppra4ZllP3s0b4I2IHb5pB/fbyC9Sm3HvsQyKW+g27SSJG2oFerDc2XcyGdA+JxD4bZsCQQDE81BFes7Scq4uuJojwPsC1uQWgjmbyI44zrNIaq1G0PBFFD0Wz0LNUt6agNOwM0TGn9osEbrttKbzX2u7lT99AkEAklus54o2zuMWwkS42TWts4o6Thy8jaQpYBLN9xh5GBOusDgbVmhy7wI4Kj/mxakCWWO1TMFUqCFwYhTqt6N9ZQJBAJmPySfTYE1wQ2WEZBK6ljyZhtASC47uqg2bBOjS3HYZ1S1XYWZWhhSHp686AVQiNV7VJowz6tcKgx3BacOh1BUCQQCuO+qJqpxEltioPhhJ+Q7xDwaTR3SvNWmcajLp81HUAL/IKiCwFnGe0KslfuZnROT8hz02Xtmt+lfDGAOCa6LE";
        String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuMsMGZInvcPd5eQSV2WK2jyCOZ6h1FCsugMm/GoV8JQK+Y1kv5a26TS7s2o3dZHtpSfWDS4u2KDqdKyQG7m8SgIAuVB83fiRTGrUk41w1z/Pir42lqwC0OijcO5O1DGUO/0j5FrZMXWHPM0hVwV3XeVRMb5J85+pkv6OxcH2prwIDAQAB";

        String k = "Nk40idoh9KRWnxsYQVvOHSlMr8XQ93ttxE/sv1RJNJ8BUYmR6wchx0vLqVfmLyOqIA2t4jGhvumT2Edn6ok7s1koQ5yvM+Jmk63dqwpXLVD+TW04Pg3pht+GMI2ksHUbL2uLpYIb0h2k+bzYHmJ1edfBHIgQsNgxDOM7DAH1+Y0=";

        RSA rsa = new RSA(pri, null);
        // byte[] encrypt = rsa.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);

        byte[] decrypt = rsa.decrypt(Base64.decode(k), KeyType.PublicKey);

        System.out.println(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
//        System.out.println(Base64.encode(encrypt));

//        String k = new String(HexUtil.encodeHex(encrypt));
//        String k = new String(HexUtil.encodeHex(encrypt));
//
//
//
//        System.out.println(StrUtil.str(HexUtil.decodeHex(k), CharsetUtil.CHARSET_UTF_8));
    }

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void redis() {

    }
}
