package com.zwq.blog.utils;

/**
 * 返回结果类
 * @author zwq
 * @date 2018/12/5.
 */
public final class Result<T> {
    /** 返回结果代码 */
    private int code;
    /** 返回结果附带的信息 */
    private String msg;
    /** 返回结果附带的对象 */
    private T data;

    /**
     * 当成功时调用该方法
     * @param data 返回成功对象
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 当失败的时候调用该方法
     * @param codeMsg 失败的代码和附带信息
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

    /**
     * 失败的时候的构造器，私有化
     * @param codeMsg
     */
    private Result(CodeMsg codeMsg) {
        if (codeMsg ==null){
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    /**
     * 成功的时候的构造器，私有化
     * 默认code为0
     * @param data
     */
    private Result(T data) {
        this.code = 200;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
