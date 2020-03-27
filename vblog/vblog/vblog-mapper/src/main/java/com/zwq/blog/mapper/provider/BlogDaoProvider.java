package com.zwq.blog.mapper.provider;

import com.zwq.blog.model.Blog;
import com.zwq.blog.vo.BlogConditionVo;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * 博文动态sql封装类
 * @author zwq
 * @date 2018/12/11.
 */
public class BlogDaoProvider {
    public String listBlog(BlogConditionVo blogConditionVo){
        String sql = new SQL(){
            {
                SELECT("t.id,t.title,t.releasedate,(select typename from t_blogtype b where b.id =t.typeid ) typename,t.content,t.summary,t.keyword,t.chickhit,t.replyhit,t.typeid,t.author");
                FROM("t_blog t");
                if(blogConditionVo.getTypeid()!=null){
                    WHERE(" t.typeid like concat('%',#{typeid},'%')");
                }
                if(!StringUtils.isEmpty(blogConditionVo.getTitle())){
                    WHERE(" t.title like concat('%',#{title},'%')");
                }
            }
        }.toString();

            sql += " order by t.id desc ";
        return sql;
    }

    public String modifyBlog(Blog blog){
        String sql = new SQL(){
            {
                UPDATE("t_blog");
                if(!StringUtils.isEmpty(blog.getTitle())){
                    SET("title = #{title}");
                }
                if(!StringUtils.isEmpty(blog.getSummary())){
                    SET("summary = #{summary}");
                }
                if(!StringUtils.isEmpty(blog.getKeyword())){
                    SET("keyword = #{keyword}");
                }
                if(!StringUtils.isEmpty(blog.getContent())){
                    SET("content = #{content}");
                }
                if(!StringUtils.isEmpty(blog.getTypeid())){
                    SET("typeid = #{typeid}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
        return sql;
    }


}
