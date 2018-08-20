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
import com.yaic.auth.thirdparty.model.ServerModel;
import com.yaic.auth.thirdparty.service.ServerService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class)
@WebAppConfiguration
public class ServerServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ServerServiceTest.class);
	@Autowired
	private ServerService serverService;

	@Test
	public void getList() throws Exception {
		ServerModel serverModel = new ServerModel();
		List<ServerModel> list = serverService.getList(serverModel);
		logger.info("list:{}", list);
	}

	@Test
	public void addInfo() throws Exception {
//		ServerModel serverModel = new ServerModel();
//		serverModel.setServerType("Type2");
//		serverModel.setServerVersion("Version2");
//		serverModel.setServerEnv("Env2");
//		serverModel.setServerStatus("2");
//		serverModel.setServerUrl("Url2");
//		serverModel.setCreatedUser("JSJS");
//		serverModel.setUpdatedUser("JSJS");
//		int i = serverService.addInfo(serverModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void updateInfo() throws Exception {
//		ServerModel serverModel = new ServerModel();
//		serverModel.setServerId(23);
//		serverModel.setServerType("Type3");
//		serverModel.setServerVersion("Version3");
//		serverModel.setServerEnv("Env3");
//		serverModel.setServerStatus("3");
//		serverModel.setServerUrl("Url3");
//		serverModel.setCreatedUser("JSJS");
//		serverModel.setUpdatedUser("JSJS");
//		int i = serverService.updateInfo(serverModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void deleteInfo() throws Exception {
//		int i = serverService.deleteInfo(22);
//		logger.info("i:{}", i);
	}

	@Test
	public void getOneByServerId() throws Exception {
		ServerModel serverModel = serverService.getOneByServerId(1);
		logger.info("serverModel:{}", serverModel);
	}

	@Test
	public void getOneByServer() throws Exception {
		ServerModel serverModel = new ServerModel();
		serverModel.setServerId(1);
		serverModel.setSystemName("2华道接口1");
		serverModel.setServerEnv("DEV");
		serverModel.setServerVersion("1.1.012");
		serverModel.setServerType("1");
		serverModel.setServerStatus("2");
		serverModel.setServerUrl("2http://10.1.135.35:8081/ncm/api/ncmInfo/ncm-service.do");
		ServerModel serverModelTemp = serverService.getOneByServer(serverModel);
		logger.info("serverModelTemp:{}", serverModelTemp);
	}

	@Test
	public void getOneByServerUrl() throws Exception {
		ServerModel serverModel = serverService
				.getOneByServerUrl("2http://10.1.135.35:8081/ncm/api/ncmInfo/ncm-service.do");
		logger.info("serverModel:{}", serverModel);
	}

	@Test
	public void deleteInfoByIds() throws Exception {
//		List<String> ids = new ArrayList<String>();
//		ids.add("8");
//		ids.add("9");
//		Integer i = serverService.deleteInfoByIds(ids);
//		logger.info("i:{}", i);
	}

}
