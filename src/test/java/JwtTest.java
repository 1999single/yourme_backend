import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.single.yourme.YourMeApplication;
import com.single.yourme.core.jwt.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 1999single
 * @date: 2020/3/4 3:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {YourMeApplication.class})
public class JwtTest {

    @Test
    public void createJWT() {

        Map<String, Object> HEADER;


        HEADER = new HashMap<>();
        HEADER.put("alg", "HS256");
        HEADER.put("typ", "JWT");


        Algorithm algorithm = Algorithm.HMAC256("syy000103xjx991106together191008");
        Date tempDate = new Date();
        Date issuedDate = new Date(tempDate.getTime() - Duration.ofMinutes(3).toMillis());
        Date notBeforeDate = issuedDate;
        Date expireDate = new Date(issuedDate.getTime() + 60*60*1000L);
        String token = JWT.create().withHeader(HEADER)
                .withClaim("phoneNum", "17758717836")
                .withClaim("nickName", "臭猪猪")
                .withClaim("id", "3176016036")
                .withClaim("fereId", "孙艳艳")
                .withIssuer("yourme.top")
                .withSubject("yourme")
                .withAudience("user")
                .withIssuedAt(issuedDate)
                .withExpiresAt(expireDate)
                .withNotBefore(notBeforeDate)
                .sign(algorithm);
        System.out.println(new Date().getTime());
        try {
            // System.out.println(JwtUtil.parseToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ5b3VybWUiLCJhdWQiOiJ1c2VyIiwibmJmIjoxNTgzMjY1Njk3LCJuaWNrTmFtZSI6IuiHreeMqueMqiIsImlzcyI6InlvdXJtZS50b3AiLCJwaG9uZU51bSI6IjE3NzU4NzE3ODM2IiwiaWQiOiIzMTc2MDE2MDM2IiwiZXhwIjoxNTgzMjY1Njk4LCJpYXQiOjE1ODMyNjU2OTcsImZlcmVJZCI6IuWtmeiJs-iJsyJ9.FltmbLXLqnYWDReY3pPprdZzruVPnacgMPDFEj1eFis").isTokenExpired());;
        } catch (Exception e) {

        } finally {
            System.out.println(new Date().getTime());
        }

        System.out.println(token);
    }

    @Test
    public void password() {
        RSA rsa = new RSA(null, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuMsMGZInvcPd5eQSV2WK2jyCOZ6h1FCsugMm/GoV8JQK+Y1kv5a26TS7s2o3dZHtpSfWDS4u2KDqdKyQG7m8SgIAuVB83fiRTGrUk41w1z/Pir42lqwC0OijcO5O1DGUO/0j5FrZMXWHPM0hVwV3XeVRMb5J85+pkv6OxcH2prwIDAQAB");
        byte[] encrypt = rsa.encrypt(StrUtil.bytes("Xu595082586.", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        String secret = URLUtil.encode(Base64.encode(encrypt));
        System.out.println(secret);
        
    }
}
