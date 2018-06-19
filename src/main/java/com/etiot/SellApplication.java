package com.etiot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("com.etiot.dao")
@ImportResource("classpath*:application-*.xml")
public class SellApplication {


	public static void main(String[] args) {
		SpringApplication.run(SellApplication.class, args);
	}
	
}
