package com.single.yourme.core.result;

/**
 * api返回内容
 *
 * @author: 1999single
 * @date: 2020/3/4 1:34
 */

public final class Result<T> {

    private Integer code;

    private String message;

    private T data;

    private Result() {

    }

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultBuilder<T> builder() {
        return new ResultBuilder<T>();
    }

    public static class ResultBuilder<M> {

        private Integer code;

        private String message;

        private M data;

        public ResultBuilder<M> success() {
            this.code = ResultStatus.SUCCESS.getCode();
            this.message = ResultStatus.SUCCESS.getMsg();
            return this;
        }

        public ResultBuilder<M> success(String msg) {
            this.code = ResultStatus.SUCCESS.getCode();
            this.message = msg;
            return this;
        }

        public ResultBuilder<M> fail() {
            this.code = ResultStatus.FAIL.getCode();
            this.message = ResultStatus.FAIL.getMsg();
            return this;
        }

        public ResultBuilder<M> fail(String msg) {
            this.code = ResultStatus.FAIL.getCode();
            this.message = msg;
            return this;
        }

        public ResultBuilder<M> invalid() {
            this.code = ResultStatus.INVALID.getCode();
            this.message = ResultStatus.INVALID.getMsg();
            return this;
        }

        public ResultBuilder<M> invalid(String msg) {
            this.code = ResultStatus.INVALID.getCode();
            this.message = msg;
            return this;
        }

        public ResultBuilder<M> data(M data) {
            this.data = data;
            return this;
        }

        public Result<M> build() {
            return new Result<M>(code, message, data);
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
