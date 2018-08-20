package com.yaic.auth.external.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yaic.auth.external.common.BaseController;
import com.yaic.auth.external.common.ResponseParameters;
import com.yaic.auth.external.common.ResultMessage;
import com.yaic.auth.external.dto.requestdto.RequestAddEditProjectDto;
import com.yaic.auth.external.dto.requestdto.RequestBaseDto;
import com.yaic.auth.external.dto.requestdto.RequestDeleteInfoDto;
import com.yaic.auth.external.dto.requestdto.RequestDeleteInfoDto.DeleteInfoDto;
import com.yaic.auth.external.dto.requestdto.RequestGetAccountDto;
import com.yaic.auth.external.dto.requestdto.RequestGetAccountListDto;
import com.yaic.auth.external.dto.requestdto.RequestGetAccountListDto.GetAccountListDto;
import com.yaic.auth.external.dto.requestdto.RequestGetDatasourceDto;
import com.yaic.auth.external.dto.requestdto.RequestGetDatasourceDto.GetDatasourceDto;
import com.yaic.auth.external.dto.requestdto.RequestGetProjectDto;
import com.yaic.auth.external.dto.requestdto.RequestGetProjectDto.GetProjectDto;
import com.yaic.auth.external.dto.requestdto.RequestgetServerListDto;
import com.yaic.auth.external.dto.requestdto.RequestgetServerListDto.GetServerListDto;
import com.yaic.auth.external.dto.responsedto.AccountExternalDto;
import com.yaic.auth.external.service.ExternalConfigService;
import com.yaic.auth.thirdparty.controller.AccountController;
import com.yaic.auth.thirdparty.dto.AccountDto;
import com.yaic.auth.thirdparty.dto.DataSourceDto;
import com.yaic.auth.thirdparty.dto.ProjectDto;
import com.yaic.auth.thirdparty.dto.ServerDto;
import com.yaic.auth.thirdparty.model.AccountModel;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.service.DataSourceService;
import com.yaic.auth.thirdparty.service.ProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
* @ClassName: ExternalConfigController 
* @Description: 外部访问权限配置接口
* @author zhaoZD
* @date 2018年6月17日 下午9:37:43 
*
 */
