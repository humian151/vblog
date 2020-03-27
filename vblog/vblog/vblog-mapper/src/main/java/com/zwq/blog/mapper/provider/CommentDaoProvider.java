package com.zwq.blog.mapper.provider;

import com.zwq.blog.vo.CommentConditionVo;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author zwq
 * @date 2018/12/12.
 */
public class CommentDaoProvider {

    public String queryComment(CommentConditionVo commentConditionVo){
        String sql = new SQL(){
            {
                SELECT("t.id,t.userip,b.title,t.blogid,t.content,t.commentdate,t.state");
                FROM("t_comment t");
                LEFT_OUTER_JOIN("t_blog b on t.blogid = b.id ");
                if(!StringUtils.isEmpty(commentConditionVo.getContent())){
                    WHERE(" t.content like concat('%',#{content},'%')");
                }
                if(!StringUtils.isEmpty(commentConditionVo.getTitle())){
                    WHERE(" b.title like concat('%',#{title},'%')");
                }
                if(!StringUtils.isEmpty(commentConditionVo.getState())){
                    WHERE("t.state = #{state}");
                }
            }
        }.toString();

        sql += " order by t.commentdate desc ";
        return sql;
    }
}
