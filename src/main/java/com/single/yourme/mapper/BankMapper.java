package com.single.yourme.mapper;

import com.single.yourme.core.cache.MybatisRedisCache;
import com.single.yourme.entity.Bank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 1999single
 * @since 2019-12-01
 */
@Repository
@CacheNamespace//(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface BankMapper extends BaseMapper<Bank> {

    List<Bank> getFuJian();

    List<Bank> getBeiJin();
}
