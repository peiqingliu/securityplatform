package com.security.platform.common.utils;

import java.util.Comparator;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/9 13:48
 * @File MapKeyComparator
 * @Software IntelliJ IDEA
 * @description todo
 */
public class MapKeyComparator implements Comparator<String> {

    public int compare(String str1, String str2) {
        return str1.compareTo(str2);   //升序排序
        //return str2.compareTo(str1);   降序排序
    }

}
