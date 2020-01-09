package com.security.platform.common.utils;

import com.security.platform.common.vo.VerificationCodeParam;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/9 13:58
 * @File SignUtil
 * @Software IntelliJ IDEA
 * @description todo
 */
public class SignUtil {

    public static boolean validateMessage(VerificationCodeParam param, String secretKey) {
        CheckUtil check = new CheckUtil(secretKey);
        check.setValue("timeStamp", param.getTimeStamp());
        boolean result = check.checkSign(param.getSign());
        return result;
    }


}
