package com.zwq.blog.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2018/7/27.
 */
@Getter
@Setter
public class MenuVo implements Serializable{
    private String title;
    private String icon;
    private boolean spread;
    private String href;
    private List<MenuVo> children;

}
