package com.single.yourme.core.jwt;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.single.yourme.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * <p>
 * shiro自定义realm，验证jwt
 * </p>
 *
 * @author 1999single
 * @since 2019-11-29
 */
@Slf4j
public class JwtRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        try {
            JwtUtil.parseToken(token);
        } catch (Exception ice) {
            throw new AuthenticationException(JSON.toJSONString(Result.builder().invalid("身份登录过期，请重新登录？！").build()));
        }
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
