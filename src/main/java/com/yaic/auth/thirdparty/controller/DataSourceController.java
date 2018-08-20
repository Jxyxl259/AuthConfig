package com.yaic.auth.thirdparty.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yaic.auth.common.BaseController;
import com.yaic.auth.common.BaseDto;
import com.yaic.auth.common.BeanCheck;
import com.yaic.auth.common.Constants;
import com.yaic.auth.common.PublicMethods;
import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.thirdparty.dto.AuthBasicInfo;
import com.yaic.auth.thirdparty.model.AccountModel;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.service.AccountService;
import com.yaic.auth.thirdparty.service.AuthConfigService;
import com.yaic.auth.thirdparty.service.DataSourceService;

/**
 * @ClassName: DataSourceController
 * @Description: 渠道基本功能
 * @author
 * @date 2018年6月17日 下午9:50:06
 * 
 */
@RestController
@RequestMapping("/source")
public class DataSourceController {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceController.class);

	@Autowired
	private DataSourceService dataSourceService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AuthConfigService authConfigService;

	// 条件查询
	@PostMapping(value = "/list")
	public ResultMessage getList(@RequestBody DataSourceModel model) throws Exception {

		BaseDto model1 = BaseDto.getPageInfo(model);
		logger.info("执行查询方法getList()，参数为:{}", model);
		Page<DataSourceModel> page = PageHelper.startPage(model1.getPageNum(), model1.getPageSize());
		dataSourceService.getList(model);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page,model.getDraw());

		return result;

	}
	
	@PostMapping(value = "/listView")
	public ResultMessage getListView(@RequestBody DataSourceModel dataSourceModel) throws Exception {
		if (dataSourceModel == null) {
			dataSourceModel = new DataSourceModel();
		}
		logger.info("执行查询方法getListView()，参数为:{}", dataSourceModel);
		Page<DataSourceModel> page = PageHelper.startPage(dataSourceModel.getPageNum(), dataSourceModel.getPageSize());
		dataSourceService.getDataSourceView(dataSourceModel);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page);

		return result;

	}
	/**
	* @Title: getInfo 
	* @Description: 根据渠道ID获取渠道信息
	* @param dataSourceModel
	* @return    
	* @return ResultMessage  
	* @throws 
	 */
	@PostMapping(value = "/getInfo")
	public ResultMessage getInfo(@RequestBody DataSourceModel dataSourceModel) throws Exception {
		logger.debug("getInfo DataSourceModel by dataSourceId:{}", dataSourceModel.getDataSourceId());
		dataSourceModel = dataSourceService.getOneByDataSourceId(dataSourceModel.getDataSourceId());
		
		AccountModel model = new AccountModel();
		if(!StringUtils.isEmpty(dataSourceModel) && !StringUtils.isEmpty(dataSourceModel.getAppId())) {
			model = accountService.getOneByAppid(dataSourceModel.getAppId());
		}
			
		List<Object> lists = new ArrayList<Object>();
		lists.add(dataSourceModel);
		lists.add(model);
		
		logger.info("查询结果：{}",lists);
		ResultMessage results = BaseController.getResults(lists);
		return results;
	}
	
	@PostMapping(value = "/listAll")
	public ResultMessage listAll(@RequestBody DataSourceModel model) throws Exception {
		logger.info("执行查询方法getListView()，参数为:{}", model);
		List<DataSourceModel> lists = dataSourceService.getList(model);
		logger.info("查询结果：{}",lists.size());
		Map<String,Object> maps = new HashMap<String,Object>();
		for (DataSourceModel m : lists) {
			maps.put(m.getDataSource()+":"+m.getSourceName(),m.getDataSourceId());
		}
		
		maps.put("sources",lists);
		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(maps);

		return result;

	}

	/** 
	* @Title: saveOrUpdParaType 
	* @Description: 添加或者编辑
	* @param dataSourceModel
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/changeInfo")
	public ResultMessage saveOrUpdParaType(@RequestBody DataSourceModel dataSourceModel) throws Exception {

		StringBuffer msg = new StringBuffer();
		Integer result = 0;
		logger.debug("验证关系是否符合规则:{}", dataSourceModel);
		String checkResult = BeanCheck.check(dataSourceModel);
		logger.debug("验证结果:" + checkResult);
		if(!StringUtils.isEmpty(checkResult)) {
			
			msg.append(checkResult);
			ResultMessage results = BaseController.getResults(result, msg.toString());
			return results;
		}else {
			
			// 判断唯一
			logger.debug("select DataSource by dataSourceModel:{}", dataSourceModel.getDataSource());
			DataSourceModel dataSourceTemp = dataSourceService.getOneByDataSourceCode(dataSourceModel.getDataSource());
			
			// 判断操作类型
			if ("add".equals(dataSourceModel.getReqType())) {
	
				if (dataSourceTemp != null) {
	
					logger.info("dataSourceModel:{} is already exist", dataSourceModel.getDataSource());
					msg.append(dataSourceModel.getDataSource() + "已存在！");
				} else {
					dataSourceModel.setDataSourceId(null);
					dataSourceModel.setCreatedUser(Constants.LOGIN_USER_CODE());
					dataSourceModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
//					result = dataSourceService.addInfo(dataSourceModel);
					
					AccountModel acctModel = new AccountModel();
					acctModel = accountService.getOneByAppid(dataSourceModel.getAppId());
					
					AuthBasicInfo basicInfo = new AuthBasicInfo();
					basicInfo.setAddRole("datasource");
					basicInfo.setDataSource(dataSourceModel.getDataSource());
					basicInfo.setSourceName(dataSourceModel.getSourceName());
					basicInfo.setPrimaryId(acctModel.getAccountId());
					logger.debug("开始执行新增方法：saveDatasourProject(),添加数据:{}", basicInfo);
					result = authConfigService.saveDatasourProject(basicInfo);
					logger.info("authMappingModel 添加结果:{}", result);
				}
	
			} else if ("edit".equals(dataSourceModel.getReqType())) {
	
				// 根据id判断是否存在
				logger.debug("select DataSource by dataSourceId:{}", dataSourceModel.getDataSourceId());
				dataSourceTemp = dataSourceService.getOneByDataSourceId(dataSourceModel.getDataSourceId());

				if (dataSourceTemp != null) {

					dataSourceModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
					logger.debug("开始执行修改方法：updateInfo(),修改信息：{}", dataSourceModel);
					result = dataSourceService.updateInfo(dataSourceModel);
					logger.info("authMappingModel 更新结果:{}", result);
				} else {
					logger.info("dataSourceId:{} is not exist", dataSourceModel.getDataSourceId());
					msg.append(dataSourceModel.getDataSourceId() + "不存在！");
				}
	
			}
	
			msg.append("add".equals(dataSourceModel.getReqType()) ? "新增" : "编辑");
			msg.append(result == 1 ? "操作成功!" : "操作失败!");
		}
		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

	/** 
	* @Title: delete 
	* @Description: 根据编号删除
	* @param dataSourceId
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@GetMapping(value = "/delete/{dataSourceId}")
	public ResultMessage delete(@PathVariable Integer dataSourceId) throws Exception {
		logger.info("dataSourceId:{}", dataSourceId);
		StringBuffer msg = new StringBuffer();
		ResultMessage results = BaseController.getResults(0,"删除操作失败!");
		Integer result = 0;
		
		//根据Id查询对应的信息
		if(dataSourceId != null && dataSourceId == PublicParamters.CAN_NOT_DELETE_ACCOUNT_ID){
			
			results = BaseController.getResults(0,"自定义_渠道为特殊渠道,不能删除");
		}else{
			logger.debug("select DataSourceModel by dataSourceId:{}",dataSourceId);
			DataSourceModel model = dataSourceService.getOneByDataSourceId(dataSourceId);
			
			if(model != null) {
				logger.debug("开始执行删除方法：deleteInfo()");
				result = dataSourceService.deleteInfo(dataSourceId);		
				logger.info("删除结束,结果：{}",result);
			}
	
			msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");
		}
		
		results = BaseController.getResults(result, msg.toString());
		return results;
	}

	/** 
	* @Title: deleteByIds 
	* @Description:  批量删除
	* @param ids
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/deleteByIds")
	public ResultMessage deleteByIds(@RequestParam(value = "ids", required = false) String ids) throws Exception {

		StringBuffer msg = new StringBuffer();
		ResultMessage results = BaseController.getResults(0,"删除操作失败!");
		logger.debug("deleteByIds DataSource by ids:{}", ids);
		
		List<String> idsParam = PublicMethods.SplitIdsToArray(ids);
		logger.debug("格式化后的ids: {}", idsParam);
		if(idsParam.contains("1")){
			
			results = BaseController.getResults(0,"自定义_渠道为特殊渠道,不能删除");
		}else{
			logger.debug("开始执行删除方法：deleteInfoByIds()");
			Integer result = dataSourceService.deleteInfoByIds(idsParam);
			logger.info("删除结束,结果：{}", result);
			msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");
			results = BaseController.getResults(result, msg.toString());
		}
		return results;
	}

}
