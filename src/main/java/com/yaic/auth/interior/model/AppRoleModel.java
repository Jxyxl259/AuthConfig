package com.yaic.auth.interior.model;

import java.io.Serializable;
import java.util.Date;

import com.yaic.auth.common.BaseDto;

public class AppRoleModel  extends BaseDto implements Serializable {
	
	private static final long serialVersionUID = AppRoleModel.class.getName().hashCode();


	/** 角色ID */
	private String roleId;
	
	/** 创建日期 */
	private Date createdDate;
	
	/** 创建人 */
	private String createdBy;
	
	/** 更新日期 */
	private Date updatedDate;
	
	/** 更新人 */
	private String updatedBy;
	
	/** 角色名称 */
	private String roleName;
	
	/** 是否有效：0无效，1 有效 */
	private Integer validFlag;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Integer getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Integer validFlag) {
        this.validFlag = validFlag;
    }
}