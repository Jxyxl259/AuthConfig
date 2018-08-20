package com.yaic.auth.external.dto.requestdto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="RequestGetDatasourceDto",description="获取渠道信息请求接收对象")
public class RequestGetDatasourceDto extends RequestBaseDto implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -5628842565159793711L;
	@ApiModelProperty(value="获取渠道信息对象", required=true)
	private GetDatasourceDto data;

	public GetDatasourceDto getData() {
		return data;
	}

	public void setData(GetDatasourceDto data) {
		this.data = data;
	}
	@ApiModel(value="GetDatasourceDto",description="获取渠道信息对象")
	public class GetDatasourceDto{
		@ApiModelProperty(value="渠道code", required=true)
		String dataSource;

		public String getDataSource() {
			return dataSource;
		}

		public void setDataSource(String dataSource) {
			this.dataSource = dataSource;
		}

	}
	
}
