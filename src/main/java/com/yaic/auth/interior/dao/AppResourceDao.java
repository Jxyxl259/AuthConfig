package com.yaic.auth.interior.dao;

import java.util.List;

import com.yaic.auth.interior.model.AppResourceModel;

public interface AppResourceDao {

    int insertSelective(AppResourceModel record);

    int deleteByPrimaryKey(String resourceId);
    
    int updateByPrimaryKeySelective(AppResourceModel record);
    
    AppResourceModel getInfoByResourceId(String resourceId);

    AppResourceModel getInfoByResourceName(String resourceName);

	List<AppResourceModel> findAllResourcesByUserId(String loginUserId);
	
	String getMaxResourceIdByParentId(String parentResourceId);
    
}