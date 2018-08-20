package com.yaic.auth.thirdparty.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.thirdparty.model.AccountModel;
import com.yaic.auth.thirdparty.service.AccountService;

/** 
* @ClassName: AccountController 
* @Description: 账户管理
* @author zhaoZD
* @date 2018年6月17日 下午9:46:30 
*  
*/
@RestController
@RequestMapping("/account")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	
	/** 
	* @Title: getList 
	* @Description: 列表查询
	* @param accountModel
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/list")
	public ResultMessage getList(@RequestBody AccountModel model) throws Exception{
		logger.info("执行查询方法，参数为，账号ID:{}，账号代码:{}，账号名称:{} ", model.getAppId(),model.getAppCode(),model.getAccountName());
		
		BaseDto model1 = BaseDto.getPageInfo(model);
		Page<AccountModel> page = PageHelper.startPage(model1.getPageNum(),model1.getPageSize());
	    accountService.getList(model);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page,model.getDraw());

		return result;
	}
	
	
	/** 
	* @Title: getInfo 
	* @Description: 根据账户编号获取账户信息
	* @param model
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/getInfo")
	public ResultMessage getInfo(@RequestBody AccountModel model)  throws Exception{
		logger.debug("getInfo Account by accountId:{}", model.getAccountId());
		model = accountService.getOneByAccountId(model.getAccountId());
		logger.info("查询结果：{}",model);
		if(model == null){
			model = new AccountModel();
		}
		ResultMessage results = BaseController.getResults(model);
		return results;
	}
	
	@PostMapping(value = "/listAll")
	public ResultMessage listAll() throws Exception {
		logger.debug("excute method: listAll() ");
		
		List<AccountModel> lists = accountService.getList(new AccountModel());
		logger.info("查询结果条数为：{} 条",lists.size());
		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(lists);

		return result;

	}

	/** 
	* @Title: saveOrUpdParaType 
	* @Description: 添加或者编辑
	* @param accountModel
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/changeInfo")
	public ResultMessage changeInfo(@RequestBody AccountModel accountModel) throws Exception {
		StringBuffer msg = new StringBuffer();
		Integer result = 0;
		logger.debug("进行数据校验：{}",accountModel);
		String checkResult = BeanCheck.check(accountModel);
		logger.debug("数据校验结束");
		if(StringUtils.isBlank(accountModel.getAppId())){
			checkResult += "数据不完整";
		}
		
		if(!StringUtils.isEmpty(checkResult)) {	
			msg.append(checkResult);
		}else {
			
			// 判断唯一
			logger.debug("select AccountModel by appCode:{}", accountModel.getAppCode());
			AccountModel accountTemp = accountService.getOneByAppCode(accountModel.getAppCode());

			// 判断操作类型
			if ("add".equals(accountModel.getReqType())) {
					
					if (accountTemp != null) {
						
						logger.info("appCode:{} is already exist", accountModel.getAppCode());
						msg.append(accountModel.getAppCode() + "已存在！");
					} else {
						
						accountModel.setCreatedUser(Constants.LOGIN_USER_CODE());
						accountModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
						logger.debug("开始执行新增方法：addInfo()");
						result = accountService.addInfo(accountModel);
						logger.info("新增结束,结果：{}",result);
					}
				
			} else if ("edit".equals(accountModel.getReqType())) {
	
				// 根据id判断是否存在
				logger.debug("select AccountModel by accountId:{}", accountModel.getAccountId());
				accountTemp = accountService.getOneByAccountId(accountModel.getAccountId());
				if (accountTemp != null) {
					
					accountModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
					logger.debug("开始执行修改方法：updateInfo()");
					result = accountService.updateInfo(accountModel);
					logger.info("修改结束,结果：{}",result);
				} else {

					logger.info("accountId:{} is not exist", accountModel.getAccountId());
					msg.append(accountModel.getAccountId() + "不存在！");
				}
	
			}
	
			msg.append("add".equals(accountModel.getReqType()) ? "新增" : "编辑");
			msg.append(result == 1 ? "操作成功!" : "操作失败!");
		}
		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

	/** 
	* @Title: delete 
	* @Description: 根据编号删除
	* @param accountId
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@GetMapping(value = "/delete/{accountId}")
	public ResultMessage delete(@PathVariable Integer accountId) throws Exception {
		StringBuffer msg = new StringBuffer();
		Integer result = 0 ;
		ResultMessage results = BaseController.getResults(0, "删除操作失败!");
		
		logger.debug("delete Account by accountId:{}", accountId);
		
		if(accountId != null && accountId == PublicParamters.CAN_NOT_DELETE_ACCOUNT_ID){
			
			results = BaseController.getResults(0,"自定义_配置为特殊账号,不能删除");
		}else{

			//根据Id查询对应的信息
			logger.debug("select AccountModel by accountId:{}",accountId);
			AccountModel model = accountService.getOneByAccountId(accountId);
			
			if(model != null) {
				logger.debug("开始执行删除方法：deleteInfo()");
				result = accountService.deleteInfo(accountId);
				logger.info("删除结束,结果：{}",result);
			}
			
			msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");
			
			results = BaseController.getResults(result, msg.toString());
		}
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
		ResultMessage results = BaseController.getResults(0, "删除操作失败!");
		
		logger.debug("deleteByIds Account by ids:{}", ids);
		
		List<String> idsParam = PublicMethods.SplitIdsToArray(ids);
		logger.debug("格式化后的ids: {}", idsParam);
		if(idsParam.contains("1")){
			
			results = BaseController.getResults(0,"自定义_配置为特殊账号,不能删除");
		}else{
			logger.debug("开始执行删除方法：deleteInfoByIds()");
			Integer result = accountService.deleteInfoByIds(idsParam);
			logger.info("删除结束,结果：{}", result);
			msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");
			results = BaseController.getResults(result, msg.toString());
		}
		return results;
	}
	
//	@PostMapping(value = "/validateAppid")
//	public boolean validateAppid(@RequestParam(value = "appId", required = false) String appId) throws Exception {
//	
//		logger.debug("validateAppid Account by appId:{}", appId);
//		AccountModel model = accountService.getOneByAppid(appId);
//		if(model == null){
//			return true;
//		}else{
//			return false;
//		}
//	}
}
