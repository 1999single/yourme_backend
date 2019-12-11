package com.single.yourme.core.result;

public enum ResultStatus {

    //操作成功
    SUCCESS(0, "success"),
    //自定义错误内容
    CUSTOM_FAIL(1, ""),
    //未登录
    NOT_LOGIN(2, "当前为游客状态，请先登录"),
    //服务器错误
    FAIL(3, "服务器被你帅崩了"),
    //余额不足
    INSUFFICIENT_FUNDS(4, "余额不足"),
    //未注册
    NOT_REGISTER(5, "未注册"),
    //接口参数被篡改
    REQUEST_DATA_EXCEPTION(-1, "对不起，你真的没有我帅");

    private int code;

    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
