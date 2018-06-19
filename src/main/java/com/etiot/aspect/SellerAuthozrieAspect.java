/**
 * @company 西安一体物联网科技有限公司
 * @file SellerAuthozrieAspect.java
 * @author zhaochao
 * @date 2018年6月7日 
 */
package com.etiot.aspect;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.etiot.constant.CookieConstant;
import com.etiot.constant.RedisConstant;
import com.etiot.exception.SellerAuthozrieException;
import com.etiot.utils.CookieUtil;

/**
 * @description :aop身份验证
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月7日
 */
@Aspect
@Component
public class SellerAuthozrieAspect {

	private Logger log=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Pointcut("execution(public * com.etiot.controller.Seller*.*(..))" + "&& !execution(public * com.etiot.controller.SellerUserController.*(..))" )
	//@Pointcut("!execution(public * com.etiot.controller.SellerUserController.*(..))" )
	public void verify(){};
	
	@Before("verify()")
	public void doCerify(){
	ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		
		//查询cookie
		Cookie cookie=CookieUtil.get(request, CookieConstant.TOKEN);
		if(cookie == null){
			log.warn("【登录校验】cookie中查不到token");
			throw new SellerAuthozrieException();
		}
		//去redis查询token信息
		String tokenValue=redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
		if(StringUtils.isEmpty(tokenValue)){
			log.warn("【登录校验】redis中查不到token");
			throw new SellerAuthozrieException();
		}
	}
}
