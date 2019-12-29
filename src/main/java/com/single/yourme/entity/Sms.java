package com.single.yourme.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

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
public class Sms implements Serializable {

    private static final long serialVersionUID = 1L;

    public Sms() {}

    public Sms(String id, String phoneNum, String authCode) {
        this.id = id;
        this.phoneNum = phoneNum;
        this.authCode = authCode;
        this.sendTime = new Date();
    }

    private String id;

    private String phoneNum;

    private String authCode;

    private String ip;

    private Date sendTime;


}
