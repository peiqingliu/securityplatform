package com.security.platform.config.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/9 16:06
 * @File OSSProperties
 * @Software IntelliJ IDEA
 * @description todo
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "oos")
public class OSSProperties {

    private  String endpoint;
    private  String keyId;
    private  String keySecret;
    private  String bucketName;
    private  String fileHost;
}
