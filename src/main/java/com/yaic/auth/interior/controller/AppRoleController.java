package com.yaic.auth.interior.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.yaic.auth.interior.model.AppRoleModel;
import com.yaic.auth.interior.service.AppRoleService;

/** 
* @ClassName: AppRoleController 
* @Description: 内部角色管理
* @author 宋淑华
* @date 2018年6月3日 下午4:00:36 
*  
*/
@RestController
@RequestMapping("/role")
public class AppRoleController {
	private static final Logger logger = LoggerFactory.getLogger(AppRoleController.class);
	@Autowired
	 private  AppRoleService  appRoleService;
	
	/**
	 * 获取角色列表
	 * @param aoData
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/list")
	public ResultMessage getList(@RequestBody AppRoleModel model) throws Exception {
		
		BaseDto model1 = BaseDto.getPageInfo(model);
		logger.debug("获取角色列表，参数roleId：{} ,roleName:{}",model.getRoleId(),model.getRoleName());
		Page<AppRoleModel> page = PageHelper.startPage(model1.getPageNum(), model1.getPageSize());
		appRoleService.getList(model);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page,model.getDraw());

		return result;

	}
	
	/** 
	* @Title: getInfo 
	* @Description: 根据角色编号获取对应角色信息
	* @param appRoleModel
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/getInfo")
	public ResultMessage getInfo(@RequestBody AppRoleModel appRoleModel) throws Exception {
		logger.debug("getInfo getRoleId by appRoleModel:{}", appRoleModel.getRoleId());
		appRoleModel = appRoleService.selectByPrimaryKey(appRoleModel.getRoleId());
		logger.info("角色名称：{}",appRoleModel.getRoleName());
		ResultMessage results = BaseController.getResults(appRoleModel);
		return results;
	}
	
	@PostMapping(value = "/getNewRoleId")
	public ResultMessage getNewRoleId() throws Exception {
		String newRoleId = appRoleService.createRoleId();
		logger.debug("AppRoleModel create getNewRoleId:{}",newRoleId);
		ResultMessage results = BaseController.getResults(0,newRoleId);
		return results;
	}
	
	/**
	 * @Title: saveOrUpdParaType @Description: 保存或者更新单条信息 @param
	 * projectModel @return @return ResultMessage @throws
	 */
	@PostMapping(value = "/changeInfo")
	public ResultMessage saveOrUpdParaType(@RequestBody AppRoleModel roleModel,HttpServletRequest request) throws Exception {
		StringBuffer msg = new StringBuffer();

		Integer result = 0;
		AppRoleModel roleTemp = null;
		// 判断操作类型
		if ("add".equals(roleModel.getReqType())) {

			// 判断唯一
			logger.debug("select userModel by roleId:{}", roleModel.getRoleId());
			roleTemp = appRoleService.selectByPrimaryKey(roleModel.getRoleId());

			if (roleTemp != null) {

				logger.info("roleId:{} is already exist", roleModel.getRoleId());
				msg.append(roleModel.getRoleId() + "已存在！");
			} else {
				
				roleModel.setCreatedBy(Constants.LOGIN_USER_CODE());
				roleModel.setUpdatedBy(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行新增方法：addInfo()，新增角色Id：{}，角色名称：{}", roleModel.getRoleId(),roleModel.getRoleName());
				result = appRoleService.addInfo(roleModel);
				logger.debug("新增完成，结果：{}", result);
			}

		} else if ("edit".equals(roleModel.getReqType())) {

			// 根据id判断是否存在
			logger.debug("select role by userId:{}", roleModel.getRoleId());
			roleTemp = appRoleService.selectByPrimaryKey(roleModel.getRoleId());

			if (roleTemp != null) {
				
				roleModel.setUpdatedBy(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行修改方法：updateInfo()，修改角色Id：{}，角色名称：{}", roleModel.getRoleId(),roleModel.getRoleName());
				result = appRoleService.updateInfo(roleModel);
				logger.debug("修改完成，结果：{}", result);
			} else {
				logger.info("roleId:{} is not exist", roleModel.getRoleId());
				msg.append(roleModel.getRoleId() + "不存在！");
			}

		}
		msg.append("add".equals(roleModel.getReqType()) ? "新增" : "编辑");
		msg.append(result == 1 ? "操作成功!" : "操作失败!");
		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

	/** 
	* @Title: deleteByIds 
	* @Description: 批量删除
	* @param ids
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/deleteByIds")
	public ResultMessage deleteByIds(@RequestParam(value = "ids", required = false) String ids) throws Exception {
		
		StringBuffer msg = new StringBuffer();
		logger.debug("deleteByIds AppRoleModel by ids:{}", ids);
		
		List<String> idsParam = PublicMethods.SplitIdsToArray(ids);
		logger.debug("格式化后的ids: {}", idsParam);
		logger.debug("开始执行删除方法：deleteList()");
		int result = appRoleService.deleteInfoByIds(idsParam);
		logger.info("删除结束,结果：{}", result);
		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");
		ResultMessage results = BaseController.getResults(result, msg.toString());
		
		return results;
	}
    
}
