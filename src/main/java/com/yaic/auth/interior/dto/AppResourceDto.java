/************************************************************************
 * 描述 ：数据库表app_RESOURCE对应的表单对象，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-10-22 09:44:48
 *
 ************************************************************************/

package com.yaic.auth.interior.dto;

import java.io.Serializable;
import java.util.List;

import com.yaic.auth.interior.model.AppResourceModel;


public class AppResourceDto implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	
	
    private String resourceId;
	
    /** 资源名称 */
    private String resourceName;

//    /** 资源类型 */
//    private String resourceType;
//
//    /** 资源层级 */
//    private Integer resourceLevel;


    /** 资源图标CLASS */
    private String resourceIconClass;


//    /** 节点标志 */
//    private Integer endFlag;
//    
//    /**显示顺序*/
//    private Integer displayOrder;
	
	private List<AppResourceSecCellDto> child;
	
	
	
	public AppResourceDto() { }

	public AppResourceDto(AppResourceModel model) {
		this.resourceId = model.getResourceId();
		this.resourceName = model.getResourceName();
		this.resourceIconClass = model.getResourceIconClass();
	}
	
	public List<AppResourceSecCellDto> getChild() {
		return child;
	}

	public void setChild(List<AppResourceSecCellDto> child) {
		this.child = child;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

//	public String getResourceType() {
//		return resourceType;
//	}
//
//	public void setResourceType(String resourceType) {
//		this.resourceType = resourceType;
//	}
//
//	public Integer getResourceLevel() {
//		return resourceLevel;
//	}
//
//	public void setResourceLevel(Integer resourceLevel) {
//		this.resourceLevel = resourceLevel;
//	}

//	public String getParentResourceId() {
//		return parentResourceId;
//	}
//
//	public void setParentResourceId(String parentResourceId) {
//		this.parentResourceId = parentResourceId;
//	}

	public String getResourceIconClass() {
		return resourceIconClass;
	}

	public void setResourceIconClass(String resourceIconClass) {
		this.resourceIconClass = resourceIconClass;
	}

//	public String getActionUrl() {
//		return actionUrl;
//	}
//
//	public void setActionUrl(String actionUrl) {
//		this.actionUrl = actionUrl;
//	}

//	public Integer getEndFlag() {
//		return endFlag;
//	}
//
//	public void setEndFlag(Integer endFlag) {
//		this.endFlag = endFlag;
//	}
//
//	public Integer getDisplayOrder() {
//		return displayOrder;
//	}
//
//	public void setDisplayOrder(Integer displayOrder) {
//		this.displayOrder = displayOrder;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
    
}