@Api(tags="外部访问权限配置接口") //修饰整个类，描述Controller的作用
@RestController
@RequestMapping("/authconfig")
public class ExternalConfigController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	ExternalConfigService configService;
	
	@Autowired
	DataSourceService dataSourceService;
	
	@Autowired
	ProjectService projectService;
	
	
	/** 
	* @Title: getNewAppid 
	* @Description: 生成新的APPID
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="生成appId",notes="")
	@PostMapping("/getNewAppid")
	public ResultMessage getNewAppid(@RequestBody @ApiParam(name="RequestBaseDto",value="生成appId请求接收对象",required=true) RequestBaseDto reqDto) throws Exception{
		
		String appid = configService.generateAppid();
		
		logger.info("getNewAppid(),reqDto:{}",JSON.toJSONString(reqDto));
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0000,ResponseParameters.MSG_0000,appid);
		return result;
	}
	
	/** 
	* @Title: getAuthTypes 
	* @Description: 获取鉴权类型
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="获取鉴权类型",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/getAuthTypes")
	public ResultMessage getAuthTypes(@RequestBody @ApiParam(name="RequestBaseDto",value="获取鉴权类型请求接收对象",required=true) RequestBaseDto reqDto) throws Exception{
		
		logger.info("getAuthTypes(),reqDto:{}",JSON.toJSONString(reqDto));

		List<String> lists = configService.getAuthTypes();
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0000,ResponseParameters.MSG_0000,lists);
		return result;
	}
	
	/** 
	* @Title: getEncryptTypes 
	* @Description: 获取加密类型
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="获取加密类型",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/getEncryptTypes")
	public ResultMessage getEncryptTypes(@RequestBody @ApiParam(name="RequestBaseDto",value="获取加密类型请求接收对象",required=true) RequestBaseDto reqDto) throws Exception{
		
		logger.info("getEncryptTypes(),reqDto:{}",JSON.toJSONString(reqDto));
		
		List<String> lists = configService.getEncryptTypes();
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0000,ResponseParameters.MSG_0000,lists);
		return result;
	}
	
	/** 
	* @Title: getServerEnvTypes 
	* @Description: 获取环境类型
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="获取环境类型",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/getServerEnvTypes")
	public ResultMessage getServerEnvTypes(@RequestBody @ApiParam(name="RequestBaseDto",value="获取环境类型请求接收对象",required=true) RequestBaseDto reqDto) throws Exception{
		
		logger.info("getServerEnvTypes(),reqDto:{}",JSON.toJSONString(reqDto));
		
		List<String> lists = configService.getServerEnvTypes();
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0000,ResponseParameters.MSG_0000,lists);
		return result;
	}
	
	/** 
	* @Title: getServerTypes 
	* @Description: 获取服务类型
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="获取服务类型",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/getServerTypes")
	public ResultMessage getServerTypes(@RequestBody @ApiParam(name="RequestBaseDto",value="获取服务类型请求接收对象",required=true) RequestBaseDto reqDto) throws Exception{
		
		logger.info("getServerTypes(),reqDto:{}",JSON.toJSONString(reqDto));
		
		List<String[]> lists = configService.getValuesByParameterType("SERVER_TYPE");
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0000,ResponseParameters.MSG_0000,lists);
		return result;
	}
	
	/** 
	* @Title: getAllServerSmallTypes 
	* @Description: 根据服务类型获取服务小类
	* @param serverType
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="获取服务小类",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/getAllServerSmallTypes")
	public ResultMessage getAllServerSmallTypes(@RequestBody @ApiParam(name="RequestBaseDto",value="获取服务小类请求接收对象",required=true) RequestBaseDto reqDto) throws Exception{
		
		logger.info("getAllServerSmallTypes(),reqDto:{}",JSON.toJSONString(reqDto));
		
		List<String[]> lists = configService.getValuesByParameterType("SERVER_MAPPING_SMALL_TYPE");
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0000,ResponseParameters.MSG_0000,lists);
		return result;
	}
	
	/** 
	* @Title: getCallbackTypes 
	* @Description: 获取回调类型
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="获取回调类型",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/getCallbackTypes")
	public ResultMessage getCallbackTypes(@RequestBody @ApiParam(name="RequestBaseDto",value="获取回调类型请求接收对象",required=true) RequestBaseDto reqDto) throws Exception{
		
		logger.info("getCallbackTypes(),reqDto:{}",JSON.toJSONString(reqDto));
		
		List<String> lists = configService.getCallbackTypes();
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0000,ResponseParameters.MSG_0000,lists);
		return result;
	}

	/** 
	* @Title: getInterfaceTypes 
	* @Description: 获取接口类型
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="获取接口类型",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/getInterfaceTypes")
	public ResultMessage getInterfaceTypes(@RequestBody @ApiParam(name="RequestBaseDto",value="获取接口类型请求接收对象",required=true)RequestBaseDto reqDto) throws Exception{
		
		logger.info("getInterfaceTypes(),reqDto:{}",JSON.toJSONString(reqDto));
		
		List<String> lists = configService.getInterfaceTypes();
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0000,ResponseParameters.MSG_0000,lists);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 
	* @Title: getAccount 
	* @Description: 根据appId获取用户信息
	* @param content
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="根据appId获取用户信息",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/getAccount")
	public ResultMessage getAccount(@RequestBody @ApiParam(name="RequestGetAccountDto",value="获取账号信息请求接收对象",required=true) RequestGetAccountDto reqDto) throws Exception{
		
		logger.info("getAccount(),reqDto:{}",JSON.toJSONString(reqDto));
		
		String appid = reqDto.getData().getAppId();
		
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0011, ResponseParameters.MSG_0011,null);
		if(StringUtils.isNotBlank(appid)){
			
			AccountDto dto = configService.getAccount(appid);
			
			if(dto != null){
				
				result = new ResultMessage(ResponseParameters.CODE_0000, ResponseParameters.MSG_0000,dto);
			}
		}
		
		return result;
	}
	

	/** 
	* @Title: getAccountList 
	* @Description: 获取所有账户信息
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="获取所有账户信息",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/getAccountList")
	public ResultMessage getAccountList(@RequestBody @ApiParam(name="RequestGetAccountListDto",value="获取所有账户信息请求接收对象",required=true) RequestGetAccountListDto reqDto) throws Exception{
		
		logger.info("getAccountList(),reqDto:{}",JSON.toJSONString(reqDto));
		
		GetAccountListDto d = reqDto.getData();
		
		AccountModel model = new AccountModel();
		model.setAccountName(d.getAccountName());
		model.setAppId(d.getAppId());
		model.setAppCode(d.getAppCode());
		
		AccountExternalDto dto = configService.getAccountList(model,d.getPageNum(),d.getPageSize());
		
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0000,ResponseParameters.MSG_0000,dto);
		return result;
	}

	
	/** 
	* @Title: getServerList 
	* @Description: 根据环境类型与服务类型,查询相关服务列表
	* @param serverModel
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="根据环境类型与服务类型,查询相关服务列表",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping(value = "/getServerList")
	public ResultMessage getServerList(@RequestBody @ApiParam(name="RequestgetServerListDto",value="获取服务列表请求接收对象",required=true) RequestgetServerListDto reqDto) throws Exception {
		
		logger.info("getServerList(),reqDto:{}",JSON.toJSONString(reqDto));
		
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0011, ResponseParameters.MSG_0011,null);
		
		GetServerListDto d = reqDto.getData();
		
		if(StringUtils.isNotBlank(d.getServerEnv()) && StringUtils.isNotBlank(d.getServerSmallType())){
			
			List<ServerDto> serverList = configService.getServerList(d.getServerEnv(),d.getServerType(),d.getServerSmallType());
			
			result = new ResultMessage(ResponseParameters.CODE_0000, ResponseParameters.MSG_0000,serverList);
		}
			
		return result;
	}
	
	
	/** 
	* @Title: getDatasource 
	* @Description: 根据datasourceId获取渠道信息
	* @param content
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="根据渠道code或者id获取渠道信息",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/getDatasource")
	public ResultMessage getDatasource(@RequestBody @ApiParam(name="RequestGetDatasourceDto",value="获取渠道信息请求接收对象",required=true) RequestGetDatasourceDto reqDto) throws Exception{
		
		logger.info("getDatasource(),reqDto:{}",JSON.toJSONString(reqDto));
		
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0021, ResponseParameters.MSG_0021,null);
		
		GetDatasourceDto d = reqDto.getData();
		
		if(StringUtils.isNotBlank(d.getDataSource())){
			
			DataSourceModel model = dataSourceService.getOneByDataSourceCode(d.getDataSource());
			
			if(model != null){
				
				DataSourceDto dto = configService.getDatasource(model);
				result = new ResultMessage(ResponseParameters.CODE_0000, ResponseParameters.MSG_0000,dto);
				logger.info("结果:{}:{},getDatasource(),dataSourceCode:{}", result.getCode(),result.getMessage(), dto.getDataSource());
			}
		}
		
		return result;
	}
	
	/** 
	* @Title: getProject 
	* @Description: 根据方案code或者id获取方案所有信息以及对应渠道信息与默认鉴权配置
	* @param content
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="根据方案code或者id获取方案所有信息以及对应渠道信息与默认鉴权配置",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/getProject")
	public ResultMessage getProject(@RequestBody @ApiParam(name="RequestGetProjectDto",value="获取方案信息请求接收对象",required=true) RequestGetProjectDto reqDto) throws Exception{

		logger.info("getProject(),reqDto:{}",JSON.toJSONString(reqDto));
		
		ResultMessage result = new ResultMessage(ResponseParameters.CODE_0011, ResponseParameters.MSG_0011,null);
		
		GetProjectDto d = reqDto.getData();
		logger.info("获取getProjectInfo接口中参数,projectCode:{},projectId:{}",d.getProjectCode(),d.getProjectId());
		
		if(d.getProjectId() != null || StringUtils.isNotBlank(d.getProjectCode())){
			
			ProjectModel model  = null;
			
			if(d.getProjectId() != null){
				
				model = projectService.getOneByProjectId(d.getProjectId());
			}else if(StringUtils.isNotBlank(d.getProjectCode())){
				
				model = projectService.getOneByProjectCode(d.getProjectCode());
			}
			
			if(model != null){
				
				ProjectDto dto = configService.getProject(model);
				logger.info("getProject(),返回dto:{}",JSON.toJSONString(dto));
				result = new ResultMessage(ResponseParameters.CODE_0000, ResponseParameters.MSG_0000,dto);
			}
					
		}
		logger.info("结果:{}:{},getProject(),projectCode:{}", result.getCode(),result.getMessage(), d.getProjectCode());
		return result;
	}

	
	/** 
	* @Title: addProject 
	* @Description: 新增方案与其下的所有信息,同时包括对应渠道信息与渠道鉴权信息,账户信息
	* @param content
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="新增方案",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/addProject")
	public ResultMessage addProject(@RequestBody @ApiParam(name="RequestAddEditProjectDto",value="方案及其对应信息，鉴权，回调，服务等",required=true) RequestAddEditProjectDto reqDto) throws Exception{
		
		logger.info("addProject(),reqDto:{}",JSON.toJSONString(reqDto));
		
		ProjectDto dto = reqDto.getData();
		
		//需要对内容进行校验
		ResultMessage result = configService.addProject(dto);
		logger.info("结果:{}:{},addProject(),projectCode:{}", result.getCode(),result.getMessage(), dto.getProjectCode());
		logger.info("addProject()新增方案接口end--");
		
		return result;
	}
	
	
	/** 
	* @Title: editProject 
	* @Description: 根据外部JSON串修改方案相关所有信息
	* @param content
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="修改方案",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/editProject")
	public ResultMessage editProject(@RequestBody @ApiParam(name="RequestAddEditProjectDto",value="方案及其对应信息，鉴权，回调，服务等",required=true) RequestAddEditProjectDto reqDto) throws Exception{
		
		logger.info("editProject(),reqDto:{}",JSON.toJSONString(reqDto));
		
		ProjectDto dto = reqDto.getData();
		
		ResultMessage result = configService.editProject(dto);
		logger.info("结果:{}:{},editProject(),projectCode:{}", result.getCode(),result.getMessage(), dto.getProjectCode());
		logger.info("editProject()修改方案接口end--");
		
		return result;
	}
	
	
	/** 
	* @Title: deleteInfo 
	* @Description: 删除信息
	* @param content
	* @return ResultMessage  
	* @throws 
	*/
	@ApiOperation(value="删除方案或渠道",notes="") //描述一个类的一个方法，或者说一个接口
	@PostMapping("/deleteInfo")
	public ResultMessage deleteInfo(@RequestBody @ApiParam(name="RequestDeleteInfoDto",value="方案类型和方案id或渠道类型和渠道id",required=true) RequestDeleteInfoDto reqDto) throws Exception{
		
		logger.info("deleteInfo(),reqDto:{}",JSON.toJSONString(reqDto));
		
		logger.info("deleteInfo()删除方案或渠道接口begin--");
		DeleteInfoDto d = reqDto.getData();
		
		ResultMessage message = configService.deleteInfo(d.getDeleteType(),d.getDeleteId());
		logger.info("deleteInfo()删除方案或渠道接口end--");
		return message;
	}
	
	
}
