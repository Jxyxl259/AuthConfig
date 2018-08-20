/************************************************************************
 * 描述 ：数据库表t_project对应的表单对象，代码生成。
 * 作者 ：ZHAOZD
 * 日期 ：2018-06-14 20:00:38
 *
 ************************************************************************/

package com.yaic.auth.thirdparty.dto;

import java.io.Serializable;
import java.util.List;

import com.yaic.auth.thirdparty.model.ProjectModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/** 
* @ClassName: ProjectDto 
* @Description: TODO
* @author zhaoZD
* @date 2018年6月17日 下午9:52:40 
*  
*/
@ApiModel(value = "ProjectDto", description = "方案对象")
public class ProjectDto implements Serializable {

	private static final long serialVersionUID = 6116313631723596736L;
	/** 主键 */
	@ApiModelProperty(value = "主键")
    private Integer projectId;
    /** 方案代码 */
	@ApiModelProperty(value ="方案代码")
    private String projectCode;
    /** 方案名称 */
	@ApiModelProperty(value ="方案名称", required = true)
    private String projectName;
//    /** app_id */
//    private Integer accountId;
//    /** 鉴权id,t_auth表主键 */
//    private Integer authId;
//    /** 渠道id */
//    private Integer dataSourceId;
	@ApiModelProperty(value ="鉴权加密对象", required = true)
    private AuthEncryptDto authEncryptDto;
	@ApiModelProperty(value ="回调信息", required = true)
    private List<CallbackUrlDto> callbackList;
	@ApiModelProperty(value ="服务和方案关系", required = true)
    private List<AuthMappingDto> authmappList;
	@ApiModelProperty(value ="渠道对象", required = true)
    private DataSourceDto dataSourceDto;
	@ApiModelProperty(value ="同渠道下有效方案个数", required = true)
	private Integer projectCounts;
    
    /** 创建用户,插入用户 */
	@ApiModelProperty(value ="创建用户,插入用户")
    private String createdUser;
    /** 更新用户 */
	@ApiModelProperty(value ="更新用户")
    private String updatedUser;
    
    
    public ProjectDto() {}

	public ProjectDto(ProjectModel model) {
		if(model == null) model =new ProjectModel();
		this.projectId = model.getProjectId();
		this.projectCode = model.getProjectCode();
		this.projectName = model.getProjectName();
//		this.authId = model.getAuthId();
//		this.dataSourceId = model.getDataSourceId();
//		this.accountId = model.getAccountId();
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

//	public Integer getAccountId() {
//		return accountId;
//	}
//
//	public void setAccountId(Integer accountId) {
//		this.accountId = accountId;
//	}
//
//	public Integer getAuthId() {
//		return authId;
//	}
//
//	public void setAuthId(Integer authId) {
//		this.authId = authId;
//	}
//
//	public Integer getDataSourceId() {
//		return dataSourceId;
//	}
//
//	public void setDataSourceId(Integer dataSourceId) {
//		this.dataSourceId = dataSourceId;
//	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	
	public AuthEncryptDto getAuthEncryptDto() {
		return authEncryptDto;
	}
	public void setAuthEncryptDto(AuthEncryptDto authEncryptDto) {
		this.authEncryptDto = authEncryptDto;
	}

	public List<CallbackUrlDto> getCallbackList() {
		return callbackList;
	}

	public void setCallbackList(List<CallbackUrlDto> callbackList) {
		this.callbackList = callbackList;
	}

	public List<AuthMappingDto> getAuthmappList() {
		return authmappList;
	}

	public void setAuthmappList(List<AuthMappingDto> authmappList) {
		this.authmappList = authmappList;
	}

	public DataSourceDto getDataSourceDto() {
		return dataSourceDto;
	}

	public void setDataSourceDto(DataSourceDto dataSourceDto) {
		this.dataSourceDto = dataSourceDto;
	}

	public Integer getProjectCounts() {
		return projectCounts;
	}

	public void setProjectCounts(Integer projectCounts) {
		this.projectCounts = projectCounts;
	}
	
	

}
