package com.yaic.auth.external.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.common.UuidUtils;
import com.yaic.auth.external.common.ResponseParameters;
import com.yaic.auth.external.common.ResultMessage;
import com.yaic.auth.external.dto.responsedto.AccountExternalDto;
import com.yaic.auth.external.service.ExternalConfigService;import com.yaic.auth.thirdparty.dao.AuthPubCodeDao;
import com.yaic.auth.thirdparty.dao.ServerDao;
import com.yaic.auth.thirdparty.dto.AccountDto;
import com.yaic.auth.thirdparty.dto.AuthEncryptDto;
import com.yaic.auth.thirdparty.dto.AuthMappingDto;
import com.yaic.auth.thirdparty.dto.CallbackUrlDto;
import com.yaic.auth.thirdparty.dto.DataSourceDto;
import com.yaic.auth.thirdparty.dto.ProjectDto;
import com.yaic.auth.thirdparty.dto.ServerDto;
import com.yaic.auth.thirdparty.model.AccountModel;
import com.yaic.auth.thirdparty.model.AuthEncryptModel;
import com.yaic.auth.thirdparty.model.AuthMappingModel;
import com.yaic.auth.thirdparty.model.AuthPubCodeModel;
import com.yaic.auth.thirdparty.model.CallbackUrlModel;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.model.ServerModel;
import com.yaic.auth.thirdparty.service.AccountService;
import com.yaic.auth.thirdparty.service.AuthEncryptService;
import com.yaic.auth.thirdparty.service.AuthMappingService;
import com.yaic.auth.thirdparty.service.CallbackUrlService;
import com.yaic.auth.thirdparty.service.DataSourceService;
import com.yaic.auth.thirdparty.service.ProjectService;

@Service
public class ExternalConfigServiceImpl implements ExternalConfigService {

	private static final Logger logger = LoggerFactory.getLogger(ExternalConfigServiceImpl.class);

	
	private static String createdUser;

	@Autowired
	AccountService accountService;

	@Autowired
	DataSourceService dataSourceService;

	@Autowired
	ProjectService projectService;

	@Autowired
	AuthMappingService authMappService;

	@Autowired
	CallbackUrlService callbackUrlService;

	@Autowired
	AuthEncryptService authEncryptService;

	@Autowired
	ServerDao serverDao;

	@Autowired
	private AuthPubCodeDao authPubCodeDao;

	@Override
	public String generateAppid() throws Exception {

		String uuid = "";

		// 判断uuid在token字段中唯一
		AccountModel model = null;

		while (true) {

			uuid = UuidUtils.getUuid();

			model = new AccountModel();
			model.setAppId(uuid);
			model = accountService.getOneByAccount(model);

			if (model == null) {
				break;
			}
		}

		return uuid;
	}

	@Override
	public AccountDto getAccount(String appId) throws Exception {

		logger.info("根据appId:{}得到对应的账户信息.",appId);
		AccountDto accountDto = new AccountDto();

		// 获取account信息
		AccountModel model = accountService.getOneByAppid(appId);
		accountDto = new AccountDto(model);

		// /*
		// * 获取dataSource信息
		// */
		// DataSourceModel datasourceModel = new DataSourceModel();
		// datasourceModel.setAppId(appId);
		// List<DataSourceModel> dataSourceList =
		// dataSourceService.getList(datasourceModel);
		//
		// //根据渠道id获取对应的方案信息
		// if(dataSourceList.size() > 0){
		//
		// List<DataSourceDto> dtoList = new ArrayList<DataSourceDto>();
		// DataSourceDto sourceDto = null;
		// for (DataSourceModel sourceModel : dataSourceList) {
		//
		// sourceDto = this.getDatasource(sourceModel);
		//
		// dtoList.add(sourceDto);
		// }
		//
		// accountDto.setDataSourList(dtoList);
		// }
		logger.info("得到account结果:{}",accountDto.toString());
		return accountDto;
	}

	@Override
	public DataSourceDto getDatasource(DataSourceModel model) throws Exception {

		// 获取渠道基本信息
		DataSourceDto dto = new DataSourceDto(model);
		
		//获取渠道对应的用户信息
		AccountModel accountModel1 = accountService.getOneByAppid(model.getAppId());
		AccountDto accountDto1  = new AccountDto(accountModel1 );
		dto.setAccountDto(accountDto1);
		
		//同渠道下有效方案个数
		Integer projectCounts = projectService.getProjectCountsBySourceId(model.getDataSourceId());
		dto.setProjectCounts(projectCounts);
		logger.info("获取同渠道下有效方案个数:{}",projectCounts);

		// 获取渠道默认方案基本信息
		ProjectModel projectModel = projectService.getDefaultProjectBySourceid(model.getDataSourceId());
		
		if(projectModel == null){
			return dto;
		}

		// 根据方案编号获取对应鉴权信息
		AuthEncryptDto authEncryDto = new AuthEncryptDto(new AuthEncryptModel(), "noId");
		if(projectModel != null){
			AuthEncryptModel authEncryModel = authEncryptService.getOneByAuthId(projectModel.getAuthId());
			if(authEncryModel != null)
				authEncryDto = new AuthEncryptDto(authEncryModel, "noId");
		}
		dto.setAuthEncryptDto(authEncryDto);
		
		// 根据projectId获取对应方案与服务关系集合
		List<AuthMappingDto> authmappList = authMappService.getMappAndServerByProId(projectModel.getProjectId());
		dto.setAuthMappList(authmappList);

		// 根据projectId获取对应回调集合
		CallbackUrlModel callbackUrlModel = new CallbackUrlModel();
		callbackUrlModel.setProjectId(projectModel.getProjectId());
		List<CallbackUrlModel> callbackUrlList = callbackUrlService.getList(callbackUrlModel);

		if (callbackUrlList.size() > 0) {

			List<CallbackUrlDto> dtoList = new ArrayList<CallbackUrlDto>();
			CallbackUrlDto callbackDto = null;

			for (CallbackUrlModel urlModel : callbackUrlList) {

				callbackDto = new CallbackUrlDto(urlModel);

				dtoList.add(callbackDto);
			}

			dto.setCallbackList(dtoList);
		}
		
		return dto;
	}

