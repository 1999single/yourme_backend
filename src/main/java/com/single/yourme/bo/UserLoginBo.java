package com.single.yourme.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户登录参数
 * </p>
 *
 * @author 1999single
 * @since 2019-12-26
 */
@Data
public class UserLoginBo {

    @NotNull
    private String phoneNum;

    @NotNull
    private String password;
}
