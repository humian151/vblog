package com.zwq.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class Blog implements Serializable{
    private Long id;
    private String title;
    private String summary;
    private Date releasedate;
    private Long chickhit;
    private Long replyhit;
    private String content;
    private Long typeid;
    private String keyword;
    private String author;

}
