package com.security.platform.common.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.security.platform.common.constant.RedisKeyConstant.pictureUrlKey;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/9 16:08
 * @File AliyunOSSUtil
 * @Software IntelliJ IDEA
 * @description todo
 */
@Slf4j
@Component
public class AliyunOSSUtil {

    @Value("${oss.bucketName}")
    private String bucketName;

    @Value("${oss.endpoint}")
    private String fileHost;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.webUrl}")
    private String webUrl;

    @Autowired
    private OSSClient ossClient;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 上传
     * @param
     * @return
     */
    public  String putObject(String ip, File file){
        log.info("OSS文件上传开始：");
        String dateStr = DateTimeUtil.formatDate();
        try {
            //容器不存在，就创建
            if(! ossClient.doesBucketExist(bucketName)){
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            //创建文件路径
            String fileUrl = fileHost+"/"+(dateStr + "/" + ip.replace("-","")+"_"+file.getName());
            //上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl,file ));
            String resultUrl = webUrl +"/"+ fileUrl;//文件的web访问地址
            redisUtil.setPictureUrl(ip + pictureUrlKey,resultUrl,5, TimeUnit.MINUTES);
            log.info("resultUrl=" + resultUrl);
            //设置权限 这里是公开读
           // ossClient.setBucketAcl(bucketName,CannedAccessControlList.PublicRead);
            if(null != result){
                log.info("OSS文件上传成功,OSS地址："+resultUrl);
                return resultUrl;
            }
        }catch (OSSException oe){
            log.error(oe.getMessage());
        }catch (ClientException ce){
            log.error(ce.getMessage());
        }finally {
            //关闭
            ossClient.shutdown();
        }
        return null;
    }

    /**
     * 删除
     * @param fileKey
     * @return
     */
    public  String deleteBlog(String fileKey){
        log.info("=========>OSS文件删除开始");
        try {
            if(!ossClient.doesBucketExist(bucketName)){
                log.info("==============>您的Bucket不存在");
                return "您的Bucket不存在";
            }else {
                log.info("==============>开始删除Object");
                ossClient.deleteObject(bucketName,fileKey);
                log.info("==============>Object删除成功："+fileKey);
                return "==============>Object删除成功："+fileKey;
            }
        }catch (Exception ex){
            log.info("删除Object失败",ex);
            return "删除Object失败";
        }
    }



}
