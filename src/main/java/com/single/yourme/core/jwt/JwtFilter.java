package com.single.yourme.core.jwt;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * shiro中的jwt拦截器
 * </p>
 *
 * @author 1999single
 * @since 2019-11-29
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 父类会在请求进入拦截器后调用该方法，返回true则继续，返回false则会调用onAccessDenied()。这里在不通过时，还调用了isPermissive()方法，我们后面解释。
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(this.isLoginRequest(request, response)) {
            return true;
        }
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch(IllegalStateException e){
            log.error("Not found any token");
        }catch (Exception e) {
            log.error("Error occurs when login", e);
        }
        return allowed || super.isPermissive(mappedValue);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String jwtToken = getAuthzHeader(request);
        log.debug("create token:{}", jwtToken);
        return new JwtToken(jwtToken);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.sendChallenge(request, response);
        return false;
    }

    /**
     *  如果Shiro Login认证成功，会进入该方法，等同于用户名密码登录成功，我们这里还判断了是否要刷新Token
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

        return true;
    }

    /**
     * 如果调用shiro的login认证失败，会回调这个方法，这里我们什么都不做，因为逻辑放到了onAccessDenied（）中。
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
        try {
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(e.getMessage());
        } catch (Exception ex) {

        }
        return false;
    }

}
