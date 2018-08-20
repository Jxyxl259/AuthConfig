package com.yaic.auth.thirdparty.service;

import java.util.List;

import com.yaic.auth.thirdparty.dto.AccountAuthMappDto;
import com.yaic.auth.thirdparty.model.AccountAuthMappModel;

/** 
* @ClassName: AccountAuthMappService 
* @Description: 
* @author zhaoZd
* @date 2018年7月26日 上午10:21:11 
*  
*/
public interface AccountAuthMappService {

	
	/** 
	* @Title: getDtoLists 
	* @Description: 根据条件获取对应的账号服务关系信息
	* @param dto
	* @return    
	* @return List<AccountAuthMappDto>  
	* @throws 
	*/
	List<AccountAuthMappDto> getSingleAccountAuthMappLists(AccountAuthMappDto dto);

	/** 
	* @Title: saveAccountAuthMappInfo 
	* @Description: 保存账户与服务关系表数据
	* @param model
	* @return    
	* @return Integer  
	* @throws 
	*/
	public Integer saveAccountAuthMappInfo(AccountAuthMappModel model);

	
	/** 
	* @Title: deleteInfo 
	* @Description: 删除关系信息
	* @param id
	* @return    
	* @return Integer  
	* @throws 
	*/
	Integer deleteInfo(Integer id);

	/**
	 * @param dto  
	* @Title: getAllHistoryMappLists 
	* @Description: 获取多个接口类型下的数据
	* @return    
	* @return List<AccountAuthMappDto>  
	* @throws 
	*/
	public List<AccountAuthMappDto> getAllHistoryMappLists(AccountAuthMappDto dto);

	Integer deleteInfoByIds(List<String> idsParam) throws Exception;



}
