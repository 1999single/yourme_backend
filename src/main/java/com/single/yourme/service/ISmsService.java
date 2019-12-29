package com.single.yourme.service;

import com.single.yourme.entity.Sms;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 1999single
 * @since 2019-12-25
 */
public interface ISmsService extends IService<Sms> {

    String sendRegisterSms(String phoneNum, String uuid);

    String getRegisterCode(String phoneNum);
}
