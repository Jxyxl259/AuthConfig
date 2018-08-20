package com.yaic.auth.thirdparty.dao;

import java.util.List;

import com.yaic.auth.thirdparty.model.AuthPubCodeModel;

public interface AuthPubCodeDao {
	List<AuthPubCodeModel> selectAll();

	List<AuthPubCodeModel> selectAllByConditions(AuthPubCodeModel authPubCodeModel);


	/** 
	* @Title: selectSmallTypeByServerType 
	* @Description: 根据服务类型获取对应的服务小类
	* //特殊方法
	* @param serverType
	* @return    
	* @return List<AuthPubCodeModel>  
	* @throws 
	*/
	List<AuthPubCodeModel> selectSmallTypeByServerType(AuthPubCodeModel authPubCodeModel);

}
