package com.zwq.blog.conf;

import com.zwq.blog.model.Menu;
import com.zwq.blog.model.RoleMenu;
import com.zwq.blog.model.User;
import com.zwq.blog.model.UserRole;
import com.zwq.blog.service.UserService;
import com.zwq.blog.vo.MenuVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义realm
 * js中md5加密后这里进行第二次md5加密，所以数据库的密码是经过两次md5加密的
 * @author zwq
 * @date 2018/12/5.
 */
@Service
public class CustomRealm  extends AuthorizingRealm{
    @Autowired
    UserService userService;

    public CustomRealm() {
        super.setName("customRealm");
    }

    /**
     * 角色权限配置
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        User user = userService.getUserByUsername(userName);
        List<UserRole> userRoles = userService.getRolesByUserId(user.getId());
        Set<String> menus = new HashSet<String>();
        Set<String> roles = new HashSet<String>();
        for(UserRole userRole : userRoles){
            roles.add(userRole.getRoleId()+"");
            List<RoleMenu> menuList = userService.getMenuByRoles(userRole.getRoleId());
            for(RoleMenu menu:menuList){
                menus.add(menu.getMenuId()+"");
            }
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(menus);

        return simpleAuthorizationInfo;
    }

    /**
     * 登录配置
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String userName = upToken.getUsername();

        // Null username is invalid
        if (userName == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        User user = userService.getUserByUsername(userName);
        if(user !=null){
            List<MenuVo> menu = loadMenu(user.getId());

            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("currUser",user);
            session.setAttribute("menus",menu);
        }
        SimpleAuthenticationInfo info =  new SimpleAuthenticationInfo(userName, user.getPassword().toCharArray(), "customRealm");
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));//加盐
        return info;
    }
    private List<MenuVo> loadMenu(Long userId){
        List<Menu> menus = userService.getMenuByUserId(userId);
        List<MenuVo> list = new ArrayList<>();
        for(Menu menu : menus){
            if(menu.getParentId()==0){
                //一级菜单
                MenuVo parentMenu = new MenuVo();
                parentMenu.setTitle(menu.getMenuName());
                parentMenu.setIcon(menu.getIcon());
                parentMenu.setSpread(false);
                parentMenu.setHref(menu.getHref());
                List<MenuVo> childList = new ArrayList<>();
                for(Menu child : menus){
                    if(child.getParentId() == menu.getId()){
                        MenuVo childMenu = new MenuVo();
                        childMenu.setTitle(child.getMenuName());
                        childMenu.setIcon(child.getIcon());
                        childMenu.setSpread(false);
                        childMenu.setHref(child.getHref());
                        childList.add(childMenu);
                    }
                }
                parentMenu.setChildren(childList);
                list.add(parentMenu);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("1234"+"1qjjfei23@#jfSSjSS");

        System.out.println(md5Hash.toString());//8562e6676b93e233dfad344ea1bc3e33
        Md5Hash md5Has2h = new Md5Hash(md5Hash.toString(),"!QAZ@WSX#EDC");
        System.out.println(md5Has2h.toString());//3d91615d1db44863c5dd41c11195c43b


    }
}
