package com.yaic.auth.common;

/**
 * model类的数据校验
 * 根据properties的配置校验类中的属性
 * 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

public class BeanCheck {
	
	private static final Logger logger = LoggerFactory.getLogger(BeanCheck.class);

	/**
	* @Title: check 
	* @Description: 对象中变量数据校验
	* @param o	对象名称
	* @return    
	* @return String  
	* @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String check(Object o) {
		
		StringBuffer resultMsg = new StringBuffer();
		
		if(1 > 0){
			return "";
		}
		
		/*
		 * 获取对应properties文件
		 */
		File file = null;
		try {
			file = ResourceUtils.getFile("classpath:checkBean/" + o.getClass().getName() + ".properties");
		} catch (Exception e) {
			logger.error("get {}.java relevant properties fail",o.getClass().getSimpleName());
			e.printStackTrace();
			return "not find :" + o.getClass().getName() + ".properties";
		}

		/*
		 * 加载.properties文件信息
		 */
		Properties properties = null;
		FileInputStream in = null;
		try {
			properties = new Properties();
			in = new FileInputStream(file);
			properties.load(in);

		} catch (Exception e) {
			logger.error("load {}.properties fail",o.getClass().getName());
			e.printStackTrace();
			return "load properties fail";
		} finally {
			try {
				in.close();
			} catch (IOException e1) {

			}
		}
		Class classBean = null;
		try {
			classBean = Class.forName(o.getClass().getName());
		} catch (Exception e1) {

			e1.printStackTrace();
			return "load class is error";
		}
		try {
			
			String value = "";			//每个规则内容
			
			Integer valueLength = 0;		//参数值长度
			Integer rangeLeft = 0;			//规则中最小长度
			Integer rangeRight = 0;			//规则中最大长度
			
			//循环properties文件内容,获取内容
			Enumeration<?> enumPro = properties.propertyNames();
			while (enumPro.hasMoreElements()) {
				//获取key
				String strKey = (String) enumPro.nextElement();
				//获取value
				String strValue = properties.getProperty(strKey);
				
				//拼接get...名称
				Method m = classBean.getMethod("get" + strKey.substring(0,1).toUpperCase() + strKey.substring(1), null);
				//对象中参数值
				Object valueMethod = m.invoke(o, null);
				
				//strValue不可为空
				if(StringUtils.isNotEmpty(strValue) && strValue.trim().length() > 0){
					String[] values = strValue.split(",");
					
					//目前只要三个规则段
					if(values.length > 0){
						
						
						//第一个规则 非空验证
						value = values[0].trim();
						if(value.equals("notNull")){

							if (valueMethod == null || valueMethod.toString().trim().equals("")) {
								resultMsg.append(strKey+" is null;");
							}
						}
							if (valueMethod != null && valueMethod.toString().trim().length() > 0){
								
								//参数值长度
								valueLength = valueMethod.toString().length();
								
								if(values.length > 1){
									//第二个规则,长度范围验证
									value = values[1].trim();
									if(value.length() > 2 && valueLength > 0){
										
										//去掉[]符号
										value = value.replace("[","").replace("]","");
										String[] ranges = value.split(":");
										//判断是否有间隔符 : ,如果有,则代表最小和最大长度,若没有默认为最大长度
										try {
											rangeLeft = -1;		//没有默认为-1
											if(ranges.length == 1){		//有间隔符
												rangeRight = Integer.parseInt(ranges[0]);
											}else{
												if(ranges[0].length() > 0)
													rangeLeft = Integer.parseInt(ranges[0]);
												rangeRight = Integer.parseInt(ranges[1]);
											}
										} catch (Exception e) {
											logger.error("{}	String parse to integer is fail,{}",strKey,ranges[0]);
											resultMsg.append(strKey + "String parse to integer is fail,"+ranges[0]);
											e.printStackTrace();
										}
										
										//小于最小长度
										if(valueLength < rangeLeft){
											resultMsg.append(strKey+" less than the min length:"+rangeLeft+";");
										}
										//大于最大长度
										if(valueLength > rangeRight){
											resultMsg.append(strKey+" greater than the max length:"+rangeRight+";");
										}
										
									}
									
									if(values.length > 2){
										//第三个规则,正则验证
										value = values[2].trim();
										if(value.length() > 2 && valueLength > 0){

											Pattern pattern = Pattern.compile(value);
											Matcher matcher = pattern.matcher(valueMethod.toString());
											if (!matcher.matches()) {
												resultMsg.append(strKey+" not conformity the regular expression;");
											}
										}
									}
								}
							}
						
					}
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		
		return resultMsg.toString();

	}

//	public static void main(String args[]) {
//		AccountModel test = new AccountModel();
//		test.setAccountName("222222");
//		test.setEmail("a45@a16.com");
//		test.setAppCode("33454");
//		test.setMobile("13299990000");
//		
//		String resu = BeanCheck.check(test);
//		System.out.println(resu);
//		
//	}

}
