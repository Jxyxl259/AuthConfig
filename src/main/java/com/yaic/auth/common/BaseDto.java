package com.yaic.auth.common;

import org.springframework.util.StringUtils;

public class BaseDto {

	private Integer pageSize;
	
	private Integer pageNum;
	
	private String reqType;
	
	private Integer draw;
	
	private Integer length;
	
	private Integer start;
	
	public static BaseDto getPageInfo(BaseDto model){
		
		//非列表查询
		if(StringUtils.isEmpty(model.getStart()) || StringUtils.isEmpty(model.getLength())){
			model.setStart(0);
			model.setLength(10);
			model.setPageSize(10);
			model.setDraw(0);
		}else{
			//改变列表中分页条数
			if(!model.getLength().equals(model.getPageSize())){
				model.setPageSize(model.getLength());
			}
		}
		
		model.setPageNum(model.getStart()/model.getLength()+1);
		
		return model;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
	
	
}
