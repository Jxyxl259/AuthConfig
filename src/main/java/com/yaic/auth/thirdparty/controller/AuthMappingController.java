package com.yaic.auth.thirdparty.controller;

import org.apache.commons.lang.StringUtils;
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
import com.yaic.auth.common.Constants;
import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.thirdparty.model.AuthEncryptModel;
import com.yaic.auth.thirdparty.model.AuthMappingModel;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.service.AuthMappingService;

/**
 * @ClassName: AuthMappingController
 * @Description: 方案与服务关系
 * @author
 * @date 2018年6月17日 下午9:48:04
 * 
 */
@RestController
@RequestMapping("/authmapping")
public class AuthMappingController {
	private static final Logger logger = LoggerFactory.getLogger(AuthMappingController.class);
	@Autowired
	private AuthMappingService authMappingService;

	/** 
	* @Title: getList 
	* @Description: 根据条件查询
	* @param authMappingModel
	* @return
	* @throws Exception  
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("/list")
	public ResultMessage getList(@RequestBody AuthMappingModel authMappingModel) throws Exception {
		if (authMappingModel == null) {
			authMappingModel = new AuthMappingModel();
		}
		logger.info("执行查询方法getList()，参数为:{}", authMappingModel);
		Page<AuthEncryptModel> page = PageHelper.startPage(authMappingModel.getPageNum(),
				authMappingModel.getPageSize());
		authMappingService.getList(authMappingModel);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page);

		return result;
	}

	/** 
	* @Title: saveOrUpdParaType 
	* @Description: 新增或者编辑
	* @param authMappingModel
	* @return
	* @throws Exception  
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("changeInfo")
	public ResultMessage saveOrUpdParaType(@RequestBody AuthMappingModel authMappingModel) throws Exception {
		StringBuffer msg = new StringBuffer();

		Integer result = 0;
		AuthMappingModel authMappingTemp = null;
		ResultMessage results = BaseController.getResults(0, "操作失败!");

		// 验证关系是否符合规则
		logger.debug("验证关系是否符合规则:{}", authMappingModel);
		String resultMeg = authMappingService.judgeAuthMappInfo(authMappingModel);
		logger.debug("验证结果:" + resultMeg);
		if (StringUtils.isNotBlank(resultMeg)) {

			results = BaseController.getResults(0, resultMeg);
			return results;
		}

		ProjectModel prodModel = new ProjectModel();
		// 如果接口类型为原始,自定义或者加密,需添加authId与appId,并需要在账号与服务关系表中添加一条记录
		if (PublicParamters.REQUEST_TYPE_HISTORY_COMMON.equals(authMappingModel.getRequestType())
				|| PublicParamters.REQUEST_TYPE_HISTORY_ENCRYPT.equals(authMappingModel.getRequestType())
				|| PublicParamters.REQUEST_TYPE_HISTORY_CUSTOM.equals(authMappingModel.getRequestType())) {

			// prodModel =
			// projectService.getOneByProjectId(authMappingModel.getProjectId());
			authMappingModel.setProjectId(null);
			authMappingModel.setAuthId(prodModel.getAuthId());
		}

		// 判断操作类型
		if ("add".equals(authMappingModel.getReqType())) {

			authMappingModel.setCreatedUser(Constants.LOGIN_USER_CODE());
			authMappingModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
			logger.debug("开始执行新增方法：addInfo(),添加数据:{}", authMappingModel);
			result = authMappingService.addInfo(authMappingModel);
			logger.info("authMappingModel 添加结果:{}", result);

			msg.append(result == 1 ? "操作成功!" : "操作失败!");
			results = BaseController.getResults(authMappingModel.getMappingId(), msg.toString());
		}

		else if ("edit".equals(authMappingModel.getReqType())) {

			// 根据id判断是否存在
			logger.debug("select AuthMapping by mappingId:{}", authMappingModel.getMappingId());
			authMappingTemp = authMappingService.getOneByMappingId(authMappingModel.getMappingId());

			if (authMappingTemp != null) {

				authMappingModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行修改方法：updateInfo(),修改信息：{}", authMappingModel);
				result = authMappingService.updateInfo(authMappingModel);
				logger.info("authMappingModel 更新结果:{}", result);

				msg.append(result == 1 ? "操作成功!" : "操作失败!");
				results = BaseController.getResults(authMappingModel.getMappingId(), msg.toString());
			} else {

				logger.info("mppingId:{} is not exist", authMappingModel.getMappingId());
				msg.append(authMappingModel.getMappingId() + "不存在！");

				results = BaseController.getResults(0, msg.toString());
			}

		}

		return results;
	}

	/** 
	* @Title: deleteById 
	* @Description: 根据编号删除关系信息
	* @param mappingId
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/deleteById")
	public ResultMessage deleteById(@RequestParam(value = "mappingId", required = false)String mappingId) throws Exception {
		StringBuffer msg = new StringBuffer();
		logger.debug("delete AuthMapping by mappingId:{}", mappingId);
		Integer result = 0;
		//根据Id查询对应的信息
		logger.debug("select AuthMappingModel by mappingId:{}",mappingId);
		AuthMappingModel model = authMappingService.getOneByMappingId(Integer.parseInt(mappingId));
		
		if(model != null) {
			 logger.debug("开始执行删除方法：deleteInfo()");
			 result = authMappingService.deleteInfo(Integer.parseInt(mappingId));
			 logger.info("删除结束,结果：{}",result);
		}
		

		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");

		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

}
