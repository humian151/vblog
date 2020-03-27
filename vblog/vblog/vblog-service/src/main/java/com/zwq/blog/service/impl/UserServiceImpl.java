package com.zwq.blog.service.impl;


import com.zwq.blog.mapper.UserDao;
import com.zwq.blog.model.Menu;
import com.zwq.blog.model.RoleMenu;
import com.zwq.blog.model.User;
import com.zwq.blog.model.UserRole;
import com.zwq.blog.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zwq
 * @date 2018/12/5.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDao userDao;

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public List<UserRole> getRolesByUserId(Long userId) {
        return null;
    }

    @Override
    public List<RoleMenu> getMenuByRoles(Long roleId) {
        return null;
    }

    @Override
    public List<Menu> getMenuByUserId(Long userId) {
        return userDao.getMenuByUserId(userId);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userDao.modifyUser(user);
    }

    @Override
    public List<User> listUser(String name) {
        name = "%"+name+"%";
        return userDao.listUser(name);
    }

    @Transactional
    @Override
    public int deleteUserBatch(String ids) {
        String[] tmp = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String str : tmp){
            list.add(Long.parseLong(str));
        }
       return userDao.delUser(list);

    }

    @Override
    public Boolean saveUser(User user) {
        //默认密码1234
        Md5Hash md5Hash = new Md5Hash("1234"+"1qjjfei23@#jfSSjSS");
        String p1 = md5Hash.toString(); //第一次加密
        //生成随机的盐
        String salt = UUID.randomUUID().toString().replaceAll("-","");
        Md5Hash md5Has2h = new Md5Hash(md5Hash.toString(),salt);
        user.setSalt(salt);
        user.setPassword(md5Has2h.toString());
        user.setAvatar("/image/face.jpg");
        return userDao.saveUser(user) >0;
    }

    @Override
    public int updatePwd(String oldpassword, String password, User user) {
        //校验密码是否一致
        oldpassword = new Md5Hash(oldpassword,user.getSalt()).toString();
        if(!oldpassword.equals(user.getPassword())){
            throw new RuntimeException("两次输入的密码不一致！");
        }
        password =  new Md5Hash(password,user.getSalt()).toString();
        return userDao.modifyUserPassword(password,user.getId());

    }
}
