package com.yaic.auth.interior.dao;


import java.util.List;
import java.util.Map;

import com.yaic.auth.interior.common.TreeNode;
import com.yaic.auth.interior.model.AppUserRoleModel;

public interface AppUserRoleDao {
	
	void deleteByUserCode(String userCode);
     
	void saveUserRoleList(List<AppUserRoleModel> lists);

	List<TreeNode> findRolesByUsercode(Map<String, String> paraMap);

	List<TreeNode> findAllRoles();
	

}