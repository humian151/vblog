package com.zwq.blog.mapper;

import com.zwq.blog.model.BlogType;
import com.zwq.blog.vo.BlogTypeFrontVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zwq
 * @date 2018/12/10.
 */
@Mapper
public interface BlogTypeDao {
    @Select("select * from t_blogtype order by orderno asc")
    public List<BlogType> queryAllBlogType();

    @Select("select * from t_blogtype where typename like '%'||#{typename}||'%' order by orderno asc")
    List<BlogType> queryBlogType(String typename);

    @Insert("insert  into t_blogtype (typename,orderno) values (#{typename},#{orderno})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public int saveBlogType(BlogType blogType);

    @Update("update t_blogtype set typename = #{typename},orderno=#{orderno} where id = #{id}")
    public int modifyBlogType(BlogType blogType);

    @Delete("<script>" +
            "delete from t_blogtype where id in " +
            "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    public int delBlogType(@Param(value = "ids") List<Long> ids);

    @Select("SELECT bt.typename,COUNT(b.id) count  FROM t_blog b LEFT JOIN  t_blogtype bt ON b.typeid = bt.id GROUP BY bt.typeName ORDER BY bt.orderNo")
    List<BlogTypeFrontVo> queryBlogTypeMenu();
}
