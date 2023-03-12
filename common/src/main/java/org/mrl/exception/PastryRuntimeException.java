package org.mrl.exception;

public class PastryRuntimeException extends RuntimeException {

    private static final String ERROR_MESSAGE_FORMAT = "errCode: %d, errMsg: %s";

    private int errCode;

    public PastryRuntimeException(int errCode) {
        super();
        this.errCode = errCode;
    }

    public PastryRuntimeException(int errCode, String errMsg) {
        super(String.format(ERROR_MESSAGE_FORMAT, errCode, errMsg));
        this.errCode = errCode;
    }

    public PastryRuntimeException(int errCode, Throwable causeThrowable) {
        super(causeThrowable);
        this.errCode = errCode;
    }

    public PastryRuntimeException(int errCode, String errMsg, Throwable causeThrowable) {
        super(String.format(ERROR_MESSAGE_FORMAT, errCode, errMsg), causeThrowable);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

}
