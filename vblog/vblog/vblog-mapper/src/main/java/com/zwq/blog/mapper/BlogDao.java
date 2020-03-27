package com.zwq.blog.mapper;

import com.zwq.blog.mapper.provider.BlogDaoProvider;
import com.zwq.blog.model.Blog;
import com.zwq.blog.vo.BlogConditionVo;
import com.zwq.blog.vo.BlogVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zwq
 * @date 2018/12/10.
 */
@Mapper
public interface BlogDao {
    @Insert("insert  into t_blog (chickhit,content,keyword,releasedate,replyhit,summary,title,typeid) values (#{chickhit},#{content},#{keyword},#{releasedate},#{replyhit},#{summary},#{title},#{typeid})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public int saveBlog(Blog blog);

    @SelectProvider(type = BlogDaoProvider.class,method = "listBlog")
    List<BlogVo> listBlog(BlogConditionVo blogConditionVo);

    @Select("select * from t_blog where id = #{blogId}")
    Blog queryBlog(Long blogId);

    @UpdateProvider(type = BlogDaoProvider.class,method = "modifyBlog")
    public void modifyBlog(Blog blog);

   // @Delete("delete from t_blog where id in (#{ids})")
    @Delete("<script>" +
            "delete from t_blog where id in " +
            "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    public int delBlog(@Param(value = "ids") List<Long> ids);
}
