package com.yaic.auth.thirdparty.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.thirdparty.dao.AuthEncryptDao;
import com.yaic.auth.thirdparty.dao.AuthMappingDao;
import com.yaic.auth.thirdparty.dao.CallbackUrlDao;
import com.yaic.auth.thirdparty.dao.ProjectDao;
import com.yaic.auth.thirdparty.dto.ProjectViewDto;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceServiceimpl.class);

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private AuthMappingDao authMappingDao;
	@Autowired
	private CallbackUrlDao callbackUrlDao;
	@Autowired
	private AuthEncryptDao authEncryptDao;

	@Override
	public List<ProjectModel> getList(ProjectModel projectModel) throws Exception {
		List<ProjectModel> list = projectDao.getList(projectModel);
		return list;
	}

	@Override
	public Integer addInfo(ProjectModel projectModel) throws Exception {
		// projectModel.setCreatedUser(Constants.LOGIN_USER_CODE());
		// projectModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
		projectModel.setValidFlag(PublicParamters.VALID_FLAG_Y);
		return projectDao.insertSelective(projectModel);
	}

	@Override
	public Integer updateInfo(ProjectModel projectModel) throws Exception {
		// projectModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
		return projectDao.updateByPrimaryKeySelective(projectModel);
	}

	@Override
	public Integer deleteInfo(Integer projectId) throws Exception {
		int result = 0;

		// 1、根据projectI判断方案是存在
		ProjectModel projectModel = projectDao.selectOneByPrimaryKey(projectId);
		if (projectModel != null) {

			// 2、删除相关数据

			callbackUrlDao.deleteByProjectId(projectId);
			authMappingDao.deleteByProjectId(projectId);

			if (projectModel.getAuthId() != null)
				authEncryptDao.deleteByPrimaryKey(projectModel.getAuthId());

			result = projectDao.deleteByPrimaryKey(projectId);

		}

		return result;
	}

	@Override
	public ProjectModel getOneByProjectId(Integer projectId) throws Exception {
		return projectDao.selectOneByPrimaryKey(projectId);
	}

	@Override
	public ProjectModel getOneByProjectCode(String projectCode) throws Exception {
		return projectDao.selectOneByProjectCode(projectCode);
	}

	@Override
	public ProjectModel getOneProject(ProjectModel projectModel) throws Exception {
		List<ProjectModel> list = projectDao.getList(projectModel);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Integer deleteInfoByIds(List<String> ids) throws Exception {
		int result = 0;
		if (ids.size() > 0) {
			logger.info("delete project by ids{}", ids);
			for (String projectIdTemp : ids) {
				Integer projectId = Integer.valueOf(projectIdTemp);
				result = this.deleteInfo(projectId);
			}
		}
		return result;
	}

	@Override
	public ProjectModel getDefaultProjectBySourceid(Integer dataSourceId) throws Exception {
		return projectDao.getDefaultProjectBySourceid(dataSourceId);
	}

	@Override
	public List<ProjectViewDto> getProjectViewDto(ProjectViewDto projectViewDto) throws Exception {
		Map<String, String> parametersMap = new HashMap<>();
		if (StringUtils.isNotBlank(projectViewDto.getProjectCode())) {
			parametersMap.put("projectCode", projectViewDto.getProjectCode());
		}
		if (StringUtils.isNotBlank(projectViewDto.getProjectName())) {

			parametersMap.put("projectName", projectViewDto.getProjectName());
		}
		if (StringUtils.isNotBlank(projectViewDto.getIsDefault())) {
			parametersMap.put("isDefault", projectViewDto.getIsDefault());
		}
		if (StringUtils.isNotBlank(projectViewDto.getSourceName())) {
			String[] codeAndName = projectViewDto.getSourceName().split(":");
			if (codeAndName.length == 2) {
				projectViewDto.setSourceName(codeAndName[codeAndName.length-1]);
			}
			parametersMap.put("sourceName", projectViewDto.getSourceName());
		}
		if (projectViewDto.getDataSourceId() != null) {
			parametersMap.put("dataSourceId", projectViewDto.getDataSourceId().toString());
		}
		List<ProjectViewDto> list = projectDao.selectProjectView(parametersMap);
		return list;
	}

	@Override
	public Integer getProjectCountsBySourceId(Integer dataSourceId) throws Exception {
		return projectDao.getProjectCountsBySourceId(dataSourceId);
	}

}
