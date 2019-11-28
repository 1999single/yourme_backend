package com.single.yourme.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.single.yourme.model.Test;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface TestMapper extends BaseMapper<Test> {
}
