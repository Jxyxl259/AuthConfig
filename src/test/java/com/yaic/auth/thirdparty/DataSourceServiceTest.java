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
import com.yaic.auth.thirdparty.dto.DataSourceViewDto;
import com.yaic.auth.thirdparty.model.DataSourceModel;
import com.yaic.auth.thirdparty.service.DataSourceService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class)
@WebAppConfiguration
public class DataSourceServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceServiceTest.class);

	@Autowired
	private DataSourceService dataSourceService;

	@Test
	public void getList() throws Exception {
		DataSourceModel dataSourceModel = new DataSourceModel();
		List<DataSourceModel> list = dataSourceService.getList(dataSourceModel);
		logger.info("dataSourceModel:{}", list);
	}

	@Test
	public void addInfo() throws Exception {
//		DataSourceModel dataSourceModel = new DataSourceModel();
//		dataSourceModel.setDataSource("CODEUPDATE" + System.currentTimeMillis());
//		dataSourceModel.setSourceName("NAMEUPDATE" + System.currentTimeMillis());
//		dataSourceModel.setAppId("2cd3026d7ff811e8957b005056b62655");
//		dataSourceModel.setCreatedUser("JSJS");
//		dataSourceModel.setUpdatedUser("JSJS");
//		int i = dataSourceService.addInfo(dataSourceModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void updateInfo() throws Exception {
//		DataSourceModel dataSourceModel = new DataSourceModel();
//		dataSourceModel.setDataSourceId(66);
//		dataSourceModel.setDataSource("dataSource66");
//		dataSourceModel.setSourceName("dataSource66");
//		dataSourceModel.setAppId("D87B29716B9A48A8B385E49E87EDFCTTT");
//		dataSourceModel.setAuthId(12);
//		dataSourceModel.setCreatedUser("JSJS");
//		dataSourceModel.setUpdatedUser("JSJS");
//		int i = dataSourceService.updateInfo(dataSourceModel);
//		logger.info(i + "dataSourceModel:{}", dataSourceModel);
	}

	@Test
	public void deleteInfo() throws Exception {
//		int result = dataSourceService.deleteInfo(59);
//		logger.info("result:{}", result);
	}

	@Test
	public void getOneByDataSourceCode() throws Exception {
		DataSourceModel dataSourceModel = dataSourceService.getOneByDataSourceCode("2datasource_code");
		logger.info("dataSourceModel:{}", dataSourceModel);
	}

	@Test
	public void getOneByDataSourceId() throws Exception {
		DataSourceModel dataSourceModel = dataSourceService.getOneByDataSourceId(1);
		logger.info("dataSourceModel:{}", dataSourceModel);
	}

	@Test
	public void getOneByDataSource() throws Exception {
		DataSourceModel dataSourceModel = new DataSourceModel();
		dataSourceModel.setDataSourceId(19);
		dataSourceModel.setDataSource("19datasource_code");
		dataSourceModel.setSourceName("19渠道名称");
		dataSourceModel.setAppId(String.valueOf(100007));
		DataSourceModel dataSourceModelTemp = dataSourceService.getOneByDataSource(dataSourceModel);
		logger.info("dataSourceModelTemp:{}", dataSourceModelTemp);
	}

	@Test
	public void deleteInfoByIds() throws Exception {
//		List<String> ids = new ArrayList<String>();
//		ids.add("55");
//		ids.add("56");
//		dataSourceService.deleteInfoByIds(ids);
//		logger.info("success");
	}

	@Test
	public void getDataSourceView() throws Exception {
		DataSourceModel dataSourceModel = new DataSourceModel();
//		dataSourceModel.setSourceName("datas");
//		dataSourceModel.setDataSource("stDat");
		List<DataSourceViewDto> list = dataSourceService.getDataSourceView(dataSourceModel);
		logger.info("list:{}", list);
	}

}
