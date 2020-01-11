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


import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTime 工具类
 *
 * @author L.cm
 */
@Slf4j
public class DateTimeUtil {

	static  DateTimeFormatter isoDate = DateTimeFormatter.ISO_DATE;

	/**
	 * 日期时间格式化
	 *
	 * @return 格式化后的时间
	 */
	public static String formatDate() {
		LocalDateTime ldt1 = LocalDateTime.now();
		String format = isoDate.format(ldt1);
		log.info("格式化后的时间=" + format);
		return format;
	}

	public static void main(String[] args) {
		formatDate();
	}
}
