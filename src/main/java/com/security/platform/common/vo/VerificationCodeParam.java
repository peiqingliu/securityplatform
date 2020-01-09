package com.security.platform.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/9 13:54
 * @File VerificationCodeParam
 * @Software IntelliJ IDEA
 * @description 验证参数实体
 */
@Data
public class VerificationCodeParam {

    //签名
    @JsonProperty(value = "Sign")
    private String sign;

    //时间戳
    @JsonProperty(value = "timeStamp")
    private long timeStamp;

}
