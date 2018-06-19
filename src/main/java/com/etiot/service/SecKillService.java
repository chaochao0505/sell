/**
 * @company 西安一体物联网科技有限公司
 * @file SecKillService.java
 * @author zhaochao
 * @date 2018年6月14日 
 */
package com.etiot.service;

/**
 * @description :秒杀
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月14日
 */
public interface SecKillService {

    /**
     * 查询秒杀活动特价商品的信息
     * @param productId
     * @return
     */
    String querySecKillProductInfo(String productId);

    /**
     * 模拟不同用户秒杀同一商品的请求
     * @param productId
     * @return
     */
    void orderProductMockDiffUser(String productId);

}
