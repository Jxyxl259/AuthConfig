package com.yaic.auth.external.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yaic.auth.thirdparty.controller.AccountController;


/** 
* @ClassName: BaseController 
* @Description: 对外接口controller公共类
* @author zhaoZd
* @date 2018年6月20日 下午1:12:51 
*  
*/
public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);


	/** 
	* @Title: getDataJson 
	* @Description: 外部JSON串截取
	* @param content
	* @return    
	* @return JSONObject  
	* @throws 
	*/
	public static JSONObject getDataJson(String content) {
		
		logger.debug("content info : {}",content);
		
		JSONObject jsonObject = JSON.parseObject(content);
		JSONObject JSONOData = (JSONObject) jsonObject.get("data");
//		JSONObject JSONOData1 = (JSONObject) jsonObject.get("uniqueId");
//		if(JSONOData1 == null){
//			
//			ResultMessage result = new ResultMessage(ResponseParameters.CODE_0000,ResponseParameters.MSG_0000,lists);
//		}
		
		return JSONOData;
	}

}
