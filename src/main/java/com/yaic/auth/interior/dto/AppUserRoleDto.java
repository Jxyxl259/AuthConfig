/************************************************************************
 * 描述 ：数据库表app_USER_ROLE对应的表单对象，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.auth.interior.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yaic.auth.interior.model.AppUserRoleModel;

public class AppUserRoleDto extends AppUserRoleModel implements Serializable{

    private static final long serialVersionUID = AppUserRoleDto.class.getName().hashCode();
    /**
     * 角色名称
     */
    private String roleName ;
    
    /**
     * 角色配置标志
     */
    private String congfigFlag ;
    
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCongfigFlag() {
		return congfigFlag;
	}

	public void setCongfigFlag(String congfigFlag) {
		this.congfigFlag = congfigFlag;
	}
    
}
