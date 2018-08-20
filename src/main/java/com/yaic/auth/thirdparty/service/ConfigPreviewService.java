package com.yaic.auth.thirdparty.service;

import com.yaic.auth.thirdparty.dto.DataSourceDto;
import com.yaic.auth.thirdparty.dto.ProjectDto;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.model.ProjectModel;

/** 
* @ClassName: ConfigPreviewService 
* @Description: 配置预览
* @author zhaoZd
* @date 2018年7月24日 下午2:07:04 
*  
*/
public interface ConfigPreviewService {

	
	/** 
	* @Title: getDatasourceInfos 
	* @Description: 根据渠道编号获取渠道所有相关信息
	* @param model
	* @return
	* @throws Exception    
	* @return DataSourceDto  
	* @throws 
	*/
	DataSourceDto getDatasourceInfos(DataSourceModel model) throws Exception;

	/** 
	* @Title: getProjectInfos 
	* @Description: 根据方案编号获取方案所有相关信息
	* @param model
	* @return
	* @throws Exception    
	* @return ProjectDto  
	* @throws 
	*/
	ProjectDto getProjectInfos(ProjectModel model) throws Exception;



}
