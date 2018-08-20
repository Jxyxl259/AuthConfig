package com.yaic.auth.thirdparty.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yaic.auth.thirdparty.dto.CallbackUrlViewDto;
import com.yaic.auth.thirdparty.model.CallbackUrlModel;

public interface CallbackUrlDao {
	int deleteByPrimaryKey(Integer callbackUrlId);

	int deleteByProjectId(Integer projectId);

	int insertSelective(CallbackUrlModel callbackUrlModel);

	CallbackUrlModel selectByPrimaryKey(Integer callbackUrlId);

	int updateByPrimaryKeySelective(CallbackUrlModel callbackUrlModel);

	List<CallbackUrlModel> selectByConditions(CallbackUrlModel callbackUrlModel);

	List<CallbackUrlViewDto> selectCallbackView(CallbackUrlModel callbackUrlModel);

	CallbackUrlModel selectOneByProjectIdAndCallbackMethod(@Param("projectId") Integer projectId,
			@Param("callbackMethod") String callbackMethod);

	/**
	 * 新增回调Url时 带上app_id
	 * @return
	 */
	String selectAppIdByProjectId(@Param("projectId") Integer projectId);
}