package com.single.yourme.core.result;

/**
 * @author 1999single
 */
public class RestResult {

    private int code;

    private String message;

    private Object data;

    private RestResult() {

    }

    private static RestResult createResult(ResultStatus status) {
        RestResult result = new RestResult();
        result.setResultStatus(status);
        result.data = null;
        return result;
    }

    private static RestResult createResult(ResultStatus status, Object data) {
        RestResult result = new RestResult();
        result.setResultStatus(status);
        result.data = data;
        return result;
    }

    private void setResultStatus(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    /**
     *
     * @return 返回操作成功
     */
    public static RestResult success() {
        return RestResult.createResult(ResultStatus.SUCCESS);
    }

    /**
     *
     * @param data 响应body
     * @return 返回操作成功
     */
    public static RestResult success(Object data) {
        return RestResult.createResult(ResultStatus.SUCCESS, data);
    }


    /**
     *
     * @return 返回服务器错误
     */
    public static RestResult fail() {
        return RestResult.createResult(ResultStatus.FAIL);
    }

    /**
     *
     * @param data 响应body
     * @return 返回服务器错误
     */
    public static RestResult fail(Object data) {
        return RestResult.createResult(ResultStatus.FAIL, data);
    }

    /**
     *
     * @param status 自定义错误状态
     * @return
     */
    public static RestResult fail(ResultStatus status) {
        return RestResult.createResult(status);
    }

    /**
     *
     * @param status 自定义错误状态
     * @param data 响应body
     * @return
     */
    public static RestResult fail(ResultStatus status, Object data) {
        return RestResult.createResult(status, data);
    }

    public RestResult resetMessage(String message) {
        if(this.code == 0) {
            this.message = message;
        } else {
            this.code = ResultStatus.CUSTOM_FAIL.getCode();
            this.message = message;
        }
        return this;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
