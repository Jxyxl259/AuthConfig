package com.yaic.auth.thirdparty.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.auth.thirdparty.dao.AccountAuthMappDao;
import com.yaic.auth.thirdparty.dao.AuthMappingDao;
import com.yaic.auth.thirdparty.dto.AccountAuthMappDto;
import com.yaic.auth.thirdparty.model.AccountAuthMappModel;
import com.yaic.auth.thirdparty.service.AccountAuthMappService;


@Service
public class AccountAuthMappServiceImpl implements AccountAuthMappService {

	private static final Logger logger = LoggerFactory.getLogger(AccountAuthMappServiceImpl.class);

	@Autowired
	private AccountAuthMappDao accountAuthMappDao;
	
	@Autowired
	private AuthMappingDao authMappingDao;

	@Override
	public List<AccountAuthMappDto> getSingleAccountAuthMappLists(AccountAuthMappDto dto) {
		logger.debug("");
		List<AccountAuthMappDto> lists = new ArrayList<AccountAuthMappDto>();
		lists = accountAuthMappDao.getSingleAccountAuthMappLists(dto);
		return lists;
	}

	@Override
	public Integer saveAccountAuthMappInfo(AccountAuthMappModel model) {
		return accountAuthMappDao.insert(model);
	}

	@Override
	public Integer deleteInfo(Integer id) {
		return accountAuthMappDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<AccountAuthMappDto> getAllHistoryMappLists(AccountAuthMappDto dto) {
		return accountAuthMappDao.getAllHistoryMappLists(dto);
	}

	@Override
	public Integer deleteInfoByIds(List<String> ids) throws Exception {
		int result = 0;
		if (ids.size() > 0) {
			for (String mappingIdTemp : ids) {
				Integer mappingId = Integer.valueOf(mappingIdTemp);
				result = authMappingDao.deleteByPrimaryKey(mappingId);
			}
		}
		return result;
	}
	
}
