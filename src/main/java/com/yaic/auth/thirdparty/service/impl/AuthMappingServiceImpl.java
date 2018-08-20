package com.yaic.auth.thirdparty.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.external.service.ExternalConfigService;
import com.yaic.auth.thirdparty.dao.AuthMappingDao;
import com.yaic.auth.thirdparty.dao.ProjectDao;
import com.yaic.auth.thirdparty.dto.AuthMappingDto;
import com.yaic.auth.thirdparty.dto.InterfaceInfoDto;
import com.yaic.auth.thirdparty.model.AuthMappingModel;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.service.AuthMappingService;

@Service
public class AuthMappingServiceImpl implements AuthMappingService {
	// private static final Logger logger =
	// LoggerFactory.getLogger(AuthMappingServiceImpl.class);
	@Autowired
	private AuthMappingDao authMappingDao;
	@Autowired
	private ExternalConfigService configService;
	@Autowired
	private ProjectDao projectDao;

	@Override
	public List<AuthMappingModel> getList(AuthMappingModel authMappingModel) throws Exception {
		List<AuthMappingModel> list = authMappingDao.selectByConditions(authMappingModel);
		return list;
	}

	@Override
	public Integer addInfo(AuthMappingModel model) throws Exception {
//		model.setCreatedUser(Constants.LOGIN_USER_CODE());
//		model.setUpdatedUser(Constants.LOGIN_USER_CODE());
		model.setValidFlag(PublicParamters.VALID_FLAG_Y);
		return authMappingDao.insertSelective(model);
	}

	@Override
	public Integer updateInfo(AuthMappingModel authMappingModel) throws Exception {
//		authMappingModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
		return authMappingDao.updateByPrimaryKeySelective(authMappingModel);
	}

	@Override
	public Integer deleteInfo(Integer mappingId) throws Exception {
		return authMappingDao.deleteByPrimaryKey(mappingId);
	}

	@Override
	public AuthMappingModel getOneByMappingId(Integer mappingId) throws Exception {
		return authMappingDao.selectByPrimaryKey(mappingId);
	}

	@Override
	public AuthMappingModel getOneByAuthMapping(AuthMappingModel authMappingModel) throws Exception {
		List<AuthMappingModel> list = authMappingDao.selectByConditions(authMappingModel);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<AuthMappingModel> getAuthMappListByReqUrlAndAppId(String appId, String requestUrl) throws Exception {
		return authMappingDao.selectByAppIdAndReqUrl(appId, requestUrl);
	}
	
	@Override
	public List<AuthMappingModel> getAuthMappListByReqUrlAndIdType(String requestUrl, String idType, Integer id) throws Exception{
		return authMappingDao.getAuthMappListByReqUrlAndIdType(requestUrl,idType,id);
	}

	@Override
	public AuthMappingModel getOneByProjectIdAndServerId(Integer projectId, Integer serverId) throws Exception {
		return authMappingDao.selectOneByProjectIdAndServerId(projectId, serverId);
	}

	@Override
	public AuthMappingModel getOneByRequestTypeAndRequestUrl(String requestType, String requestUrl) throws Exception {
		return authMappingDao.selectOneByRequestTypeAndRequestUrl(requestType, requestUrl);
	}

	@Override
	public List<InterfaceInfoDto> selectListsByAppid(String appId) throws Exception {
		return authMappingDao.selectListsByAppid(appId);
	}

	@Override
	public List<AuthMappingDto> getMappAndServerByProId(Integer projectId) throws Exception {
		return authMappingDao.getMappAndServerByProId(projectId);
	}

	@Override
	public Integer deleteByProjectId(Integer projectId) throws Exception {
		return authMappingDao.deleteByProjectId(projectId);
	}

	@Override
	public String judgeAuthMappInfo(AuthMappingModel model) throws Exception {
		
		AuthMappingDto dto = new AuthMappingDto(model);
		List<AuthMappingDto> lists = new ArrayList<AuthMappingDto>();
		lists.add(dto);
		
		Integer datasourceId = null,projectId = null;
		
		if(!StringUtils.isEmpty(model.getProjectId())){
			ProjectModel projectModel = projectDao.selectOneByPrimaryKey(model.getProjectId());
			datasourceId = projectModel.getDataSourceId();
			projectId = model.getProjectId();
		}
		
		//DataSourceModel sourceModel = datasourceDao.selectOneByPrimaryKey(datasourceId);
		
		String result = configService.judgeRequestUrl(lists,"", datasourceId,projectId,model.getReqType());
		
		return result;
	}

}
