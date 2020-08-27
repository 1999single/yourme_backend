package com.single.yourme.service.impl;

import com.single.yourme.entity.ApkUpdate;
import com.single.yourme.mapper.ApkUpdateMapper;
import com.single.yourme.service.IApkUpdateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 1999single
 * @since 2020-08-26
 */
@Service
public class ApkUpdateServiceImpl extends ServiceImpl<ApkUpdateMapper, ApkUpdate> implements IApkUpdateService {

    @Autowired
    private ApkUpdateMapper apkUpdateMapper;

    @Override
    public ApkUpdate getLastedApkVersion() {
        return apkUpdateMapper.getLastedApkVersion();
    }
}
