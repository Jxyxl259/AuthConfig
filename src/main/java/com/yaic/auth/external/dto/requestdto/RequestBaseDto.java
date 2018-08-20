package com.yaic.auth.external.dto.requestdto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: RequestBaseDto 
* @Description: 外部请求JSON最外层参数对象
* @author zhaoZd
* @date 2018年7月25日 上午10:24:37 
*  
*/
@ApiModel(value = "RequestBaseDto", description = "外部请求JSON最外层参数对象")
public class RequestBaseDto implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 2510247854456843331L;
	@ApiModelProperty(value = "请求时间", required = true)
	private String requestTime;
	@ApiModelProperty(value = "唯一标识", required = true)
	private String uniqueId;
	
	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
}
