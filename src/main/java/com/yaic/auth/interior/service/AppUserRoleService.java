package com.yaic.auth.interior.service;

import java.util.List;

import com.yaic.auth.interior.common.TreeNode;

public interface AppUserRoleService {

	/** 
	* @Title: findMyRolesByUserCode 
	* @Description: 根据用户code获取角色资源
	* @param String
	* @return    
	* @return List<TreeNode>  
	* @throws 
	*/
	public List<TreeNode> findMyRolesByUserCode(String userCode);

	/** 
	* @Title: findAllRoles 
	* @Description: 获取所有的角色列表
	* @return    
	* @return List<TreeNode>  
	* @throws 
	*/
	public List<TreeNode> findAllRoles();
	
	/** 
	* @Title: saveUserRoles 
	* @Description: 保存用户角色关系信息
	* @param userCode
	* @param idsParam    
	* @return void  
	* @throws 
	*/
	void saveUserRoles(String userCode, List<String> idsParam);


}
