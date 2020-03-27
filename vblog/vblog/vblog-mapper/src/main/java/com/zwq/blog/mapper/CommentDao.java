package com.zwq.blog.mapper;

import com.zwq.blog.mapper.provider.CommentDaoProvider;
import com.zwq.blog.model.Comment;
import com.zwq.blog.vo.CommentConditionVo;
import com.zwq.blog.vo.CommentVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zwq
 * @date 2018/12/12.
 */
@Mapper
public interface CommentDao {
    @SelectProvider(type = CommentDaoProvider.class,method = "queryComment")
    List<CommentVo> queryComment(CommentConditionVo commentConditionVo);

    @Update("update t_comment set state = #{state} where id = #{id}")
    int modifyComment(Comment comment);

    @Delete("<script>" +
            "delete from t_comment where id in " +
            "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int delComment(@Param(value = "ids") List<Long> ids);


}
