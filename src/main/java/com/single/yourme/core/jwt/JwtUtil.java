package com.single.yourme.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.single.yourme.entity.UserAccount;
import lombok.Data;

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
                .withClaim("phoneNum", claim.getPhoneNum())
                .withClaim("nickName", claim.getNickName())
                .withClaim("id", claim.getId())
                .withClaim("fereId", claim.getFereId())
                .withIssuer(TOKEN_ISSUER)
                .withSubject(TOKEN_SUBJECT)
                .withAudience("user")
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
            String phoneNum = jwt.getClaim("phoneNum").asString();
            String nickName = jwt.getClaim("nickName").asString();
            String id = jwt.getClaim("id").asString();
            String fereId = jwt.getClaim("fereId").asString();
            return new CustomClaim(phoneNum, nickName, id, fereId);
        }
    }

    @Data
    public static class CustomClaim {

        public CustomClaim() { }

        public CustomClaim(String phoneNum, String nickName, String id, String fereId) {
            this.phoneNum = phoneNum;
            this.nickName = nickName;
            this.id = id;
            this.fereId = fereId;
        }

        public CustomClaim(UserAccount account) {
            this.phoneNum = account.getPhoneNum();
            this.nickName = account.getNickname();
            this.id = account.getId();
            this.fereId = account.getFereId();
        }

        private String nickName;

        private String phoneNum;

        private String id;

        private String fereId;
    }

}