	@Override
	public ProjectDto getProject(ProjectModel model) throws Exception {

		logger.info("getProject(),获取方案信息接口.begin---");
		// 添加方案基本信息
		ProjectDto dto = new ProjectDto(model);

		//同渠道下有效方案个数
		Integer projectCounts = projectService.getProjectCountsBySourceId(model.getDataSourceId());
		dto.setProjectCounts(projectCounts);
		logger.info("获取同渠道下有效方案个数:{}",projectCounts);
		
		// 根据projectId获取对应方案与服务关系集合
		logger.info("获取方案与服务关系信息集合");
		List<AuthMappingDto> authmappList = authMappService.getMappAndServerByProId(model.getProjectId());
		logger.info("获取方案与服务关系信息集合条数:{}",authmappList.size());
		dto.setAuthmappList(authmappList);

		// 根据projectId获取对应回调集合
		CallbackUrlModel callbackUrlModel = new CallbackUrlModel();
		callbackUrlModel.setProjectId(model.getProjectId());
		logger.info("获取方案与回调关系信息集合");
		List<CallbackUrlModel> callbackUrlList = callbackUrlService.getList(callbackUrlModel);
		logger.info("获取方案与回调关系信息集合条数:{}",callbackUrlList.size());

		if (callbackUrlList.size() > 0) {

			List<CallbackUrlDto> dtoList = new ArrayList<CallbackUrlDto>();
			CallbackUrlDto callbackDto = null;

			for (CallbackUrlModel urlModel : callbackUrlList) {

				callbackDto = new CallbackUrlDto(urlModel);

				dtoList.add(callbackDto);
			}

			dto.setCallbackList(dtoList);
		}

		// 根据projectId获取对应权限配置信息
		logger.info("获取方案鉴权信息");
		AuthEncryptModel authEncryptModel = authEncryptService.getOneByAuthId(model.getAuthId());
		AuthEncryptDto authEncryptDto = new AuthEncryptDto(authEncryptModel, "noId");
		dto.setAuthEncryptDto(authEncryptDto);

		// 根据dataSourceId获取对应渠道基本信息
		logger.info("获取渠道基本信息");
		DataSourceModel dataSourceModel = dataSourceService.getOneByDataSourceId(model.getDataSourceId());
		DataSourceDto dataSourceDto = new DataSourceDto(dataSourceModel);

		//获取渠道对应的用户信息
		logger.info("获取渠道所属账号基本信息");
		AccountModel accountModel1 = accountService.getOneByAppid(dataSourceModel.getAppId());
		AccountDto accountDto1  = new AccountDto(accountModel1 );
		dataSourceDto.setAccountDto(accountDto1);
		
		//获取渠道对应的鉴权信息
		logger.info("获取默认方案鉴权信息");
		ProjectModel defaultProject = projectService.getDefaultProjectBySourceid(dataSourceModel.getDataSourceId());
		AuthEncryptModel authEncryptModel1 = authEncryptService.getOneByAuthId(defaultProject.getAuthId());
		AuthEncryptDto authEncryptDto1 = new AuthEncryptDto(authEncryptModel1, "noId");
		dataSourceDto.setAuthEncryptDto(authEncryptDto1);
		
		// 根据projectId获取对应方案与服务关系集合
		logger.info("渠道默认方案与服务关系信息");
		List<AuthMappingDto> authmappList1 = authMappService.getMappAndServerByProId(defaultProject.getProjectId());
		dataSourceDto.setAuthMappList(authmappList1);
		logger.info("渠道默认方案与服务关系信息,条数:{}",authmappList1.size());

		logger.info("渠道默认方案与回调关系信息");
		// 根据projectId获取对应回调集合
		CallbackUrlModel callbackUrlModel1 = new CallbackUrlModel();
		callbackUrlModel1.setProjectId(defaultProject.getProjectId());
		List<CallbackUrlModel> callbackUrlList1 = callbackUrlService.getList(callbackUrlModel1);
		logger.info("渠道默认方案与回调关系信息,条数:{}",callbackUrlList1.size());

		if (callbackUrlList1.size() > 0) {

			List<CallbackUrlDto> dtoList = new ArrayList<CallbackUrlDto>();
			CallbackUrlDto callbackDto = null;

			for (CallbackUrlModel urlModel : callbackUrlList1) {

				callbackDto = new CallbackUrlDto(urlModel);

				dtoList.add(callbackDto);
			}

			dataSourceDto.setCallbackList(dtoList);
		}
		
		dto.setDataSourceDto(dataSourceDto);
		logger.info("getProject(),获取方案信息接口.end--dto:{}-",JSON.toJSON(dto));
		return dto;
	}

	@Override
	public AccountExternalDto getAccountList(AccountModel accountModel,Integer pageNum,Integer pageSize) throws Exception {

		List<AccountDto> accountLists = new ArrayList<AccountDto>();
		Page<AccountDto> pageInfo = PageHelper.startPage(pageNum,pageSize);
		List<AccountModel> lists = accountService.getList(accountModel);

		AccountDto dto = null;
		for (AccountModel model : lists) {
			dto = new AccountDto(model);
			accountLists.add(dto);
		}
		
		AccountExternalDto extDto = new AccountExternalDto(
				pageNum,pageSize,String.valueOf(pageInfo.getTotal()),pageInfo.getPages(),accountLists);
		return extDto;
	}

	@Override
	public List<String> getAuthTypes() throws Exception {

		List<String> lists = new ArrayList<>();
		lists.add(PublicParamters.AUTH_TYPE_IP_1);
		lists.add(PublicParamters.AUTH_TYPE_MD5_1);
		lists.add(PublicParamters.AUTH_TYPE_NO_AUTH);
		lists.add(PublicParamters.AUTH_TYPE_RSA_1);
		lists.add(PublicParamters.AUTH_TYPE_COMMON_AUTH);
		
		return lists;
	}
	
