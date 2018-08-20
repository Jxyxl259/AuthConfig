package com.yaic.auth.interior.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yaic.auth.common.Constants;
import com.yaic.auth.interior.common.TreeNode;
import com.yaic.auth.interior.dao.AppRoleResourceDao;
import com.yaic.auth.interior.model.AppRoleResourceModel;
import com.yaic.auth.interior.service.AppRoleResService;

@Service
public class AppRoleResServiceImpl implements AppRoleResService {

	@Autowired
	AppRoleResourceDao appRoleResourceDao;

	/**
	 * 根据角色ID获取角色对应的资源
	 */
	@Override
	public List<TreeNode> findResourceByRoleId(Map<String, String> paraMap) throws Exception {
		
		List<TreeNode> resourceList = appRoleResourceDao.findResourceByRoleId(paraMap);

		return resourceList;
	}

	/**
	 * 获取所有的菜单资源
	 */
	@Override
	public List<TreeNode> findAllMenuResource() throws Exception {
		
		return appRoleResourceDao.findAllMenuResource();
	}

	/**
	* Title: saveRoleResource
	* Description: 保存角色资源关系信息
	* @param roleId
	* @param idsParam 
	*/
	@Override
	@Transactional
	public void saveRoleResource(String roleId, List<String> idsParam) {

		appRoleResourceDao.deleteByRoleId(roleId);
		
		if(idsParam.size() > 0){
			
			String loginUserCode = Constants.LOGIN_USER_CODE();
			AppRoleResourceModel model = null;
			
			List<AppRoleResourceModel> lists = new ArrayList<AppRoleResourceModel>();
			
			for (String id : idsParam) {
				
				model = new AppRoleResourceModel();
				model.setRoleId(roleId);
				model.setCreatedBy(loginUserCode);
				model.setUpdatedBy(loginUserCode);
				model.setResourceId(id);
				
				lists.add(model);
			}
			
			appRoleResourceDao.saveRoleResourceList(lists);
		}
		
	}

}
