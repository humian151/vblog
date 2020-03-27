package com.zwq.blog.service.impl;

import com.zwq.blog.mapper.CommentDao;
import com.zwq.blog.model.Comment;
import com.zwq.blog.service.CommentService;
import com.zwq.blog.vo.CommentConditionVo;
import com.zwq.blog.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zwq
 * @date 2018/12/12.
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentDao commentDao;

    @Override
    public List<CommentVo> listComment(CommentConditionVo commentConditionVo) {
        return commentDao.queryComment(commentConditionVo);
    }

    @Transactional
    @Override
    public Boolean modifyComment(Comment comment) {
        return commentDao.modifyComment(comment) >0 ;
    }

    @Transactional
    @Override
    public int delComment(String ids) {
        String[] tmp = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String str : tmp){
            list.add(Long.parseLong(str));
        }
        return commentDao.delComment(list);
    }
}
