package com.yaic.auth.interior.dao;

import java.util.List;

import com.yaic.auth.interior.model.AppRoleModel;


public interface AppRoleDao {
	
    Integer deleteByPrimaryKey(String roleId);

    Integer addInfo(AppRoleModel roleModel);
    
    Integer updateInfo(AppRoleModel roleModel);

    AppRoleModel selectByPrimaryKey(String roleId);

   	List<AppRoleModel> getList(AppRoleModel roleDto);

   	String getMaxRoleId();
}