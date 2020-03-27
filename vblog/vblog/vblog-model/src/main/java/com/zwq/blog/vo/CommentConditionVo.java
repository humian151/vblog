package com.zwq.blog.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zwq
 * @date 2018/12/12.
 */
@Getter
@Setter
public class CommentConditionVo implements Serializable{
    private String title;
    private String content;
    private Long state;

}
