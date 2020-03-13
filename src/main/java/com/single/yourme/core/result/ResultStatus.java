package com.single.yourme.core.result;

public enum ResultStatus {

    // 操作成功
    SUCCESS(0, "success"),
    // 操作失败
    FAIL(1, "fail"),
    // 操作不合法
    INVALID(2, "invalid");

    private int code;

    private String msg;

    ResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMSg(String message) {
        this.msg = message;
    }
}
