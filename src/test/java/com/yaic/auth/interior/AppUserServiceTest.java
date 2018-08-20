package com.yaic.auth.interior;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.yaic.auth.Run;
import com.yaic.auth.interior.model.AppUserModel;
import com.yaic.auth.interior.service.AppUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class)
@WebAppConfiguration
public class AppUserServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AppUserServiceTest.class);
	@Autowired
	private AppUserService appUserService;

	@Test
	public void addInfo() throws Exception {
//		AppUserModel appUserModel = new AppUserModel();
//		appUserModel.setUserId(UuidUtils.getUuid());
//		appUserModel.setCreatedBy("JSJS");
//		appUserModel.setUpdatedBy("JSJS");
//		appUserModel.setUserCode("userCode");
//		appUserModel.setUserTname("普通用户");
//		appUserModel.setUserCname("普通用户");
//		appUserModel.setUserEname("Common User");
//		appUserModel.setPassword("1234567qwe");
//		appUserModel.setCompanyCode("copyCode");
//		appUserModel.setMobile("12345678");
//		int i = appUserService.addInfo(appUserModel);
//		logger.info("i:{}", i);

	}

	@Test
	public void updateInfo() throws Exception {
//		AppUserModel appUserModel = new AppUserModel();
//		appUserModel.setUserId("B7163E81870B42ADBD5264");
//		appUserModel.setCreatedBy("JSJS1");
//		appUserModel.setUpdatedBy("JSJS1");
//		appUserModel.setUserCode("userCod1");
//		appUserModel.setUserTname("普通用户1");
//		appUserModel.setUserCname("普通用户1");
//		appUserModel.setUserEname("Common Use1");
//		appUserModel.setPassword("1234567qwe1");
//		appUserModel.setCompanyCode("copyCod1");
//		appUserModel.setMobile("123456781");
//		int i = appUserService.updateInfo(appUserModel);
//		logger.info("i:{}", i);

	}

	@Test
	public void deleteInfo() throws Exception {
//		List<AppUserModel> userList = new ArrayList<AppUserModel>();
//		AppUserModel appUserModel = new AppUserModel();
//		appUserModel.setUserCode("test");
//		userList.add(appUserModel);
//		int i = appUserService.deleteInfo(userList);
//		logger.info("i:{}", i);
	}

	@Test
	public void getInfoByUserCode() throws Exception {
		AppUserModel appUserModel = appUserService.getInfoByUserCode("aaaaa");
		logger.info("appUserModel:{}", appUserModel);
	}

	@Test
	public void selectByPrimaryKey() throws Exception {
		AppUserModel appUserModel = appUserService.selectByPrimaryKey("B7163E81870B42ADBD5264B235AEFDEF");
		logger.info("appUserModel:{}", appUserModel);
	}

	@Test
	public void getList() throws Exception {
		AppUserModel appUserModel = new AppUserModel();
		appUserModel.setPassword("0cf485fa058a05e09bdc1e5f1d0bf511b5180b9a");
		appUserModel.setUserCode("qqqq");
		appUserModel.setUserCname("普通用户");
		ArrayList<AppUserModel> list = appUserService.getList(appUserModel);
		logger.info("list:{}", list);
	}

	@Test
	public void deleteList() throws Exception {
//		List<AppUserModel> userList = new ArrayList<AppUserModel>();
//		AppUserModel userDto = new AppUserModel();
//		userDto.setUserCode("test");
//		userList.add(userDto);
////		int i = appUserService.deleteList(userList);
////		logger.info("i:{}", i);
	}

}
