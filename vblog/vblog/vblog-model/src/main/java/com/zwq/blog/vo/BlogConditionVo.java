package com.zwq.blog.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zwq
 * @date 2018/12/11.
 */
@Getter
@Setter
public class BlogConditionVo implements Serializable{
    private Long typeid;
    private String title;

}
