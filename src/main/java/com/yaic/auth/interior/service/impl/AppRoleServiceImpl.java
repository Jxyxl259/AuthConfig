package com.yaic.auth.interior.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.interior.dao.AppRoleDao;
import com.yaic.auth.interior.dao.AppRoleResourceDao;
import com.yaic.auth.interior.model.AppRoleModel;
import com.yaic.auth.interior.service.AppRoleService;

@Service
public class AppRoleServiceImpl implements AppRoleService {
	@Autowired
	AppRoleDao appRoleDao;

	@Autowired
	AppRoleResourceDao appRoleResourceDao;

	/**
	 * 根据角色主键ID查找角色
	 */
	@Override
	public AppRoleModel selectByPrimaryKey(String roleId) throws Exception {
		AppRoleModel roleDto = appRoleDao.selectByPrimaryKey(roleId);
		return roleDto;
	}

	/**
	 * 多条件查询角色
	 */
	@Override
	public List<AppRoleModel> getList(AppRoleModel roleDto) throws Exception{
		
		return appRoleDao.getList(roleDto);
	}

	@Override
	public Integer addInfo(AppRoleModel roleModel) throws Exception {
		
		roleModel.setValidFlag(PublicParamters.VALID_FLAG_Y);
		return appRoleDao.addInfo(roleModel);
	}

	@Override
	public Integer updateInfo(AppRoleModel roleModel) throws Exception {
		
		return appRoleDao.updateInfo(roleModel);
	}

	@Override
	public Integer deleteByPrimaryKey(String roleid) throws Exception {
		
		//删除角色前先删除角色对应的资源
		appRoleResourceDao.deleteByRoleId(roleid);
	    //再删除角色
	    Integer count  = appRoleDao.deleteByPrimaryKey(roleid);
	
		return count;
	}

	@Override
	public Integer deleteInfoByIds(List<String> ids) throws Exception {

		Integer result = 0;
		if (ids.size() > 0) {
			for (String dataSourceIdTemp : ids) {
				result = this.deleteByPrimaryKey(dataSourceIdTemp);
			}
		}
		return result;
	}

	@Override
	public String createRoleId() {
		
		String roleid = appRoleDao.getMaxRoleId();
		String newRoleId = String.format("%04d",Integer.parseInt(roleid)+1); 
		return newRoleId;
	}

}
