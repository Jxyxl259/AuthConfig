package com.yaic.auth.interior.service;

import java.util.List;

import com.yaic.auth.interior.dto.AppResourceDto;
import com.yaic.auth.interior.model.AppResourceModel;

/** 
* @ClassName: AppResourceService 
* @Description: 资源管理接口
* @author zhaoZd
* @date 2018年7月6日 上午11:04:07 
*  
*/
public interface AppResourceService {
	
	/** 
	* @Title: addInfo 
	* @Description: 新增记录
	* @param dto
	* @return
	* @throws Exception  
	* @return Integer  
	* @throws 
	*/
	public Integer addInfo(AppResourceModel dto) throws Exception;
	
	/** 
	* @Title: createResourceId 
	* @Description: 根据页面传来的父级编号得到新的资源编号
	* @param resourceId
	* @param parentResourceId
	* @return    
	* @return String  
	* @throws 
	*/
	public String createResourceId(String resourceId);

	/**
	 * 修改记录信息
	 * @param dto
	 * @return
	 */
	public Integer updateInfo(AppResourceModel dto) throws Exception;

	/**
	 * 根据主键编号删除数据
	 * @param resourceId
	 * @return
	 */
	public Integer deleteByPrimaryKey(String resourceId) throws Exception;

	/**
	 * 根据resourceName精确查询对象
	 * @param resourceName
	 * @return
	 */
	public AppResourceModel getInfoByResourceName(String resourceName) throws Exception;
	
	/**
	 * 根据resourceId查询对象信息
	 * @param userId
	 * @return
	 */
	public AppResourceModel getInfoByResourceId(String resourceId) throws Exception;
    
	/** 
	* @Title: findAllResourcesByUserId 
	* @Description: 获取当前登录用户拥有的资源(左侧菜单使用)
	* @param loginUserId
	* @return    
	* @return List<AppResourceDto>  
	* @throws 
	*/
	public List<AppResourceDto> findAllResourcesByUserId(String loginUserId);
	
}
