package com.single.yourme.service;

import com.single.yourme.bo.UserLoginBo;
import com.single.yourme.bo.UserRegisterBo;
import com.single.yourme.core.result.Result;
import com.single.yourme.entity.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 1999single
 * @since 2019-12-25
 */
public interface IUserAccountService extends IService<UserAccount> {

    Result login(UserLoginBo loginBo);

    Result register(UserRegisterBo registerBo);

    String createPairCode(String id);

    String getUserIdByPairCode(String pairCode);

}
