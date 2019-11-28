package com.single.yourme.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 1999single
 * @date 2019-11-25 19:38
 */
@Data
@TableName("test")
public class Test {

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("name")
    private String name;
}
