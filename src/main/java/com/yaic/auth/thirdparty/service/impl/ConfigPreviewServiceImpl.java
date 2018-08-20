package com.yaic.auth.thirdparty.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.thirdparty.dao.AuthPubCodeDao;
import com.yaic.auth.thirdparty.dto.AuthMappingDto;
import com.yaic.auth.thirdparty.dto.DataSourceDto;
import com.yaic.auth.thirdparty.dto.ProjectDto;
import com.yaic.auth.thirdparty.model.AuthPubCodeModel;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.service.AuthConfigService;
import com.yaic.auth.thirdparty.service.ConfigPreviewService;


@Service
public class ConfigPreviewServiceImpl implements ConfigPreviewService {

//	private static final Logger logger = LoggerFactory.getLogger(DataSourceServiceimpl.class);

	@Autowired
	private AuthConfigService authConfigService;
	
	@Autowired
	private AuthPubCodeDao authPubCodeDao;
	
	@Override
	public ProjectDto getProjectInfos(ProjectModel model) throws Exception {
		
		ProjectDto dto = authConfigService.getFullProjectInfo(model);
		
		if(dto != null){
			
			dto = changeListInfos(dto);
		}
		
		return dto;
	}

	private ProjectDto changeListInfos(ProjectDto dto) {

		String authType = "";
		if(dto != null && dto.getAuthEncryptDto() != null){
			authType = dto.getAuthEncryptDto().getAuthType();
		}
		
		if(dto.getAuthmappList() != null){
			
			//得到环境对应IP的数据字典信息
			Map<String,String> envMap = getPubCodeMap("IP_ENV");	//环境对外IP
			Map<String,String> serverMap = getPubCodeMap("SERVER_MAPPING_SMALL_TYPE");	//服务类型
			
			String showRequestUrl = "",showServerType = "",requestParams="";
			for (int i = 0; i < dto.getAuthmappList().size(); i++) {
				AuthMappingDto mappDto = dto.getAuthmappList().get(i);
				
				requestParams = getRequestParams(authType,mappDto.getRequestType());
				
				//修改外部请求地址
				showRequestUrl = envMap.get(mappDto.getServerEnv())
						+ mappDto.getRequestUrl() + requestParams;
				mappDto.setServerUrl(showRequestUrl);
				
				//展示服务类型
				showServerType = serverMap.get(mappDto.getServerSmallType());
				mappDto.setServerType(showServerType);
				
				dto.getAuthmappList().set(i,mappDto);
			}
		}
		
		return dto;
	}

	/** 
	* @Title: getRequestParams 
	* @Description: 配置预览中 服务地址展示逻辑处理
	* @param authType		鉴权类型
	* @param requestType	接口类型
	* @return    
	* @return String  
	* @throws 
	*/
	private String getRequestParams(String authType, String requestType) {

		String reqParams = "";
		Map<String,String> reqParamMap = getPubCodeMap("REQ_PARAMS");	//请求参数
		
		if(PublicParamters.AUTH_TYPE_NO_AUTH.equals(authType)){
			
			reqParams = "?" + reqParamMap.get("APP_ID") + "[&" + reqParamMap.get("DATASOURCE_CODE") + "&" 
					+ reqParamMap.get("PROJECT_CODE") + "]";
			
		}else if(PublicParamters.AUTH_TYPE_COMMON_AUTH.equals(authType)){
			
			reqParams = "?" + reqParamMap.get("ACCESS_TOKEN") + "&" + reqParamMap.get("OPEN_ID") + "[&" 
					+ reqParamMap.get("DATASOURCE_CODE") + "&" 	+ reqParamMap.get("PROJECT_CODE")+ "]";
			
		}else if(PublicParamters.AUTH_TYPE_IP_1.equals(authType) ||
				PublicParamters.AUTH_TYPE_MD5_1.equals(authType) ||
				PublicParamters.AUTH_TYPE_RSA_1.equals(authType)){
			
			reqParams = "?" + reqParamMap.get("APP_ID") + "&" + reqParamMap.get("APP_SECRET") + "&" 
					+ reqParamMap.get("SIGN") + "[&" + reqParamMap.get("DATASOURCE_CODE") + "&" 
					+ reqParamMap.get("PROJECT_CODE")+ "]";
		}
		
		if(PublicParamters.REQUEST_TYPE_DATA_SOURCE.equals(requestType) || 
				PublicParamters.REQUEST_TYPE_PROJECT.equals(requestType)){
			
			reqParams = reqParams.replace("[","").replaceAll("]","");
		}
			
		
		return reqParams;
	}

	@Override
	public DataSourceDto getDatasourceInfos(DataSourceModel model) throws Exception {
		DataSourceDto dto = authConfigService.getFullDatasourceInfo(model);
		
		if(dto != null && dto.getProjectList() != null && dto.getProjectList().size() > 0){
			
			
			ProjectDto projectDto = changeListInfos(dto.getProjectList().get(0));
			
			dto.getProjectList().set(0,projectDto);
			
		}
		
		return dto;
	}


	private Map<String, String> getPubCodeMap(String parameterType) {
		
		AuthPubCodeModel authPubCodeModel = new AuthPubCodeModel();
		authPubCodeModel.setParameterType(parameterType);
		authPubCodeModel.setValidFlag(PublicParamters.VALID_FLAG_Y);
		
		//获取环境IP数据字典
		List<AuthPubCodeModel> lists = authPubCodeDao.selectAllByConditions(authPubCodeModel);
		
		Map<String, String> maps = new HashMap<String,String>();
		for (AuthPubCodeModel model : lists) {
			maps.put(model.getParameterValue(),model.getParameterDescribe());
		}
		
		return maps;
	}


}
