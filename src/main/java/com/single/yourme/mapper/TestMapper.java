package com.single.yourme.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.single.yourme.entity.Test;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper extends BaseMapper<Test> {
}