	@Override
	public List<String[]> getAuthTypes1() throws Exception {
		
		List<String[]> lists1 = getValuesByParameterType("AUTH_TYPE");
		
		return lists1;
	}

	@Override
	public List<String> getEncryptTypes() throws Exception {

		List<String> lists = new ArrayList<>();
		lists.add(PublicParamters.ENCRYPT_TYPE_NO_ENCRYPT);
		lists.add(PublicParamters.ENCRYPT_TYPE_AES_1);
		lists.add(PublicParamters.ENCRYPT_TYPE_RSA_1);
		lists.add(PublicParamters.ENCRYPT_TYPE_HISTORY_ENCRYPT);

		return lists;
	}

	@Override
	public List<String> getServerEnvTypes() throws Exception {

		List<String> lists = new ArrayList<>();
		lists.add(PublicParamters.SERVER_ENV_TYPE_DEV);
		lists.add(PublicParamters.SERVER_ENV_TYPE_SIT);
		lists.add(PublicParamters.SERVER_ENV_TYPE_UAT);
		lists.add(PublicParamters.SERVER_ENV_TYPE_PROD);

		return lists;
	}

//	@Override
//	public List<String> getServerTypes() throws Exception {
//		List<String> lists = new ArrayList<String>();
//		
//		AuthPubCodeModel authPubCodeModel = new AuthPubCodeModel();
//		authPubCodeModel.setParameterType("SERVER_TYPE");
//		authPubCodeModel.setValidFlag(PublicParamters.VALID_FLAG_Y);
//		
//		List<AuthPubCodeModel> authPubCodeModelList = authPubCodeDao.selectAllByConditions(authPubCodeModel);
//		
//		if (authPubCodeModelList.size() > 0) {
//			for (AuthPubCodeModel authPubCodeModelTemp : authPubCodeModelList) {
//				lists.add(authPubCodeModelTemp.getParameterValue());
//			}
//		}
//		return lists;
//	}
	
//	@Override
//	public List<String> getAllServerSmallTypes() throws Exception {
//		
//		return authPubCodeDao.selectSmallTypeByServerType("%");
//	}
	
	@Override
	public List<String[]> getServerSmallTypes(String serverType) throws Exception {
		List<String[]> lists = new ArrayList<String[]>();
		
		AuthPubCodeModel model = new AuthPubCodeModel();
		model.setParameterType("SERVER_MAPPING_SMALL_TYPE");
		model.setParameterValue(serverType);

		List<AuthPubCodeModel> authPubCodeModelList = authPubCodeDao.selectSmallTypeByServerType(model);
		
		if (authPubCodeModelList.size() > 0) {
			String[] serverSmallTypes = null;
			for (AuthPubCodeModel authPubCodeModelTemp : authPubCodeModelList) {
				serverSmallTypes = new String[2];
				serverSmallTypes[0] = authPubCodeModelTemp.getParameterValue();
				serverSmallTypes[1] = authPubCodeModelTemp.getParameterDescribe();
				lists.add(serverSmallTypes);
			}
		}
		return lists;
	}

	@Override
	public List<String> getCallbackTypes() throws Exception {

		List<String> lists = new ArrayList<>();
		lists.add(PublicParamters.CALL_BACK_TYPE_PAY_CALLBACK);
		lists.add(PublicParamters.CALL_BACK_TYPE_CANCEL_INSURANCE_CALLBACK);

		return lists;
	}

	@Override
	public List<ServerDto> getServerList(String envType, String serverType,String serverSmallType) throws Exception {

		List<ServerDto> serverLists = new ArrayList<ServerDto>();
		
		ServerModel serverModel = new ServerModel();
		serverModel.setServerEnv(envType);
		serverModel.setServerType(serverSmallType);
		serverModel.setServerStatus(PublicParamters.SERVER_STATUS_1);
		serverModel.setCreatedUser(PublicParamters.SERVER_STATUS_9);

		List<ServerModel> lists = serverDao.selectByConditions(serverModel);

		ServerDto dto = null;
		for (ServerModel model : lists) {
			dto = new ServerDto(model);
			serverLists.add(dto);
		}

		return serverLists;
	}

	@Override
	public List<String> getInterfaceTypes() throws Exception {

		List<String> lists = new ArrayList<>();
		lists.add(PublicParamters.REQUEST_TYPE_DATA_SOURCE);
		lists.add(PublicParamters.REQUEST_TYPE_PROJECT);
		lists.add(PublicParamters.REQUEST_TYPE_HISTORY_COMMON);
		lists.add(PublicParamters.REQUEST_TYPE_HISTORY_CUSTOM);
		lists.add(PublicParamters.REQUEST_TYPE_HISTORY_ENCRYPT);

		return lists;
	}

