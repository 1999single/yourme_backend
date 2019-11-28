package com.single.yourme.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Jave Web Token
 *
 * @author 1999single
 * @date 2019-11-26 13:17
 */
public final class JwtUtil {

    private JwtUtil() { }

    private static final String TOKEN_SECRET = "syy000103xjx991106together191008";

    private static final String TOKEN_ISSUER = "yourme.top";

    private static final String TOKEN_SUBJECT = "yourme";

    private static final Long TOKEN_LIFETIME = 10 * 60 * 60 * 1000L;

    private static final Map<String, Object> HEADER;

    static {
        HEADER = new HashMap<>();
        HEADER.put("alg", "HS256");
        HEADER.put("typ", "JWT");
    }

    /**
     * 创建token
     * @return token
     */
    public static String createToken(CustomClaim claim) {

        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + TOKEN_LIFETIME);
        System.out.println(nowDate.getTime());
        System.out.println(expireDate.getTime());
        String token = JWT.create().withHeader(HEADER)
                .withClaim("loginName", claim.getLoginName())
                .withClaim("userName", claim.getUserName())
                .withIssuer(TOKEN_ISSUER)
                .withSubject(TOKEN_SUBJECT)
                .withAudience("APP1", "APP2")
                .withIssuedAt(nowDate)
                .withExpiresAt(expireDate)
                .sign(algorithm);
        return token;
    }

    public static JwtInfo parseToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return new JwtInfo(jwt);
    }

    public static class JwtInfo {

        private DecodedJWT jwt;

        private JwtInfo(DecodedJWT jwt) {
            this.jwt = jwt;
        }

        public Boolean isTokenExpired() {
            return jwt.getExpiresAt().before(new Date());
        }

        public Date getIssuedAt() {
            return jwt.getIssuedAt();
        }

        public Date getExpiresAt() {
            return jwt.getExpiresAt();
        }

        public String getAudience() {
            return jwt.getAudience().get(0);
        }

        public List<String> getAudiences() {
            return jwt.getAudience();
        }

        public String getSubject() {
            return jwt.getSubject();
        }

        public CustomClaim getCustomClaim() {
            String loginName = jwt.getClaim("loginName").asString();
            String userName = jwt.getClaim("userName").asString();
            return new CustomClaim(loginName, userName);
        }
    }

    @Data
    public static class CustomClaim {

        public CustomClaim() { }

        public CustomClaim(String loginName, String userName) {
            this.loginName = loginName;
            this.userName = userName;
        }

        private String loginName;

        private String userName;

    }

}
