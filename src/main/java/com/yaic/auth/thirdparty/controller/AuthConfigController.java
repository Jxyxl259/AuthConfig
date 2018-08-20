package com.yaic.auth.thirdparty.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yaic.auth.common.BaseController;
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.external.service.ExternalConfigService;
import com.yaic.auth.thirdparty.dto.AuthBasicInfo;
import com.yaic.auth.thirdparty.dto.DataSourceDto;
import com.yaic.auth.thirdparty.dto.ProjectDto;
import com.yaic.auth.thirdparty.dto.ServerDto;
import com.yaic.auth.thirdparty.model.AccountModel;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.service.AccountAuthMappService;
import com.yaic.auth.thirdparty.service.AccountService;
import com.yaic.auth.thirdparty.service.AuthConfigService;
import com.yaic.auth.thirdparty.service.AuthMappingService;
import com.yaic.auth.thirdparty.service.DataSourceService;
import com.yaic.auth.thirdparty.service.ProjectService;





/** 
* @ClassName: AuthConfigController 
* @Description: 配置管理功能
* @author 
* @date 2018年6月22日 上午10:44:17 
*  
*/
@RestController
@RequestMapping("/innerconfig")
public class AuthConfigController {

	private static final Logger logger = LoggerFactory.getLogger(AuthConfigController.class);

	@Autowired
	private AuthConfigService authconfigService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	DataSourceService dataSourceService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	ExternalConfigService configService;
	
	@Autowired
	AuthMappingService authMappingService;
	
	@Autowired
	AccountAuthMappService accountAuthMappService;
	
	static String accountPictureUrl = "../../lib/zTree/v3/css/zTreeStyle/img/diy/1_close.png";
	
	static String dataSourcePictureUrl = "../../lib/zTree/v3/css/zTreeStyle/img/diy/3.png";
	
	static String projectPictureUrl = "../../lib/zTree/v3/css/zTreeStyle/img/diy/8.png";
		
	/** 
	* @Title: queryAccountList 
	* @Description: 获取左侧树所有账户信息
	* @return
	* @throws Exception    
	* @return Map<String,Object>  
	* @throws 
	*/
	@PostMapping(value="account_list")  
	public Map<String,Object> queryAccountList(@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize,@RequestParam("accountName")String accountName) throws Exception{  
		Map<String,Object> map = new HashMap<String,Object>();  

		
		if(StringUtils.isBlank(pageNum.toString()))		pageNum = 1;
		if(StringUtils.isBlank(pageSize.toString()))	pageSize = 20;
		
		JSONArray jsonArray = new JSONArray();  
		AccountModel accountModel = new AccountModel();
		if(!StringUtils.isBlank(accountName)) accountModel.setAccountName(accountName);
		
		Page<Object> page = PageHelper.startPage(pageNum,pageSize);
		logger.debug("执行方法getAccountTrees() 获取所有账户");
		List<AccountModel> list = authconfigService.getAccountTrees(accountModel);
		logger.info("查询账户信息结果：{}", list.size());
		if(list !=null && list.size()>0){ 
			
			for(int i = 0 ; i<list.size() ; i++){  
				
				AccountModel deviceInfo = list.get(i);  
				JSONObject json = new JSONObject();  
				json.put("id", deviceInfo.getAccountId());  
				json.put("pId", 0);  
				json.put("name", deviceInfo.getAppCode() +":"+ deviceInfo.getAccountName());  
				json.put("rfid", deviceInfo.getAppId());  
				json.put("icon", accountPictureUrl);  

				jsonArray.add(json);  
			}
	
			map.put("data", jsonArray); 
			map.put("pages", page.getPages()); 
			map.put("pageInfo", "当前显示第"+pageNum+"页,共"+page.getPages()+"页,共"+page.getTotal()+"条");
		}
//		logger.info("<======= 已退出queryRegion方法");  
		return map;  
	} 
	
