package com.security.platform.modules.api;

import com.security.platform.common.utils.AliyunOSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/10 17:06
 * @File OssController
 * @Software IntelliJ IDEA
 * @description todo
 */
@RestController
@Slf4j
@RequestMapping("/us/test")
public class OssController {

    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file){
        try {

            if (null != file) {
                String filename = file.getOriginalFilename();
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    //上传到OSS
                    //上传文件
                    InputStream is = new FileInputStream(newFile);
                    String uploadUrl = aliyunOSSUtil.putObject("124.226.139.136",is);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
