package com.security.plateform.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/11/17 11:04
 * @description 前后台数据交换标准
 */
@Data
public class Result<T> implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    private boolean success;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 结果对象
     */
    private T result;

}
