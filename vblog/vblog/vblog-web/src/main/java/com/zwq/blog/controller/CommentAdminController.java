package com.zwq.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwq.blog.model.Comment;
import com.zwq.blog.service.CommentService;

import com.zwq.blog.utils.CodeMsg;
import com.zwq.blog.utils.DataTable;
import com.zwq.blog.utils.Result;
import com.zwq.blog.vo.CommentConditionVo;
import com.zwq.blog.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zwq
 * @date 2018/12/12.
 */
@RestController
@RequestMapping("/comment")
public class CommentAdminController {
    @Autowired
    CommentService commentService;

    @GetMapping("/listComment")
    public DataTable<List<CommentVo>> listComment(String title, String content, Long state, Integer pageNum, Integer limit){
        PageHelper.startPage(pageNum,limit);
        CommentConditionVo commentConditionVo = new CommentConditionVo();
        commentConditionVo.setTitle(title);
        commentConditionVo.setContent(content);
        commentConditionVo.setState(state);
        List<CommentVo> list = commentService.listComment(commentConditionVo);
        PageInfo<CommentVo> pageInfo = new PageInfo(list,limit);
        DataTable<List<CommentVo>> result = new DataTable<List<CommentVo>>((int) pageInfo.getTotal(), pageInfo.getList());
        return result;
    }

    @PostMapping("/modifyComment")
    public Result<String> modifyComment(@RequestBody Comment comment){
        Boolean flag =commentService.modifyComment(comment);
        if(flag){
            return Result.success("保存成功！");
        }
        return Result.error(CodeMsg.SAVE_BLOG_ERROR);
    }

    @PostMapping("/delComment")
    public Result delComment(String ids){
        int l = commentService.delComment(ids);
        if (l >0){
            return Result.success("删除成功");
        }else {
            return Result.error(CodeMsg.DELETE_FAIL);
        }
    }
}
