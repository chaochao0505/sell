/**
 * @company 西安一体物联网科技有限公司
 * @file SecKillController.java
 * @author zhaochao
 * @date 2018年6月14日 
 */
package com.etiot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiot.service.SecKillService;

/**
 * @description :秒杀控制层
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月14日
 */
@RestController
@RequestMapping("/skill")
public class SecKillController {
	
	private final Logger log=LoggerFactory.getLogger(this.getClass());
	
	 @Autowired
	    private SecKillService secKillService;

	    /**
	     * 查询秒杀活动特价商品的信息
	     * @param productId
	     * @return
	     */
	    @GetMapping("/query/{productId}")
	    public String query(@PathVariable String productId)throws Exception
	    {
	        return secKillService.querySecKillProductInfo(productId);
	    }


	    /**
	     * 秒杀，没有抢到获得"哎呦喂,xxxxx",抢到了会返回剩余的库存量
	     * @param productId
	     * @return
	     * @throws Exception
	     */
	    @GetMapping("/order/{productId}")
	    public String skill(@PathVariable String productId)throws Exception
	    {
	        log.info("@skill request, productId:" + productId);
	        secKillService.orderProductMockDiffUser(productId);
	        return secKillService.querySecKillProductInfo(productId);
	    }
}
