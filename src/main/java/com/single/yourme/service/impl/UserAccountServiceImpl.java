package com.single.yourme.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.single.yourme.bo.UserLoginBo;
import com.single.yourme.bo.UserRegisterBo;
import com.single.yourme.core.config.LettuceRedisConfig;
import com.single.yourme.core.jwt.JwtUtil;
import com.single.yourme.core.result.RestResult;
import com.single.yourme.entity.UserAccount;
import com.single.yourme.mapper.UserAccountMapper;
import com.single.yourme.service.ISmsService;
import com.single.yourme.service.IUserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 1999single
 * @since 2019-12-25
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

    @Value("${rsa.PrivateKey}")
    private String privateKey;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private ISmsService smsService;

    @Override
    public RestResult login(UserLoginBo loginBo) {
        RestResult result = null;
        QueryWrapper<UserAccount> qw = new QueryWrapper<>();
        qw.eq("phone_num", loginBo.getPhoneNum());
        UserAccount userAccount = userAccountMapper.selectOne(qw);
        if (ObjectUtil.isNotNull(userAccount)) {
            RSA rsa = new RSA(privateKey, null);
            byte[] decrypt = rsa.decrypt(Base64.decode(loginBo.getPassword()), KeyType.PrivateKey);
            String originalPwd = StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
            String SHA256Pwd = SecureUtil.sha256(originalPwd);
            if (SHA256Pwd.equals(userAccount.getAccountPwd())) {
                JwtUtil.CustomClaim customClaim = new JwtUtil.CustomClaim(userAccount.getPhoneNum(), userAccount.getNickname());
                result = RestResult.success(JwtUtil.createToken(customClaim)).resetMessage("登陆成功！");
            } else {
                result = RestResult.fail().resetMessage("账号/密码错误！");
            }
        } else {
            result = RestResult.fail().resetMessage("账号/密码错误！");
        }
        return result;
    }

    @Override
    public RestResult register(UserRegisterBo registerBo) {
        RestResult result = null;

        QueryWrapper<UserAccount> qw = new QueryWrapper<>();
        qw.eq("phone_num", registerBo.getPhoneNum());
        UserAccount userAccount = userAccountMapper.selectOne(qw);
        if (ObjectUtil.isNotNull(userAccount)) {
            return RestResult.fail().resetMessage("账号已存在！");
        }
        UserAccount account = new UserAccount();
        account.setPhoneNum(registerBo.getPhoneNum());
        account.setNickname(registerBo.getPhoneNum());
        String realCode = smsService.getRegisterCode(account.getPhoneNum());
        if (registerBo.getCode().equals(realCode)) {
            RSA rsa = new RSA(privateKey, null);
            byte[] decrypt = rsa.decrypt(Base64.decode(registerBo.getPassword()), KeyType.PrivateKey);
            String realPwd = StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
            account.setAccountPwd(SecureUtil.sha256(realPwd));
            userAccountMapper.insert(account);
            result = RestResult.success();
        } else {
            result = RestResult.fail().resetMessage("验证码错误！");
        }
        return result;
    }

}
