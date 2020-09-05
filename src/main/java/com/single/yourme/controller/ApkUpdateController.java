package com.single.yourme.controller;


import cn.hutool.core.util.IdUtil;
import com.single.yourme.core.result.Result;
import com.single.yourme.entity.ApkUpdate;
import com.single.yourme.service.IApkUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 1999single
 * @since 2020-08-26
 */
@RestController
@RequestMapping("/apk-update")
public class ApkUpdateController {

    @Autowired
    private IApkUpdateService apkUpdateService;

    @GetMapping("/version")
    public Result<ApkUpdate> getLastedApkVersion() {
        ApkUpdate apkUpdate = apkUpdateService.getLastedApkVersion();
        return Result.<ApkUpdate>builder().success().data(apkUpdate).build();
    }
}
