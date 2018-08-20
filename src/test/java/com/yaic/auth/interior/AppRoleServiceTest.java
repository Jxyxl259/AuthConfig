package com.yaic.auth.interior;

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
import com.yaic.auth.interior.model.AppRoleModel;
import com.yaic.auth.interior.service.AppRoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class)
@WebAppConfiguration
public class AppRoleServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AppRoleServiceTest.class);
	@Autowired
	private AppRoleService appRoleService;

	@Test
	public void selectByPrimaryKey() throws Exception {
		AppRoleModel appRoleModel = appRoleService.selectByPrimaryKey("0001");
		logger.info("appRoleModel:{}", appRoleModel);
	}

	@Test
	public void saveRoleResource() throws Exception {
//		Map<String, Object> reqMap = new HashMap<String, Object>();
//		List<String> list = new ArrayList<String>();
//		list.add("1639");
//		list.add("1640");
//		reqMap.put("resourceIds", list);
//		reqMap.put("userId", "B7163E81870B42ADBD5264B235AEFDEF");
//		reqMap.put("roleId", "0002");
////		appRoleService.saveRoleResource(reqMap);
//		logger.info("end");
	}

	@Test
	public void getList() throws Exception {
		AppRoleModel appRoleModel = new AppRoleModel();
		appRoleModel.setRoleId("0");
		appRoleModel.setRoleName("A");
		List<AppRoleModel> list = appRoleService.getList(appRoleModel);
		logger.info("list:{}", list);
	}

	@Test
	public void addInfo() throws Exception{
//		AppRoleModel appRoleModel = new AppRoleModel();
//		String newRoleId = appRoleService.createRoleId();
//		appRoleModel.setRoleId(newRoleId);
//		appRoleModel.setRoleName("testName"+newRoleId);
//		appRoleModel.setCreatedBy("JSJS");
//		appRoleModel.setUpdatedBy("JSJS");
//		int i = appRoleService.addInfo(appRoleModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void updateInfo() throws Exception {
//		AppRoleModel appRoleModel = new AppRoleModel();
//		appRoleModel.setRoleId("aqwer12345");
//		appRoleModel.setRoleName("testName2");
//		appRoleModel.setCreatedBy("JSJS1");
//		appRoleModel.setUpdatedBy("JSJS1");
//		int i = appRoleService.updateInfo(appRoleModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void deleteByPrimaryKey() throws Exception {
//		int i = appRoleService.deleteByPrimaryKey("123456789");
//		logger.info("i:{}", i);
	}

}
