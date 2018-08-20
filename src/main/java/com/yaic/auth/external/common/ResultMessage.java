package com.yaic.auth.external.common;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



/** 
* @ClassName: ResultMessage 
* @Description: 统一对外接口的返回信息
* @author zhaoZd
* @date 2018年6月20日 下午12:48:25 
*  
*/
@ApiModel(description = "返回响应数据")
public class ResultMessage implements Serializable {
	private static final long serialVersionUID = -6095631145766356539L;
	
	/** 执行状态 */
    @ApiModelProperty(value = "执行状态 ")
	private String state;
	
	/** 状态 0000:成功 	else：失败 */
    @ApiModelProperty(value = "状态 0000:成功 	else：失败 ")
	private String code;

	/** 对应状态的消息 */
    @ApiModelProperty(value = "对应状态的消息")
	private String message;
	
	/** 具体业务数据 */
    @ApiModelProperty(value = "业务数据 ")
	private Object data;
	
	
	public ResultMessage() {}
	
	

	public ResultMessage(String code, String message, Object data) {
		this.state = "normal";
		this.code = code;
		this.message = message;
		this.data = data;
	}



	public ResultMessage(String state, String code, String message, Object data) {
		this.state = state;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
