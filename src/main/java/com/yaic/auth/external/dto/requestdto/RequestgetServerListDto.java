package com.yaic.auth.external.dto.requestdto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="RequestgetServerListDto",description="获取服务列表请求接收对象")
public class RequestgetServerListDto extends RequestBaseDto implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -5628842565159793711L;
	@ApiModelProperty(value="服务列表对象",required=true)
	private GetServerListDto data;

	public GetServerListDto getData() {
		return data;
	}

	public void setData(GetServerListDto data) {
		this.data = data;
	}
	@ApiModel(value="GetServerListDto",description="服务列表对象")
	public class GetServerListDto{
		@ApiModelProperty(value="环境类型",required=true)
		String serverEnv;
		@ApiModelProperty(value="服务类型",required=true)
		String serverType;
		@ApiModelProperty(value="服务小类类型",required=true)
		String serverSmallType;

		public String getServerType() {
			return serverType;
		}

		public void setServerType(String serverType) {
			this.serverType = serverType;
		}

		public String getServerSmallType() {
			return serverSmallType;
		}

		public void setServerSmallType(String serverSmallType) {
			this.serverSmallType = serverSmallType;
		}

		public String getServerEnv() {
			return serverEnv;
		}

		public void setServerEnv(String serverEnv) {
			this.serverEnv = serverEnv;
		}
		
		
	}
	
}
