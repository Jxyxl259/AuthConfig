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
import com.yaic.auth.common.ResultMessage;
import com.yaic.auth.external.service.ExternalConfigService;
import com.yaic.auth.thirdparty.model.ServerModel;
import com.yaic.auth.thirdparty.service.ServerService;


/**
 * @Title ServerController
 * @Description 服务功能
 * @author wangJs
 * @date 2018/06/04
 * 
 */
@RestController
@RequestMapping("/server")
public class ServerController {
	private static final Logger logger = LoggerFactory.getLogger(ServerController.class);
	@Autowired
	private ServerService serverService;
	
	@Autowired
	ExternalConfigService configService;

	/** 
	* @Title: getList 
	* @Description: 列表查询
	* @param model
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("/list")
	public ResultMessage getList(@RequestBody ServerModel model) throws Exception {
		logger.info("执行查询方法getList()，参数为:{}", model);
		BaseDto model1 = BaseDto.getPageInfo(model);
		Page<ServerModel> page = PageHelper.startPage(model1.getPageNum(), model1.getPageSize());
		serverService.getList(model);

		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(page,model.getDraw());

		return result;
	}
	
	/**
	* @Title: getInfo 
	* @Description: 根据服务ID获取服务信息
	* @param model
	* @return    
	* @return ResultMessage  
	* @throws 
	 */
	@PostMapping(value = "/getInfo")
	public ResultMessage getInfo(@RequestBody ServerModel model) throws Exception {
		logger.debug("getInfo ServerModel by serverId:{}", model.getServerId());
		model = serverService.getOneByServerId(model.getServerId());
		logger.info("查询结果：{}",model);
		ResultMessage results = BaseController.getResults(model);
		return results;
	}

	/** 
	* @Title: saveOrUpdParaType 
	* @Description: 保存或者更新单条信息
	* @param model
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping("/changeInfo")
	public ResultMessage saveOrUpdParaType(@RequestBody ServerModel model) throws Exception {
		StringBuffer msg = new StringBuffer();

		ResultMessage results = null;
		Integer result = 0;
		ServerModel serverTemp = null;
		logger.debug("进行数据校验：{}",model);
		String checkResult = BeanCheck.check(model);
		logger.debug("数据校验结束");
		if(!StringUtils.isEmpty(checkResult)) {
			msg.append(checkResult);
		}else {
				
			// 判断操作类型
			if("checkUrl".equals(model.getReqType())) {	//验证URL唯一性
				ServerModel model1 = new ServerModel();
				model1.setServerEnv(model.getServerEnv());
				model1.setServerType(model.getServerType());
				model1.setServerUrl(model.getServerUrl());
				List<ServerModel> lists = serverService.getList(model1);
				if(lists != null && lists.size() > 0){
					for (ServerModel model2 : lists) {
						if(model2.getServerId().equals(model.getServerId()))
							continue;
						msg.append("在").append(model2.getServerEnv())
							.append("环境下的").append(model2.getServerType())
							.append("类型中,已存在该URL地址\n");
					}
					result = 1;
				}
				results = BaseController.getResults(result, msg.toString());
				return results;
			}

			else if("add".equals(model.getReqType())) {
	
				model.setCreatedUser(Constants.LOGIN_USER_CODE());
				model.setUpdatedUser(Constants.LOGIN_USER_CODE());
				logger.debug("开始执行新增方法：addInfo()");
				result = serverService.addInfo(model);
				logger.info("新增完成，结果:{}",result);
	
			}else if ("edit".equals(model.getReqType())) {
	
				// 根据id判断是否存在
				logger.debug("select ServerModel by serverId:{}", model.getServerId());
				serverTemp = serverService.getOneByServerId(model.getServerId());
	
				if (serverTemp != null) {
	
					model.setUpdatedUser(Constants.LOGIN_USER_CODE());
					logger.debug("开始执行修改方法：updateInfo()");
					result = serverService.updateInfo(model);
					logger.info("修改完成，结果：{}",result);
				} else {
					logger.info("serverId:{} is not exist", model.getServerId());
					msg.append(model.getServerId() + "不存在！");
				}
			}
			msg.append("add".equals(model.getReqType()) ? "新增" : "编辑");
			msg.append(result == 1 ? "操作成功!" : "操作失败!");
		}
		results = BaseController.getResults(result, msg.toString());
		return results;
	}

	/** 
	* @Title: delete 
	* @Description: 根据编号删除
	* @param serverId
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@GetMapping(value = "/delete/{serverId}")
	public ResultMessage delete(@PathVariable Integer serverId) throws Exception {
		StringBuffer msg = new StringBuffer();
		Integer result = 0;
		logger.debug("delete Server by serverId:{}", serverId);
		//根据Id查询对应的信息
		logger.debug("select ServerModel by serverId:{}",serverId);
		ServerModel model = serverService.getOneByServerId(serverId);
		
		if(model != null) {
			 logger.debug("开始执行删除方法：deleteInfo()");
			 result = serverService.deleteInfo(serverId);
			 logger.info("删除结束,结果：{}",result);
		}
		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");

		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}

	/** 
	* @Title: deleteByIds 
	* @Description: 批量删除
	* @param ids
	* @return    
	* @return ResultMessage  
	* @throws 
	*/
	@PostMapping(value = "/deleteByIds")
	public ResultMessage deleteByIds(@RequestParam(value = "ids", required = false) String ids) throws Exception {
		StringBuffer msg = new StringBuffer();

		List<String> idsParam = PublicMethods.SplitIdsToArray(ids);
		logger.debug("deleteByIds Server by ids:{}", ids);
		int result = serverService.deleteInfoByIds(idsParam);
		logger.info("删除结束,结果：{}", result);
		msg.append(result > 0 ? "删除操作成功!" : "删除操作失败!");

		ResultMessage results = BaseController.getResults(result, msg.toString());
		return results;
	}
	
	@PostMapping("/serverTypeist")
	public ResultMessage getServerTypeList() throws Exception{
		logger.debug("开始执行方法getValuesByParameterType()");
		List<String[]> lists = configService.getValuesByParameterType("SERVER_TYPE");
		logger.info("结果：{}"+lists.size());
		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(lists);

		return result;
	}

	@PostMapping("/serverEnvTypeist")
	public ResultMessage getServerEnvTypeList() throws Exception{
		logger.debug("开始执行方法getServerEnvTypes()");
		List<String> lists = configService.getServerEnvTypes();
		logger.info("结果：{}"+lists.size());
		// 封装返回结果为统一格式JSON
		ResultMessage result = BaseController.getResults(lists);
		
		return result;
	}
	
	
}
