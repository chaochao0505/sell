/**
 * @company 西安一体物联网科技有限公司
 * @file PushMessageServiceImpl.java
 * @author zhaochao
 * @date 2018年6月11日 
 */
package com.etiot.service.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiot.config.WechatAccountConfig;
import com.etiot.dto.OrderDto;
import com.etiot.service.PushMessageService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * @description :消息推送业务层
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年6月11日
 */
@Service
public class PushMessageServiceImpl implements PushMessageService{

	private final Logger log=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WxMpService wxMpService;
	@Autowired
	private WechatAccountConfig accountConfig;
	
	@Override
	public void orderStatus(OrderDto orderDto) {
		WxMpTemplateMessage wxMpTemplateMessage=new WxMpTemplateMessage();
		wxMpTemplateMessage.setTemplateId(accountConfig.getTemplateId());
		//如果是测试公众号的模板id，以及appid和appscrect都是测试公众号的，就用测试公众号的openid，如果是借用的就用借用的openid
		wxMpTemplateMessage.setToUser("oVRgg1j9-KIEnRR2JOoG0imADBnM");
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "亲，请记得收货。"),
                new WxMpTemplateData("keyword1", "微信点餐"),
                new WxMpTemplateData("keyword2", "18868812345"),
                new WxMpTemplateData("keyword3", orderDto.getOrderId()),
                new WxMpTemplateData("keyword4", orderDto.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5", "￥" + orderDto.getOrderAmount()),
                new WxMpTemplateData("remark", "欢迎再次光临！")
        );
		wxMpTemplateMessage.setData(data);
		try {
			wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
		} catch (WxErrorException e) {
			log.error("【微信模板消息】发送失败,={}",e);
			e.printStackTrace();
		}
		
	}

}
