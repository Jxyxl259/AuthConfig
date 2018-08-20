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
import com.yaic.auth.thirdparty.dto.CallbackUrlViewDto;
import com.yaic.auth.thirdparty.model.CallbackUrlModel;
import com.yaic.auth.thirdparty.service.CallbackUrlService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class)
@WebAppConfiguration
public class CallbackUrlServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(CallbackUrlServiceTest.class);
	@Autowired
	private CallbackUrlService callbackUrlService;

	@Test
	public void getList() throws Exception {
		CallbackUrlModel callbackUrlModel = new CallbackUrlModel();
		List<CallbackUrlModel> list = callbackUrlService.getList(callbackUrlModel);
		logger.info("list:{}", list);
	}

	@Test
	public void addInfo() throws Exception {
//		CallbackUrlModel callbackUrlModel = new CallbackUrlModel();
//		callbackUrlModel.setProjectId(1);
//		callbackUrlModel.setCallbackMethod("callbackMethod00001");
//		callbackUrlModel.setCallbackUrl("/test/CallbackUrl");
//		callbackUrlModel.setCallbackType("RETREATS");
//		callbackUrlModel.setCreatedUser("JSJS");
//		callbackUrlModel.setUpdatedUser("JSJS");
//		int i = callbackUrlService.addInfo(callbackUrlModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void updateInfo() throws Exception {
//		CallbackUrlModel callbackUrlModel = new CallbackUrlModel();
//		callbackUrlModel.setCallbackUrlId(79);
//		callbackUrlModel.setProjectId(2);
//		callbackUrlModel.setCallbackMethod("callbackMethod00002");
//		callbackUrlModel.setCallbackUrl("/tes2/CallbackUrl");
//		callbackUrlModel.setCallbackType("RETREATS2");
//		callbackUrlModel.setCreatedUser("JSJS");
//		callbackUrlModel.setUpdatedUser("JSJS");
//		int i = callbackUrlService.updateInfo(callbackUrlModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void deleteInfo() throws Exception {
//		int i = callbackUrlService.deleteInfo(1);
//		logger.info("i:{}", i);
	}

	@Test
	public void getOneByCallbackUrl() throws Exception {
		CallbackUrlModel callbackUrlModel = new CallbackUrlModel();
		callbackUrlModel.setCallbackUrlId(1);
		callbackUrlModel.setProjectId(1);
		callbackUrlModel.setCallbackType("PAY");
		CallbackUrlModel callbackUrlModelTemp = callbackUrlService.getOneByCallbackUrl(callbackUrlModel);
		logger.info("callbackUrlModelTemp:{}", callbackUrlModelTemp);
	}
	
	@Test
	public void deleteInfoByIds() throws Exception {
//		List<String> ids = new ArrayList<String>();
//		ids.add("57");
//		ids.add("58");
//		int i = callbackUrlService.deleteInfoByIds(ids);
//		logger.info("i:{}", i);
	}

	@Test
	public void deleteByProjectId() throws Exception {
//		int i = callbackUrlService.deleteByProjectId(1);
//		logger.info("i:{}", i);
	}

	@Test
	public void getCallbackUrlViewDto() throws Exception {
		CallbackUrlModel callbackUrlModel = new CallbackUrlModel();
		callbackUrlModel.setCallbackUrl("callbackUrl001");
		// callbackUrlModel.setCallbackType("Type001");
		// callbackUrlModel.setProjectId(16);
		List<CallbackUrlViewDto> list = callbackUrlService.getCallbackUrlViewDto(callbackUrlModel);
		logger.info("list:{}", list);
	}

}
