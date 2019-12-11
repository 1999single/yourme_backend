package com.single.yourme.service.impl;

import com.single.yourme.entity.Bank;
import com.single.yourme.mapper.BankMapper;
import com.single.yourme.service.IBankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 1999single
 * @since 2019-12-01
 */
@Service
public class BankServiceImpl extends ServiceImpl<BankMapper, Bank> implements IBankService {

    @Autowired
    private BankMapper bankMapper;

    @Override
    //@Cacheable(value = "local_ehcache")
    public List<Bank> getFuJian() {
        return bankMapper.getFuJian();
    }

    public List<Bank> getBeiJin() {
        return bankMapper.getBeiJin();
    }
}
