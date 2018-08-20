package com.yaic.auth.interior.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yaic.auth.common.Constants;
import com.yaic.auth.common.UuidUtils;
import com.yaic.auth.interior.common.TreeNode;
import com.yaic.auth.interior.dao.AppUserRoleDao;
import com.yaic.auth.interior.model.AppUserRoleModel;
import com.yaic.auth.interior.service.AppUserRoleService;

@Service
public class AppUserRoleServiceImpl implements  AppUserRoleService{
    
	@Autowired
	private AppUserRoleDao  appUserRoleDao;
	
	@Override
	public List<TreeNode> findMyRolesByUserCode(String userCode) {

		Map<String , String> paraMap = new HashMap<String, String>();
		paraMap.put("userCode",userCode);
		
		return appUserRoleDao.findRolesByUsercode(paraMap);
	}

	@Override
	public List<TreeNode> findAllRoles() {
		return appUserRoleDao.findAllRoles();
	}
	
	@Override
	@Transactional
	public void saveUserRoles(String userCode, List<String> idsParam) {

		appUserRoleDao.deleteByUserCode(userCode);
		
		if(idsParam.size() > 0){
			
			String loginUserCode = Constants.LOGIN_USER_CODE();
			AppUserRoleModel model = null;
			
			List<AppUserRoleModel> lists = new ArrayList<AppUserRoleModel>();
			
			for (String id : idsParam) {
				
				model = new AppUserRoleModel();
				model.setUserRoleId(UuidUtils.getUuid());
				model.setUserId(userCode);
				model.setRoleId(id);
				model.setCreatedBy(loginUserCode);
				model.setUpdatedBy(loginUserCode);
				
				lists.add(model);
			}
			
			appUserRoleDao.saveUserRoleList(lists);
		}
		
	}

}
