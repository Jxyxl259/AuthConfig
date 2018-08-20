/************************************************************************
 * 描述 ：数据库表t_account对应的表单对象，代码生成。
 * 作者 ：ZHAOZD
 * 日期 ：2018-06-14 20:00:38
 *
 ************************************************************************/

package com.yaic.auth.external.dto.responsedto;

import java.io.Serializable;
import java.util.List;

import com.yaic.auth.thirdparty.dto.AccountDto;


/** 
* @ClassName: AccountExternalDto 
* @Description: 账号查询接口返回对象
* @author zhaoZd
* @date 2018年7月25日 上午10:19:29 
*  
*/
public class AccountExternalDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 146541698607044778L;

	private Integer pageNum;
	
	private Integer pageSize;
	
	private String totalCount;
	
	private Integer totalPage;
	
	private List<AccountDto> dataList;
	
	

	public AccountExternalDto() {
		super();
	}

	public AccountExternalDto(Integer pageNum, Integer pageSize, String totalCount, Integer totalPage,
			List<AccountDto> dataList) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.dataList = dataList;
	}


	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<AccountDto> getDataList() {
		return dataList;
	}

	public void setDataList(List<AccountDto> dataList) {
		this.dataList = dataList;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	
}
