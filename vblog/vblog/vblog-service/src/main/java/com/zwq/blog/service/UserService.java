package com.zwq.blog.service;

import com.zwq.blog.model.Menu;
import com.zwq.blog.model.RoleMenu;
import com.zwq.blog.model.User;
import com.zwq.blog.model.UserRole;

import java.util.List;

/**
 * 用户管理service
 * Created by DELL on 2018/9/13.
 */
public interface UserService {
    User getUserByUsername(String username);

    List<UserRole> getRolesByUserId(Long userId);

    List<RoleMenu> getMenuByRoles(Long roleId);

    List<Menu> getMenuByUserId(Long id);

    void updateUser(User user);

   List<User> listUser(String name);

    int deleteUserBatch(String ids);

    Boolean saveUser(User user);

    int updatePwd(String oldpassword, String password, User user);
}
