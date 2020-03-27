package com.zwq.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class UserRole  implements Serializable {
    private Long id;
    private Long userId;
    private Long roleId;

}
