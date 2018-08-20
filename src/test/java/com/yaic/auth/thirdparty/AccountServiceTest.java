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
import com.yaic.auth.external.service.ExternalConfigService;
import com.yaic.auth.thirdparty.model.AccountModel;
import com.yaic.auth.thirdparty.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class)
@WebAppConfiguration
public class AccountServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);
	@Autowired
	private AccountService accountService;
	@Autowired
	ExternalConfigService configService;

	@Test
	public void getList() throws Exception {
		AccountModel accountModel = new AccountModel();
		List<AccountModel> list = accountService.getList(accountModel);
		logger.info("list:{}", list);
	}

	@Test
	public void getOneByAccount() throws Exception {
		AccountModel accountModel = new AccountModel();
		accountModel.setAccountName("测试AccountName1");
		accountModel.setMobile("13333333333");
		accountModel.setCreatedUser("JSJS");
		accountModel.setUpdatedUser("JSJS");
		AccountModel accountModelTemp = accountService.getOneByAccount(accountModel);
		logger.info("accountModelTemp:{}", accountModelTemp);
	}

	@Test
	public void getOneByAppid() throws Exception {
		AccountModel accountModelTemp = accountService.getOneByAppid("2D36ABB396FC4B9C855C8575F35ECEEB");
		logger.info("accountModelTemp:{}", accountModelTemp);
	}

	@Test
	public void getOneByAppCode() throws Exception {
		AccountModel accountModelTemp = accountService.getOneByAppCode("138100004Code");
		logger.info("accountModelTemp:{}", accountModelTemp);
	}
	
	@Test
	public void getOneByAccountId() throws Exception {
		AccountModel accountModelTemp = accountService.getOneByAccountId(16);
		logger.info("accountModelTemp:{}", accountModelTemp);
	}

	@Test
	public void addInfo() throws Exception {
//		AccountModel accountModel = new AccountModel();
//		accountModel.setAppId(configService.generateAppid());
//		accountModel.setAccountName("NAME5");
//		accountModel.setAppCode("CODE6");
//		accountModel.setMobile("13812345678");
//		accountModel.setValidFlag(PublicParamters.VALID_FLAG_Y);
//		accountModel.setCreatedUser("JSJS");
//		accountModel.setUpdatedUser("JSJS");
//		int result = accountService.addInfo(accountModel);
//		logger.info("result:{}", result);
	}

	@Test
	public void updateInfo() throws Exception {
//		AccountModel accountModel = new AccountModel();
//		accountModel.setAccountId(39);
//		accountModel.setAppId("2D36ABB396FC4B9C855C8575F35ECEQA");
//		accountModel.setAccountName("测试updataName5");
//		accountModel.setAppCode("AppCode5");
//		accountModel.setMobile("133123456781");
//		accountModel.setValidFlag(PublicParamters.VALID_FLAG_Y);
//		accountModel.setUpdatedUser("JSJS");
//		int result = accountService.updateInfo(accountModel);
//		logger.info("result:{}", result);
	}

	@Test
	public void deleteInfo() throws Exception {
//		int result = accountService.deleteInfo(1);
//		logger.info("result:{}", result);
	}

	@Test
	public void deleteInfoByIds() throws Exception {
//		List<String> ids = new ArrayList<String>();
//		ids.add("36");
//		ids.add("37");
//		int result = accountService.deleteInfoByIds(ids);
//		logger.info("result:{}", result);
	}

}
