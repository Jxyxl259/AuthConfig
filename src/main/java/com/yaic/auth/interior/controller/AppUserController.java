package com.yaic.auth.interior.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yaic.auth.common.BaseController;
import com.yaic.auth.common.BaseDto;
import com.yaic.auth.common.Constants;
import com.yaic.auth.common.PublicMethods;
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.common.UuidUtils;
import com.yaic.auth.interior.dto.AppUserDto;
import com.yaic.auth.interior.model.AppUserModel;
import com.yaic.auth.interior.service.AppRoleService;
import com.yaic.auth.interior.service.AppUserService;

/**
 * 内部用户账号表
 * 2018-06-01
 *
 */
@RestController
@RequestMapping("/user")
public class AppUserController {
	private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);

	@Autowired
	AppUserService appUserService;
	
	@Autowired
	AppRoleService appRoleService;
	
	/** 
	* @Title: getList 
	* @Description: 获取用户列表 
	* @param model
	* @return
	* @throws Exception  
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("/list")
	public ResultMessage getList(@RequestBody AppUserModel model) throws Exception {
		
		BaseDto model1 = BaseDto.getPageInfo(model);
		logger.debug("获取用户列表，参数model：{}",model);
		Page<AppUserDto> page = PageHelper.startPage(model1.getPageNum(), model1.getPageSize());
		appUserService.getList(model);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page,model.getDraw());

		return result;

	}
		
	/** 
	* @Title: getInfo 
	* @Description: 根据用户编号获取用户信息
	* @param appUserModel
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/getInfo")
	public ResultMessage getInfo(@RequestBody AppUserModel appUserModel) throws Exception {
		logger.debug("根据用户编号获取用户信息，userId:{}", appUserModel.getUserId());
		appUserModel = appUserService.selectByPrimaryKey(appUserModel.getUserId());
		logger.info("用户信息：{}",appUserModel);
		ResultMessage results = BaseController.getResults(appUserModel);
		return results;
	}
	
	/** 
	* @Title: getLoginInfo 
	* @Description: 获取登录用户基本信息
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/getLoginInfo")
	public ResultMessage getLoginInfo() throws Exception {
		List<Object> lists = new ArrayList<Object>();
		
		logger.debug("获取登录用户基本信息, session.loginUserCode:{}",Constants.LOGIN_USER_CODE());
		AppUserModel appUserModel = appUserService.getInfoByUserCode(Constants.LOGIN_USER_CODE());
		logger.info("用户信息：{}", appUserModel);
		if(appUserModel != null){
			lists.add(appUserModel);
		}
		ResultMessage results = BaseController.getResults(lists);
		return results;
	}

	/** 
	* @Title: changeInfo 
	* @Description: 新增或编辑单行数据
	* @param userModel
	* @return
	* @throws Exception  
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/changeInfo")
	public ResultMessage changeInfo(@RequestBody AppUserModel userModel) throws Exception {
		StringBuffer msg = new StringBuffer();
		/*String loginUserId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY);*/
		Integer result = 0;
		AppUserModel userTemp = null;
		// 判断操作类型
		if ("add".equals(userModel.getReqType())) {

			// 判断唯一
			logger.debug("select userModel by userCode:{}", userModel.getUserCode());
			userTemp = appUserService.getInfoByUserCode(userModel.getUserCode());

			if (userTemp != null) {

				logger.info("userCode:{} is already exist", userModel.getUserCode());
				msg.append(userModel.getUserCode() + "已存在！");
			} else {
				String userIds = UuidUtils.getUuid(); 
				userModel.setUserId(userIds);
				appUserService.buildPassword(userModel);
				
				userModel.setCompanyCode("compCode");		//归属机构代码
				userModel.setCreatedBy(Constants.LOGIN_USER_CODE());
				userModel.setUpdatedBy(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行新增方法：addInfo()，新增信息：{}", userModel);
				result = appUserService.addInfo(userModel);
				logger.debug("新增完成，结果：{}", result);
			}

		} else if ("edit".equals(userModel.getReqType())) {

			// 根据id判断是否存在
			logger.debug("select Project by getUserId:{}", userModel.getUserId());
			userTemp = appUserService.selectByPrimaryKey(userModel.getUserId());

			if (userTemp != null) {

				userModel.setUpdatedBy(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行修改方法：updateInfo()，修改信息为：{}", userModel);
				result = appUserService.updateInfo(userModel);
				logger.debug("修改完成，结果：{}", result);
			} else {
				logger.info("userId:{} is not exist", userModel.getUserId());
				msg.append(userModel.getUserId() + "不存在！");
			}

		}
		
		msg.append("add".equals(userModel.getReqType()) ? "新增" : "编辑");
		msg.append(result == 1 ? "操作成功!" : "操作失败!");
		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

	/** 
	* @Title: deleteByIds 
	* @Description: 根据编号删除用户信息
	* @param ids
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/deleteByIds")
	public ResultMessage deleteByIds(@RequestParam(value = "ids", required = false) String ids) throws Exception {
		
		StringBuffer msg = new StringBuffer();
		Integer result = null;
		logger.debug("deleteByIds DataSource by ids:{}", ids);
		
		List<String> idsParam = PublicMethods.SplitIdsToArray(ids);
		logger.debug("格式化后的ids: {}", idsParam);
		logger.debug("开始执行删除方法：deleteList()");
		result = appUserService.deleteList(idsParam);
		logger.info("删除结束,结果：{}", result);
		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");
		logger.info("用户 " + "[" + Constants.LOGIN_USER_CODE()+ "] 删除 用户数：" +  idsParam.size());    
		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}
	
}
