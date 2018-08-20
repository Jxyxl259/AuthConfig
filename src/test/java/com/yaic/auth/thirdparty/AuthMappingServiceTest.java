package com.yaic.auth.thirdparty;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.yaic.auth.Run;
import com.yaic.auth.thirdparty.dto.AuthMappingDto;
import com.yaic.auth.thirdparty.dto.InterfaceInfoDto;
import com.yaic.auth.thirdparty.model.AuthMappingModel;
import com.yaic.auth.thirdparty.service.AuthMappingService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Run.class)
public class AuthMappingServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AuthMappingServiceTest.class);
	@Autowired
	private AuthMappingService authMappingService;

	@Test
	public void getList() throws Exception {
		AuthMappingModel authMappingModel = new AuthMappingModel();
		List<AuthMappingModel> list = authMappingService.getList(authMappingModel);
		logger.info("list:{}", list);
	}

	@Test
	public void addInfo() throws Exception {
//		AuthMappingModel authMappingModel = new AuthMappingModel();
//		authMappingModel.setProjectId(1);
//		authMappingModel.setServerId(1);
//		authMappingModel.setRequestUrl("/test/com");
//		authMappingModel.setCreatedUser("JSJS");
//		authMappingModel.setUpdatedUser("JSJS");
//		int i = authMappingService.addInfo(authMappingModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void updateInfo() throws Exception {
//		AuthMappingModel authMappingModel = new AuthMappingModel();
//		authMappingModel.setMappingId(92);
//		authMappingModel.setProjectId(2);
//		authMappingModel.setServerId(2);
//		authMappingModel.setRequestUrl("/test2/com");
//		authMappingModel.setCreatedUser("JSJS");
//		authMappingModel.setUpdatedUser("JSJS");
//		Integer i = authMappingService.updateInfo(authMappingModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void deleteInfo() throws Exception {
//		Integer i = authMappingService.deleteInfo(11);
//		logger.info("i:{}", i);
	}

	@Test
	public void getOneByMappingId() throws Exception {
		AuthMappingModel authMappingModel = authMappingService.getOneByMappingId(2);
		logger.info("authMappingModel:{}", authMappingModel);
	}

	@Test
	public void getOneByAuthMapping() throws Exception {
		AuthMappingModel authMappingModel = new AuthMappingModel();
		authMappingModel.setMappingId(2);
		authMappingModel.setProjectId(10);
		authMappingModel.setRequestType("CHANNEL");
		authMappingModel.setRequestUrl("ncm-service111");
		authMappingModel.setServerId(3);
		AuthMappingModel authMappingModelTemp = authMappingService.getOneByAuthMapping(authMappingModel);
		logger.info("authMappingModelTemp:{}", authMappingModelTemp);
	}

	@Test
	public void getOneAuthMappingByReqUrlAndAccountId() throws Exception {
		List<AuthMappingModel> lists = authMappingService
				.getAuthMappListByReqUrlAndAppId("4E97F8183439B7893251280E53170Cc6", "ncm-service");
		logger.info("authMappingModel:{}", lists);
	}

	@Test
	public void getOneByProjectIdAndServerId() throws Exception {
		AuthMappingModel authMappingModel = authMappingService.getOneByProjectIdAndServerId(10, 3);
		logger.info("authMappingModel:{}", authMappingModel);
	}

	@Test
	public void getOneByRequestTypeAndRequestUrl() throws Exception {
		AuthMappingModel authMappingModel = authMappingService.getOneByRequestTypeAndRequestUrl("CHANNEL",
				"ncm-service111");
		logger.info("authMappingModel:{}", authMappingModel);
	}

	@Test
	public void selectListsByAppid() throws Exception {
		List<InterfaceInfoDto> list = authMappingService.selectListsByAppid("4E97F8183439B7893251280E53170CC5");
		logger.info("list:{}", list);
	}

	@Test
	public void getMappAndServerByProId() throws Exception {
		List<AuthMappingDto> list = authMappingService.getMappAndServerByProId(1);
		logger.info("list:{}", list);
	}

	@Test
	public void deleteByProjectId() throws Exception {
//		int result = authMappingService.deleteByProjectId(10);
//		logger.info("result:{}", result);
	}

}
