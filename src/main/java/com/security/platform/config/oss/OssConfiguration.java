package com.security.platform.config.oss;

import com.aliyun.oss.OSSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/10 16:39
 * @File OssConfiguration
 * @Software IntelliJ IDEA
 * @description todo
 */
@Slf4j
@Configuration
public class OssConfiguration {

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.keyId}")
    private String accessKeyId;

    @Value("${oss.keySecret}")
    private String accessKeySecret;

    @Value("${oss.bucketName}")
    private String bucketName;

    @Value("${oss.fileHost}")
    private String fileHost;

    @Bean
    OSSClient ossClient() {
        return new OSSClient(this.endpoint, this.accessKeyId, this.accessKeySecret);
    }
}
