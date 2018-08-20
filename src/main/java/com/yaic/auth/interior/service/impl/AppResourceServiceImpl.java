package com.yaic.auth.interior.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.auth.interior.dao.AppResourceDao;
import com.yaic.auth.interior.dto.AppResourceDto;
import com.yaic.auth.interior.dto.AppResourceSecCellDto;
import com.yaic.auth.interior.model.AppResourceModel;
import com.yaic.auth.interior.service.AppResourceService;

@Service
public class AppResourceServiceImpl implements AppResourceService {
	
	@Autowired
	AppResourceDao appResourceDao;

	@Override
	public Integer addInfo(AppResourceModel model) throws Exception {
		
		return appResourceDao.insertSelective(model);
	}

	@Override
	public String createResourceId(String parentResourceId) {
		
		String newResourceId = "";
		
		//判断父级编号是否为空
		if(StringUtils.isBlank(parentResourceId) || parentResourceId.equals("0")){
			parentResourceId = "0000";
		}
		
		//根据父级编号获取其下最大子编号
		String resourceId= appResourceDao.getMaxResourceIdByParentId(parentResourceId);
		
		//父节点为0000,代表为主节点,新建的话,第二位数字加1
		if(parentResourceId.equals("0000")){
			newResourceId = String.format("%04d",Integer.parseInt(resourceId)+100); 
		}else{
			//代表为子节点,新建的话最后一位加1
			newResourceId = String.format("%04d",Integer.parseInt(resourceId)+1); 
		}
		
		return newResourceId;
	}

	@Override
	public Integer updateInfo(AppResourceModel dto) throws Exception {

		return appResourceDao.updateByPrimaryKeySelective(dto);
	}

	@Override
	public Integer deleteByPrimaryKey(String resourceId) throws Exception {
		
		return appResourceDao.deleteByPrimaryKey(resourceId);
	}

	@Override
	public AppResourceModel getInfoByResourceName(String resourceName) throws Exception{
		return appResourceDao.getInfoByResourceName(resourceName);
	}

	@Override
	public AppResourceModel getInfoByResourceId(String resourceId) throws Exception {
		return appResourceDao.getInfoByResourceId(resourceId);
	}
	
	@Override
	public List<AppResourceDto> findAllResourcesByUserId(String loginUserId) {
		
		List<AppResourceDto> newList = new ArrayList<AppResourceDto>();
		
		List<AppResourceModel> oldList = appResourceDao.findAllResourcesByUserId(loginUserId);
		
		for (AppResourceModel oldModel : oldList) {
			if(oldModel.getParentResourceId().equals("0000")){
				AppResourceDto dto = new AppResourceDto(oldModel);
				dto.setChild(new ArrayList<AppResourceSecCellDto>());
				newList.add(dto);
			}
		}
		
		for (AppResourceDto newdto : newList) {
			
			for (AppResourceModel oldModle : oldList) {
				
				if(newdto.getResourceId().equals(oldModle.getParentResourceId())){
					newdto.getChild().add(new AppResourceSecCellDto(oldModle));
				}
			}
		}
		
		return newList;
	}


}
