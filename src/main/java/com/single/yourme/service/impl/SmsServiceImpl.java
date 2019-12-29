package com.single.yourme.service.impl;

import com.single.yourme.core.config.LettuceRedisConfig;
import com.single.yourme.core.message.SmsUtils;
import com.single.yourme.entity.Sms;
import com.single.yourme.mapper.SmsMapper;
import com.single.yourme.service.ISmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SmsServiceImpl extends ServiceImpl<SmsMapper, Sms> implements ISmsService {

    @Autowired
    private SmsMapper smsMapper;

    @Override
    @Cacheable(cacheNames = LettuceRedisConfig.CacheName.REGISTER_CODE_10MIN, key = "'register_code'+'['+#phoneNum+']'")
    public String sendRegisterSms(String phoneNum, String uuid) {
        String str = String.valueOf(System.currentTimeMillis());
        String code = str.substring(str.length() - 6);
        SmsUtils.sendSms(phoneNum, uuid, code);
        Sms sms = new Sms(uuid, phoneNum, code);
        smsMapper.insert(sms);
        return code;
    }

    @Override
    @Cacheable(cacheNames = LettuceRedisConfig.CacheName.REGISTER_CODE_10MIN, key = "'register_code'+'['+#phoneNum+']'")
    public String getRegisterCode(String phoneNum) {
        return null;
    }
}
