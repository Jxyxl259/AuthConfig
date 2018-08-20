package com.yaic.auth.interior.dao;

import java.util.List;
import java.util.Map;

import com.yaic.auth.interior.common.TreeNode;
import com.yaic.auth.interior.model.AppRoleResourceModel;

public interface AppRoleResourceDao {
    
    void deleteByRoleId(String roleId);
    
    void saveRoleResourceList(List<AppRoleResourceModel> lists);

    List<TreeNode> findAllMenuResource();
	
	List<TreeNode> findResourceByRoleId(Map<String, String> paraMap);
}