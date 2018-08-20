package com.yaic.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@ServletComponentScan
@SpringBootApplication		//启动标志
@MapperScan(basePackages={"com.yaic.auth.thirdparty.dao","com.yaic.auth.interior.dao","com.yaic.auth.common.init.dao"})		//mybatis reload xml
public class Run extends SpringBootServletInitializer{
	
	 @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Run.class);
    }
	 
	/**
	 * 
	* @Title: main 
	* @Description: 项目启动入口
	* @param args    
	* @return void  
	* @throws
	 */
	public static void main(String[] args) {
		SpringApplication.run(Run.class, args);
	}
} 
