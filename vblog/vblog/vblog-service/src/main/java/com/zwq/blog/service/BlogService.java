package com.zwq.blog.service;

import com.zwq.blog.model.Blog;
import com.zwq.blog.vo.BlogConditionVo;
import com.zwq.blog.vo.BlogVo;

import java.util.List;

/**
 * @author zwq
 * @date 2018/12/10.
 */
public interface BlogService {
    Boolean saveBlog(Blog blog);

    List<BlogVo> listBlog(BlogConditionVo blogConditionVo);

    Blog queryBlog(Long blogId);

    Boolean modifyBlog(Blog blog);

    int delBlog(String ids);
}
