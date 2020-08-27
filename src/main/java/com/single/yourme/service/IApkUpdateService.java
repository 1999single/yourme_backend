package com.single.yourme.service;

import com.single.yourme.entity.ApkUpdate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 1999single
 * @since 2020-08-26
 */
public interface IApkUpdateService extends IService<ApkUpdate> {

    public ApkUpdate getLastedApkVersion();
}
