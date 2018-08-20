package com.yaic.auth.interior.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.interior.dao.AppUserDao;
import com.yaic.auth.interior.dao.AppUserRoleDao;
import com.yaic.auth.interior.model.AppUserModel;
import com.yaic.auth.interior.service.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {
	
	@Autowired
	AppUserDao appUserDao;
	
	@Autowired
	AppUserRoleDao appUserRoleDao;

	
	
	@Override
	public Integer addInfo(AppUserModel dto) throws Exception {
		dto.setValidFlag(PublicParamters.VALID_FLAG_Y);
		return appUserDao.insertSelective(dto);
	}

	@Override
	public Integer updateInfo(AppUserModel dto) throws Exception {
		int resu = appUserDao.updateByPrimaryKeySelective(dto);
		return resu;
	}

	@Override
	public AppUserModel getInfoByUserCode(String userCode) throws Exception {
		return appUserDao.getInfoByUserCode(userCode);
	}

	@Override
	public AppUserModel selectByPrimaryKey(String userId) throws Exception {
		return appUserDao.selectByPrimaryKey(userId);
	}

	@Override
	public ArrayList<AppUserModel> getList(AppUserModel userDto) throws Exception {
		return  appUserDao.getList(userDto);
	}

	@Override
	@Transactional
	public int deleteList(List<String> ids) throws Exception {

		int count = 0; 
		for (String userId : ids) {
			
			AppUserModel model = appUserDao.selectByPrimaryKey(userId);
			//删除用户角色
			appUserRoleDao.deleteByUserCode(model.getUserCode());
			//删除用户
			appUserDao.deleteByPrimaryKey(userId);
			count++;
		}
		return count;
	}

	@Override
	public void buildPassword(AppUserModel userModel) {
		String newPassword = new SimpleHash(
				PublicParamters.algorithmName,
                userModel.getPassword(),
                ByteSource.Util.bytes(userModel.getUserCode() + PublicParamters.SALT),
                PublicParamters.hashIterations).toHex();
		userModel.setPassword(newPassword);
	}

}
