package com.zwq.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class BlogType  implements Serializable {

    private Long id;
    private String typename;
    private Long orderno;

}