	@Override
	@Transactional
	public ResultMessage addProject(ProjectDto dto) throws Exception {

		logger.info("addProject(),添加方案,begin----");
		ResultMessage result = null;
		if(StringUtils.isBlank(dto.getCreatedUser())) {
			createdUser = PublicParamters.PDMS_DEFAULT_CREATED_USER;
		}else {
			createdUser = dto.getCreatedUser();
		}
		logger.info("addProject(),createdUser:{}",createdUser);

		try {

			//数据校验
			logger.info("方案code校验,begin---");
			result = checkRight(dto);
			if(result != null) return result;
			
			DataSourceDto sourceDto = dto.getDataSourceDto();
			
			logger.info("addProject(),添加方案,projectCode:{}",dto.getProjectCode());
			// 验证request_url正确性
			logger.info("request_url校验,begin---");
			if(dto.getAuthmappList() != null && dto.getAuthmappList().size() >= 0){
				logger.info("方案服务关系数据条数:{}",dto.getAuthmappList().size());
				String message = judgeRequestUrl(dto.getAuthmappList(), sourceDto.getAccountDto().getAppId(), null, null,"add");
				if (StringUtils.isNotBlank(message)) {
					logger.info("request_url校验失败,逻辑结束");
					return result = new ResultMessage(ResponseParameters.CODE_0022, message, null);
				}
			}
			
			//判断datasource1
			DataSourceModel sourceModel = null;
			if(StringUtils.isBlank(sourceDto.getDataSource())){	//无sourceCode
				logger.info("渠道code不可为空,逻辑结束");
				return result = new ResultMessage(ResponseParameters.CODE_0021, ResponseParameters.MSG_0021, null);
			}
			
			//有sourceId
			if(sourceDto.getDataSourceId() != null && StringUtils.isNotBlank(sourceDto.getDataSourceId().toString())){
				logger.info("渠道编号不为空,根据渠道code:{},得到对应渠道信息",sourceDto.getDataSource());
				sourceModel = dataSourceService.getOneByDataSourceCode(sourceDto.getDataSource());
				if(sourceModel != null && !sourceModel.getDataSourceId().equals(sourceDto.getDataSourceId())){
					logger.info("渠道编号不为空,但是数据库中渠道编号与json中编号不一致,渠道code在数据库其他数据中已存在,逻辑结束");
					return result = new ResultMessage(ResponseParameters.CODE_0018, ResponseParameters.MSG_0018, null);
				}
				
				logger.info("渠道编号不为空,根据渠道编号得到对应渠道信息");
				sourceModel = dataSourceService.getOneByDataSourceId(sourceDto.getDataSourceId());
			}else{//无sourceId
				// 验证DataSourceCode唯一性
				logger.info("渠道编号为空,根据渠道code:{}得到对应渠道信息",sourceDto.getDataSource());
				sourceModel = dataSourceService.getOneByDataSourceCode(sourceDto.getDataSource());
				if(sourceModel != null){
					logger.info("渠道信息不为空,表明渠道code在数据库其他数据中已存在,逻辑结束");
					return result = new ResultMessage(ResponseParameters.CODE_0018, ResponseParameters.MSG_0018, null);
				}
			}

			if (sourceModel != null) {

				logger.info("渠道信息不为空,修改渠道基本信息");
				if(!sourceDto.getDataSource().equals(sourceModel.getDataSource())
						|| !sourceDto.getSourceName().equals(sourceModel.getSourceName())){
					logger.info("渠道code:{}和渠道名称:{}有变动,修改渠道基本信息",sourceDto.getDataSource(),sourceDto.getSourceName());
					sourceModel.setDataSource(sourceDto.getDataSource());
					sourceModel.setSourceName(sourceDto.getSourceName());
					sourceModel.setUpdatedUser(createdUser);
					dataSourceService.updateInfo(sourceModel);
				}
			}else{
				logger.info("渠道信息为空,添加渠道相关的所有信息");
				sourceModel = addDatasourceRelevantInfo(sourceDto);
			}

			// 二.添加方案相关信息
			// 1.得到鉴权信息
			logger.info("添加方案对应鉴权基本信息begin----");
			AuthEncryptModel authEncryModelPro = addAuthEncryptInfo(dto.getAuthEncryptDto(),sourceModel.getDataSourceId());

			// 2.方案信息入库
			logger.info("添加方案基本信息begin----");
			AccountModel accoModel = accountService.getOneByAppid(sourceModel.getAppId());
			ProjectModel projectModel1 = addProjectBasicInfo(dto.getProjectCode(), dto.getProjectName(),
					sourceModel.getDataSourceId(), authEncryModelPro.getAuthId(), 
					PublicParamters.DEFAULT_PROJECT_N,accoModel.getAccountId());

			Integer projectId = projectModel1.getProjectId();

			// 4.回调信息入库
			logger.info("添加方案与回调关系信息begin----");
			addCallbackUrlInfo(dto.getCallbackList(), projectId);

			// 5.服务和方案关系入库
			logger.info("添加方案与服务关系信息begin----");
			addAuthMappInfo(dto.getAuthmappList(), projectId);

			result = new ResultMessage(ResponseParameters.CODE_0000, ResponseParameters.MSG_0000, null);
		} catch (Exception e) {
			logger.error("添加方案逻辑出现异常,end----");
			result = new ResultMessage(ResponseParameters.CODE_0016, ResponseParameters.MSG_0016, null);
		}

		logger.info("添加方案相关信息result:{}",JSON.toJSON(result));
		return result;
	}

	private DataSourceModel addDatasourceRelevantInfo(DataSourceDto sourceDto) throws Exception {
		
		// 一.添加JSON中渠道相关信息
		// 1.添加账户信息
		logger.info("addDatasourceRelevantInfo(),添加渠道相关信息,begin---");
		AccountDto acctDto = sourceDto.getAccountDto();
		if(StringUtils.isBlank(acctDto.getAppCode()))
			acctDto.setAppCode(sourceDto.getDataSource());
		if(StringUtils.isBlank(acctDto.getAccountName()))
			acctDto.setAccountName(sourceDto.getSourceName());
		logger.info("添加账号基本信息begin----");
		AccountModel accountModel = addAccountInfo(acctDto);

		// 2.添加渠道基本信息
		logger.info("添加渠道基本信息begin----");
		DataSourceModel sourceModel = addDatasourceInfo(sourceDto, accountModel.getAppId());

		// 3.添加渠道鉴权信息
		logger.info("添加渠道鉴权信息begin----");
		AuthEncryptModel authEncryModel = addAuthEncryptInfo(sourceDto.getAuthEncryptDto(),sourceModel.getDataSourceId());

		// 4.添加默认方案
		String projectName = PublicParamters.DEFAULT_PROJECT_NAME;
		String projectCode = sourceModel.getDataSource();

		logger.info("添加默认方案基本信息begin----");
		ProjectModel defualtProject = addProjectBasicInfo(projectCode, projectName, sourceModel.getDataSourceId(), authEncryModel.getAuthId(),
				PublicParamters.DEFAULT_PROJECT_Y,accountModel.getAccountId());
		
		// 5.回调信息入库
		logger.info("添加默认方案与回调关系信息begin----");
		addCallbackUrlInfo(sourceDto.getCallbackList(),defualtProject.getProjectId());
		
		// 6.服务和方案关系入库
		logger.info("添加默认方案与服务关系信息begin----");
		addAuthMappInfo(sourceDto.getAuthMappList(), defualtProject.getProjectId());
		
		logger.info("添加渠道相关所有信息成功.end---");
		return sourceModel;
	}

