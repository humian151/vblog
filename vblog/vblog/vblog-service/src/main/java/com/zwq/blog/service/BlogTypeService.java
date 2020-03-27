package com.zwq.blog.service;

import com.zwq.blog.model.BlogType;
import com.zwq.blog.vo.BlogTypeFrontVo;

import java.util.List;

/**
 * @author zwq
 * @date 2018/12/10.
 */
public interface BlogTypeService {
    public List<BlogType> queryAllBlogType();

    List<BlogType> listBlogType(String typename);

    Boolean saveBlogType(BlogType blogType);

    Boolean modifyBlogType(BlogType blogType);

    int delBlogType(String ids);

    List<BlogTypeFrontVo> showBlogTypeMenu();
}
