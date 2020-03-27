package com.zwq.blog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwq.blog.model.User;
import com.zwq.blog.service.UserService;
import com.zwq.blog.utils.CodeMsg;
import com.zwq.blog.utils.DataTable;
import com.zwq.blog.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zwq
 * @date 2018/12/5.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @GetMapping("/listUser")
    public DataTable<List<User>> listUser(String name, Integer pageNum, Integer limit){
        PageHelper.startPage(pageNum,limit);
        List list = userService.listUser(name);
        PageInfo<User> pageInfo = new PageInfo(list,limit);
        DataTable<List<User>> result = new DataTable<List<User>>((int) pageInfo.getTotal(), pageInfo.getList());
        return result;
    }

    /**
     * 新增用户
     * @param blog
     * @return
     */
    @PostMapping("/saveUser")
    public Result<String> saveBlog(@RequestBody User user){

        Boolean flag =userService.saveUser(user);
        if(flag){
            return Result.success("保存成功！");
        }
        return Result.error(CodeMsg.SAVE_BLOG_ERROR);
    }

    @PostMapping("/modifyUser")
    public Result<String> modifyUser(@RequestBody User user){
        userService.updateUser(user);
        return Result.success("保存成功！");
    }

    @PostMapping("/delUser")
    public Result delUser(String ids){
        int l = userService.deleteUserBatch(ids);
        if (l >0){
            return Result.success("删除成功");
        }else {
            return Result.error(CodeMsg.DELETE_FAIL);
        }
    }

    @PostMapping("/updatePwd")
    public Result updatePwd(@RequestParam("oldpassword") String oldpassword,@RequestParam("password") String password){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("currUser");
        int i = userService.updatePwd(oldpassword,password,user);
        if (i >0){
            return Result.success("修改成功");
        }else {
            return Result.error(CodeMsg.DELETE_FAIL);
        }
    }
}
