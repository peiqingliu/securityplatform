/*
 *      Copyright (c) 2018-2028, DreamLu All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: DreamLu 卢春梦 (596392912@qq.com)
 */
package com.security.platform.common.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象工具类
 *
 * @author L.cm
 */
public class ObjectUtil extends org.springframework.util.ObjectUtils {

	/**
	 * 判断元素不为空
	 * @param obj object
	 * @return boolean
	 */
	public static boolean isNotEmpty(@Nullable Object obj) {
		return !ObjectUtil.isEmpty(obj);
	}

	public static String mapToString(Map<String, String[]> paramMap){

		if (paramMap == null) {
			return "";
		}
		Map<String, Object> params = new HashMap<>(16);
		for (Map.Entry<String, String[]> param : paramMap.entrySet()) {

			String key = param.getKey();
			String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
			String obj = StrUtil.endWithIgnoreCase(param.getKey(), "password") ? "你是看不见我的" : paramValue;
			params.put(key,obj);
		}
		return new Gson().toJson(params);
	}

	public static String mapToStringAll(Map<String, String[]> paramMap){

		if (paramMap == null) {
			return "";
		}
		Map<String, Object> params = new HashMap<>(16);
		for (Map.Entry<String, String[]> param : paramMap.entrySet()) {

			String key = param.getKey();
			String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
			params.put(key, paramValue);
		}
		return new Gson().toJson(params);
	}

	public static <T> Map<String, Object> beanToMap(T bean) {
		Map<String, Object> map = Maps.newHashMap();
		if (bean != null) {
			BeanMap beanMap = BeanMap.create(bean);
			for (Object key : beanMap.keySet()) {
				map.put(key+"", beanMap.get(key));
			}
		}
		return map;
	}

}
