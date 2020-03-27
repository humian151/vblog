package com.zwq.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.zwq.blog.model.Blog;
import com.zwq.blog.model.BlogType;
import com.zwq.blog.model.User;
import com.zwq.blog.service.BlogService;
import com.zwq.blog.service.BlogTypeService;
import com.zwq.blog.utils.CodeMsg;
import com.zwq.blog.utils.DataTable;
import com.zwq.blog.utils.Result;
import com.zwq.blog.vo.BlogConditionVo;
import com.zwq.blog.vo.BlogVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author zwq
 * @date 2018/12/10.
 */
@RestController
@RequestMapping("/blog")
public class BlogAdminController {
    @Autowired
    BlogTypeService blogTypeService;
    @Autowired
    BlogService blogService;

    /**
     * 获取博客类别
     * @return
     */
    @GetMapping("/loadBlogType")
    public Result<List<BlogType>> loadBlogType(){
        List<BlogType> blogTypes = blogTypeService.queryAllBlogType();
        if(!CollectionUtils.isEmpty(blogTypes)){
            return Result.success(blogTypes);
        }
        return null;
    }

    /**
     * 保存博文
     * @param blog
     * @return
     */
    @PostMapping("/saveBlog")
    public Result<String> saveBlog(@RequestBody Blog blog){
        blog.setReleasedate(new Date());
        Boolean flag =blogService.saveBlog(blog);
        if(flag){
            return Result.success("保存成功！");
        }
        return Result.error(CodeMsg.SAVE_BLOG_ERROR);
    }
    /**
     * 修改博文
     * @param blog
     * @return
     */
    @PostMapping("/modifyBlog")
    public Result<String> modifyBlog(@RequestBody Blog blog){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("currUser");
        blog.setAuthor(user.getName());
        blog.setReleasedate(new Date());
        Boolean flag =blogService.modifyBlog(blog);
        if(flag){
            return Result.success("保存成功！");
        }
        return Result.error(CodeMsg.SAVE_BLOG_ERROR);
    }

    /**
     * 分页查询博文列表
     * @param typeid
     * @param title
     * @param pageNum
     * @param limit
     * @return
     */
    @GetMapping("/listBlog")
    public DataTable<List<BlogVo>> listBlog(Long typeid, String title, Integer pageNum, Integer limit){
        PageHelper.startPage(pageNum,limit);
        BlogConditionVo blogConditionVo = new BlogConditionVo();
        blogConditionVo.setTypeid(typeid);
        blogConditionVo.setTitle(title);
        List<BlogVo> list = blogService.listBlog(blogConditionVo);
        PageInfo<BlogVo> pageInfo = new PageInfo(list,limit);
        DataTable<List<BlogVo>> result = new DataTable<List<BlogVo>>((int) pageInfo.getTotal(), pageInfo.getList());
        return result;
    }
    @GetMapping("/queryBlog/{blogid}")
    public Result queryBlog(@PathVariable("blogid")Long blogId){
        Blog blog = blogService.queryBlog(blogId);
        return Result.success(blog);
    }
    @PostMapping("/delBlog")
    public Result delBlog(String ids){
        int l = blogService.delBlog(ids);
        if (l >0){
            return Result.success("删除成功");
        }else {
            return Result.error(CodeMsg.DELETE_FAIL);
        }

    }

}
