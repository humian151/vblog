package com.zwq.blog.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取redis配置信息到对象中
 * @author zwq
 * @date 2018/12/5.
 */
@Component
@ConfigurationProperties(prefix = "redis")
@Getter
@Setter
public class RedisConfig{
    private String host;
    private int port;
    private int timeout;//秒
    private String password;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;//秒
}
