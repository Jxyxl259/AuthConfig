package com.yaic.auth.thirdparty.dao;

import java.util.List;

import com.yaic.auth.thirdparty.model.AuthEncryptModel;

public interface AuthEncryptDao {
	
	int deleteByPrimaryKey(Integer authId);

	int insertSelective(AuthEncryptModel authEncryptModel);

	int updateByPrimaryKeySelective(AuthEncryptModel authEncryptModel);

	AuthEncryptModel selectByPrimaryKey(Integer authId);

	List<AuthEncryptModel> selectByConditions(AuthEncryptModel authEncryptModel);
	
	
	List<AuthEncryptModel> selectListsByAppid(String appid);

}