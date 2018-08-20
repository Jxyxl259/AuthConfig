package com.yaic.auth.interior.model;

import java.io.Serializable;
import java.util.Date;

import com.yaic.auth.common.BaseDto;

public class AppCompanyModel extends BaseDto implements Serializable {

	private static final long serialVersionUID = AppCompanyModel.class.getName().hashCode();

    /** 机构ID */
    private String companyId;

    /** 创建日期 */
    private Date createdDate;

	/** 创建人 */
	private String createdBy;
	
	/** 更新日期 */
	private Date updatedDate;
	
	/** 更新人 */
	private String updatedBy;
	
	/** 机构简体名称 */
	private String companyCname;
	
	/** 机构英文名称 */
	private String companyEname;
	
	/** 机构繁体名称 */
	private String companyTname;
	
	/** 菜单层级 */
	private Integer companyLevel;
	
	/** 上级菜单ID */
	private String parentCompanyId;
	
	/** 是否有效：0无效，1 有效 */
	private Integer validFlag;
	
	/** 备注 */
	private String remark;
	
	/** 其他标志 */
	private String flag;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
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

    public String getCompanyCname() {
        return companyCname;
    }

    public void setCompanyCname(String companyCname) {
        this.companyCname = companyCname == null ? null : companyCname.trim();
    }

    public String getCompanyEname() {
        return companyEname;
    }

    public void setCompanyEname(String companyEname) {
        this.companyEname = companyEname == null ? null : companyEname.trim();
    }

    public String getCompanyTname() {
        return companyTname;
    }

    public void setCompanyTname(String companyTname) {
        this.companyTname = companyTname == null ? null : companyTname.trim();
    }

    public Integer getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(Integer companyLevel) {
        this.companyLevel = companyLevel;
    }

    public String getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(String parentCompanyId) {
        this.parentCompanyId = parentCompanyId == null ? null : parentCompanyId.trim();
    }

    public Integer getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Integer validFlag) {
        this.validFlag = validFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }
}