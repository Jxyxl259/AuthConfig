package com.yaic.auth.thirdparty.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yaic.auth.common.BaseController;
import com.yaic.auth.common.Constants;
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.thirdparty.model.AuthEncryptModel;
import com.yaic.auth.thirdparty.model.AuthMappingModel;
import com.yaic.auth.thirdparty.service.AuthEncryptService;
import com.yaic.auth.thirdparty.service.AuthMappingService;

/** 
* @ClassName: AuthEncryptController 
* @Description: 渠道与方案的鉴权与加密功能
* @author 
* @date 2018年6月17日 下午9:47:19 
*  
*/
@RestController
@RequestMapping("/authEncrypt")
public class AuthEncryptController {
	private static final Logger logger = LoggerFactory.getLogger(AuthEncryptController.class);
	@Autowired
	private AuthEncryptService authEncryptService;
	@Autowired
	private AuthMappingService authMappingService;
	

	/** 
	* @Title: getList 
	* @Description: 获取鉴权信息
	* @param authEncryptModel
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("/list")
	public ResultMessage getList(@RequestBody AuthEncryptModel authEncryptModel) throws Exception {
		if (StringUtils.isEmpty(authEncryptModel)) {
			authEncryptModel = new AuthEncryptModel();
		}
		logger.info("执行查询方法getList()，参数为:{}", authEncryptModel);
		Page<AuthEncryptModel> page = PageHelper.startPage(authEncryptModel.getPageNum(), authEncryptModel.getPageSize());
		authEncryptService.getList(authEncryptModel);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page);

		return result;

	}

	/** 
	* @Title: saveOrUpdParaType 
	* @Description: 添加或者编辑
	* @param authEncryptModel
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("/changeInfo")
	public ResultMessage changeInfo(@RequestBody AuthEncryptModel authEncryptModel) throws Exception {
		StringBuffer msg = new StringBuffer();

		Integer result = 0;
		AuthEncryptModel authEncryptTemp = null;
		ResultMessage results = BaseController.getResults(0,"操作失败!");
		
		//服务配置 HIS 类型的特殊添加
		boolean editAuthMappInfo = false;
		String authMappId = authEncryptModel.getAppId();
		if(authEncryptModel.getReqType().indexOf(":") > 0){
			editAuthMappInfo = true;
			
			//鉴权信息不存在,则新增一条
			if(StringUtils.isEmpty(authEncryptModel.getAuthId())){
				authEncryptModel.setReqType("add");
			}else
				authEncryptModel.setReqType(authEncryptModel.getReqType().split(":")[0]);
				
		}
		
		// 判断操作类型
		if ("add".equals(authEncryptModel.getReqType())) {

			if(editAuthMappInfo)
				authEncryptModel.setAppId(null);
			
			authEncryptModel.setCreatedUser(Constants.LOGIN_USER_CODE());
			authEncryptModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
			logger.debug("开始执行新增方法：addInfo(),添加数据:{}",authEncryptModel);
			result = authEncryptService.addInfo(authEncryptModel);
			logger.info("authEncryptModel 添加结果:{}",result);

			msg.append(result == 1 ? "操作成功!" : "操作失败!");
			results = BaseController.getResults(authEncryptModel.getAuthId(), msg.toString());
		} else if ("edit".equals(authEncryptModel.getReqType())) {

			// 根据id判断是否存在
			logger.debug("select AuthEncryptModel by accountId:{}", authEncryptModel.getAuthId());
			authEncryptTemp = authEncryptService.getOneByAuthId(authEncryptModel.getAuthId());

			if (authEncryptTemp != null) {

				if(editAuthMappInfo)
					authEncryptModel.setAppId(authEncryptTemp.getAppId());
				
				authEncryptModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行修改方法：updateInfo(),修改信息：{}",authEncryptModel);
				result = authEncryptService.updateInfo(authEncryptModel);
				logger.info("authEncryptModel 更新结果:{}",result);
				
				msg.append(result == 1 ? "操作成功!" : "操作失败!");
				results = BaseController.getResults(authEncryptModel.getAuthId(), msg.toString());
			} else {

				logger.info("authId:{} is not exist", authEncryptModel.getAuthId());
				msg.append(authEncryptModel.getAuthId() + "不存在！");
				
				results = BaseController.getResults(0, msg.toString());
			}
		}
		
		//修改服务关系信息中的authId
		if(result > 0 && editAuthMappInfo){
			AuthMappingModel model1 = authMappingService.getOneByMappingId(Integer.parseInt(authMappId));
			model1.setAuthId(authEncryptModel.getAuthId());
			result = authMappingService.updateInfo(model1);
		}

		return results;
	}

	/** 
	* @Title: delete 
	* @Description: 根据编号删除
	* @param authId
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@GetMapping(value = "/delete/{authId}")
	public ResultMessage delete(@PathVariable Integer authId) throws Exception {
		StringBuffer msg = new StringBuffer();
		Integer result = 0 ;
		logger.debug("delete AuthEncrypt by authId:{}", authId);
		
		//根据Id查询对应的信息
		logger.debug("select AuthEncryptModel by authId:{}",authId);
		AuthEncryptModel model = authEncryptService.getOneByAuthId(authId);
		
		if(model != null) {
			logger.debug("开始执行删除方法：deleteInfo()");
			result = authEncryptService.deleteInfo(authId);	
			logger.info("删除结束,结果：{}",result);
		}

		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");

		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

}
