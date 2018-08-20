package com.yaic.auth.thirdparty.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.yaic.auth.common.PublicMethods;
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.thirdparty.dto.AccountAuthMappDto;
import com.yaic.auth.thirdparty.model.AccountAuthMappModel;
import com.yaic.auth.thirdparty.model.AccountModel;
import com.yaic.auth.thirdparty.model.AuthEncryptModel;
import com.yaic.auth.thirdparty.service.AccountAuthMappService;
import com.yaic.auth.thirdparty.service.AccountService;
import com.yaic.auth.thirdparty.service.AuthEncryptService;


/** 
* @ClassName: AccountAuthMappController 
* @Description: 账户与服务关系功能
* @author zhaoZd
* @date 2018年7月26日 下午5:31:49 
*  
*/
@RestController
@RequestMapping("accountAuth")
public class AccountAuthMappController {
	private static final Logger logger = LoggerFactory.getLogger(AccountAuthMappController.class);
	@Autowired
	private AccountAuthMappService accountAuthMappService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AuthEncryptService authEncryptService;

	
	/** 
	* @Title: getList 
	* @Description: 获取所有HIS相关服务关系数据
	* @param projectModel
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("/list")
	public ResultMessage getList(@RequestBody AccountAuthMappDto model) throws Exception {
		BaseDto model1 = BaseDto.getPageInfo(model);
		Page<AccountAuthMappDto> page = PageHelper.startPage(model1.getPageNum(),model1.getPageSize());
		
		logger.info("获取所有HIS相关服务关系数据,参数 getRequestType:{}, getRequestUrl:{}, getServerEnv:{}, getServerStatus:{}, getServerType:{}",
				model.getRequestType(),model.getRequestUrl(),model.getServerEnv(),model.getServerStatus(),model.getServerType());
		accountAuthMappService.getAllHistoryMappLists(model);
		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page,model.getDraw());

		return result;
	}
	
	
	@PostMapping(value = "/getInfo")
	public ResultMessage getInfo(@RequestBody AccountAuthMappDto dto)  throws Exception{
		logger.debug("getInfo AccountAuthMappDto by mappingId:{}", dto.getMappingId());
		
		List<Object> listRes = new ArrayList<Object>();
		AuthEncryptModel encModel = new AuthEncryptModel();
		
		List<AccountAuthMappDto> lists = accountAuthMappService.getAllHistoryMappLists(dto);
		logger.info("查询结果：{}",lists.size());
		if(lists != null && lists.size() > 0){
			dto = lists.get(0);
			if(!StringUtils.isEmpty(dto.getAuthMappAuthId())){
				logger.debug("查询鉴权加密信息，参数：{}",dto.getAuthMappAuthId());
				encModel = authEncryptService.getOneByAuthId(dto.getAuthMappAuthId());
				logger.debug("查询结果：{}", encModel);
			}
		}

		listRes.add(dto);
		listRes.add(encModel);
		ResultMessage results = BaseController.getResults(listRes);
		return results;
	}
	
	/** 
	* @Title: getAccountAndMappingInfo 
	* @Description: 加载账户信息与该账户下的所有服务关系信息
	* @param model
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/getAccountAndMappingInfo")
	public ResultMessage getAccountAndMappingInfo(@RequestBody AccountModel model)  throws Exception{
		
		logger.debug("getAccountAndMappingInfo Account by accountId:{}", model.getAccountId());
		Map<String, Object> maps = new HashMap<String,Object>();
		
		AccountAuthMappDto dto = new AccountAuthMappDto();
		logger.debug("获取所有服务关系信息");
		List<AccountAuthMappDto> allHistoryMappLists = accountAuthMappService.getAllHistoryMappLists(dto);
		dto.setAccountId(model.getAccountId());
		logger.debug("获取该用户下的所有服务关系信息");
		List<AccountAuthMappDto> singleAccountAuthMappLists = accountAuthMappService.getSingleAccountAuthMappLists(dto);
		logger.debug("根据accountId：{} 查询账户信息",model.getAccountId());
		model = accountService.getOneByAccountId(model.getAccountId());
		logger.info("查询账户信息结果：{}",model);
		maps.put("returnAccount", model);
		maps.put("authMappLists", allHistoryMappLists);
		maps.put("singleAccountAuthMappLists", singleAccountAuthMappLists);
		
		ResultMessage results = BaseController.getResults(maps);
		return results;
	}
	
	@PostMapping("addAccountAuthmapp")
	public ResultMessage addAccountAuthmapp(@RequestBody AccountAuthMappDto dto) throws Exception {
		StringBuffer msg = new StringBuffer();
		Integer result = 0;
		ResultMessage results = BaseController.getResults(0,"操作失败");
		
		if(dto != null && dto.getAccountId() != null && dto.getMappingId() != null){
			
			//根据accountId获取对应的账户信息
			logger.debug("select AccountAuthMappDto by AccountId:{}", dto.getAccountId());
			AccountModel accoModel = accountService.getOneByAccountId(dto.getAccountId());
			
			AccountAuthMappDto dto1 = new AccountAuthMappDto(dto.getAccountId(),dto.getMappingId(),null,null);
			List<AccountAuthMappDto> lists = accountAuthMappService.getSingleAccountAuthMappLists(dto1);
			if(lists.size() > 0){
				logger.info("此关系数据已存在,请勿重复添加");
				
				msg.append("此关系数据已存在,请勿重复添加");
				results = BaseController.getResults(0, msg.toString());
			}else{
				AccountAuthMappModel model = new AccountAuthMappModel();
				model.setAppId(accoModel.getAppId());
				model.setMappingId(dto.getMappingId());
				logger.debug("开始执行新增方法：saveAccountAuthMappInfo()");
				result = accountAuthMappService.saveAccountAuthMappInfo(model);
				logger.info("新增结束,结果：{}",result);
				
				msg.append(result == 1 ? "操作成功!" : "操作失败!");
				results = BaseController.getResults(model.getId(), msg.toString());
			}
			
		}
		
		return results;
	}
	
	@PostMapping(value = "/deleteAccountAuthMapp")
	public ResultMessage deleteAccountAuthMapp(@RequestBody AccountAuthMappModel model) throws Exception {

		StringBuffer msg = new StringBuffer();

		logger.debug("delete AccountAuthMappModel by id:{}", model.getId());
		
		Integer result = accountAuthMappService.deleteInfo(model.getId());
		logger.info("删除结束,结果：{}",result);
		
		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");

		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}
	
	@PostMapping(value = "/deleteByIds")
	public ResultMessage deleteByIds(@RequestParam(value = "ids", required = false) String ids) throws Exception {
	
		StringBuffer msg = new StringBuffer();
		ResultMessage results = BaseController.getResults(0,"删除操作失败!");
		
		logger.debug("deleteByIds Account by ids:{}", ids);
		
		List<String> idsParam = PublicMethods.SplitIdsToArray(ids);
		
		logger.debug("格式化后的ids: {}", idsParam);
		if(idsParam.contains("1")){
			
			results = BaseController.getResults(0,"自定义_配置为特殊账号,不能删除");
		}else{
			logger.debug("开始执行删除方法：deleteInfoByIds()");
			Integer result = accountAuthMappService.deleteInfoByIds(idsParam);
			logger.info("删除结束,结果：{}", result);
			msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");
			results = BaseController.getResults(result, msg.toString());
		}
		return results;
	}
}
