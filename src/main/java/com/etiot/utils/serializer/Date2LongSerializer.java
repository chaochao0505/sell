/**
 * @company 西安一体物联网科技有限公司
 * @file Date2LongSerializer.java
 * @author zhaochao
 * @date 2018年4月20日 
 */
package com.etiot.utils.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @description : 时间转换类
 * @author : zhaochao
 * @version : V1.0.0
 * @date : 2018年4月20日
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		gen.writeNumber(value.getTime()/1000);
	}

}
