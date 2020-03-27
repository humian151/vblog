package com.zwq.blog.controller;

import com.zwq.blog.utils.CodeMsg;
import com.zwq.blog.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @PostMapping("/subLogin")
    public Result subLogin(@RequestParam(name = "username") String username,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "rememberMe",required = false) String rememberMe){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            if("on".equals(rememberMe)){
                token.setRememberMe(true);
            }else {
                token.setRememberMe(false);
            }
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.error(CodeMsg.LOGIN_FAIL);
        }
        return Result.success("登录成功！");
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    public void logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }
}
