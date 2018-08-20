/************************************************************************
 * 描述 ：数据库表t_project对应的表单对象，代码生成。
 * 作者 ：ZHAOZD
 * 日期 ：2018-06-14 20:00:38
 *
 ************************************************************************/

package com.yaic.auth.external.dto.responsedto;

import java.io.Serializable;


/** 
* @ClassName: AddProjectReturnDto 
* @Description: 新增渠道后返回对象
* @author zhaoZD
* @date 2018年6月17日 下午9:52:40 
*  
*/
public class AddProjectReturnDto implements Serializable {


	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -3972161322673401981L;

	private Integer projectId;
    
    private Integer dataSourceId;

	public AddProjectReturnDto() {}

	public AddProjectReturnDto(Integer projectId, Integer dataSourceId) {
		super();
		this.projectId = projectId;
		this.dataSourceId = dataSourceId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
    
    
	

}
