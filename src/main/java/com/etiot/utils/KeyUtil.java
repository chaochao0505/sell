/**
 * @company 西安一体物联网科技有限公司
 * @file KeyUtil.java
 * @author zhaochao
 * @date 2018年4月18日 
 */
package com.etiot.utils;

import java.util.UUID;

/**
 * @description :生成uuid
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月18日
 */
public class KeyUtil {

	public static String getUniqueKey(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
