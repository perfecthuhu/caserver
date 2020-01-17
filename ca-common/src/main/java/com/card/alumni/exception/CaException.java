package com.card.alumni.exception;

/**
 * @author liumingyu
 * @date 2019-11-19 10:50 PM
 */
public class CaException extends RuntimeException {
    private static final long serialVersionUID = 8333750150545190863L;

    private int code = -1;

    public CaException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CaException(String message) {
        super(message);
        this.code = ResultCodeInterface.FAIL_CODE;
    }

    public CaException(ResultCodeInterface resultCodeInterface) {
        super(resultCodeInterface.getMsg());
        this.code = resultCodeInterface.getCode();
    }

    public CaException(ResultCodeInterface resultCodeInterface, String message) {
        super(message);
        this.code = resultCodeInterface.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getName());
        sb.append(": (").append(code).append(")");

        String message = getLocalizedMessage();
        if (message != null) {
            sb.append(message);
        }

        return sb.toString();
    }
}
