package com.yaic.auth.external.dto.requestdto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="RequestGetProjectDto",description="获取方案信息请求接收对象")
public class RequestGetProjectDto extends RequestBaseDto implements Serializable {
	
	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -5628842565159793711L;
	@ApiModelProperty(value="方案信息对象")
	private GetProjectDto data;

	public GetProjectDto getData() {
		return data;
	}

	public void setData(GetProjectDto data) {
		this.data = data;
	}
	@ApiModel(value="GetProjectDto",description="方案信息对象")
	public class GetProjectDto{
		@ApiModelProperty(value="渠道编号   二选一")
		Integer projectId;
		@ApiModelProperty(value="渠道code 二选一")
		String projectCode;
		public Integer getProjectId() {
			return projectId;
		}
		public void setProjectId(Integer projectId) {
			this.projectId = projectId;
		}
		public String getProjectCode() {
			return projectCode;
		}
		public void setProjectCode(String projectCode) {
			this.projectCode = projectCode;
		}


	}
	
}
