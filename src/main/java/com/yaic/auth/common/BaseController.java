package com.yaic.auth.common;

import com.github.pagehelper.Page;

/**
 * 
* @ClassName: BaseController 
* @Description: 自定义所有controller类父类
* @author zhaoZD
* @date 2018年6月17日 下午9:33:51 
*
 */
public class BaseController {

	

	/**
	 * 
	* @Title: getResults 
	* @Description: 对内 封装controller中 list返回JSON格式
	* @param page
	* @return    
	* @return ResultMessage  
	* @throws
	 */
	public static <T> ResultMessage getResults(Page<T> page) {
		
		ResultMessage result = new ResultMessage();
		
		result.setDataList(page.getResult());
		result.setRecordsFiltered(page.getPageSize());
		result.setRecordsTotal(page.getTotal());
		result.setDraw(1);
		
		return result;
	}
	
	public static <T> ResultMessage getResults(Page<T> page,Integer draw) {
		
		ResultMessage result = new ResultMessage();
		
		result.setDataList(page.getResult());
		result.setRecordsFiltered(page.getPageSize());
		result.setRecordsTotal(page.getTotal());
		result.setPageSize(page.getPageSize());
		result.setDraw(draw++);
		
		return result;
	}
	
	public static <T> ResultMessage getResults(Object lists) {
		
		ResultMessage result = new ResultMessage();
		
		result.setDataList(lists);
		
		return result;
	}

	/**
	 * 
	* @Title: getResults 
	* @Description: 对内 封装controller中 add edit delete 返回JSON格式
	* @param resultCode
	* @param resutlMsg
	* @return    
	* @return ResultMessage  
	* @throws
	 */
	public static ResultMessage getResults(Integer resultCode, String resutlMsg) {
		
		ResultMessage results = new ResultMessage();
		
		results.setResultCode(resultCode>0?resultCode.toString():"9999");
		results.setResultMsg(resutlMsg);
		
		return results;
	}
	
}
