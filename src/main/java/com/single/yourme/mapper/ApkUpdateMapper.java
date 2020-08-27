package com.single.yourme.mapper;

import com.single.yourme.entity.ApkUpdate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 1999single
 * @since 2020-08-26
 */
public interface ApkUpdateMapper extends BaseMapper<ApkUpdate> {

    ApkUpdate getLastedApkVersion();
}
