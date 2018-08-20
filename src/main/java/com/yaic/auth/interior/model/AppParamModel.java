package com.yaic.auth.interior.model;

import java.io.Serializable;
import java.util.Date;

public class AppParamModel implements Serializable {
	
	private static final long serialVersionUID = AppParamModel.class.getName().hashCode();


	/** 参数ID */
	private String parameterId;
	
	/** 创建日期 */
	private Date createdDate;
	
	/** 创建人 */
	private String createdBy;
	
	/** 更新日期 */
	private Date updatedDate;
	
	/** 更新人 */
	private String updatedBy;
	
	/** 参数类型 */
	private String parameterType;
	
	/** 参数代码 */
	private String parameterCode;
	
	/** 参数简体名称 */
	private String parameterCname;
	
	/** 参数英文名称 */
	private String parameterEname;
	
	/** 参数繁体名称 */
	private String parameterTname;
	
	/** 是否有效：0无效，1 有效 */
	private Integer validFlag;
	
	/** 备注 */
	private String remark;
	
	/** 其他标志 */
	private String flag;

    public String getParameterId() {
        return parameterId;
    }

    public void setParameterId(String parameterId) {
        this.parameterId = parameterId == null ? null : parameterId.trim();
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

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType == null ? null : parameterType.trim();
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode == null ? null : parameterCode.trim();
    }

    public String getParameterCname() {
        return parameterCname;
    }

    public void setParameterCname(String parameterCname) {
        this.parameterCname = parameterCname == null ? null : parameterCname.trim();
    }

    public String getParameterEname() {
        return parameterEname;
    }

    public void setParameterEname(String parameterEname) {
        this.parameterEname = parameterEname == null ? null : parameterEname.trim();
    }

    public String getParameterTname() {
        return parameterTname;
    }

    public void setParameterTname(String parameterTname) {
        this.parameterTname = parameterTname == null ? null : parameterTname.trim();
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