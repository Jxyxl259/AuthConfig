package com.yaic.auth.thirdparty.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yaic.auth.common.BaseController;
import com.yaic.auth.common.BaseDto;
import com.yaic.auth.common.BeanCheck;
import com.yaic.auth.common.Constants;
import com.yaic.auth.common.PublicMethods;
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.thirdparty.model.AuthTokenModel;
import com.yaic.auth.thirdparty.service.AuthTokenService;


/** 
* @ClassName: AuthTokenController 
* @Description: Token功能
* @author 
* @date 2018年6月17日 下午9:48:26 
*  
*/
@RestController
@RequestMapping("/authtoken")
public class AuthTokenController {
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenController.class);
	@Autowired
	private AuthTokenService authTokenService;

	@PostMapping("/list")
	public ResultMessage getList(@RequestBody AuthTokenModel model) throws Exception {
		logger.info("执行查询方法getList()，参数为:{}", model);
		BaseDto model1 = BaseDto.getPageInfo(model);

		Page<AuthTokenModel> page = PageHelper.startPage(model1.getPageNum(), model1.getPageSize());
		authTokenService.getList(model);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page,model.getDraw());

		return result;
	}
	
	/**
	* @Title: getInfo 
	* @Description: 根据id获取token信息
	* @param serverModel
	* @return    
	* @return ResultMessage  
	* @throws 
	 */
	@PostMapping(value = "/getInfo")
	public ResultMessage getInfo(@RequestBody AuthTokenModel authTokenModel) throws Exception {
		logger.debug("getInfo AuthTokenModel by authTokenId:{}", authTokenModel.getAppId());
		authTokenModel = authTokenService.getOneByTokenId(authTokenModel.getTokenId());
		logger.debug("查询结果为：{}",authTokenModel);
		ResultMessage results = BaseController.getResults(authTokenModel);
		return results;
	}

	/** 
	* @Title: saveOrUpdParaType 
	* @Description: 新增或者编辑
	* @param authTokenModel
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("/changeInfo")
	public ResultMessage saveOrUpdParaType(@RequestBody AuthTokenModel authTokenModel) throws Exception {
		StringBuffer msg = new StringBuffer();
        logger.info("authTokenModel:{}",authTokenModel);
		Integer result = 0;
		AuthTokenModel authTokenTemp = null;
		logger.debug("进行数据校验：{}",authTokenModel);
		String checkResult = BeanCheck.check(authTokenModel);
		logger.debug("数据校验结束");
		if(!StringUtils.isEmpty(checkResult)) {
			msg.append(checkResult);
		}else {
//		 判断操作类型
		if ("add".equals(authTokenModel.getReqType())) {

			// 判断唯一
			logger.debug("select AuthTokenModel by appId:{},appSecret:{}", authTokenModel.getAppId(),
					authTokenModel.getAppSecret());
			authTokenTemp = authTokenService.getOneByAppIdAndAppSecret(authTokenModel.getAppId(), authTokenModel.getAppSecret());

			if (!StringUtils.isEmpty(authTokenTemp)) {

				logger.info("appId:{},appSecret:{} is already exist", authTokenModel.getAppId(), authTokenModel.getAppSecret());
				msg.append(authTokenModel.getAppId() + "," + authTokenModel.getAppSecret() + "已存在！");
			} else {

				authTokenModel.setCreatedUser(Constants.LOGIN_USER_CODE());
				authTokenModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行新增方法：addInfo()");
				result = authTokenService.addInfo(authTokenModel);
				logger.info("新增结束,结果：{}",result);
			}

		} else if ("edit".equals(authTokenModel.getReqType())) {

			// 判断是否存在
			logger.debug("select AuthTokenModel by tokenId:{}",authTokenModel.getTokenId());
			authTokenTemp = authTokenService.getOneByTokenId(authTokenModel.getTokenId());
			
			if (!StringUtils.isEmpty(authTokenTemp)) {
       
				authTokenModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行修改方法：updateInfo()");
				result = authTokenService.updateInfo(authTokenModel);
				logger.info("修改结束,结果：{}",result);
			} else {

				logger.info("authTokenModel:{} is not exist", authTokenModel);
				msg.append(authTokenModel + "不存在！");
			}

		}

		msg.append("add".equals(authTokenModel.getReqType()) ? "新增" : "编辑");
		msg.append(result == 1 ? "操作成功!" : "操作失败!");
		}
		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

	/** 
	* @Title: delete 
	* @Description: 根据编号删除
	* @param appId
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@GetMapping(value = "/delete/{tokenId}")
	public ResultMessage delete(@PathVariable Integer tokenId) throws Exception {
		StringBuffer msg = new StringBuffer();
		Integer result = 0;
		logger.debug("delete AuthToken by tokenId:{}", tokenId);
		//根据Id查询对应的信息
		logger.debug("select AuthTokenModel by tokenId:{}",tokenId);
		AuthTokenModel model = authTokenService.getOneByTokenId(tokenId);
		
		if(model != null) {
			 logger.debug("开始执行删除方法：deleteInfo()");
			 result = authTokenService.deleteInfo(tokenId);
			 logger.info("删除结束,结果：{}",result);
		}

		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");

		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}
	
	/** 
	* @Title: deleteByIds 
	* @Description: 批量删除
	* @param appIds
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/deleteByIds")
	public ResultMessage deleteByIds(@RequestParam(value = "ids", required = false) String ids) throws Exception {
		
		StringBuffer msg = new StringBuffer();
		logger.debug("deleteByIds authToke by tokenIds:{}", ids);
		
		List<String> appIdsParam = PublicMethods.SplitIdsToArray(ids);
		logger.debug("格式化后的ids: {}", appIdsParam);
		logger.debug("开始执行删除方法：deleteInfoByIds()");
		int result = authTokenService.deleteInfoByIds(appIdsParam);
		logger.info("删除结束,结果：{}", result);
		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");

		ResultMessage results = BaseController.getResults(result,msg.toString());
		return results;
	}

}
