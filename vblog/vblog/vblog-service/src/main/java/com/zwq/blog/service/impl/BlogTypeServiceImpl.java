package com.zwq.blog.service.impl;

import com.zwq.blog.mapper.BlogTypeDao;
import com.zwq.blog.model.BlogType;
import com.zwq.blog.service.BlogTypeService;
import com.zwq.blog.vo.BlogTypeFrontVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 博客类别控制类
 * @author zwq
 * @date 2018/12/10.
 */
@Service
public class BlogTypeServiceImpl implements BlogTypeService{
    @Autowired
    BlogTypeDao blogTypeDao;
    @Override
    public List<BlogType> queryAllBlogType() {
        return blogTypeDao.queryAllBlogType();
    }

    @Override
    public List<BlogType> listBlogType(String typename) {
        return blogTypeDao.queryBlogType(typename);
    }

    @Override
    public Boolean saveBlogType(BlogType blogType) {
        return blogTypeDao.saveBlogType(blogType) > 0;
    }

    @Override
    public Boolean modifyBlogType(BlogType blogType) {
        return blogTypeDao.modifyBlogType(blogType) >0;
    }

    @Override
    public int delBlogType(String ids) {
        String[] tmp = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String str : tmp){
            list.add(Long.parseLong(str));
        }
        return blogTypeDao.delBlogType(list);
    }

    @Override
    public List<BlogTypeFrontVo> showBlogTypeMenu() {
        return blogTypeDao.queryBlogTypeMenu();
    }
}
