package com.itheima.exception;
//自定义异常处理器，用于封装异常信息，对异常进行分类,不继承也可以，但要在之后每个方法后都要写Throws SystemExceptionAdvice
public class SystemException extends RuntimeException{
    private Integer code;//异常编号

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
