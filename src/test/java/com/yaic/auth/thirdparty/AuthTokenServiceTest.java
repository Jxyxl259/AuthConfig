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
import com.yaic.auth.thirdparty.model.AuthTokenModel;
import com.yaic.auth.thirdparty.service.AuthTokenService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class)
@WebAppConfiguration
public class AuthTokenServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenServiceTest.class);
	@Autowired
	private AuthTokenService authTokenService;
//	@Autowired
//	private ExternalConfigService externalConfigService;

	@Test
	public void getList() throws Exception {
		AuthTokenModel authTokenModel = new AuthTokenModel();
		List<AuthTokenModel> list = authTokenService.getList(authTokenModel);
		logger.info("list:{}", list);
	}

	@Test
	public void addInfo() throws Exception {
//		AuthTokenModel authTokenModel = new AuthTokenModel();
//		authTokenModel.setAppId("B7163E81870B42ADBD5264B235AEFDEF");
//		authTokenModel.setAppSecret(UuidUtils.getUuid());
//		authTokenModel.setOpenId(UuidUtils.getUuid());
//		authTokenModel.setAccessToken(UuidUtils.getUuid());
//		authTokenModel.setRefreshToken(UuidUtils.getUuid());
//		authTokenModel.setCreatedUser("JSJS");
//		authTokenModel.setUpdatedUser("JSJS");
//		int i = authTokenService.addInfo(authTokenModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void updateInfo() throws Exception {
//		AuthTokenModel authTokenModel = new AuthTokenModel();
//		authTokenModel.setTokenId(25);
//		authTokenModel.setAppSecret(String.valueOf(25));
//		authTokenModel.setOpenId(String.valueOf(25));
//		authTokenModel.setAccessToken(String.valueOf(25));
//		authTokenModel.setRefreshToken(String.valueOf(25));
//		authTokenModel.setCreatedUser("JSJS");
//		authTokenModel.setUpdatedUser("JSJS");
//		Integer i = authTokenService.updateInfo(authTokenModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void deleteInfo() throws Exception {
//		int i = authTokenService.deleteInfo(19);
//		logger.info("i:{}", i);
	}

	@Test
	public void getOneByAppIdAndAppSecret() throws Exception {
		AuthTokenModel authTokenModelTemp = authTokenService
				.getOneByAppIdAndAppSecret("4E97F8183439B7893251280E53170CC5", "A44B23ED115D2DF83687C729D34D37B3");
		logger.info("authTokenModelTemp:{}", authTokenModelTemp);
	}

	@Test
	public void deleteInfoByIds() throws Exception {
//		List<String> tokenIds = new ArrayList<String>();
//		tokenIds.add("2");
//		tokenIds.add("3");
//		int i = authTokenService.deleteInfoByIds(tokenIds);
//		logger.info("i:{}", i);
	}

	@Test
	public void getOneByTokenId() throws Exception {
		AuthTokenModel authTokenModelTemp = authTokenService.getOneByTokenId(2);
		logger.info("authTokenModelTemp:{}", authTokenModelTemp);
	}

}
