package com.yaic.auth.external;

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
import com.yaic.auth.external.service.ExternalConfigService;
import com.yaic.auth.thirdparty.dto.ServerDto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class)
@WebAppConfiguration
public class ExternalConfigServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ExternalConfigServiceTest.class);

	@Autowired
	ExternalConfigService configService;

	@Test
	public void generateAppid() throws Exception {
//		String uuid = configService.generateAppid();
//		logger.info("uuid:{}", uuid);
	}

	@Test
	public void getAccount() throws Exception {
//		String appid = "4E97F8183439B7893251280E53170CC5";
//		AccountDto accountDto = configService.getAccount(appid);
//		String accountJSON = JSON.toJSONString(accountDto);
//		logger.info("accountJSON:{}", accountJSON);
	}

	@Test
	public void getDatasource() throws Exception {
//		DataSourceModel dataSourceModel = new DataSourceModel();
////		dataSourceModel.setDataSourceId(1);
//		dataSourceModel.setDataSource("datasource_test3");
//		DataSourceDto dataSourceDto = configService.getDatasource(dataSourceModel);
//		logger.info("dataSourceDto:{}", dataSourceDto);
	}

	@Test
	public void getProject() throws Exception {
//		ProjectModel projectModel = new ProjectModel();
//		projectModel.setProjectId(1);
//		projectModel.setAuthId(27);
//		projectModel.setDataSourceId(1);
//		ProjectDto projectDto = configService.getProject(projectModel);
//		logger.info("projectDto:{}", projectDto);
	}

	@Test
	public void getAuthTypes() throws Exception {
		List<String> list = configService.getAuthTypes();
		logger.info("list:{}", list);
	}

	@Test
	public void getEncryptTypes() throws Exception {
		List<String> list = configService.getEncryptTypes();
		logger.info("list:{}", list);
	}

	@Test
	public void getServerEnvTypes() throws Exception {
		List<String> list = configService.getServerEnvTypes();
		logger.info("list:{}", list);
	}

	@Test
	public void getServerTypes() throws Exception {
//		List<String> list = configService.getServerTypes();
//		logger.info("list:{}", list);
	}

	@Test
	public void getServerSmallTypes() throws Exception {
//		List<String> list = configService.getServerSmallTypes("%");
//		logger.info("list:{}", list);
	}

	@Test
	public void getCallbackTypes() throws Exception {
		List<String> list = configService.getCallbackTypes();
		logger.info("list:{}", list);
	}

	@Test
	public void getServerList() throws Exception {
		List<ServerDto> list = configService.getServerList("DEV", "1","A");
		logger.info("list:{}", list);
	}

	@Test
	public void getInterfaceTypes() throws Exception {
		List<String> list = configService.getInterfaceTypes();
		logger.info("list:{}", list);
	}

	// @Test
	public void addProject() throws Exception {

	}

	// @Test
	public void editProject() throws Exception {

	}

	// @Test
	public void addAuthMappInfo() throws Exception {

	}

	// @Test
	public void addCallbackUrlInfo() throws Exception {

	}

	@Test
	public void deleteInfo() throws Exception{
	}

}
