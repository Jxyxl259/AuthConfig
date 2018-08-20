package com.yaic.auth.thirdparty.dao;

import java.util.List;

import com.yaic.auth.thirdparty.dto.AccountAuthMappDto;
import com.yaic.auth.thirdparty.model.AccountAuthMappModel;

public interface AccountAuthMappDao {

	Integer insert(AccountAuthMappModel model);

	Integer deleteByPrimaryKey(Integer id);

	List<AccountAuthMappDto> getSingleAccountAuthMappLists(AccountAuthMappDto dto);

	List<AccountAuthMappDto> getAllHistoryMappLists(AccountAuthMappDto dto);
}