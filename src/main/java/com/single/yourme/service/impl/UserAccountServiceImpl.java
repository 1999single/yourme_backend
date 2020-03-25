package com.single.yourme.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.single.yourme.bo.UserLoginBo;
import com.single.yourme.bo.UserRegisterBo;
import com.single.yourme.core.config.LettuceRedisConfig;
import com.single.yourme.core.jwt.JwtUtil;
import com.single.yourme.core.result.Result;
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
    public Result login(UserLoginBo loginBo) {
        Result result = null;
        QueryWrapper<UserAccount> qw = new QueryWrapper<>();
        qw.eq("phone_num", loginBo.getPhoneNum());
        UserAccount userAccount = userAccountMapper.selectOne(qw);
        if (ObjectUtil.isNotNull(userAccount)) {
            RSA rsa = new RSA(privateKey, null);
            byte[] decrypt = rsa.decrypt(Base64.decode(loginBo.getPassword()), KeyType.PrivateKey);
            String originalPwd = StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
            String SHA256Pwd = SecureUtil.sha256(originalPwd);
            if (SHA256Pwd.equals(userAccount.getAccountPwd())) {
                JwtUtil.CustomClaim customClaim = new JwtUtil.CustomClaim(userAccount);
                result = Result.builder().success("登陆成功！").data(JwtUtil.createToken(customClaim)).build();
            } else {
                result = Result.builder().fail("账号/密码错误！").build();
            }
        } else {
            result = Result.builder().fail("账号/密码错误！").build();
        }
        return result;
    }

    @Override
    public Result register(UserRegisterBo registerBo) {
        Result result = null;

        QueryWrapper<UserAccount> qw = new QueryWrapper<>();
        qw.eq("phone_num", registerBo.getPhoneNum());
        UserAccount userAccount = userAccountMapper.selectOne(qw);
        if (ObjectUtil.isNotNull(userAccount)) {
            return Result.builder().fail("账号已存在！").build();
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
            result = Result.builder().success().build();
        } else {
            result = Result.builder().fail("验证码错误！").build();
        }
        return result;
    }

    @Override
    @Cacheable(cacheNames = LettuceRedisConfig.CacheName.REGISTER_CODE_10MIN, key = "'register_code'+'['+#phoneNum+']'")
    public String createPairCode(String id) {
        return null;
    }

    @Override
    @Cacheable(cacheNames = LettuceRedisConfig.CacheName.REGISTER_CODE_10MIN, key = "'pair_code'+'['+#pairCode+']'")
    public String getUserIdByPairCode(String pairCode) {
        return "3176016036";
    }
}