	/** 
	* @Title: queryDatasourceList 
	* @Description: 根据账户编号获取其下的渠道信息
	* @param accountId
	* @return
	* @throws Exception    
	* @return Map<String,Object>  
	* @throws 
	*/
	@PostMapping(value="datasource_list")  
	public Map<String,Object> queryDatasourceList(@RequestParam("parentId")String parentId) throws Exception{  
//		logger.info("======>进入queryParenCodeY方法");  
		logger.debug("执行方法queryDatasourceList() 根据账户编号获取其下的渠道信息");
		Map<String,Object> map = new HashMap<String, Object>();  
		JSONArray jsonArray = new JSONArray();
		
		List<DataSourceModel> list = authconfigService.getDatasourceTrees(parentId);
		logger.info("查询结果：",list.size());
		if(list !=null && list.size()>0){
			
			for(int i = 0; i<list.size(); i++){  
				
				DataSourceModel deviceInfo = list.get(i);  
				  
				JSONObject json = new JSONObject();  
				json.put("id", deviceInfo.getDataSourceId());  
				json.put("pId", deviceInfo.getDataSourceId());  
				json.put("name", deviceInfo.getDataSource() +":"+ deviceInfo.getSourceName());  
				json.put("rfid", deviceInfo.getAppId());  
				json.put("icon", dataSourcePictureUrl);  

				jsonArray.add(json);  
			}
		}
		map.put("data", jsonArray);  
//		logger.info("======>退出queryParenCodeY方法");  
		
		return map;  
	}
	
	/** 
	* @Title: queryProjectList 
	* @Description: 根据渠道编号获取其下的方案信息
	* @param datasourceId
	* @return
	* @throws Exception    
	* @return Map<String,Object>  
	* @throws 
	*/
	@PostMapping(value="project_list")  
	public Map<String,Object> queryProjectList(@RequestParam("parentId")String parentId) throws Exception{  
//		logger.info("======>进入queryParenCodeY方法");  
		logger.debug("执行方法queryProjectList() 根据渠道编号获取其下的方案信息");
		Map<String,Object> map = new HashMap<String, Object>();  
		JSONArray jsonArray = new JSONArray();
		
		List<ProjectModel> list = authconfigService.getProjectTrees(parentId);
		logger.info("查询结果：",list.size());
		if(list !=null && list.size()>0){
			
			for(int i = 0; i<list.size(); i++){  
				
				ProjectModel deviceInfo = list.get(i);  
				
				JSONObject json = new JSONObject();  
				json.put("id", deviceInfo.getProjectId());  
				json.put("pId", deviceInfo.getProjectId());  
				json.put("name", deviceInfo.getProjectCode() + ":" + deviceInfo.getProjectName());  
				json.put("rfid", deviceInfo.getProjectCode());  
				json.put("icon", projectPictureUrl);  

				jsonArray.add(json);  
			}
		}
		map.put("data", jsonArray);  
//		logger.info("======>退出queryParenCodeY方法");  
		
		return map;  
	} 


