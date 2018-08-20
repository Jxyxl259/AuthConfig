package com.yaic.auth.thirdparty.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.thirdparty.dao.AuthEncryptDao;
import com.yaic.auth.thirdparty.model.AuthEncryptModel;
import com.yaic.auth.thirdparty.service.AuthEncryptService;

@Service
public class AuthEncryptServiceImpl implements AuthEncryptService {
	// private static final Logger logger =
	// LoggerFactory.getLogger(AuthEncryptServiceImpl.class);
	@Autowired
	private AuthEncryptDao authEncryptDao;

	@Override
	public Integer addInfo(AuthEncryptModel model) throws Exception {
		model.setValidFlag(PublicParamters.VALID_FLAG_Y);
		return authEncryptDao.insertSelective(model);
	}

	@Override
	public List<AuthEncryptModel> getList(AuthEncryptModel authEncryptModel) throws Exception {
		List<AuthEncryptModel> list = authEncryptDao.selectByConditions(authEncryptModel);
		return list;
	}

	@Override
	public Integer updateInfo(AuthEncryptModel authEncryptModel) throws Exception {
//		authEncryptModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
		return authEncryptDao.updateByPrimaryKeySelective(authEncryptModel);
	}

	@Override
	public Integer deleteInfo(Integer authId) throws Exception {
		return authEncryptDao.deleteByPrimaryKey(authId);
	}

	@Override
	public AuthEncryptModel getOneByAuthId(Integer authId) throws Exception {
		return authEncryptDao.selectByPrimaryKey(authId);
	}

	@Override
	public List<AuthEncryptModel> selectByAppid(String appid) throws Exception {
		return authEncryptDao.selectListsByAppid(appid);
	}

}
