package com.yaic.auth.interior.model;

import java.io.Serializable;
import java.util.Date;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class AppUserExcludeResourceModel implements Serializable {
	
	 private static final long serialVersionUID = AppUserExcludeResourceModel.class.getName().hashCode();

	    /** 用户资源排除关联ID */
	    private String userExcludeResourceId;
	 

	    /** 创建日期 */
	    private Date createdDate;

	    /** 创建人 */
	    private String createdBy;

	    /** 更新日期 */
	    private Date updatedDate;

	    /** 更新人 */
	    private String updatedBy;

	    /** 用户ID */
	    private String userId;

	    /** 资源ID */
	    private String resourceId;

	    /**
	     * 设置属性用户资源排除关联ID的值
	     */
	    public void setUserExcludeResourceId(String userExcludeResourceId) {
	        this.userExcludeResourceId = userExcludeResourceId;
	    }

	    /**
	     * 获取属性用户资源排除关联ID的值
	     */     
	    public String getUserExcludeResourceId() {
	        return this.userExcludeResourceId;
	    }

	    /**
	     * 设置属性创建日期的值
	     */
	    public void setCreatedDate(Date createdDate) {
	        this.createdDate = createdDate;
	    }

	    /**
	     * 获取属性创建日期的值
	     */
	    public Date getCreatedDate() {
	        return this.createdDate;
	    }

	    /**
	     * 设置属性创建人的值
	     */
	    public void setCreatedBy(String createdBy) {
	        this.createdBy = createdBy;
	    }

	    /**
	     * 获取属性创建人的值
	     */
	    public String getCreatedBy() {
	        return this.createdBy;
	    }

	    /**
	     * 设置属性更新日期的值
	     */
	    public void setUpdatedDate(Date updatedDate) {
	        this.updatedDate = updatedDate;
	    }

	    /**
	     * 获取属性更新日期的值
	     */
	    public Date getUpdatedDate() {
	        return this.updatedDate;
	    }

	    /**
	     * 设置属性更新人的值
	     */
	    public void setUpdatedBy(String updatedBy) {
	        this.updatedBy = updatedBy;
	    }

	    /**
	     * 获取属性更新人的值
	     */
	    public String getUpdatedBy() {
	        return this.updatedBy;
	    }

	    /**
	     * 设置属性用户ID的值
	     */
	    public void setUserId(String userId) {
	        this.userId = userId;
	    }

	    /**
	     * 获取属性用户ID的值
	     */
	    public String getUserId() {
	        return this.userId;
	    }

	    /**
	     * 设置属性资源ID的值
	     */
	    public void setResourceId(String resourceId) {
	        this.resourceId = resourceId;
	    }

	    /**
	     * 获取属性资源ID的值
	     */
	    public String getResourceId() {
	        return this.resourceId;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        return EqualsBuilder.reflectionEquals(this, obj);
	    }

	    @Override
	    public int hashCode() {
	        return HashCodeBuilder.reflectionHashCode(this);
	    }

	    @Override
	    public String toString() {
	        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	    }

}
