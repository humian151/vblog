package com.zwq.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwq.blog.model.BlogType;
import com.zwq.blog.service.BlogTypeService;
import com.zwq.blog.utils.CodeMsg;
import com.zwq.blog.utils.DataTable;
import com.zwq.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zwq
 * @date 2018/12/12.
 */
@RestController
@RequestMapping("/blogType")
public class BlogTypeAdminController {
    @Autowired
    BlogTypeService blogTypeService;

    @GetMapping("/listBlogType")
    public DataTable<List<BlogType>> listBlogType(String typename, Integer pageNum, Integer limit){
        PageHelper.startPage(pageNum,limit);
        List<BlogType> list = blogTypeService.listBlogType(typename);
        PageInfo<BlogType> pageInfo = new PageInfo(list,limit);
        DataTable<List<BlogType>> result = new DataTable<List<BlogType>>((int) pageInfo.getTotal(), pageInfo.getList());
        return result;
    }


    @PostMapping("/saveBlogType")
    public Result<String> saveBlogType(@RequestBody BlogType blogType){
        Boolean flag =blogTypeService.saveBlogType(blogType);
        if(flag){
            return Result.success("保存成功！");
        }
        return Result.error(CodeMsg.SAVE_BLOG_ERROR);
    }

    @PostMapping("/modifyBlogType")
    public Result<String> modifyBlogType(@RequestBody BlogType blogType){
        Boolean flag =blogTypeService.modifyBlogType(blogType);
        if(flag){
            return Result.success("保存成功！");
        }
        return Result.error(CodeMsg.SAVE_BLOG_ERROR);
    }

    @PostMapping("/delBlogType")
    public Result delBlogType(String ids){
        int l = blogTypeService.delBlogType(ids);
        if (l >0){
            return Result.success("删除成功");
        }else {
            return Result.error(CodeMsg.DELETE_FAIL);
        }
    }
}
