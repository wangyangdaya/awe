package com.awe.core.throwable;

import org.springframework.util.StringUtils;

/**
 * <p>这个异常类用来简化处理异常</p>
 * <p>遇到无需明确分类处理的异常，可以统一用此异常处理，封装{@link ErrorCode}枚举作为参数，用于区分异常类别</p>
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/9.</p>
 */
public class GeneralException extends RuntimeException {
    private static final long serialVersionUID = 3761977150343281224L;
    private ErrorCode exceptionType = ErrorCode.OTHER;

    public GeneralException(ErrorCode exceptionType, Throwable e) {
        super(e);
        this.exceptionType = exceptionType;
    }

    public GeneralException(ErrorCode exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }

    public GeneralException(ErrorCode exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public GeneralException(ErrorCode exceptionType, String message, Throwable e) {
        super(message, e);
        this.exceptionType = exceptionType;
    }

    public GeneralException(String message, Throwable e) {
        super(message, e);
    }

    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(Throwable e) {
        super(e);
    }

    public GeneralException() {
        super();
        this.exceptionType = ErrorCode.OTHER;
    }

    public ErrorCode getExceptionType() {
        return this.exceptionType;
    }

    @Override
    public String getMessage() {
        return StringUtils.isEmpty(super.getMessage()) ? this.exceptionType.toString() : this.exceptionType.toString() + ':' + super.getMessage();
    }

    public String getSimpleMessage() {
        return StringUtils.isEmpty(super.getMessage()) ? this.exceptionType.toString() : super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return StringUtils.isEmpty(super.getLocalizedMessage()) ? this.exceptionType.toString() : this.exceptionType.toString() + ':' + super.getLocalizedMessage();
    }

    @Override
    public void printStackTrace() {
        synchronized (System.err) {
            System.err.println(this.exceptionType.toString());
        }
        super.printStackTrace();
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
}
