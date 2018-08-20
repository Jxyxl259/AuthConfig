package com.yaic.auth.interior.service;

import java.util.ArrayList;
import java.util.List;

import com.yaic.auth.interior.model.AppUserModel;

public interface AppUserService {


	/**
	 * 新增记录
	 * @param dto
	 * @return
	 */
	public Integer addInfo(AppUserModel dto) throws Exception;

	/**
	 * 修改记录信息
	 * @param dto
	 * @return
	 */
	public Integer updateInfo(AppUserModel dto) throws Exception;

	/**
	 * 根据主键编号删除数据
	 * @param userId
	 * @return
	 */
//	public Integer deleteInfo(List<AppUserModel> userList) throws Exception;

	/**
	 * 根据userCode精确查询对象
	 * @param userCode
	 * @return
	 */
	public AppUserModel getInfoByUserCode(String userCode) throws Exception;

	/**
	 * 根据userId查询对象信息
	 * @param userId
	 * @return
	 */
	public AppUserModel selectByPrimaryKey(String userId) throws Exception;
	
     /**
      * 获取用户列表
      * @param userDto
      */
	public ArrayList<AppUserModel> getList(AppUserModel userDto) throws Exception;

	public int deleteList(List<String> idsParam) throws Exception;

	
	/** 
	* @Title: buildPassword 
	* @Description: 生成密码
	* @param userModel    
	* @return void  
	* @throws 
	*/
	public void buildPassword(AppUserModel userModel);

	
}
