package com.yaic.auth.thirdparty.controller;

import java.util.List;

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
import com.yaic.auth.thirdparty.dto.ProjectViewDto;
import com.yaic.auth.thirdparty.model.AccountModel;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.service.AccountService;
import com.yaic.auth.thirdparty.service.DataSourceService;
import com.yaic.auth.thirdparty.service.ProjectService;

/**
 * 方案界面基本功能
 * 
 * @Title ProjectController
 * @Description 方案管理功能
 * @author wangJS
 * @date 2018/06/01
 *
 */
@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DataSourceService dataSourceService;
	@Autowired
	private AccountService accountService;
	
	


	/** 
	* @Title: getList 
	* @Description:  展示列表信息
	* @param projectModel
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("/list1")
	public ResultMessage getList1(@RequestBody ProjectModel projectModel) throws Exception {

		if (projectModel == null) {
			projectModel = new ProjectModel();
		}
		logger.info("执行查询方法getList1()，参数为:{}", projectModel);
		Page<ProjectModel> page = PageHelper.startPage(projectModel.getPageNum(), projectModel.getPageSize());
		projectService.getList(projectModel);
		
		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page);

		return result;
	}

	@PostMapping("/list")
	public ResultMessage getList(@RequestBody ProjectViewDto model) throws Exception {

		BaseDto model1 = BaseDto.getPageInfo(model);
		logger.info("执行查询方法getList()，参数为:{}", model);
//		System.out.println("model1.getPageNum()---"+model1.getPageNum()+"-----model1.getPageSize()---"+model1.getPageSize());
		Page<ProjectModel> page = PageHelper.startPage(model1.getPageNum(),model1.getPageSize());
		projectService.getProjectViewDto(model);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page,model.getDraw());
		return result;
	}

	
	/** 
	* @Title: getInfo 
	* @Description: 根据方案ID获取方案信息
	* @param projectModel
	* @throws Exception    
	* @return ResultMessage  
	*/
	@PostMapping(value = "/getInfo")
	public ResultMessage getInfo(@RequestBody ProjectModel projectModel) throws Exception {
		logger.debug("getInfo projectModel by projectId:{}", projectModel.getProjectId());
		projectModel = projectService.getOneByProjectId(projectModel.getProjectId());
		logger.info("查询结果：{}",projectModel);
		ResultMessage results = BaseController.getResults(projectModel);
		return results;
	}
	
	
	@PostMapping(value = "/listAll")
	public ResultMessage listAll() throws Exception {
		ProjectModel model = new ProjectModel();
		model.setIsDefault(PublicParamters.DEFAULT_PROJECT_N);
		logger.debug("excute method: listAll() ");
		List<ProjectModel> lists = projectService.getList(model);
		logger.info("查询结果条数为：{} 条",lists.size());
		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(lists);

		return result;

	}


	/** 
	* @Title: saveOrUpdParaType 
	* @Description: 保存或者更新单条信息
	* @param projectModel
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/changeInfo")
	public ResultMessage saveOrUpdParaType(@RequestBody ProjectModel projectModel) throws Exception {
		StringBuffer msg = new StringBuffer();

		Integer result = 0;
		ProjectModel projectTemp = null;
		logger.debug("进行数据校验：{}",projectModel);
		String checkResult = BeanCheck.check(projectModel);
		logger.debug("数据校验结束");
		if (!StringUtils.isEmpty(checkResult)) {
			msg.append(checkResult);
		} else {
			// 判断操作类型
			if ("add".equals(projectModel.getReqType())) {

				// 判断唯一
				logger.debug("select Project by projectCode:{}", projectModel.getProjectCode());
				projectTemp = projectService.getOneByProjectCode(projectModel.getProjectCode());

				if (projectTemp != null) {

					logger.info("projectCode:{} is already exist", projectModel.getProjectCode());
					msg.append(projectModel.getProjectCode() + "已存在！");
				} else {
					
					DataSourceModel sourceModel = dataSourceService.getOneByDataSourceId(projectModel.getDataSourceId());
					
					AccountModel accoModel = accountService.getOneByAppid(sourceModel.getAppId());

					projectModel.setAccountId(accoModel.getAccountId());
					projectModel.setIsDefault(PublicParamters.DEFAULT_PROJECT_N);
					projectModel.setCreatedUser(Constants.LOGIN_USER_CODE());
					projectModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
					logger.debug("开始执行新增方法：addInfo()");
					result = projectService.addInfo(projectModel);
					logger.debug("projectModel 添加成功");
				}

			} else if ("edit".equals(projectModel.getReqType())) {

				// 根据id判断是否存在
				logger.debug("select Project by projectId:{}", projectModel.getProjectId());
				projectTemp = projectService.getOneByProjectId(projectModel.getProjectId());

				if (projectTemp != null) {

					projectModel.setUpdatedUser(Constants.LOGIN_USER_CODE());
					logger.debug("开始执行修改方法：updateInfo()");
					result = projectService.updateInfo(projectModel);
					logger.debug("projectModel 更新成功");
				} else {
					logger.info("projectId:{} is not exist", projectModel.getProjectId());
					msg.append(projectModel.getProjectId() + "不存在！");
				}

			}

			msg.append("add".equals(projectModel.getReqType()) ? "新增" : "编辑");
			msg.append(result == 1 ? "操作成功!" : "操作失败!");
		}
		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

	/** 
	* @Title: delete 
	* @Description: 根据编号单条删除
	* @param projectId
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@GetMapping(value = "/delete/{accountId}")
	public ResultMessage delete(@PathVariable Integer projectId) throws Exception {

		StringBuffer msg = new StringBuffer();
		Integer result = 0;
		logger.debug("delete Project by projectId:{}", projectId);
		//根据Id查询对应的信息
		logger.debug("select ProjectModel by projectId:{}",projectId);
		ProjectModel model = projectService.getOneByProjectId(projectId);
		
		if(model != null) {
			logger.debug("开始执行删除方法：deleteInfo()");
			result = projectService.deleteInfo(projectId);
			logger.info("删除结束,结果：{}",result);
		}

		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");

		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

	/** 
	* @Title: deleteByIds 
	* @Description: 根据编号批量删除信息
	* @param ids
	* @return
	* @throws Exception    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/deleteByIds")
	public ResultMessage deleteByIds(@RequestParam(value = "ids", required = false) String ids) throws Exception {

		StringBuffer msg = new StringBuffer();
		logger.debug("deleteByIds Project by ids:{}", ids);

		List<String> idsParam = PublicMethods.SplitIdsToArray(ids);
		logger.debug("格式化后的ids: {}", idsParam);
		logger.debug("开始执行删除方法：deleteInfoByIds()");
		int result = projectService.deleteInfoByIds(idsParam);
		logger.info("删除结束,结果：{}", result);
		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");

		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

}
