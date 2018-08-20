package com.yaic.auth.thirdparty.dto;

import java.io.Serializable;
import java.util.Date;

import com.yaic.auth.common.BaseDto;


/** 
* @ClassName: AccountAuthMappDto 
* @Description: 账号服务关系表
* @author zhaoZd
* @date 2018年7月26日 上午10:23:35 
*  
*/
public class AccountAuthMappDto extends BaseDto implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 146541698607044778L;
	
	
	private Integer id;
	
	private String appId;
	
	private Integer accountId;
	
	private String appCode;
	
	private String accountName;
	
	private Integer mappingId;
	
	private Integer authMappAuthId;
	
	private Integer projectId;
	
	private String requestType;
	
	private String requestUrl;
	
	private Integer serverId;
	
	private String serverEnv;
	
	private String serverStatus;
	
	private String serverStatusStr;
	
	private String serverType;
	
	private String serverTypeStr;
	
	private String serverUrl;
	
	private Date createdDate;

	private String createdUser;

	private Date updatedDate;

	private String updatedUser;
	
	

	public AccountAuthMappDto() {}

	public AccountAuthMappDto(Integer accountId, Integer mappingId, Integer projectId, Integer serverId) {
		this.accountId = accountId;
		this.mappingId = mappingId;
		this.projectId = projectId;
		this.serverId = serverId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public String getServerEnv() {
		return serverEnv;
	}

	public void setServerEnv(String serverEnv) {
		this.serverEnv = serverEnv;
	}

	public String getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(String serverStatus) {
		this.serverStatus = serverStatus;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public Integer getMappingId() {
		return mappingId;
	}

	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getServerTypeStr() {
		return serverTypeStr;
	}

	public void setServerTypeStr(String serverTypeStr) {
		this.serverTypeStr = serverTypeStr;
	}

	public String getServerStatusStr() {
		return serverStatusStr;
	}

	public void setServerStatusStr(String serverStatusStr) {
		this.serverStatusStr = serverStatusStr;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Integer getAuthMappAuthId() {
		return authMappAuthId;
	}

	public void setAuthMappAuthId(Integer authMappAuthId) {
		this.authMappAuthId = authMappAuthId;
	}
	
	
	
}
