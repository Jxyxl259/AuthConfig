package com.yaic.auth.thirdparty.dao;

import java.util.List;

import com.yaic.auth.thirdparty.model.ServerModel;

public interface ServerDao {
	
    int deleteByPrimaryKey( Integer serverId );

    int insertSelective( ServerModel serverModel );

    int updateByPrimaryKeySelective( ServerModel serverModel );

    ServerModel selectOneByPrimaryKey( Integer serverId );
    
    List<ServerModel> selectByConditions(ServerModel serverModel);
    
    ServerModel selectOneByServerUrl(String serverUrl);
    
    List<ServerModel> selectAllView(ServerModel serverModel);
}