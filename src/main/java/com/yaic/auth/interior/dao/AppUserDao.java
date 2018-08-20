package com.yaic.auth.interior.dao;

import java.util.ArrayList;

import com.yaic.auth.interior.model.AppUserModel;

public interface AppUserDao {
	
    int deleteByPrimaryKey(String userId);

    int insertSelective(AppUserModel record);

    int updateByPrimaryKeySelective(AppUserModel record);
    
    AppUserModel selectByPrimaryKey(String userId);

    AppUserModel getInfoByUserCode(String userCode);

	ArrayList<AppUserModel> getList(AppUserModel userDto);

}