/**
 * @company 西安一体物联网科技有限公司
 * @file SellerUserController.java
 * @author zhaochao
 * @date 2018年6月5日 
 */
package com.etiot.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.etiot.config.ProjectUrlConfig;
import com.etiot.constant.CookieConstant;
import com.etiot.constant.RedisConstant;
import com.etiot.entity.SellerInfoEntity;
import com.etiot.enums.ResultEnum;
import com.etiot.service.SellerInfoService;
import com.etiot.utils.CookieUtil;
import com.etiot.utils.KeyUtil;

/**
 * @description :卖家登录
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月5日
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

	@Autowired
	private SellerInfoService sellerService;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private ProjectUrlConfig urlConfig;
	
	/**
	 * 登录
	 * @param openid
	 * @param response
	 * @param map
	 * @return
	 */
	@GetMapping("/login")
	public ModelAndView login(String openid,HttpServletResponse response,Map<String,Object> map){
		//1.openid和数据库数据比对
		SellerInfoEntity entity=sellerService.findByOpenid(openid);
		if(entity==null){
			map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
			map.put("url","/seller/order/list");
			
			return new ModelAndView("/common/error");
		}
		//2.设置token到redis
		String token=KeyUtil.getUniqueKey();
		Integer expire=RedisConstant.EXPIRE;
		redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire,TimeUnit.SECONDS);
		//3.设置token到cookie
		CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
		return new ModelAndView("redirect:"+urlConfig.getSell()+"/seller/order/list");
	}
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest httpServletRequest,
								HttpServletResponse httpServletResponse,
								Map<String,Object> map){
		//1.从cookie里查询				
		Cookie cookie=CookieUtil.get(httpServletRequest, CookieConstant.TOKEN);
		if(cookie!=null){
			//2.清除redis
			redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
			//3.清除cookie
			CookieUtil.set(httpServletResponse, CookieConstant.TOKEN, null, 0);
		}
		map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
		map.put("url", "/seller/order/list");
		return new ModelAndView("/common/success",map);		
	}
	
}
