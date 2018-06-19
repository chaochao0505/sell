/**
 * @company 西安一体物联网科技有限公司
 * @file JsonUtil.java
 * @author zhaochao
 * @date 2018年5月17日 
 */
package com.etiot.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @description :json转化工具类
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月17日
 */
public class JsonUtil {

	public static String  toJson(Object object){
		GsonBuilder gsonBuilder=new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson =gsonBuilder.create();
		
		return gson.toJson(object);
	}
}
