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
import com.yaic.auth.thirdparty.dto.ProjectViewDto;
import com.yaic.auth.thirdparty.model.ProjectModel;
import com.yaic.auth.thirdparty.service.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class)
@WebAppConfiguration
public class ProjectServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ProjectServiceTest.class);

	@Autowired
	private ProjectService projectService;

	@Test
	public void getList() throws Exception {
		ProjectModel projectModel = new ProjectModel();
		List<ProjectModel> list = projectService.getList(projectModel);
		logger.info("list:{}", list);
	}

	@Test
	public void addInfo() throws Exception {
//		ProjectModel projectModel = new ProjectModel();
//		projectModel.setProjectCode("CODE123456789");
//		projectModel.setProjectName("NAME123456789");
//		projectModel.setAuthId(50);
//		projectModel.setDataSourceId(66);
//		projectModel.setIsDefault(1);
//		projectModel.setCreatedUser("JSJS");
//		projectModel.setUpdatedUser("JSJS");
//		int i = projectService.addInfo(projectModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void updateInfo() throws Exception {
//		ProjectModel projectModel = new ProjectModel();
//		projectModel.setProjectId(73);
//		projectModel.setProjectCode("CODE222456789");
//		projectModel.setProjectName("NAME22456789");
//		projectModel.setAuthId(50);
//		projectModel.setDataSourceId(65);
//		projectModel.setIsDefault(1);
//		projectModel.setCreatedUser("JSJS");
//		projectModel.setUpdatedUser("JSJS");
//		Integer i = projectService.updateInfo(projectModel);
//		logger.info("i:{}", i);
	}

	@Test
	public void deleteInfo() throws Exception {
//		Integer i = projectService.deleteInfo(12);
//		logger.info("i:{}", i);
	}

	@Test
	public void getOneByProjectId() throws Exception {
		ProjectModel projectModel = projectService.getOneByProjectId(12);
		logger.info("projectModel:{}", projectModel);
	}


	@Test
	public void getOneProject() throws Exception {
		ProjectModel projectModel = new ProjectModel();
		projectModel.setProjectId(12);
		projectModel.setProjectCode("12project_code");
		projectModel.setProjectName("12方案名称");
		projectModel.setIsDefault(1);
		ProjectModel projectModelTemp = projectService.getOneProject(projectModel);
		logger.info("projectModelTemp:{}", projectModelTemp);
	}

	@Test
	public void deleteInfoByIds() throws Exception {
//		List<String> ids = new ArrayList<String>();
//		ids.add("49");
//		ids.add("50");
//		Integer i = projectService.deleteInfoByIds(ids);
//		logger.info("i:{}", i);
	}

	@Test
	public void getDefaultProjectBySourceid() throws Exception {
		ProjectModel projectModel = projectService.getDefaultProjectBySourceid(1);
		logger.info("projectModel:{}", projectModel);
	}

	@Test
	public void selectProjectView() throws Exception {
		ProjectViewDto projectViewDto = new ProjectViewDto();
		projectViewDto.setProjectCode("project_code");
		projectViewDto.setProjectName("方案名称");
		projectViewDto.setIsDefault("1");
		List<ProjectViewDto> list = projectService.getProjectViewDto(projectViewDto);
		logger.info("list：{}", list);

	}

}
