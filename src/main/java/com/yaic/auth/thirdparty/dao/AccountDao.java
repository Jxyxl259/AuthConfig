package com.yaic.auth.thirdparty.dao;

import java.util.List;
import java.util.Map;

import com.yaic.auth.thirdparty.model.AccountModel;

public interface AccountDao {

	List<AccountModel> getLists(AccountModel accountModel);

	AccountModel getOneByAppid(String appId);

	AccountModel getOneByAppCode(String appCode);

	AccountModel selectByPrimaryKey(Integer accountId);

	int insertSelective(AccountModel accountModel);

	int updateByPrimaryKeySelective(AccountModel accountModel);

	int deleteByPrimaryKey(Integer accountId);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	Integer deleteInfoByIds(Map<String, Object> paramMap);

	List<AccountModel> getAccountInfoByCodeOrName(AccountModel model);

}