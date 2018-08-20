package com.yaic.auth.interior.service;

import java.util.List;
import java.util.Map;

import com.yaic.auth.interior.common.TreeNode;

public interface AppRoleResService {

	/** 
	* @Title: findResourceByRoleId 
	* @Description: 根据角色编号获取角色资源关系 
	* @param paraMap
	* @return
	* @throws Exception  
	* @return List<TreeNode>  
	* @throws 
	*/
	List<TreeNode> findResourceByRoleId(Map<String, String> paraMap) throws Exception;
	
	/** 
	* @Title: findAllMenuResource 
	* @Description: 获取所有角色资源关系信息
	* @return
	* @throws Exception  
	* @return List<TreeNode>  
	* @throws 
	*/
	List<TreeNode> findAllMenuResource() throws Exception;
	
	/** 
	* @Title: saveRoleResource 
	* @Description: 保存角色与资源关系信息
	* @param roleId
	* @param idsParam    
	* @return void  
	* @throws 
	*/
	void saveRoleResource(String roleId, List<String> idsParam);



}
