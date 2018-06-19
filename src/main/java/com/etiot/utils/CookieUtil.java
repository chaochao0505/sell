/**
 * @company 西安一体物联网科技有限公司
 * @file CookieUtil.java
 * @author zhaochao
 * @date 2018年6月5日 
 */
package com.etiot.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月5日
 */
public class CookieUtil {

	/**
	 * 设置cookie
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void set(HttpServletResponse response,String name,String value,int maxAge){
		Cookie cookie=new Cookie(name,value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	/**
	 * 获取cookie
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie get(HttpServletRequest request,String name){
		Map<String,Cookie> cookieMap=readCookieMap(request);
		if(cookieMap.containsKey(name)){
			return cookieMap.get(name);
		}else{
			return null;
		}
	}
	
	/**
	 * cookie封装成map
	 * @param request
	 * @return
	 */
	public static Map<String,Cookie> readCookieMap(HttpServletRequest request){
		Map<String,Cookie> cookieMap=new HashMap<>();
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		
		return cookieMap;
	}
}
