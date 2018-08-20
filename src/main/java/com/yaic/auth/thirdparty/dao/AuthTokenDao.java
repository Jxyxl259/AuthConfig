package com.yaic.auth.thirdparty.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yaic.auth.thirdparty.model.AuthTokenModel;

public interface AuthTokenDao {
	
	Integer deleteByPrimaryKey(Integer tokenId);

	Integer insertSelective(AuthTokenModel authTokenModel);
	
	Integer updateByAppIdSelective(AuthTokenModel authTokenModel);
	
	AuthTokenModel selectByPrimaryKey(Integer tokenId);

	List<AuthTokenModel> selectByConditions(AuthTokenModel authTokenModel);

	AuthTokenModel selectOneByAppIdAndAppSecret(@Param("appId") String appId, @Param("appSecret") String appSecret);

}