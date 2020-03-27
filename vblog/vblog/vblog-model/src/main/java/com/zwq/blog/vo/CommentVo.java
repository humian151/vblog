package com.zwq.blog.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zwq
 * @date 2018/12/12.
 */
@Getter
@Setter
public class CommentVo implements Serializable{

    private Long id;
    private String userip;
    private String title; //博客标题
    private Long blogid;
    private String content; //评论内容
    private Date commentdate;
    private Long state;

}
