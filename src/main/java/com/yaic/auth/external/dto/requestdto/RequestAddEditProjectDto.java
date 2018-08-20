package com.yaic.auth.external.dto.requestdto;

import java.io.Serializable;

import com.yaic.auth.thirdparty.dto.ProjectDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="RequestAddEditProjectDto",description = "新增修改方案接收对象")
public class RequestAddEditProjectDto extends RequestBaseDto implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -5628842565159793711L;
	@ApiModelProperty(value = "方案对象",required =true)
	private ProjectDto data;

	public ProjectDto getData() {
		return data;
	}

	public void setData(ProjectDto data) {
		this.data = data;
	}

}
