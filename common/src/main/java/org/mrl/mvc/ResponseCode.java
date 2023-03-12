package org.mrl.mvc;

public enum ResponseCode {

    SUCCESS(200, "OK"),
    FAIL(500, "FAIL"),

    UNAUTHORIZED(401, "UNAUTHORIZED"),
    ACCESS_DENIED(403, "ACCESS DENIED");

    int code;
    String message;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.message = msg;
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

    public void setMessage(String msg) {
        this.message = msg;
    }
}
