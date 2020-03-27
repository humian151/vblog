package com.zwq.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class User implements Serializable{
    private Long id;
    private String username;
    private String name;
    private String password;
    private String avatar;
    private String nickname;
    private String prifile;
    private String sign;
    private String salt;

}
