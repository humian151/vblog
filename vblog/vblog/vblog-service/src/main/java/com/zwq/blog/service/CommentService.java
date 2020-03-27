package com.zwq.blog.service;

import com.zwq.blog.model.Comment;
import com.zwq.blog.vo.CommentConditionVo;
import com.zwq.blog.vo.CommentVo;

import java.util.List;

/**
 * @author zwq
 * @date 2018/12/12.
 */
public interface CommentService {
    List<CommentVo> listComment(CommentConditionVo commentConditionVo);

    Boolean modifyComment(Comment comment);

    int delComment(String ids);
}
