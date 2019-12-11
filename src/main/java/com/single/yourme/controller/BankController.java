package com.single.yourme.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.single.yourme.core.result.RestResult;
import com.single.yourme.entity.Bank;
import com.single.yourme.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 1999single
 * @since 2019-12-01
 */
@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private IBankService bankService;

    @RequestMapping("/fujian")
    public RestResult getFuJian() {

//        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
//        wrapper.eq("City", "福州市");
//        List<Bank> banks = bankService.list(wrapper);
        return RestResult.success(bankService.getFuJian());
    }

    @RequestMapping("/beijin")
    public RestResult getBeiJin() {

//        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
//        wrapper.eq("City", "福州市");
//        List<Bank> banks = bankService.list(wrapper);
        return RestResult.success(bankService.getBeiJin());
    }
}
