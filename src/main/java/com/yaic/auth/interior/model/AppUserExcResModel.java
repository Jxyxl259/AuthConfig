package com.yaic.auth.interior.model;

import java.io.Serializable;
import java.util.Date;

public class AppUserExcResModel implements Serializable {
	
	private static final long serialVersionUID = AppUserExcResModel.class.getName().hashCode();

    /** 用户资源排除关联ID */
    private Integer userExcludeResourceId;

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

    public Integer getUserExcludeResourceId() {
        return userExcludeResourceId;
    }

    public void setUserExcludeResourceId(Integer userExcludeResourceId) {
        this.userExcludeResourceId = userExcludeResourceId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }
}