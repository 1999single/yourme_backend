package com.single.yourme.service;

import com.single.yourme.entity.Bank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 1999single
 * @since 2019-12-01
 */
public interface IBankService extends IService<Bank> {

    public List<Bank> getFuJian();

    public List<Bank> getBeiJin();
}
