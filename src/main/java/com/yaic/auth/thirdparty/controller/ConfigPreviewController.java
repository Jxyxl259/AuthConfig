package com.yaic.auth.thirdparty.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yaic.auth.common.BaseController;
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.thirdparty.dto.DataSourceDto;
import com.yaic.auth.thirdparty.dto.ProjectDto;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.service.ConfigPreviewService;

/** 
* @ClassName: ConfigPreviewController 
* @Description: 配置预览界面功能
* @author zhaoZD
* @date 2018年6月17日 下午9:46:30 
*  
*/
@RestController
@RequestMapping("/configview")
public class ConfigPreviewController {

	private static final Logger logger = LoggerFactory.getLogger(ConfigPreviewController.class);

	@Autowired
	private ConfigPreviewService configPreviewService;

	
	
	/** 
	* @Title: getDatasourceInfos 
	* @Description: 获取渠道以及渠道下的所有相关信息
	* @param model
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/getDatasourceInfos")
	public ResultMessage getDatasourceInfos(@RequestBody DataSourceModel model) throws Exception {
		
		//封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(9999,"数据参数有误");
		
		if(model != null && model.getDataSourceId() != null){
			logger.debug("获取渠道所有相关信息 	getDatasourceInfos(),参数:{}",model);
			DataSourceDto dto = configPreviewService.getDatasourceInfos(model);
			logger.debug("getDatasourceInfos(),根据渠道编号获取渠道所有相关信息正常");
			
			if(dto != null)
				result = BaseController.getResults(dto);
		}
		
		return result;
	}
	
	@PostMapping(value = "/getProjectInfos")
	public ResultMessage getProjectInfos(@RequestBody ProjectModel model) throws Exception {
		
		//封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(9999,"数据参数有误");
		
		if(model != null && model.getProjectId() != null){
			logger.debug("获取方案所有相关信息 	getProjectInfos(),参数:{}",model);
			ProjectDto dto = configPreviewService.getProjectInfos(model);
			logger.debug("getProjectInfos(),根据方案编号获取方案所有相关信息正常");
			
			if(dto != null)
				result = BaseController.getResults(dto);
		}

		return result;
	}
	
	
}
