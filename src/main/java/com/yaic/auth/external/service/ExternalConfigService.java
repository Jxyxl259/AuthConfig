package com.yaic.auth.external.service;

import java.util.List;

import com.yaic.auth.external.common.ResultMessage;
import com.yaic.auth.external.dto.responsedto.AccountExternalDto;
import com.yaic.auth.thirdparty.dto.AccountDto;
import com.yaic.auth.thirdparty.dto.AuthMappingDto;
import com.yaic.auth.thirdparty.dto.DataSourceDto;
import com.yaic.auth.thirdparty.dto.ProjectDto;
import com.yaic.auth.thirdparty.dto.ServerDto;
import com.yaic.auth.thirdparty.model.AccountModel;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.model.ProjectModel;


/** 
* @ClassName: ExternalConfigService 
* @Description: 外部接口类
* @author zhaoZd
* @date 2018年6月19日 下午5:06:41 
*  
*/
public interface ExternalConfigService {
	
	/** 
	* @Title: generateAppid 
	* @Description: 自动生成APPID
	* @return    
	* @return String  
	* @throws 
	*/
	String generateAppid() throws Exception;
	
	/** 
	* @Title: getAccount 
	* @Description: 根据appId获取账户DTO
	* @param appid
	* @return    
	* @return AccountDto  
	* @throws 
	*/
	AccountDto getAccount(String appid) throws Exception;
	
	/**
	 * @param model 
	 * @param pageSize 
	 * @param pageNum  
	* @Title: getAccountList 
	* @Description: 获取所有账户信息
	* @return    
	* @return AccountExternalDto
	* @throws 
	*/
	AccountExternalDto getAccountList(AccountModel model, Integer pageNum, Integer pageSize) throws Exception;
	
	/** 
	* @Title: getAuthTypes 
	* @Description: 获取鉴权类型
	* @return    
	* @return List<String>  
	* @throws 
	*/
	List<String> getAuthTypes() throws Exception;
	
	/** 
	 * @Title: getAuthTypes1 
	 * @Description: 获取鉴权类型与描述
	 * @return    
	 * @return List<String[]>  
	 * @throws 
	 */
	List<String[]> getAuthTypes1() throws Exception;
	
	/** 
	* @Title: getEncryptTypes 
	* @Description: 获取加密类型
	* @return    
	* @return List<String>  
	* @throws 
	*/
	List<String> getEncryptTypes() throws Exception;
	
	/** 
	* @Title: getServerEnvTypes 
	* @Description: 获取环境类型
	* @return    
	* @return List<String>  
	* @throws 
	*/
	List<String> getServerEnvTypes() throws Exception;
	
//	/** 
//	* @Title: getServerTypes 
//	* @Description: 获取服务类型
//	* @return    
//	* @return List<String>  
//	* @throws 
//	*/
//	List<String[]> getServerTypes() throws Exception;
	
//	/** 
//	* @Title: getAllServerSmallTypes 
//	* @Description: 获取所有的服务小类
//	* @return
//	* @throws Exception    
//	* @return List<String>  
//	* @throws 
//	*/
//	List<String> getAllServerSmallTypes() throws Exception;
	
	/**
	 * @param serverType  
	* @Title: getServerSmallTypes 
	* @Description: 获取服务小类
	* @return    
	* @return List<String>  
	* @throws 
	*/
	List<String[]> getServerSmallTypes(String serverType) throws Exception;
	
	/** 
	* @Title: getCallbackTypes 
	* @Description: 获取回调类型
	* @return    
	* @return List<String>  
	* @throws 
	*/
	List<String> getCallbackTypes() throws Exception;
	
	/** 
	* @Title: getDatasource 
	* @Description: 根据渠道对象获取渠道DTO
	* @param model
	* @return    
	* @return DataSourceDto  
	* @throws 
	*/
	DataSourceDto getDatasource(DataSourceModel model) throws Exception;
	
	/** 
	* @Title: getProject 
	* @Description: 根据方案对象获取方案DTO与鉴权配置DTO
	* @param model
	* @return    
	* @return ProjectDto  
	* @throws 
	*/
	ProjectDto getProject(ProjectModel model) throws Exception;

	/**
	 * @param serverSmallType 
	* @Title: getServerList 
	* @Description: 根据环境类型,服务类型获取对应服务列表
	* @param envType	环境类型
	* @param serverType	服务类型
	* @return    
	* @return List<ServerDto>  
	* @throws 
	*/
	List<ServerDto> getServerList(String envType, String serverType, String serverSmallType) throws Exception;
	
	
	/** 
	* @Title: getInterfaceTypes 
	* @Description: 获取接口类型
	* @return    
	* @return List<String>  
	* @throws 
	*/
	List<String> getInterfaceTypes() throws Exception;
	
	/** 
	* @Title: judgeRequestUrl 
	* @Description: 验证request_url正确性
	* @param authmappList
	* @param appId
	* @param datasourceId
	* @param projectId
	* @param type
	* @return    
	* @return String  
	* @throws 
	*/
	String judgeRequestUrl(List<AuthMappingDto> authmappList,String appId,Integer datasourceId,Integer projectId,String type)  throws Exception;
	
	
	
	
	
	
//	/** 
//	* @Title: addDatasource 
//	* @Description: 根据外部JSON串添加渠道以及相关信息
//	* @param json
//	* @return    
//	* @return String  
//	* @throws 
//	*/
//	ResultMessage addDatasource(DataSourceDto dto);
	
	
	/** 
	* @Title: addProject 
	* @Description: 根据外部JSON串添加方案以及相关信息
	* @param dto
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	ResultMessage addProject(ProjectDto projectDto) throws Exception;

	
	
	
	
	
	
	
//	/** 
//	* @Title: editDatasource 
//	* @Description: 根据外部JSON串修改渠道以及相关信息
//	* @param dto
//	* @return    
//	* @return ResultMessage  
//	* @throws 
//	*/
//	ResultMessage editDatasource(DataSourceDto dto);
	
	
	/** 
	* @Title: editProject 
	* @Description: 根据外部JSON串修改方案相关所有信息
	* @param dto
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	ResultMessage editProject(ProjectDto dto) throws Exception;
	
//	/** 
//	* @Title: getServerMappingSmall 
//	* @Description: 
//	* @param 
//	* @return    
//	* @return  
//	* @throws 
//	*/
//	List<String[]> getServerMappingSmall() throws Exception;
	
	/** 
	* @Title: deleteInfo 
	* @Description: 根据删除类型与主键编号删除相关信息
	* @param deleteType
	* @param deleteId
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	ResultMessage deleteInfo(String deleteType, String deleteId) throws Exception;

	public List<String[]> getValuesByParameterType(String parameterType) throws Exception;
}
