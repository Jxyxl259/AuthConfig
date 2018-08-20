package com.yaic.auth.external.dto.requestdto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RequestGetAccountDto", description = "获取账号信息请求接收对象")
public class RequestGetAccountDto extends RequestBaseDto implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -5628842565159793711L;
	@ApiModelProperty(value = "账号信息对象",required=true)
	private GetAccountDto data;

	public GetAccountDto getData() {
		return data;
	}

	public void setData(GetAccountDto data) {
		this.data = data;
	}
	@ApiModel(value = "GetAccountDto", description = "账号信息对象")
	public class GetAccountDto{
		@ApiModelProperty(value ="appId", required=true)
		String appId;

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}
		
		
	}
	
}
