/**
 * @company 西安一体物联网科技有限公司
 * @file EnumUtil.java
 * @author zhaochao
 * @date 2018年5月23日 
 */
package com.etiot.utils;

import com.etiot.enums.CodeEnum;

/**
 * @description :枚举工具类
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月23日
 */
public class EnumUtil {

	public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
	for (T each : enumClass.getEnumConstants()) {
		if(code.equals(each.getCode())){
			return each;
		}
	}
		return null;
	}
}
