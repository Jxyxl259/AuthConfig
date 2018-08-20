package com.yaic.auth.interior.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.yaic.auth.interior.model.AppUserModel;
import com.yaic.auth.interior.service.AppUserRoleService;

/** 
* @ClassName: AppUserRoleController 
* @Description: 内部用户与角色关系
* @author zhaoZd
* @date 2018年6月3日 下午3:47:17 
*  
*/
@RestController
@RequestMapping("/userRole")
public class AppUserRoleController {
	private static final Logger logger = LoggerFactory.getLogger(AppUserRoleController.class);

	@Autowired
	AppUserRoleService appUserRoleService ;
	
	/** 
	* @Title: getRoles 
	* @Description: 获取角色配置信息
	* @param model
	* @return
	* @throws Exception    
	* @return Map<String,Object>  
	* @throws 
	*/
	@PostMapping(value = "/getRoles")
    public  Map<String , Object> getRoles(@RequestBody AppUserModel model) throws Exception {
			
		Map<String , Object> resultMap = new HashMap<String, Object>();

		//根据用户code获取角色资源
		logger.debug("根据用户code获取角色资源, userCode:{}", model.getUserCode());
	   	List<TreeNode> myRoles = appUserRoleService.findMyRolesByUserCode(model.getUserCode());
		logger.info("查询结果：{}", myRoles.size());
	   	//获取所有的角色列表
		logger.debug("获取所有的角色列表,findAllRoles() ");
	   	List<TreeNode> allRoles = appUserRoleService.findAllRoles();
		logger.info("查询结果：{}", allRoles.size());
	   	resultMap.put("userCname",model.getUserCname());
	   	resultMap.put("myRoles", myRoles);
	   	resultMap.put("allRoles", allRoles);
	   	
        return resultMap;
    }
	
    /** 
    * @Title: saveUserRoles 
    * @Description: 保存用户与服务关系信息
    * @param ids
    * @param userCode
    * @return
    * @throws Exception  
    * @return ResultMessage  
    * @throws 
    */
    @ResponseBody
    @RequestMapping(value = "/saveUserRoles")
    public ResultMessage saveUserRoles(String ids,String userCode) throws Exception {

    	ResultMessage results = BaseController.getResults(0,"配置失败");
    	
		try {
			logger.info("ids:{}", ids);
			logger.debug("saveUserRoles by ids:{}", ids);
			
			List<String> idsParam = PublicMethods.SplitIdsToArray(ids);
			logger.debug("格式化后的ids: {}", idsParam);
			appUserRoleService.saveUserRoles(userCode,idsParam);
			logger.info("save success");
			results = BaseController.getResults(1,"配置成功");
		} catch (Exception e) {
			
			results = BaseController.getResults(0,"配置失败");
			throw e;
		}

		return results;
    }
	
	
}
