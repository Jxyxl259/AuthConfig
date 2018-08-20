package com.yaic.auth.thirdparty.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.thirdparty.dao.AuthTokenDao;
import com.yaic.auth.thirdparty.model.AuthTokenModel;
import com.yaic.auth.thirdparty.service.AuthTokenService;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

	@Autowired
	private AuthTokenDao authTokenDao;

	@Override
	public List<AuthTokenModel> getList(AuthTokenModel authTokenModel) throws Exception {
		List<AuthTokenModel> list = authTokenDao.selectByConditions(authTokenModel);
		return list;
	}

	@Override
	public Integer addInfo(AuthTokenModel authTokenModel) throws Exception {
		authTokenModel.setValidFlag(PublicParamters.VALID_FLAG_Y);
		return authTokenDao.insertSelective(authTokenModel);
	}

	@Override
	public Integer updateInfo(AuthTokenModel authTokenModel) throws Exception {
		return authTokenDao.updateByAppIdSelective(authTokenModel);
	}

	@Override
	public Integer deleteInfo(Integer tokenId)  throws Exception{
		return authTokenDao.deleteByPrimaryKey(tokenId);
	}

//	@Override
//	public AuthTokenModel getOneByAuthToken(AuthTokenModel authTokenModel) throws Exception {
//		List<AuthTokenModel> list = authTokenDao.selectByConditions(authTokenModel);
//		if (list.size() > 0) {
//			return list.get(0);
//		}
//		return null;
//	}

	@Override
	public AuthTokenModel getOneByAppIdAndAppSecret(String appId, String appSecret)  throws Exception{
		return authTokenDao.selectOneByAppIdAndAppSecret(appId, appSecret);
	}

	@Override
	public Integer deleteInfoByIds(List<String> tokenIds)  throws Exception{
		int result = 0;
		if (tokenIds.size() > 0) {
			for (String tokenId : tokenIds) {
				Integer tokenIdTemp = Integer.valueOf(tokenId);
				result = this.deleteInfo(tokenIdTemp);
			}
		}
		return result;
	}

	@Override
	public AuthTokenModel getOneByTokenId(Integer tokenId) throws Exception {
		return authTokenDao.selectByPrimaryKey(tokenId);
	}

}
