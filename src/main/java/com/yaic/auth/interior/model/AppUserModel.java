package com.yaic.auth.interior.model;

import java.io.Serializable;
import java.util.Date;

import com.yaic.auth.common.BaseDto;

public class AppUserModel extends BaseDto implements Serializable {
	
	private static final long serialVersionUID = AppUserModel.class.getName().hashCode();

	private String userId;
	 
	/** 创建日期 */
	private Date createdDate;
	
	/** 创建人 */
	private String createdBy;
	
	
	/** 更新日期 */
	private Date updatedDate;
	
	/** 更新人 */
	private String updatedBy;
	
	/** 员工代码 */
	private String userCode;
	
	/** 员工简体中文名称 */
	private String userCname;
	
	/** 员工繁体中文名称 */
	private String userTname;
	
	/** 员工英文名称 */
	private String userEname;
	
	/** 密码 */
	private String password;
	
	/** 印鉴 */
	private String seal;
	
	/** 密码设置日期 */
	private Date passwordSetDate;
	
	/** 密码过期日期 */
	private Date passwordExpireDate;
	
	/** 机构代码 */
	private String companyCode;
	
	/** 电话号码 */
	private String phone;
	
	/** 手机号码 */
	private String mobile;
	
	/** 通信地址 */
	private String address;
	
	/** 邮政编码 */
	private String postCode;
	
	/** 邮箱 */
	private String email;
	
	/** 1-有效；0-无效 */
	private Integer validFlag;
	
	/** 备注 */
	private String remark;
	
	/** 标志字段 */
	private String flag;
	
	/** 加密密码的盐 */
	private String salt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserCname() {
        return userCname;
    }

    public void setUserCname(String userCname) {
        this.userCname = userCname == null ? null : userCname.trim();
    }

    public String getUserTname() {
        return userTname;
    }

    public void setUserTname(String userTname) {
        this.userTname = userTname == null ? null : userTname.trim();
    }

    public String getUserEname() {
        return userEname;
    }

    public void setUserEname(String userEname) {
        this.userEname = userEname == null ? null : userEname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

   public void setSalt(String salt) {
       this.salt = salt == null ? null : salt.trim();
       }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal == null ? null : seal.trim();
    }

    public Date getPasswordSetDate() {
        return passwordSetDate;
    }

    public void setPasswordSetDate(Date passwordSetDate) {
        this.passwordSetDate = passwordSetDate;
    }

    public Date getPasswordExpireDate() {
        return passwordExpireDate;
    }

    public void setPasswordExpireDate(Date passwordExpireDate) {
        this.passwordExpireDate = passwordExpireDate;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Integer validFlag) {
        this.validFlag = validFlag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	@Override
	public String toString() {
		return "AppUserModel [userId=" + userId + ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy + ", userCode=" + userCode
				+ ", userCname=" + userCname + ", userTname=" + userTname + ", userEname=" + userEname + ", password="
				+ password + ", seal=" + seal + ", passwordSetDate=" + passwordSetDate + ", passwordExpireDate="
				+ passwordExpireDate + ", companyCode=" + companyCode + ", phone=" + phone + ", mobile=" + mobile
				+ ", address=" + address + ", postCode=" + postCode + ", email=" + email + ", validFlag=" + validFlag
				+ ", remark=" + remark + ", flag=" + flag + ", salt=" + salt + "]";
	}
    
}