	private ResultMessage checkRight(ProjectDto dto) throws Exception {
		
		ResultMessage result = null;
		//验证方案code 是否唯一
		if (StringUtils.isBlank(dto.getProjectCode())) {
			logger.info("方案CODE为空,projectCode:{}",dto.getProjectCode());
			result  = new ResultMessage(ResponseParameters.CODE_0013, ResponseParameters.MSG_0013, null);
			return result;
		}

		// 先得到projectCode,验证唯一性
		ProjectModel model1 = projectService.getOneByProjectCode(dto.getProjectCode().trim());
		if (model1 != null) {
			logger.info("方案CODE已存在,projectCode:{}",dto.getProjectCode());
			result = new ResultMessage(ResponseParameters.CODE_0017, ResponseParameters.MSG_0017, null);
			return result;
		}
		return result;
	}

	@Override
	public String judgeRequestUrl(List<AuthMappingDto> authmappList, String appId, Integer datasourceId,
			Integer projectId, String type) throws Exception {

		StringBuffer result = new StringBuffer();
		String requestType = "";
		String requestUrl = "";
		AuthMappingModel model = null;
		List<AuthMappingModel> lists = null;
		
		if(authmappList == null)
			return result.toString();
		
		for (AuthMappingDto dto : authmappList) {

			lists = new ArrayList<AuthMappingModel>();
			// 判断接口类型
			requestType = dto.getRequestType();
			requestUrl = dto.getRequestUrl();
			model = new AuthMappingModel();

			// 1.自定义,加密,原始 全表唯一
			if (PublicParamters.REQUEST_TYPE_HISTORY_CUSTOM.equals(requestType) ||
					PublicParamters.REQUEST_TYPE_HISTORY_COMMON.equals(requestType) ||
					PublicParamters.REQUEST_TYPE_HISTORY_ENCRYPT.equals(requestType)) {

				model.setRequestUrl(dto.getRequestUrl());
				lists = authMappService.getList(model);
				if (lists.size() > 0) {
					if(("edit".equals(type) && !lists.get(0).getMappingId().equals(dto.getMappingId())
							|| "add".equals(type)))
						result.append("接口地址 " + dto.getRequestUrl() + "需全局唯一		");
				}
			} else {

				// 2.方案 本方案下唯一
				if (PublicParamters.REQUEST_TYPE_PROJECT.equals(requestType) && projectId != null) {
					//根据ID的类型和requestUrl得到对应的服务关系集合
					lists = authMappService.getAuthMappListByReqUrlAndIdType(requestUrl,"project",projectId);
				}
				// 3.渠道或者加密       渠道下所有方案中唯一
				else if ((PublicParamters.REQUEST_TYPE_DATA_SOURCE.equals(requestType))	&& datasourceId != null) {
					//根据ID的类型和requestUrl得到对应的服务关系集合
					lists = authMappService.getAuthMappListByReqUrlAndIdType(requestUrl,"datasource",datasourceId);
				}
				
				if(lists.size() > 0){
					
					if(("edit".equals(type) && !lists.get(0).getMappingId().equals(dto.getMappingId())
							|| "add".equals(type)) || lists.size() > 1){
						
						if (PublicParamters.REQUEST_TYPE_PROJECT.equals(requestType)){
							result.append("接口地址 " + dto.getRequestUrl() + "需在该方案中唯一		");
						}else{
							result.append("接口地址 " + dto.getRequestUrl() + "需在该渠道下的所有方案中唯一		");
						}
						break;
					}
				}
				
			}
		}
		logger.info("requestUrl验证结果:result:{}",result.toString());
		return result.toString();
	}

