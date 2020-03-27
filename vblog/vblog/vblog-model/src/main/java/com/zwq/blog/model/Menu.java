package com.zwq.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class Menu  implements Serializable {

    private Long id;
    private String menuName;
    private Long parentId;
    private String href;
    private String icon;


}
