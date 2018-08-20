package com.yaic.auth.interior.service;

import java.util.List;

import com.yaic.auth.interior.model.AppRoleModel;

public interface AppRoleService {

	AppRoleModel selectByPrimaryKey(String roleId) throws Exception;

	/** 
	* @Title: getList 
	* @Description: 条件查询
	* @param roleDto
	* @return
	* @throws Exception  
	* @return List<AppRoleModel>  
	* @throws 
	*/
	List<AppRoleModel> getList(AppRoleModel roleDto) throws Exception;

	/** 
	* @Title: addInfo 
	* @Description: 添加角色信息
	* @param roleModel
	* @return
	* @throws Exception  
	* @return Integer  
	* @throws 
	*/
	Integer addInfo(AppRoleModel roleModel) throws Exception;

	/** 
	* @Title: updateInfo 
	* @Description: 编辑保存角色信息 
	* @param roleModel
	* @return
	* @throws Exception  
	* @return Integer  
	* @throws 
	*/
	Integer updateInfo(AppRoleModel roleModel) throws Exception;

	/** 
	* @Title: deleteByPrimaryKey 
	* @Description: 根据角色编号删除信息
	* @param roleid
	* @return
	* @throws Exception  
	* @return Integer
	* @throws 
	*/
	Integer deleteByPrimaryKey(String roleid) throws Exception;
	
	/** 
	* @Title: deleteInfoByIds 
	* @Description: 批量删除
	* @param ids
	* @return    
	* @return Integer  
	* @throws 
	*/
	Integer deleteInfoByIds(List<String> idsParam) throws Exception;

	/** 
	* @Title: createRoleId 
	* @Description: 生成新的角色编号
	* @return    
	* @return String  
	* @throws 
	*/
	String createRoleId();

}
