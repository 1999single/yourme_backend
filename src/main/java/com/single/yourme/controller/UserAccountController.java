package com.single.yourme.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.single.yourme.bo.UserLoginBo;
import com.single.yourme.bo.UserRegisterBo;
import com.single.yourme.core.aop.annotation.ValidParam;
import com.single.yourme.core.message.SmsUtils;
import com.single.yourme.core.result.RestResult;
import com.single.yourme.core.utils.RedisUtils;
import com.single.yourme.entity.UserAccount;
import com.single.yourme.service.ISmsService;
import com.single.yourme.service.IUserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 有关用户账号的接口，登录、注册、找回密码等
 * </p>
 *
 * @author 1999single
 * @since 2019-12-24
 */
@Slf4j
@RestController
@RequestMapping("/user/account")
public class UserAccountController {

    @Autowired
    private ISmsService smsService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/login")
    @ValidParam
    public RestResult login(@Valid UserLoginBo userLoginBo, BindingResult errors) {
        return userAccountService.login(userLoginBo);
    }

    @GetMapping("/register-code")
    public RestResult sendRegisterCode(String phoneNum) {
        try {
            smsService.sendRegisterSms(phoneNum, IdUtil.simpleUUID());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return RestResult.success();
    }

    @PostMapping("/register")
    @ValidParam
    public RestResult register(@Valid UserRegisterBo registerBo, BindingResult errors) {
        return userAccountService.register(registerBo);
    }

}
