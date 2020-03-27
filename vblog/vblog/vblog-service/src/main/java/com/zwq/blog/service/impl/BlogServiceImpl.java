package com.zwq.blog.service.impl;

import com.zwq.blog.mapper.BlogDao;
import com.zwq.blog.model.Blog;
import com.zwq.blog.service.BlogService;
import com.zwq.blog.vo.BlogConditionVo;
import com.zwq.blog.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zwq
 * @date 2018/12/10.
 */
@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    BlogDao blogDao;

    @Transactional
    @Override
    public Boolean saveBlog(Blog blog) {
        blogDao.saveBlog(blog);
        return true;
    }

    @Override
    public List<BlogVo> listBlog(BlogConditionVo blogConditionVo) {
        return blogDao.listBlog(blogConditionVo);
    }

    @Override
    public Blog queryBlog(Long blogId) {
        return blogDao.queryBlog(blogId);
    }
    @Transactional
    @Override
    public Boolean modifyBlog(Blog blog) {
        blogDao.modifyBlog(blog);
        return true;
    }

    @Transactional
    @Override
    public int delBlog(String ids) {
        String[] tmp = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String str : tmp){
            list.add(Long.parseLong(str));
        }
        return blogDao.delBlog(list);
    }
}
