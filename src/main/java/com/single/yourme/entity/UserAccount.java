package com.single.yourme.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 1999single
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("nickname")
    private String nickname;

    @TableField("phone_num")
    private String phoneNum;

    @TableField("account_pwd")
    private String accountPwd;

    @TableField("email")
    private String email;

    @TableField("status")
    private Integer status;

    @TableField("fere_id")
    private String fereId;

}
