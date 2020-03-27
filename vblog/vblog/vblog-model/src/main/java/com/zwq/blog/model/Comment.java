package com.zwq.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
public class Comment  implements Serializable {

    private Long id;
    private Long blogid;
    private String userip;
    private String content;
    private Date commentdate;
    private Long state; //0未审核，1审核通过，2审核拒绝


}