	@Override
	@Transactional
	public ResultMessage editProject(ProjectDto dto) throws Exception {

		logger.info("editProject(),编辑方案信息接口.begin---");
		ResultMessage result = new ResultMessage();
		if(StringUtils.isBlank(dto.getUpdatedUser())) {
			createdUser = PublicParamters.PDMS_DEFAULT_CREATED_USER;
		}else {
			createdUser = dto.getUpdatedUser();
		}
		logger.info("editProject(),createdUser:{}",createdUser);
		
		try {
			DataSourceDto sourceDto = dto.getDataSourceDto();
			
			if(dto.getProjectId() == null || StringUtils.isBlank(dto.getProjectId().toString())){	//无projectId
				logger.info("方案编号为空,逻辑结束");
				return result = new ResultMessage(ResponseParameters.CODE_0025, ResponseParameters.MSG_0025, null);
			}
			if (StringUtils.isBlank(dto.getProjectCode())) {	//无projectCode
				logger.info("方案code为空,逻辑结束");
				return result = new ResultMessage(ResponseParameters.CODE_0019, ResponseParameters.MSG_0019, null);
			}
			if(StringUtils.isBlank(sourceDto.getDataSource())){	//无sourceCode
				logger.info("渠道code为空,逻辑结束");
				return result = new ResultMessage(ResponseParameters.CODE_0021, ResponseParameters.MSG_0021, null);
			}

			logger.info("editProject(),编辑方案,projectCode:{}",dto.getProjectCode());
			// 先得到projectCode,验证唯一性
			logger.info("根据方案code:{},得到对应的方案信息",dto.getProjectCode());
			ProjectModel projectModel = projectService.getOneByProjectCode(dto.getProjectCode().trim());
			if (projectModel != null && !projectModel.getProjectId().equals(dto.getProjectId())) {
				logger.info("方案信息不为空,同时数据库中方案编号与json中不一致,逻辑结束");
				return result = new ResultMessage(ResponseParameters.CODE_0017, ResponseParameters.MSG_0017, null);
			}else{
				logger.info("根据方案编号:{}得到对应的方案信息",dto.getProjectId());
				projectModel = projectService.getOneByProjectId(dto.getProjectId());
			}
			
			if(projectModel == null){
				logger.info("方案编号在数据库中不存在,projectId:{}",dto.getProjectId());
				return result = new ResultMessage(ResponseParameters.CODE_0025, ResponseParameters.MSG_0025, null);
			}


			// 验证DataSourceCode唯一性
			logger.info("根据渠道code:{}得到对应的渠道信息",sourceDto.getDataSource());
			DataSourceModel sourceModel = dataSourceService.getOneByDataSourceCode(sourceDto.getDataSource().trim());
			
			boolean isNotAddDatasource = true;
			if(sourceDto.getDataSourceId() == null || StringUtils.isBlank(sourceDto.getDataSourceId().toString())){		//无sourceId
				
				if(sourceModel == null){
					logger.info("json中渠道编号为空,同时渠道code在数据库中不存在,表明需新增渠道信息");
					//添加source信息
					logger.info("新增渠道begin----");
					sourceModel = addDatasourceRelevantInfo(sourceDto);
					isNotAddDatasource = false;
				}else{		//sourceCode已存在
					logger.error("json中渠道编号为空,但渠道code在数据库中已存在,逻辑结束");
					return result = new ResultMessage(ResponseParameters.CODE_0018, ResponseParameters.MSG_0018, null);
				}
			}else{	//有sourceId
				if(sourceModel == null || sourceDto.getDataSourceId().equals(sourceModel.getDataSourceId())){	//sourceCode唯一
					//修改source基本信息
					logger.info("json中渠道编号不为空,同时渠道code在数据库中不存在,或者数据库中渠道编号与json中一致,逻辑正确");
					logger.info("渠道基本信息begin----");
					sourceModel = dataSourceService.getOneByDataSourceId(sourceDto.getDataSourceId());
					sourceModel.setDataSourceId(sourceDto.getDataSourceId());
					sourceModel.setDataSource(sourceDto.getDataSource());
					sourceModel.setSourceName(sourceDto.getSourceName());
					sourceModel.setUpdatedUser(createdUser);
					dataSourceService.updateInfo(sourceModel);
					logger.info("渠道基本信息成功,end----");
				}else{
					logger.error("json中渠道编号不为空,但json中渠道编号与数据库中不一致,逻辑结束");
					return result = new ResultMessage(ResponseParameters.CODE_0018, ResponseParameters.MSG_0018, null);
				}
			}
			
			// 验证渠道中appId与账户信息中appId是否一致
			if (sourceModel != null && !sourceModel.getAppId().equals(sourceDto.getAccountDto().getAppId())) {
				logger.error("渠道中appId与账户信息中appId不一致,逻辑结束");
				result = new ResultMessage(ResponseParameters.CODE_0020, ResponseParameters.MSG_0020, null);
				return result;
			}
			
			//判断渠道下是否只有一个方案,是的话方可进行修改操作
			logger.info("根据渠道编号得到所属的所有方案信息集合");
			List<ProjectModel> list1 = projectService.getList(new ProjectModel(sourceModel.getDataSourceId()));
			if(list1 == null){
				logger.error("渠道下无任何所属方案,逻辑结束");
				return new ResultMessage(ResponseParameters.CODE_0023, ResponseParameters.MSG_0023, null);
			}else{
				logger.info("渠道下的方案条数:{}",list1.size());
				if(list1.size() <= 2 && isNotAddDatasource){		//渠道下只有一个方案,修改渠道相关信息

					// 一.添加JSON中渠道相关信息
					// 1.修改账户信息
					logger.info("修改账户基本信息begin----");
					addAccountInfo(sourceDto.getAccountDto());

					// 2.修改渠道基本信息
					logger.info("修改渠道基本信息begin----");
					sourceModel = editDatasourceInfo(sourceDto, sourceModel);

					// 3.修改渠道鉴权信息
					// 根据渠道信息获取默认方案信息
					logger.info("修改默认方案鉴权信息begin----");
					ProjectModel projectModel1 = projectService.getDefaultProjectBySourceid(sourceModel.getDataSourceId());
					editAuthEncryptInfo(sourceDto.getAuthEncryptDto(), projectModel1);
					
					//4.修改默认方案中的服务关系信息
					logger.info("修改默认方案与服务关系信息begin----");
					editAuthMappInfo(sourceDto.getAuthMappList(),projectModel1.getProjectId());
					
					//5.修改默认方案中的回调信息  
					logger.info("修改默认方案与回调关系信息begin----");
					editCallbackUrlInfo(sourceDto.getCallbackList(),projectModel1.getProjectId());
				}
				
				// 二.修改方案相关信息
				// 1.修改方案基本信息
				logger.info("修改方案基本信息begin----");
				projectModel.setProjectId(dto.getProjectId());
				projectModel.setDataSourceId(sourceModel.getDataSourceId());
				projectModel.setProjectCode(dto.getProjectCode());
				projectModel.setProjectName(dto.getProjectName());
				projectModel.setUpdatedUser(createdUser);
				projectService.updateInfo(projectModel);
				logger.info("修改方案基本信息end----");

				// 2.修改方案鉴权信息
				logger.info("修改方案鉴权基本信息begin----");
				editAuthEncryptInfo(dto.getAuthEncryptDto(), projectModel);

				Integer projectId = projectModel.getProjectId();

				// 3.修改方案回调信息
				logger.info("修改方案与回调关系信息begin----");
				editCallbackUrlInfo(dto.getCallbackList(), projectId);

				// 4.修改方案与服务关系信息
				logger.info("修改方案与服务关系信息begin----");
				editAuthMappInfo(dto.getAuthmappList(), projectId);
				
				if(list1.size() <= 2){
					result = new ResultMessage(ResponseParameters.CODE_0000, ResponseParameters.MSG_0000, null);
				}else{
					result = new ResultMessage(ResponseParameters.CODE_0000,"渠道下有多个方案,只能修改渠道基本信息和方案的相关信息,修改成功", null);
				}
				
				logger.info("修改方案相关信息结果result:{}",JSON.toJSON(result));
			}

		} catch (Exception e) {

			result = new ResultMessage(ResponseParameters.CODE_0016, ResponseParameters.MSG_0016, null);
			throw e;
		}

		return result;
	}

	
	private ProjectModel addProjectBasicInfo(String projectCode, String projectName, Integer sourceId, Integer authId,
			Integer isDefault, Integer accountId) throws Exception {

		ProjectModel model = new ProjectModel();

		model.setProjectCode(projectCode);
		model.setProjectName(projectName);
		model.setDataSourceId(sourceId);
		model.setIsDefault(isDefault);
		model.setAuthId(authId);
		model.setAccountId(accountId);

		model.setCreatedUser(createdUser);
		model.setUpdatedUser(createdUser);
		projectService.addInfo(model);
		logger.info("添加方案基本信息成功,end--projectId:{}--",model.getProjectId());
		return model;
	}

	
	private AuthEncryptModel addAuthEncryptInfo(AuthEncryptDto authEncryptDto,Integer sourceId) throws Exception {
		
		//根据渠道编号获取渠道信息
		logger.info("添加鉴权信息中,根据渠道编号获取渠道信息");
		DataSourceModel sourceModel = dataSourceService.getOneByDataSourceId(sourceId);

		AuthEncryptModel model = new AuthEncryptModel();

		model.setAuthType(authEncryptDto.getAuthType());
		model.setAuthParam1(authEncryptDto.getAuthParam1());
		model.setAuthParam2(authEncryptDto.getAuthParam2());
		model.setEncryptType(authEncryptDto.getEncryptType());
		model.setEncryptParam1(authEncryptDto.getEncryptParam1());
		model.setEncryptParam2(authEncryptDto.getEncryptParam2());
		model.setAppId(sourceModel.getAppId());
		model.setCreatedUser(createdUser);
		model.setUpdatedUser(createdUser);

		authEncryptService.addInfo(model);
		logger.info("添加鉴权基本信息成功,end----");
		return model;
	}

	
	private AuthEncryptModel editAuthEncryptInfo(AuthEncryptDto authEncryptDto, ProjectModel projectModel)
			throws Exception {

		// 根据默认方案编号获取对应鉴权信息
		AuthEncryptModel model = authEncryptService.getOneByAuthId(projectModel.getAuthId());

		model.setAuthType(authEncryptDto.getAuthType());
		model.setAuthParam1(authEncryptDto.getAuthParam1());
		model.setAuthParam2(authEncryptDto.getAuthParam2());
		model.setEncryptType(authEncryptDto.getEncryptType());
		model.setEncryptParam1(authEncryptDto.getEncryptParam1());
		model.setEncryptParam2(authEncryptDto.getEncryptParam2());

		authEncryptService.updateInfo(model);
		logger.info("修改鉴权基本信息成功end----");

		return model;
	}

	
	private DataSourceModel addDatasourceInfo(DataSourceDto sourceDto, String appId) throws Exception {

		DataSourceModel model = new DataSourceModel();

		model.setDataSource(sourceDto.getDataSource());
		model.setSourceName(sourceDto.getSourceName());
		model.setAppId(appId);

		model.setCreatedUser(createdUser);
		model.setUpdatedUser(createdUser);
		dataSourceService.addInfo(model);
		logger.info("添加渠道基本信息成功,end----");
		return model;
	}

	
	private DataSourceModel editDatasourceInfo(DataSourceDto sourceDto, DataSourceModel model) throws Exception {

		model.setDataSource(sourceDto.getDataSource());
		model.setSourceName(sourceDto.getSourceName());

		model.setCreatedUser(createdUser);
		model.setUpdatedUser(createdUser);
		dataSourceService.updateInfo(model);
		logger.info("修改渠道基本信息成功,end----");
		return model;
	}

	
	private AccountModel addAccountInfo(AccountDto accountDto) throws Exception {

		logger.info("addAccountInfo(),添加账号基本信息");
		AccountModel model = accountService.getOneByAppid(accountDto.getAppId());

		if (model == null) {

			// add
			model = new AccountModel();
			model.setAppId(accountDto.getAppId());
			model.setAccountName(accountDto.getAccountName());
			model.setAppCode(accountDto.getAppCode());
			model.setEmail(accountDto.getEmail());
			model.setMobile(accountDto.getMobile());
			model.setCreatedUser(createdUser);
			model.setUpdatedUser(createdUser);

			accountService.addInfo(model);
			logger.info("添加账号基本信息成功");
		} else {

			// edit
			model.setAccountName(accountDto.getAccountName());
			model.setEmail(accountDto.getEmail());
			model.setMobile(accountDto.getMobile());
			model.setUpdatedUser(createdUser);

			accountService.updateInfo(model);
			logger.info("修改账号基本信息成功");
		}
		return model;
	}

	

