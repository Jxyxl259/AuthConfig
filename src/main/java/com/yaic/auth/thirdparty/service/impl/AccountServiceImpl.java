package com.yaic.auth.thirdparty.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.thirdparty.dao.AccountDao;
import com.yaic.auth.thirdparty.dao.AuthEncryptDao;
import com.yaic.auth.thirdparty.dao.AuthMappingDao;
import com.yaic.auth.thirdparty.dao.CallbackUrlDao;
import com.yaic.auth.thirdparty.dao.DataSourceDao;
import com.yaic.auth.thirdparty.dao.ProjectDao;
import com.yaic.auth.thirdparty.model.AccountModel;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.service.AccountService;


@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceServiceimpl.class);

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private DataSourceDao dataSourceDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private AuthMappingDao authMappingDao;
	@Autowired
	private CallbackUrlDao callbackUrlDao;
	@Autowired
	private AuthEncryptDao authEncryptDao;

	@Override
	public List<AccountModel> getList(AccountModel accountModel) throws Exception {
		return accountDao.getLists(accountModel);
	}

	@Override
	public AccountModel getOneByAccount(AccountModel accountModel) throws Exception{

		List<AccountModel> list = this.getList(accountModel);

		if (list.size() > 0) {
			logger.debug("if list.size > 0 ,get the first record");
			return list.get(0);
		}
		return null;
	}

	@Override
	public AccountModel getOneByAppid(String appId) throws Exception {
		return accountDao.getOneByAppid(appId);
	}

	@Override
	public AccountModel getOneByAppCode(String appCode)  throws Exception{
		return accountDao.getOneByAppCode(appCode);
	}

	@Override
	public AccountModel getOneByAccountId(Integer accountId)  throws Exception{
		return accountDao.selectByPrimaryKey(accountId);
	}

	@Override
	public Integer addInfo(AccountModel accountModel) throws Exception {
//		accountModel.setCreatedUser(Constants.LOGIN_USER_CODE());
//		accountModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
		accountModel.setValidFlag(PublicParamters.VALID_FLAG_Y);
		int resu = accountDao.insertSelective(accountModel);
		return resu;
	}

	@Override
	public Integer updateInfo(AccountModel accountModel)  throws Exception{
//		accountModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
		int resu = accountDao.updateByPrimaryKeySelective(accountModel);
		return resu;
	}

	@Override
	public Integer deleteInfo(Integer accountId) throws Exception {
		int result = 0;
		
		// 1.根据accountId查出所有，得到accountModel
		AccountModel accountModel = accountDao.selectByPrimaryKey(accountId);
		
		if (accountModel != null) {
			
			logger.info("delete account by accountId:{},accountName{}", accountModel.getAccountId(),accountModel.getAccountName());
			// 2.根据appId查出所有渠道信息，得到多dataSourceId
			List<DataSourceModel> dataSourcelists = dataSourceDao.getLists(new DataSourceModel(accountModel.getAppId()));
			logger.debug("account:{},dataSourcelists.size{}",accountModel.getAccountName(), dataSourcelists.size());
			
			if (dataSourcelists.size() > 0) {
				
				// 3.根据dataSourceId查出所有方案，得到projectId,authId
				for (DataSourceModel dataSourceIdTemp : dataSourcelists) {
					
					List<ProjectModel> projectLists = projectDao.getList(new ProjectModel(dataSourceIdTemp.getDataSourceId()));
					logger.debug("datasource:{},projectLists.size:{}",dataSourceIdTemp.getSourceName(), projectLists.size());
					
					if (projectLists.size() > 0) {
						
						// 4.更新删除
						for (ProjectModel model : projectLists) {
							
							callbackUrlDao.deleteByProjectId(model.getProjectId());
							authMappingDao.deleteByProjectId(model.getProjectId());
							
							if(model.getAuthId() != null)
								authEncryptDao.deleteByPrimaryKey(model.getAuthId());
							
							projectDao.deleteByPrimaryKey(model.getProjectId());
							logger.debug("delete callbackUrl,authMapping,authEncrypt,project by projectId{}",model.getProjectId());
						}
					}
					dataSourceDao.deleteByPrimaryKey(dataSourceIdTemp.getDataSourceId());
				}
			}
			int i = accountDao.deleteByPrimaryKey(accountId);
			if (i > 0)
				result = i;
			logger.info("accountModel 删除成功");
		}else{
			
			logger.info("delete account by accountId:{},accountModel is null",accountId);
		}
		return result;
	}

	@Override
	public Integer deleteInfoByIds(List<String> ids) throws Exception {
		int result = 0;
		if (ids.size() > 0) {
			for (String accountIdTemp : ids) {
				Integer accountId = Integer.valueOf(accountIdTemp);
				result = this.deleteInfo(accountId);
			}
		}
		return result;
	}

	@Override
	public List<AccountModel> getAccountInfoByCodeOrName(AccountModel model) throws Exception {
		return accountDao.getAccountInfoByCodeOrName(model);
	}
}
