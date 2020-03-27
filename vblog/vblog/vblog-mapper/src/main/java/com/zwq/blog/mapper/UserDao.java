package com.zwq.blog.mapper;

import com.zwq.blog.model.Menu;
import com.zwq.blog.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zwq
 * @date 2018/12/5.
 */
@Mapper
public interface UserDao {
    @Select("select * from t_user where username = #{username}")
    public User getUserByUsername(@Param("username") String username);

    @Select("select * from t_user where name like #{name}")
    public List<User> listUser(@Param("name") String name);

    @Insert("insert  into t_user (username,name,password,avatar,nickname,prifile,sign,salt) values (#{username},#{name},#{password},#{avatar},#{nickname},#{prifile},#{sign},#{salt})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public int saveUser(User user);

    @Update("update t_user set username=#{username},name = #{name},nickname=#{nickname},sign=#{sign},prifile=#{prifile} where id = #{id}")
    public void modifyUser(User user);

    @Update("update t_user set password = #{newPassword} where id = #{id}")
    public int modifyUserPassword(@Param("newPassword") String newPassword, @Param("id") Long id);

    @Delete("<script>" +
            "delete from t_user where id in " +
            "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    public int delUser(@Param(value = "ids") List<Long> ids);

    @Select("SELECT DISTINCT m.*  FROM  t_menu m,  t_role_menu rm,  t_user_role ur  WHERE m.id = rm.menu_id  AND rm.role_id = ur.role_id and ur.id=#{userId} ")
    List<Menu> getMenuByUserId(Long userId);


}
