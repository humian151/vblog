package com.zwq.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class RoleMenu  implements Serializable {

    private Long id;
    private Long menuId;
    private Long roleId;


}
