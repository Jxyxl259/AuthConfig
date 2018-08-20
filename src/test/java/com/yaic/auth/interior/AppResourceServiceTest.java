package com.yaic.auth.interior;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.yaic.auth.Run;
import com.yaic.auth.interior.model.AppResourceModel;
import com.yaic.auth.interior.service.AppResourceService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class)
@WebAppConfiguration
public class AppResourceServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AppResourceServiceTest.class);
	@Autowired
	private AppResourceService appResourceService;

	@Test
	public void getInfoByResourceName() throws Exception {
		AppResourceModel appResourceModel = appResourceService.getInfoByResourceName("系统管理");
		logger.info("appResourceModel:{}", appResourceModel);
	}

	@Test
	public void addInfo() throws Exception {
//		AppResourceModel appResourceModel = new AppResourceModel();
//		appResourceModel.setResourceId("0105");
//		appResourceModel.setCreatedBy("JSJS");
//		appResourceModel.setUpdatedBy("JSJS");
//		appResourceModel.setResourceName("测试用例");
//		appResourceModel.setResourceType("menu");
//		appResourceModel.setResourceLevel(2);
//		appResourceModel.setParentResourceId("1");
//		appResourceModel.setResourceIconClass("fa fa-cogs fa-fw fa-spin");
//		appResourceModel.setActionUrl("/test/test");
//		appResourceModel.setEndFlag(1);
//		appResourceModel.setDisplayOrder(1);
//		int i = appResourceService.addInfo(appResourceModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void updateInfo() throws Exception {
//		AppResourceModel appResourceModel = new AppResourceModel();
//		appResourceModel.setResourceId("0105");
//		appResourceModel.setCreatedBy("JSJS");
//		appResourceModel.setUpdatedBy("JSJS");
//		appResourceModel.setResourceName("测试用例2");
//		appResourceModel.setResourceType("menu2");
//		appResourceModel.setResourceLevel(3);
//		appResourceModel.setParentResourceId("2");
//		appResourceModel.setResourceIconClass("fa2 fa-cogs fa-fw fa-spin");
//		appResourceModel.setActionUrl("/test2/test");
//		appResourceModel.setEndFlag(2);
//		appResourceModel.setDisplayOrder(2);
//		int i = appResourceService.updateInfo(appResourceModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void deleteInfo() throws Exception {
//		int result = appResourceService.deleteInfo("0105");
//		logger.info("result:{}", result);
	}

	@Test
	public void getInfoByResourceId() throws Exception {
		AppResourceModel appResourceModel = appResourceService.getInfoByResourceId("0103");
		logger.info("appResourceModel:{}", appResourceModel);
	}

	@Test
	public void deleteByPrimaryKey() throws Exception {
//		AppResourceModel appResourceModel = new AppResourceModel();
//		appResourceModel.setResourceId("0105");
//		int result = appResourceService.deleteByPrimaryKey(appResourceModel);
//		logger.info("result:{}", result);
	}

}
