package com.yaic.auth.interior.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yaic.auth.common.BaseController;
import com.yaic.auth.common.PublicMethods;
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.interior.common.TreeNode;
import com.yaic.auth.interior.model.AppRoleModel;
import com.yaic.auth.interior.service.AppRoleResService;

/** 
* @ClassName: AppRoleController 
* @Description: 内部角色资源关系
* @author 宋淑华
* @date 2018年6月3日 下午4:00:36 
*  
*/
@RestController
@RequestMapping("/roleRes")
public class AppRoleResController {
	
	private static final Logger logger = LoggerFactory.getLogger(AppRoleResController.class);
	
	@Autowired
	 private  AppRoleResService  appRoleResService;

	/**
     * 根据角色ID获取角色资源
     * @return
     * @throws Exception
     */
	@PostMapping(value = "/getResource")
    public  Map<String , Object>  getResource(@RequestBody AppRoleModel appRoleModel) throws Exception {
			
		Map<String , String> paraMap = new HashMap<String, String>();
		Map<String , Object> resultMap = new HashMap<String, Object>();
			
		//查询条件
		if(StringUtils.isNotBlank(appRoleModel.getRoleId())){
			paraMap.put("roleId", appRoleModel.getRoleId());
		}
	   	  
		//根据角色ID获取角色资源
		logger.debug("根据角色ID获取角色资源, roleId:{}",paraMap.get("roleId"));
	   	List<TreeNode> resources = appRoleResService.findResourceByRoleId(paraMap);
	 	logger.debug("查询结果：{}", resources.size());
	   	//获取所有的资源
	 	logger.debug("获取所有的资源   findAllMenuResourceNew() " );
	   	List<TreeNode> menuResourceList = appRoleResService.findAllMenuResource();
	 	logger.debug("查询结果：{}", menuResourceList.size());
	   	resultMap.put("roleId",appRoleModel.getRoleId());
	   	resultMap.put("resources", resources);
	   	resultMap.put("menuResourceList", menuResourceList);
	   	
        return resultMap;
    }
	
	 /**
     * 保存角色资源
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveRoleResource")
    public ResultMessage saveRoleResource(String ids,String roleId) throws Exception {

    	ResultMessage results = BaseController.getResults(0,"配置失败");
    	
		try {
			logger.info("ids:{}", ids);
			logger.debug("saveRoleResource by ids:{}", ids);
			
			List<String> idsParam = PublicMethods.SplitIdsToArray(ids);
			logger.debug("格式化后的ids: {}", idsParam);
			appRoleResService.saveRoleResource(roleId,idsParam);
			logger.info("save success");
			results = BaseController.getResults(1,"配置成功");
		} catch (Exception e) {
			
			results = BaseController.getResults(0,"配置失败");
			throw e;
		}

		return results;
    }
	
    
    
    
    
    
    
    
    
    
}
