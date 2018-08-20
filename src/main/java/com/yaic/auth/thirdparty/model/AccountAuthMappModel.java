package com.yaic.auth.thirdparty.model;

import java.io.Serializable;

import com.yaic.auth.common.BaseDto;

/** 
* @ClassName: AccountAuthMappModel 
* @Description: 账户与服务关系表(原始,加密,自定义类型)
* @author zhaoZd
* @date 2018年7月25日 下午11:14:07 
*  
*/
public class AccountAuthMappModel extends BaseDto implements Serializable {


    /** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 470181452264670462L;
	
	/** 主键 */
    private Integer id;
    /** 方案代码 */
    private String appId;
    /** 方案名称 */
    private Integer mappingId;
//    /** 创建时间,默认插入时间 */
//    private Date createdDate;
//    /** 创建用户,插入用户 */
//    private String createdUser;
//    /** 更新时间,默认插入更新时间 */
//    private Date updatedDate;
//    /** 更新用户 */
//    private String updatedUser;
    
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Integer getMappingId() {
		return mappingId;
	}
	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}

    
    

}