	/** 
	* @Title: addAuthMappInfo 
	* @Description: 添加方案服务关系信息 
	* @param authmappList
	* @param projectId
	* @throws Exception  
	* @return void  
	* @throws 
	*/
	private void addAuthMappInfo(List<AuthMappingDto> authmappList, Integer projectId) throws Exception {

		// 添加方案服务关系信息
		 if(authmappList != null && authmappList.size() > 0){
			 
			ProjectModel proModel = projectService.getOneByProjectId(projectId);
	
			AuthMappingModel authmappModel = null;
			for (AuthMappingDto callbackUrlDto : authmappList) {
	
				authmappModel = new AuthMappingModel();
				authmappModel.setProjectId(projectId);
				authmappModel.setRequestType(callbackUrlDto.getRequestType());
				authmappModel.setRequestUrl(callbackUrlDto.getRequestUrl());
				authmappModel.setServerId(callbackUrlDto.getServerId());
				
				if(PublicParamters.REQUEST_TYPE_HISTORY_COMMON.equals(callbackUrlDto.getRequestType()) ||
						PublicParamters.REQUEST_TYPE_HISTORY_ENCRYPT.equals(callbackUrlDto.getRequestType()) ||
						PublicParamters.REQUEST_TYPE_HISTORY_CUSTOM.equals(callbackUrlDto.getRequestType())){
					
					authmappModel.setAuthId(proModel.getAuthId());
				}
				authmappModel.setCreatedUser(createdUser);
				authmappModel.setUpdatedUser(createdUser);
				authMappService.addInfo(authmappModel);
				logger.info("添加方案与服务关系信息成功,projectId:{},callbackId:{}",projectId,authmappModel.getMappingId());
			}
			logger.info("添加方案与服务关系信息成功,end----");
		}
	}


