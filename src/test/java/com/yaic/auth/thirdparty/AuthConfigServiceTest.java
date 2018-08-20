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
import com.yaic.auth.thirdparty.dto.DataSourceDto;
import com.yaic.auth.thirdparty.dto.ProjectDto;
import com.yaic.auth.thirdparty.model.AccountModel;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.service.AuthConfigService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class)
@WebAppConfiguration
public class AuthConfigServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AuthConfigServiceTest.class);
	@Autowired
	private AuthConfigService authConfigService;

	@Test
	public void getAccountTrees() throws Exception {
		AccountModel accountModel = new AccountModel();
		accountModel.setAccountName("中汇经纪");
		List<AccountModel> list = authConfigService.getAccountTrees(accountModel);
		logger.info("list:{}", list);
	}


	@Test
	public void getFullProjectInfo() throws Exception {
		ProjectModel model = new ProjectModel();
		model.setProjectId(1);
		ProjectDto projectDto = authConfigService.getFullProjectInfo(model);
		logger.info("projectDto:{}", projectDto);
	}

	@Test
	public void getFullDatasourceInfo() throws Exception {
		DataSourceModel model = new DataSourceModel();
		model.setDataSourceId(1);
		DataSourceDto dataSourceDto = authConfigService.getFullDatasourceInfo(model);
		logger.info("dataSourceDto:{}", dataSourceDto);
	}

	//@Test
	public void saveDatasourProject() throws Exception {
//		AuthBasicInfo authBasicInfo = new AuthBasicInfo();
//		authBasicInfo.setAddRole("datasource");
//		authBasicInfo.setAuthParam1("测试param");
//		authBasicInfo.setAuthType("测试type");
//		authBasicInfo.setDataSource("测试dataSource");
//		authBasicInfo.setEncryptParam1("测试encryptParam1");
//		authBasicInfo.setPrimaryId(21);
//		authBasicInfo.setEncryptType("1");
//		authBasicInfo.setProjectCode("测试projectCode");
//		authBasicInfo.setProjectName("测试projectName");
//		authBasicInfo.setSourceName("测试sourceName");
//		int i = authConfigService.saveDatasourProject(authBasicInfo);
//		logger.info("i:{}", i);
	}

}
