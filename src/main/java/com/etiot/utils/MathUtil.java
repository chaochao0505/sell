/**
 * @company 西安一体物联网科技有限公司
 * @file MathUtil.java
 * @author zhaochao
 * @date 2018年5月18日 
 */
package com.etiot.utils;

/**
 * @description :
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年5月18日
 */
public class MathUtil {

	private static final Double MONEY_RANGE=0.01;
	
	public static Boolean equals(Double d1,Double d2){
		Double result=Math.abs(d1-d2);
		if(result<MONEY_RANGE){
			return true; 
		}else{
			return false;
		}
		
	}
}
