package com.zwq.blog.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zwq
 * @date 2018/12/11.
 */
@Getter
@Setter
public class BlogVo implements Serializable{
    private Long id;
    private String title;
    private Date releasedate;
    private String typename;
    private String content;
    private String summary;
    private String keyword;
    private Long chickhit;
    private Long replyhit;
    private Long typeid;
    private String author;

}
