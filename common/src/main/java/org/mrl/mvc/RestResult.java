package org.mrl.mvc;

import java.io.Serializable;

public class RestResult<T> implements Serializable {

    private int code;

    private String message;

    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> RestResultBuilder<T> builder() {
        return new RestResultBuilder<T>();
    }

    public static final class RestResultBuilder<T> {

        private int code;

        private String message;

        private T data;

        public RestResultBuilder<T> withCode(int code) {
            this.code = code;
            return this;
        }

        public RestResultBuilder<T> withMessage(String message) {
            this.message = message;
            return this;
        }

        public RestResultBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        public RestResult<T> build() {
            RestResult<T> restResult = new RestResult<>();
            restResult.setCode(code);
            restResult.setData(data);
            restResult.setMessage(message);
            return restResult;
        }
    }
}
