package com.yaic.auth.external.dto.requestdto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="RequestGetAccountListDto",description="获取所有账户信息请求接收对象")
public class RequestGetAccountListDto extends RequestBaseDto implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -5628842565159793711L;
	@ApiModelProperty(value="账户信息列表对象")
	private GetAccountListDto data;
	
	public GetAccountListDto getData() {
		return data;
	}

	public void setData(GetAccountListDto data) {
		this.data = data;
	}
	
	@ApiModel(value="GetAccountListDto",description="账户信息列表对象")
	public class GetAccountListDto{
		@ApiModelProperty(value="第几页(从1开始)")
		private Integer pageNum = 1;
		@ApiModelProperty(value="每页加载条数")
		private Integer	pageSize = 20;
		@ApiModelProperty(value="账户名称")
		private String accountName;
		@ApiModelProperty(value="APPID")
		private String appId;
		@ApiModelProperty(value="账户CODE")
		private String appCode;
		
		public Integer getPageNum() {
			return pageNum;
		}

		public void setPageNum(Integer pageNum) {
			if(pageNum == null || pageNum < 1)
				pageNum = 1;
			this.pageNum = pageNum;
		}

		public Integer getPageSize() {
			return pageSize;
		}

		public void setPageSize(Integer pageSize) {
			if(pageSize == null || pageSize < 1)
				pageSize = 20;
			this.pageSize = pageSize;
		}

		public String getAccountName() {
			return accountName;
		}

		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getAppCode() {
			return appCode;
		}

		public void setAppCode(String appCode) {
			this.appCode = appCode;
		}
	}

}
