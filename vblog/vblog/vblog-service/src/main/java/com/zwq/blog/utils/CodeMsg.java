package com.zwq.blog.utils;

/**
 * 返回结果代码类
 * @author zwq
 * @date 2018/12/5.
 */
public final class CodeMsg {
    private int code;
    private String msg;

    //公共异常
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg REQUEST_VERIFY = new CodeMsg(500102, "验证码错误");
    public static CodeMsg ACCESS_LIMIT_REACHED= new CodeMsg(500104, "访问太频繁！");

    //登录模块
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
    public static CodeMsg USER_EMPTY = new CodeMsg(500212, "用户名不能为空");
    public static CodeMsg USER_ERROR = new CodeMsg(500213, "用户格式错误");
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500214, "用户不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
    public static CodeMsg PASSWORDTYPE_ERROR = new CodeMsg(500216, "密码格式错误");
    public static CodeMsg LOGIN_FAIL = new CodeMsg(500217, "登录失败");
    public static CodeMsg DELETE_FAIL = new CodeMsg(500218, "删除失败");
    public static CodeMsg PASSWORD_NOT_EQUAL = new CodeMsg(500219, "旧密码输入错误");


    //博客模块
    public static CodeMsg SAVE_BLOG_ERROR =  new CodeMsg(500301, "保存博文异常");

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }



}
