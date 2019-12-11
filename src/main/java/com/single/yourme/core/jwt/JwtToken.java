package com.single.yourme.core.jwt;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * <p>
 * token包装器
 * </p>
 *
 * @author 1999single
 * @since 2019-11-29
 */
@Data
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken() { }

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
