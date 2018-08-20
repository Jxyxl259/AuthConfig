package com.yaic.auth.thirdparty.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
import com.yaic.auth.thirdparty.model.CallbackUrlModel;
import com.yaic.auth.thirdparty.service.CallbackUrlService;


/** 
* @ClassName: CallbackUrlController 
* @Description: 回调功能
* @author wangjs
* @date 2018年6月4日 下午9:48:54 
*  
*/
@RestController
@RequestMapping("callback")
public class CallbackUrlController {
	private static final Logger logger = LoggerFactory.getLogger(CallbackUrlController.class);
	@Autowired
	private CallbackUrlService callbackUrlService;

	@PostMapping("/list1")
	public ResultMessage getList1(@RequestBody CallbackUrlModel callbackUrlModel) throws Exception {
		if (StringUtils.isEmpty(callbackUrlModel)) {
			callbackUrlModel = new CallbackUrlModel();
		}
		logger.info("执行查询方法getList1()，参数为:{}", callbackUrlModel);
		Page<CallbackUrlModel> page = PageHelper.startPage(callbackUrlModel.getPageNum(), callbackUrlModel.getPageSize());
		callbackUrlService.getList(callbackUrlModel);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page);

		return result;
	}
	
	@PostMapping("/list")
	public ResultMessage getList(@RequestBody CallbackUrlModel model) throws Exception {

		BaseDto model1 = BaseDto.getPageInfo(model);
		logger.info("执行查询方法getList()，参数为:{}", model);
		Page<CallbackUrlModel> page = PageHelper.startPage(model1.getPageNum(), model1.getPageSize());
		callbackUrlService.getCallbackUrlViewDto(model);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page,model.getDraw());

		return result;
	}
	
	
	/** 
	* @Title: getInfo 
	* @Description: 根据编号获取回调信息
	* @param model
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/getInfo")
	public ResultMessage getInfo(@RequestBody CallbackUrlModel callbackUrlModel) throws Exception {
		logger.debug("getInfo ServerModel by callbackId:{}", callbackUrlModel.getCallbackUrlId());
		callbackUrlModel = callbackUrlService.getOneByCallbackUrlId(callbackUrlModel.getCallbackUrlId());
		logger.info("查询结果：{}",callbackUrlModel);
		ResultMessage results = BaseController.getResults(callbackUrlModel);
		return results;
	}

	/** 
	* @Title: saveOrUpdParaType 
	* @Description: 新增或者编辑
	* @param callbackUrlModel
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("changeInfo")
	public ResultMessage saveOrUpdParaType(@RequestBody CallbackUrlModel callbackUrlModel) throws Exception {
		StringBuffer msg = new StringBuffer();

		Integer result = 0;
		CallbackUrlModel temp = null;
		ResultMessage results =  BaseController.getResults(0,"操作失败!");
		
		// 判断唯一
		logger.debug("select CallbackUrl by projectId:{},getCallbackType:{}", callbackUrlModel.getProjectId(),
				callbackUrlModel.getCallbackType());
		temp = new CallbackUrlModel();
		temp.setCallbackType(callbackUrlModel.getCallbackType());
		temp.setCallbackEnv(callbackUrlModel.getCallbackEnv());
		temp.setProjectId(callbackUrlModel.getProjectId());
		List<CallbackUrlModel> lists = callbackUrlService.getList(temp);
		
		// 判断操作类型
		if ("add".equals(callbackUrlModel.getReqType())) {

			if (lists != null && lists.size() > 0) {

				logger.info("projectId:{},getCallbackType:{},getCallbackEnv:{} is already exist", callbackUrlModel.getProjectId(),
						callbackUrlModel.getCallbackType(),callbackUrlModel.getCallbackEnv());
				msg.append("回调类型:" + callbackUrlModel.getCallbackType() + "在"+callbackUrlModel.getCallbackEnv()+"环境下已存在！");
				
				results = BaseController.getResults(0, msg.toString());
			} else {

				callbackUrlModel.setCreatedUser(Constants.LOGIN_USER_CODE());
				callbackUrlModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行新增方法：addInfo()");
				result = callbackUrlService.addInfo(callbackUrlModel);
				logger.info("callbackUrlModel 添加结果:{}",result);
				
				msg.append(result == 1 ? "操作成功!" : "操作失败!");
				results = BaseController.getResults(callbackUrlModel.getCallbackUrlId(), msg.toString());
			}

		} else if ("edit".equals(callbackUrlModel.getReqType())) {

			if (lists != null && lists.size() > 0 && !lists.get(0).getCallbackUrlId().equals(callbackUrlModel.getCallbackUrlId())) {

				logger.info("projectId:{},getCallbackType:{},getCallbackEnv:{} is already exist", callbackUrlModel.getProjectId(),
						callbackUrlModel.getCallbackType(),callbackUrlModel.getCallbackEnv());
				msg.append("回调类型:" + callbackUrlModel.getCallbackType() + "在"+callbackUrlModel.getCallbackEnv()+"环境下已存在！");
				
				results = BaseController.getResults(0, msg.toString());
				return results;
			}
			
			// 根据id判断是否存在
			logger.debug("select CallbackUrl by callbackUrlId:{}", callbackUrlModel.getCallbackUrlId());
			temp = callbackUrlService.getOneByCallbackUrlId(callbackUrlModel.getCallbackUrlId());
			logger.debug("查询结果：{}",temp);
			if (temp != null) {

				callbackUrlModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行修改方法：updateInfo()");
				result = callbackUrlService.updateInfo(callbackUrlModel);
				logger.info("callbackUrlModel 更新结果:{}",result);
				
				msg.append(result == 1 ? "操作成功!" : "操作失败!");
				results = BaseController.getResults(callbackUrlModel.getCallbackUrlId(), msg.toString());
			} else {
				logger.info("callbackUrlId:{} is not exist", callbackUrlModel.getCallbackUrlId());
				msg.append("数据不存在！");
				
				results = BaseController.getResults(0, msg.toString());
			}

		}

		return results;
	}

	/** 
	* @Title: deleteById 
	* @Description: 根据编号单条删除
	* @param callbackUrlId
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/deleteById")
	public ResultMessage deleteById(@RequestParam(value = "callbackUrlId", required = false)String callbackUrlId) throws Exception {
		StringBuffer msg = new StringBuffer();
		Integer result = 0;
		logger.debug("delete CallbackUrl by callbackUrlId:{}", callbackUrlId);
		
		//根据Id查询对应的信息
		logger.debug("select CallbackUrlModel by callbackUrlId:{}",callbackUrlId);
		CallbackUrlModel model = callbackUrlService.getOneByCallbackUrlId(Integer.parseInt(callbackUrlId));
		
		if(model != null) {
			logger.debug("开始执行删除方法：deleteInfo()");
			result = callbackUrlService.deleteInfo(Integer.parseInt(callbackUrlId));
			logger.info("删除结束,结果：{}",result);
		}

		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");

		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

	/** 
	* @Title: deleteByIds 
	* @Description: 批量删除
	* @param ids
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/deleteByIds")
	public ResultMessage deleteByIds(@RequestParam(value = "ids", required = false) String ids) throws Exception {
		StringBuffer msg = new StringBuffer();

		List<String> idsParam = PublicMethods.SplitIdsToArray(ids);
		logger.debug("deleteByIds CallbackUrl by ids:{}", ids);
		int result = callbackUrlService.deleteInfoByIds(idsParam);

		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");

		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

}
