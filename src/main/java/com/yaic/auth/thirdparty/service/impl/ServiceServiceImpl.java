package com.yaic.auth.thirdparty.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.thirdparty.dao.AuthMappingDao;
import com.yaic.auth.thirdparty.dao.ServerDao;
import com.yaic.auth.thirdparty.model.ServerModel;
import com.yaic.auth.thirdparty.service.ServerService;

@Service
public class ServiceServiceImpl implements ServerService {
	// private static final Logger logger = LoggerFactory.getLogger(ServiceServiceImpl.class);

	@Autowired
	private ServerDao serverDao;
	@Autowired
	private AuthMappingDao authMappingDao;

	@Override
	public List<ServerModel> getList(ServerModel serverModel) throws Exception {
		List<ServerModel> list = new ArrayList<ServerModel>();
		list = serverDao.selectAllView(serverModel);
		return list;
	}

	@Override
	public Integer addInfo(ServerModel serverModel) {
		serverModel.setValidFlag(PublicParamters.VALID_FLAG_Y);
		int i = serverDao.insertSelective(serverModel);
		return i;
	}

	@Override
	public Integer updateInfo(ServerModel serverModel) {
		return serverDao.updateByPrimaryKeySelective(serverModel);
	}

	@Override
	public Integer deleteInfo(Integer serverId) {
		int result = 0;
		authMappingDao.deleteByServerId(serverId);
		int i = serverDao.deleteByPrimaryKey(serverId);
		if (i == 1) {
			result = 1;
		}
		return result;
	}

	@Override
	public ServerModel getOneByServerId(Integer serverId) {
		return serverDao.selectOneByPrimaryKey(serverId);

	}

	@Override
	public ServerModel getOneByServer(ServerModel serverModel) {
		List<ServerModel> list = serverDao.selectByConditions(serverModel);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public ServerModel getOneByServerUrl(String serverUrl) {
		return serverDao.selectOneByServerUrl(serverUrl);
	}

	@Override
	public Integer deleteInfoByIds(List<String> ids) {
		int result = 0;
		if (ids.size() > 0) {
			for (String serverIdTemp : ids) {
				Integer serverId = Integer.valueOf(serverIdTemp);
				result = this.deleteInfo(serverId);
			}
		}
		return result;
	}

}