	/** 
	* @Title: getFullProjectInfo 
	* @Description: 获取方案以及方案下的所有相关信息
	* @param accountModel
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/getFullProjectInfo")
	public ResultMessage getFullProjectInfo(@RequestBody ProjectModel model) throws Exception {
		logger.debug("执行方法getFullProjectInfo() 获取方案以及方案下的所有相关信息");
		ProjectDto dto = null;
		//封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(9999,"数据参数有误");
		
		if(model != null && model.getProjectId() != null){
			
			dto = authconfigService.getFullProjectInfo(model);
			logger.debug("getFullProjectInfo(),根据方案编号获取方案所有相关信息正常");
			
			if(dto != null)
				result = BaseController.getResults(dto);
		}

		return result;
	}
	
	/** 
	* @Title: getFullDatasourceInfo 
	* @Description: 获取渠道以及渠道下的所有相关信息
	* @param model
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/getFullDatasourceInfo")
	public ResultMessage getFullDatasourceInfo(@RequestBody DataSourceModel model) throws Exception {
		logger.debug("执行方法getFullDatasourceInfo() 获取渠道以及渠道下的所有相关信息");
		DataSourceDto dto = null;
		//封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(9999,"数据参数有误");
		
		if(model != null && model.getDataSourceId() != null){
			
			dto = authconfigService.getFullDatasourceInfo(model);
			logger.debug("getFullDatasourceInfo(),根据渠道编号获取渠道所有相关信息正常");
			
			if(dto != null)
				result = BaseController.getResults(dto);
		}
		
		return result;
	}
	
	/** 
	* @Title: getAllTypesInfo 
	* @Description: 索取所有相关类型
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/getAllTypesInfo")
	public ResultMessage getAllTypesInfo() throws Exception{
		logger.debug("执行方法getAllTypesInfo() 获取所有相关类型");
		Map<String,Object> maps = new HashMap<String,Object>();
		
		maps.put("authTypes",configService.getAuthTypes1());			//获取鉴权类型
		maps.put("encryptTypes",configService.getEncryptTypes());		//获取加密类型
		maps.put("serverEnvTypes",configService.getServerEnvTypes());	//获取环境类型列表
		maps.put("interfaceTypes",configService.getInterfaceTypes());	//获取接口类型
		maps.put("interfaceTypesSustom",authconfigService.interfaceTypesSustom());	//获取原始,自定义,加密的接口类型
		maps.put("callbackTypes",configService.getCallbackTypes());		//获取回调类型
		maps.put("serverTypes",configService.getValuesByParameterType("SERVER_TYPE"));	//获取服务类型
		maps.put("serverMappingSmall",configService.getValuesByParameterType("SERVER_MAPPING_SMALL_TYPE"));//接口小类
		maps.put("serverStatus",configService.getValuesByParameterType("SERVER_STATUS"));//获取服务状态
		
		ResultMessage result = BaseController.getResults(maps);
		return result;
	}
	
	/** 
	* @Title: getServerSmallTypes 
	* @Description: 根据服务类型获取服务小类
	* @param serverType
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("/getServerSmallTypes")
	public ResultMessage getServerSmallTypes(@RequestParam("serverType")String serverType) throws Exception{
		logger.debug("getServerSmallTypes() 根据服务类型获取服务小类");
		List<String[]> lists = configService.getServerSmallTypes(serverType);
		ResultMessage result = BaseController.getResults(lists);
		return result;
	}
	
	@PostMapping(value = "/getServerList")
	public ResultMessage getServerList(
			@RequestParam("serverEnv")String serverEnv,
			@RequestParam("serverType")String serverType,
			@RequestParam("serverSmallType")String serverSmallType) throws Exception {
		logger.debug("getServerList() 获取服务列表，参数serverEnv:{},serverType:{},serverSmallType:{}",
				serverEnv,serverType,serverSmallType);
		List<ServerDto> serverList = configService.getServerList(serverEnv,serverType,serverSmallType);
		logger.info("查询结果:{}",serverList.size());
		ResultMessage result = BaseController.getResults(serverList);
		return result;
	}
	
	/** 
	* @Title: saveDatasourProject 
	* @Description: 新增保存渠道或方案以及鉴权信息
	* @param basicInfo
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/saveDatasourProject")
	public ResultMessage saveDatasourProject(@RequestBody AuthBasicInfo basicInfo) throws Exception {
		logger.debug("saveDatasourProject() 新增保存渠道或方案以及鉴权信息");
		boolean checkRes = false;
		String message = "";
		ResultMessage results = null;
		
		//新增渠道或方案,验证code是否唯一
		if(basicInfo.getAddRole().equals("datasource")){
			message = "渠道";
			DataSourceModel model = dataSourceService.getOneByDataSourceCode(basicInfo.getDataSource());
			if(model != null){
				message += "代码已存在,请重新输入";
				checkRes = true;
			}
				
		}else if(basicInfo.getAddRole().equals("project")){
			message = "方案";
			ProjectModel model = projectService.getOneByProjectCode(basicInfo.getProjectCode());
			if(model != null){
				message += "代码已存在,请重新输入";
				checkRes = true;
			}
		} 
		
		if(checkRes){
			results = BaseController.getResults(0,message);
		}else{
			
			Integer result = authconfigService.saveDatasourProject(basicInfo);
			logger.debug("新增完成，结果:{}",result);
			
			message += result > 0 ? "新增成功" : "新增失败";
			
			results = BaseController.getResults(result,message);
		}
		return results;
	}
	
}
