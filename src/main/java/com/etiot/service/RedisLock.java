/**
 * @company 西安一体物联网科技有限公司
 * @file RedisLock.java
 * @author zhaochao
 * @date 2018年6月14日 
 */
package com.etiot.service;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.core.Commit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @description :redis锁
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月14日
 */
@Commit
@Component
public class RedisLock {

	private final Logger log=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 加锁
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean lock(String key,String value){
		if(redisTemplate.opsForValue().setIfAbsent(key, value)){
			return true;
		}
		String currentValue=redisTemplate.opsForValue().get(key);
		//如果锁过期
		if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue)<System.currentTimeMillis()){
			//获取上一个锁的时间
			String oldValue=redisTemplate.opsForValue().getAndSet(key, value);
			if(StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 解锁
	 * @param key
	 * @param value
	 */
	public void unlock(String key,String value){
		try {
			String currentValue = redisTemplate.opsForValue().get(key);
			if (!StringUtils.isEmpty(currentValue) && value.equals(currentValue)) {
				redisTemplate.opsForValue().getOperations().delete(key);
			} 
		} catch (Exception e) {
			log.error("【redis分布式锁】 解锁异常，{}",e);
		}
	}
}
