package com.yaic.auth.external.dto.requestdto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value = "RequestDeleteInfoDto", description = "删除信息接收对象")
public class RequestDeleteInfoDto extends RequestBaseDto implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -5628842565159793711L;
	@ApiModelProperty(value = "删除信息对象", required = true)
	private DeleteInfoDto data;

	public DeleteInfoDto getData() {
		return data;
	}

	public void setData(DeleteInfoDto data) {
		this.data = data;
	}
	@ApiModel(value = "DeleteInfoDto", description = "删除信息对象")
	public class DeleteInfoDto{
		@ApiModelProperty(value = "主键编号", required = true)
		String deleteId;
		@ApiModelProperty(value = "删除类型", required = true)
		String deleteType;
		public String getDeleteId() {
			return deleteId;
		}
		public void setDeleteId(String deleteId) {
			this.deleteId = deleteId;
		}
		public String getDeleteType() {
			return deleteType;
		}
		public void setDeleteType(String deleteType) {
			this.deleteType = deleteType;
		}


	}
	
}
