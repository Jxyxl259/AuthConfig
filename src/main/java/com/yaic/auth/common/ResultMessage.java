package com.yaic.auth.common;

import java.io.Serializable;


/**
 * 
* @ClassName: ResultMessage 
* @Description: 统一controller的返回信息
* @author zhaoZD
* @date 2018年6月17日 下午9:34:58 
*
 */
public class ResultMessage implements Serializable {
	private static final long serialVersionUID = -6095631145766356539L;
	
	/** 总条数 */
	private Long recordsTotal;
	
	/** 当前页数 */
	private Integer recordsFiltered;
	
	
	/** 每页页条数 */
	private Integer draw;
	
	
	/** 状态 0000:成功 	else：失败 */
	private String resultCode;
	

	/** 对应状态的消息 */
	private String resultMsg;
	
	
	/** 具体业务数据 */
	private Object dataList;
	
	private Integer pageSize;
	
	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getDataList() {
		return dataList;
	}

	public void setDataList(Object dataList) {
		this.dataList = dataList;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	
	
	
	
	
	

//	/** 此构造方法应用于data为null的场景 */
//	public ResponseMessage(String message) {
//		this.state = SUCCESS;// 1
//		this.message = message;
//	}



}
