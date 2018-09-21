package com.mg.weixin.rest;

/**
 * @Auther: fujian
 * @Date: 2018/9/20 16:03
 * @Description:用于封装各种执行结果
 */
public class Result {
    private int code;
    private boolean success;
    private String message;
    private Object data;
    public Result(){}
    public Result(int code,boolean success){
        this.code=code;
        this.success=success;
    }
    public Result(int code,boolean success,String message){
        this.code=code;
        this.success=success;
        this.message=message;
    }
    public Result(int code,boolean success,String message,Object data){
        this.code=code;
        this.success=success;
        this.message=message;
        this.data=data;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
