package com.yaic.auth.interior.model;

import java.io.Serializable;
import java.util.Date;

public class AppUserRoleModel implements Serializable {


	private static final long serialVersionUID = AppUserRoleModel.class.getName().hashCode();

	/** 用户角色关联ID */
	private String userRoleId;
	
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
	
	/** 角色ID */
	private String roleId;

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId == null ? null : userRoleId.trim();
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}