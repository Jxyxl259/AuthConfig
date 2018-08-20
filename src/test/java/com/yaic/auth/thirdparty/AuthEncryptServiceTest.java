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
import com.yaic.auth.thirdparty.model.AuthEncryptModel;
import com.yaic.auth.thirdparty.service.AuthEncryptService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Run.class)
public class AuthEncryptServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AuthEncryptServiceTest.class);
	@Autowired
	private AuthEncryptService authEncryptService;

	@Test
	public void getList() throws Exception {
		AuthEncryptModel authEncryptModel = new AuthEncryptModel();
		List<AuthEncryptModel> list = authEncryptService.getList(authEncryptModel);
		logger.info("list:{}", list);
	}

	@Test
	public void addInfo() throws Exception {
//		AuthEncryptModel authEncryptModel = new AuthEncryptModel();
//		authEncryptModel.setEncryptType("MD5_1");
//		authEncryptModel.setAuthParam1("authParam1");
//		authEncryptModel.setAuthParam2("authParam2");
//		authEncryptModel.setEncryptParam1("encryptParam1");
//		authEncryptModel.setEncryptParam2("encryptParam2");
//		authEncryptModel.setCreatedUser("JSJS");
//		authEncryptModel.setUpdatedUser("JSJS");
//		int i = authEncryptService.addInfo(authEncryptModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void updateInfo() throws Exception {
//		AuthEncryptModel authEncryptModel = new AuthEncryptModel();
//		authEncryptModel.setAuthId(50);
//		authEncryptModel.setAuthType("MD5");
//		authEncryptModel.setEncryptType("MD5_1");
//		authEncryptModel.setAuthParam1("authParam1");
//		authEncryptModel.setAuthParam2("authParam2");
//		authEncryptModel.setEncryptParam1("encryptParam1");
//		authEncryptModel.setEncryptParam2("encryptParam2");
//		authEncryptModel.setCreatedUser("JSJS");
//		authEncryptModel.setUpdatedUser("JSJS");
//		int i = authEncryptService.updateInfo(authEncryptModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void deleteInfo() throws Exception {
//		Integer i = authEncryptService.deleteInfo(35);
//		logger.info("i:{}", i);
	}

	@Test
	public void getOneByAuthId() throws Exception {
		AuthEncryptModel authEncryptModel = authEncryptService.getOneByAuthId(19);
		logger.info("authEncryptModel:{}", authEncryptModel);
	}

	@Test
	public void selectByAppid() throws Exception {
		List<AuthEncryptModel> lists = authEncryptService.selectByAppid("D87B29716B9A48A8B385E49E87EDFC3E");
		logger.info("lists:{}", lists);
	}

}
