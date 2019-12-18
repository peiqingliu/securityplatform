package com.security.platform.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/9/10 16:24
 * @description 主要用于从token中解析出来用户的信息
 */
@Data
@AllArgsConstructor
public class TokenUser implements Serializable {

    private String username;

    private List<String> permissions;

    private Boolean saveLogin;
}
