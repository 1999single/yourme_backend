package com.single.yourme.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.single.yourme.bo.UserLoginBo;
import com.single.yourme.bo.UserRegisterBo;
import com.single.yourme.core.aop.annotation.ValidParam;
import com.single.yourme.core.result.Result;
import com.single.yourme.core.utils.RedisUtils;
import com.single.yourme.entity.UserAccount;
import com.single.yourme.service.ISmsService;
import com.single.yourme.service.IUserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;

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
    public Result login(@Valid UserLoginBo userLoginBo, BindingResult errors) {
        return userAccountService.login(userLoginBo);
    }

    @GetMapping("/register-code")
    public Result sendRegisterCode(String phoneNum) {
        try {
            String code = smsService.getRegisterCode(phoneNum);
            smsService.sendRegisterSms(phoneNum, IdUtil.simpleUUID(), code);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Result.builder().success().build();
    }

    @PostMapping("/register")
    @ValidParam
    public Result register(@Valid UserRegisterBo registerBo, BindingResult errors) {
        return userAccountService.register(registerBo);
    }

    @GetMapping("/pair-code")
    public Result createPairCode(String id) {
        String pairCode = (String) redisUtils.get("ID_TO_PAIRCODE_10MIN::id[" + id + "]");
        if (StrUtil.isEmpty(pairCode)) {
            boolean f;
            do {
                pairCode = String.valueOf(RandomUtil.randomInt(10000000, 99999999));
                f = redisUtils.get("PAIRCODE_TO_ID_10MIN::pair_code[" + pairCode + "]") != null;
            }while (f);
            redisUtils.set("ID_TO_PAIRCODE_10MIN::id[" + id + "]", pairCode, Duration.ofDays(1).getSeconds());
            redisUtils.set("PAIRCODE_TO_ID_10MIN::pair_code[" + pairCode + "]", id, Duration.ofDays(1).getSeconds());
        }
        return Result.builder().success().data(pairCode).build();
    }

    @PutMapping("/pair")
    public Result pair(String id, String pairCode) {
        String mate = (String) redisUtils.get("PAIRCODE_TO_ID_10MIN::pair_code[" + pairCode + "]");
        String oPairCode = (String) redisUtils.get("ID_TO_PAIRCODE_10MIN::id[" + id + "]");
        if (StrUtil.isEmpty(mate)) {
            return Result.builder().fail("配对码失效！").build();
        }
        if (StrUtil.equals(pairCode, oPairCode)) {
            return Result.builder().fail("不可以和自己生孩子！").build();
        }
        UserAccount account0 = new UserAccount();
        account0.setId(id);
        account0.setFereId(mate);
        userAccountService.updateById(account0);
        UserAccount account1 = new UserAccount();
        account1.setId(mate);
        account1.setFereId(id);
        userAccountService.updateById(account1);
        redisUtils.del("PAIRCODE_TO_ID_10MIN::pair_code[" + pairCode + "]", "ID_TO_PAIRCODE_10MIN::id[" + mate + "]", "PAIRCODE_TO_ID_10MIN::pair_code[" + oPairCode + "]", "ID_TO_PAIRCODE_10MIN::id[" + id + "]");
        return Result.builder().success().build();
    }

}
