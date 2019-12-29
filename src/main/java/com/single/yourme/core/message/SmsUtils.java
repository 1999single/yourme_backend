package com.single.yourme.core.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sms.entity.request.QuerySingleSmsStatusRequest;
import sms.entity.request.QueryTemplateStatusRequest;
import sms.entity.request.SendSingleSmsRequest;
import sms.entity.request.SubmitTemplateRequest;
import sms.entity.response.QuerySingleSmsStatusResponse;
import sms.entity.response.QueryTemplateStatusResponse;
import sms.entity.response.SendSingleSmsResponse;
import sms.entity.response.SubmitTemplateResponse;
import sms.main.YZXClient;

/**
 * @author: dxkj
 * @description:
 * @date: 2017/12/7
 * 短信API的DEMO，直接通过执行main可以直接调用
 * 工程依赖于两个jar包
 * 1：JAVA_YZX_SDK.jar
 * 2：gson.jar
 */
@Component
public class SmsUtils {
    /**
     * 注册云智信的手机号
     */
    private static final String account = "17758717836";
    /**
     * 交易秘钥，请联系商务获取
     */
    private static final String tradeKey = "70fee1ed175640fcbdc2a174dba377ed";
    /**
     * 请求地址
     */
    private static final String url = "http://api.yunzhixin.com:11140";

    public static SendSingleSmsResponse sendSms(String phoneNum, String uuid, String code) {
        //初始化商户信息
        YZXClient.init(account, tradeKey, url);
        //组装请求对象
        SendSingleSmsRequest request = new SendSingleSmsRequest();
        //必填：发送手机号
        request.setMobile(phoneNum);
        //必填：自定义订单编号，保证唯一性
        request.setOrderId(uuid);
        //必填：短信模板编号，联系客服申请
        request.setTplId("TP19122533");
        //非必填：当模板内含有变量时必填,例如模板为（您的验证码#code#）
        request.setParams("code:" + code);
        //获取响应实体类
        SendSingleSmsResponse sendSingleSmsResponse = YZXClient.getResponse(request);
        System.out.println(sendSingleSmsResponse.toString());
        return sendSingleSmsResponse;
    }

    public static QuerySingleSmsStatusResponse querySms() {
        //初始化商户信息
        YZXClient.init(account, tradeKey, url);
        //组装请求对象
        QuerySingleSmsStatusRequest request = new QuerySingleSmsStatusRequest();
        //必填：需要查询的订单编号
        request.setOrderId("asdfghjkl1234");
        //获取响应实体类
        QuerySingleSmsStatusResponse querySingleSmsStatusResponse = YZXClient.getResponse(request);
        return querySingleSmsStatusResponse;
    }

    public static SubmitTemplateResponse submitTemplate() {
        //初始化商户信息
        YZXClient.init(account, tradeKey, url);
        //组装请求对象
        SubmitTemplateRequest request = new SubmitTemplateRequest();
        //必填：需要传入的短信签名
        request.setSignature("云智信");
        //必填：短信类型，1：验证码类，2：通知类，3：营销类
        request.setSmsType("1");
        //必填：模板内容，若有变量，用##分割，变量名自定义且勿重复
        request.setTemplateContent("您好，您的验证码是#code#");
        //获取响应实体类
        SubmitTemplateResponse submitTemplateResponse = YZXClient.getResponse(request);
        return submitTemplateResponse;
    }

    public static QueryTemplateStatusResponse queryTemplate() {
        //初始化商户信息
        YZXClient.init(account, tradeKey, url);
        //组装请求对象
        QueryTemplateStatusRequest request = new QueryTemplateStatusRequest();
        //必填：提交模板接口获得的编号
        request.setTplId("TP1200001");
        //获取响应实体类
        QueryTemplateStatusResponse queryTemplateStatusResponse = YZXClient.getResponse(request);
        return queryTemplateStatusResponse;
    }
}
