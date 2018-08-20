package com.yaic.auth.interior.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yaic.auth.common.BaseController;
import com.yaic.auth.common.Constants;
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.interior.common.TreeNode;
import com.yaic.auth.interior.dto.AppResourceDto;
import com.yaic.auth.interior.model.AppResourceModel;
import com.yaic.auth.interior.service.AppResourceService;
import com.yaic.auth.interior.service.AppRoleResService;

/**
 * 内部资源表功能
 * @author zhaoZD
 * 2018-06-01
 *
 */
@RestController
@RequestMapping("/resource")
public class AppResourceController {
	
	private static final Logger logger = LoggerFactory.getLogger(AppResourceController.class);
	
	@Autowired
	AppResourceService appResourceService;
	
	@Autowired
	AppRoleResService appRoleResService;
	
	//条件查询
	@PostMapping(value = "/list")
	public Map<String, Object> getResourceList(@RequestBody AppResourceModel dto) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		//查询所有资源
		logger.debug("查询所有资源   findAllMenuResource() ");
		List<TreeNode> menuResourceList =  appRoleResService.findAllMenuResource();
		
		//封装树
		List<TreeNode> copyMenuNodes = new ArrayList<TreeNode>();
		copyMenuNodes.addAll(menuResourceList); 
		
		//得到菜单树
		for(TreeNode treeNode : menuResourceList){
			for(int i = 0 ;i<copyMenuNodes.size() ;i++ ){
				if(treeNode.getParentId().equals(copyMenuNodes.get(i).getId())){
					copyMenuNodes.get(i).addChild(treeNode);
					copyMenuNodes.remove(treeNode);
				}
			}
		}       
		
		result.put("menuResourceTree", copyMenuNodes);
		return result;
	}
	
	/** 
	* @Title: getInfo 
	* @Description: 根据资源编号获取资源信息
	* @param appResourceModel
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/getInfo")
	public ResultMessage getInfo(@RequestBody AppResourceModel appResourceModel) throws Exception {
		logger.debug("getInfo appResourceModel by appresourceId:{}", appResourceModel.getResourceId());
		appResourceModel = appResourceService.getInfoByResourceId(appResourceModel.getResourceId());
		logger.info("获取资源信息，参数resourceId：{}", appResourceModel.getResourceId());
		ResultMessage results = BaseController.getResults(appResourceModel);
		return results;
	}
	
	
	/** 
	* @Title: saveOrUpdParaType 
	* @Description: 添加或者编辑
	* @param dto
	* @return
	* @throws Exception  
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value="/changeInfo")
	public ResultMessage saveOrUpdParaType(@RequestBody AppResourceModel dto) throws Exception {

		StringBuffer msg = new StringBuffer();
		Integer result = 0;
		
		//判断操作类型
		if("add".equals(dto.getReqType())){
			//先判断资源名称唯一
			logger.debug("判断资源名称唯一, 参数resourceName:{} ", dto.getResourceName());
			AppResourceModel d = appResourceService.getInfoByResourceName(dto.getResourceName());
			if(d != null){
				// 判断唯一
				logger.debug("select resourceName by AppResourceModel:{}", dto.getResourceName());
				msg.append(dto.getResourceName()+"已存在！");
			}else{
				
				dto.setParentResourceId(dto.getResourceId());
				
				//根据页面传来的父级编号得到新的资源编号
				logger.debug("根据页面传来的父级编号得到新的资源编号, 参数 resourceId:{}", dto.getResourceId());
				String resourceId = appResourceService.createResourceId(dto.getResourceId());
				logger.debug("新的资源编号 resourceId:{}" , resourceId);
				dto.setResourceId(resourceId);
				
				dto.setResourceType("menu");
				dto.setResourceLevel(2);
				dto.setEndFlag(1);
				
				dto.setCreatedBy(Constants.LOGIN_USER_CODE());
				dto.setUpdatedBy(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行新增方法：addInfo()，新增资源Id：{}，名称：{}",dto.getResourceId(), dto.getResourceName());
				result = appResourceService.addInfo(dto);
				logger.debug("新增完成，结果：{}", result);
			}
		}else if("edit".equals(dto.getReqType())){
			
			//判断资源编号是否存在
			AppResourceModel d = appResourceService.getInfoByResourceId(dto.getResourceId());
			if(d != null){
				
				dto.setUpdatedBy(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行修改方法：updateInfo()，修改信息为：{}", dto);
				result = appResourceService.updateInfo(dto);
				logger.debug("修改完成，结果：{}", result);
			}else{
				
				logger.info("resourceId:{} is not exist", dto.getResourceId());
				msg.append(dto.getResourceId()+"不存在！");
			}
			
		}
		
		msg.append("add".equals(dto.getReqType()) ? "新增" : "编辑");
		msg.append(result == 1 ? "操作成功!" : "操作失败!");
		
		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}
	
	
	/** 
	* @Title: delete 
	* @Description: 根据编号删除
	* @param resourceId
	* @return
	* @throws Exception  
	* @return String  
	* @throws 
	*/
	@GetMapping(value="/delete/{resourceId}")
	public String delete(@PathVariable String resourceId) throws Exception{
		
		Integer result = 0;
		StringBuffer msg = new StringBuffer();
		
		//判断资源编号是否存在
		logger.debug("判断资源编号是否存在, resourceId :{}", resourceId);
		AppResourceModel d = appResourceService.getInfoByResourceId(resourceId);
		if(d != null){
			logger.debug("开始执行删除方法 deleteInfo(), 参数 ：{}", resourceId );
			result = appResourceService.deleteByPrimaryKey(resourceId);
			logger.info("删除完成， 结果：{}", result);
		}else{
			msg.append(resourceId + "不存在！");
		}
		
		msg.append(result == 1 ? "操作成功!" : "操作失败!");
		return msg.toString();
	}
	
//	//根据编号查询对象详细信息
//	@GetMapping(value="/show/{resourceId}")
//	public AppResourceModel show(@PathVariable String resourceId) throws Exception {
//		return appResourceService.getInfoByResourceId(resourceId);
//	}
	
	/**
	 * 根据resourceId删除资源   逻辑有误,需加上可用标识
	 * 
	 * @param jsonRequest
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/deleteResource")
	public Map<String, Object> deleteMenu(String resourceId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String msg = "";

		String userId = Constants.LOGIN_USER_ID();
		logger.debug("删除资源，参数 resourceId:{}", resourceId);
		int i = appResourceService.deleteByPrimaryKey(resourceId);
		logger.info("删除完成，结果：{}", i);
		if (i > 0) {
			msg = "删除成功！";
		} else {
			msg = "删除失败！";
		}

		logger.info("用户【" + userId + "】删除资源【" + resourceId + "】");
		logger.info("用户【" + userId + "】删除资源【" + resourceId + "】");
		result.put("msg", msg);
		result.put("flag", "Y");
		return result;
	}

	
	/** 
	* @Title: menu 
	* @Description: 加载左侧菜单
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value="menu")  
	public ResultMessage menu() throws Exception{  

		ResultMessage result = null;
		String loginUserId = Constants.LOGIN_USER_ID();
		logger.debug("查询左侧菜单资源，参数 loginUserId：{}", loginUserId);
		List<AppResourceDto> list = appResourceService.findAllResourcesByUserId(loginUserId);
		
		logger.debug("加载左侧菜单完成");  
		result = BaseController.getResults(list);
		return result;  
	} 
	
}