	/** 
	* @Title: addCallbackUrlInfo 
	* @Description: 添加方案回调信息
	* @param callbackList
	* @param projectId
	* @throws Exception  
	* @return void  
	* @throws 
	*/
	private void addCallbackUrlInfo(List<CallbackUrlDto> callbackList, Integer projectId) throws Exception {

		// 添加回调信息
		CallbackUrlModel callbackModel = null;
		if(callbackList != null && callbackList.size() > 0){
			for (CallbackUrlDto callbackUrlDto : callbackList) {

				callbackModel = new CallbackUrlModel();
				callbackModel.setProjectId(projectId);
				callbackModel.setCallbackEnv(callbackUrlDto.getCallbackEnv());
				callbackModel.setCallbackType(callbackUrlDto.getCallbackType());
				callbackModel.setCallbackMethod(callbackUrlDto.getCallbackMethod());
				callbackModel.setCallbackUrl(callbackUrlDto.getCallbackUrl());

				callbackModel.setCreatedUser(createdUser);
				callbackModel.setUpdatedUser(createdUser);
				callbackUrlService.addInfo(callbackModel);
				logger.info("添加方案与回调关系信息成功,projectId:{},callbackId:{}",projectId,callbackModel.getCallbackUrlId());
			}
			logger.info("添加方案与回调关系信息成功,end----");
		}

	}

	
	/** 
	* @Title: editCallbackUrlInfo 
	* @Description: 编辑方案回调信息 
	* @param callbackList
	* @param projectId
	* @throws Exception  
	* @return void  
	* @throws 
	*/
	private void editCallbackUrlInfo(List<CallbackUrlDto> callbackList, Integer projectId) throws Exception {

		// 根据方案编号删除对应回调信息
		logger.info("根据方案编号删除相关回调信息----");
		callbackUrlService.deleteByProjectId(projectId);

		// 添加新的回调信息
		logger.info("添加方案与回调关系信息begin----");
		addCallbackUrlInfo(callbackList, projectId);
	}

	/** 
	* @Title: editAuthMappInfo 
	* @Description: 编辑方案与服务关系信息
	* @param authmappList
	* @param projectId
	* @throws Exception  
	* @return void  
	* @throws 
	*/
	private void editAuthMappInfo(List<AuthMappingDto> authmappList, Integer projectId) throws Exception {

		// 根据方案编号删除对应方案与服务关系信息
		logger.info("根据方案编号删除相关----");
		authMappService.deleteByProjectId(projectId);

		// 添加新的方案服务关系信息
		logger.info("添加方案与服务关系信息begin----");
		addAuthMappInfo(authmappList, projectId);
	}

	
	@Override
	public ResultMessage deleteInfo(String deleteType, String deleteId) throws Exception {

		Integer resu = 0;
		ResultMessage result = new ResultMessage();

		if (StringUtils.isNotEmpty(deleteId) && StringUtils.isNotEmpty(deleteType)) {

			Integer delId = Integer.parseInt(deleteId);

			if ("dataSource".equals(deleteType)) {

				// 验证id是否存在
				DataSourceModel model = dataSourceService.getOneByDataSourceId(delId);
				if (model == null) {

					result = new ResultMessage(ResponseParameters.CODE_0012, ResponseParameters.MSG_0012, null);
					return result;
				} else {

					resu = dataSourceService.deleteInfo(delId);
					if (resu > 0)
						result = new ResultMessage(ResponseParameters.CODE_0000, ResponseParameters.MSG_0000, null);
					else
						result = new ResultMessage(ResponseParameters.CODE_9999, ResponseParameters.MSG_9999, null);
				}
			}

			else if ("project".equals(deleteType)) {

				// 验证id是否存在
				ProjectModel model = projectService.getOneByProjectId(delId);
				if (model == null) {

					result = new ResultMessage(ResponseParameters.CODE_0012, ResponseParameters.MSG_0012, null);
					return result;
				} else {

					resu = projectService.deleteInfo(delId);
					if (resu > 0)
						result = new ResultMessage(ResponseParameters.CODE_0000, ResponseParameters.MSG_0000, null);
					else
						result = new ResultMessage(ResponseParameters.CODE_9999, ResponseParameters.MSG_9999, null);
				}
			}

		} else {
			result = new ResultMessage(ResponseParameters.CODE_0012, ResponseParameters.MSG_0012, null);
		}

		return result;
	}

	
	@Override
	public List<String[]> getValuesByParameterType(String parameterType) throws Exception {
        List<String[]> lists = new ArrayList<String[]>();
		
		AuthPubCodeModel authPubCodeModel = new AuthPubCodeModel();
		authPubCodeModel.setParameterType(parameterType);
		authPubCodeModel.setValidFlag(PublicParamters.VALID_FLAG_Y);
		
		List<AuthPubCodeModel> authPubCodeModelList = authPubCodeDao.selectAllByConditions(authPubCodeModel);
		
		if (authPubCodeModelList.size() > 0) {
			String[] serverSmallTypes = null;
			for (AuthPubCodeModel authPubCodeModelTemp : authPubCodeModelList) {
				serverSmallTypes = new String[2];
				serverSmallTypes[0] = authPubCodeModelTemp.getParameterValue();
				serverSmallTypes[1] = authPubCodeModelTemp.getParameterDescribe();
				lists.add(serverSmallTypes);
			}
		}
		return lists;
	}


}
