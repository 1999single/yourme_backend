package com.single.yourme.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.single.yourme.mapper.TestMapper;
import com.single.yourme.entity.Test;
import com.single.yourme.service.ITestService;
import org.springframework.stereotype.Service;

/**
 * @author 1999single
 * @date 2019-11-25 19:45
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {


}
