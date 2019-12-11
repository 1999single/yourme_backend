package com.single.yourme.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 1999single
 * @since 2019-12-01
 */
@Data
@TableName("bank")
public class Bank implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 开户行名称
     */
    @TableField("Bank_Name")
    private String bankName;

    /**
     * 银联号
     */
    @TableField("UnionPay_Number")
    private Long unionpayNumber;

    @TableField("Bank_Category_Number")
    private String bankCategoryNumber;

    /**
     * 市
     */
    @TableField("City")
    private String city;

    /**
     * 省
     */
    @TableField("Province")
    private String province;


}
