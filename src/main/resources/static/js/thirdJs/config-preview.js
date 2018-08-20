var sourceMap,loadData,value1="";
$(function(){
	
	$("#projectSelID").hide();
	loadSourceText();
	
	$("#accountDiv").hide();
	$("#sourceDiv").hide();
	$("#projectDiv").hide();
	
	
	$(".panel").Huifold({
		titCell:'.panel-header',
		mainCell:'.panel-body',
		type:1,
		trigger:'click',
		className:"selected",
		speed:"first",
	});
});

/*
 * 加载渠道搜索框
 */
function loadSourceText(){
	
	var availableTags = new Array();
	
	RestfulClient.post("/source/listAll",{},
	function(result) {

		sourceMap = result.dataList;//sources;
		$.each(result.dataList.sources, function( key, val ) {
			availableTags.push(val.dataSource+":"+val.sourceName);
		} );
		
		$( "#datasourceName" ).autocomplete({
		     source: availableTags
		});
	});
}

/*
 * 渠道搜索框失焦事件,加载方案下拉框与渠道默认信息DIV
 */
function loadProjectSelect(){
	
	var value1 = $("#datasourceName").val();
	loadData = { "isDefault":0,"sourceName":value1};
	
	if(value1.length > 0 && value1.indexOf(":") > 0){
		var valueId = sourceMap[value1];
		
		if(valueId != null && valueId > 0){
			loadData = { "isDefault":0,"dataSourceId":valueId};
			
			loadDatasourceInfo(valueId);
		}
		
		$("#projectSelID").show();
		$("#projectSelID").empty();
		$("#projectSelID").append("<option style='width: 60px;' value=''>----请选择----</option>");
		
		RestfulClient.post("/project/list",loadData,function(result) {
			if(result.dataList.length > 0){
				
				$.each(result.dataList, function( key, val ) {
					$("#projectSelID").append("<option value='"+val.projectId+"'>"+val.projectCode+":"+val.projectName+"</option>");
				});
			}else{
				alert("该渠道下暂无所属方案");
			}
			
		});
	}else{
		alert("请在加载出的渠道中选择");
		$("#datasourceName").val("");
		//清空project下拉框内容
		$("#projectSelID").empty();
		//清空所有ul内容
		$(".account_wrap ul").remove();
		//DIV隐藏
		$("#accountDiv").hide();
		$("#sourceDiv").hide();
		$("#projectDiv").hide();
	}
	
}

/*
 * 加载渠道默认信息
 */
function loadDatasourceInfo(valueId){
	
	//清空所有ul内容
	$(".account_wrap ul").remove();
	//隐藏方案信息
	$("#projectDiv").hide();
	
	RestfulClient.post("/configview/getDatasourceInfos",{
		"dataSourceId":valueId
	},function(result) {

		sourceBasicInfo = result.dataList;

		var accountinfo = sourceBasicInfo.accountDto;
		projectinfos = sourceBasicInfo.projectList;
		
		//账户信息
		loadAccountinfo(accountinfo);
		//渠道信息
		loadDatasourceinfo(sourceBasicInfo);
		
		$("#accountDiv").show();
		$("#sourceDiv").show();
		
		if(projectinfos){

			//鉴权信息
			authencryptinfo = projectinfos[0].authEncryptDto;
			if(authencryptinfo != null)	loadAuthEncryptInfo(authencryptinfo,"sourceEncrpty");
			//回调信息
			callbackinfo = projectinfos[0].callbackList;
			if(callbackinfo != null)	loadCallbackInfo(callbackinfo,"sourceCallback");
			//方案服务关系信息
			authmappinfo = projectinfos[0].authmappList;
			if(authmappinfo != null)	loadAuthMappInfo(authmappinfo,"sourceServer");
		}
	});
}

/*
 * 加载方案所有信息
 */
function loadProjectInfo(){
	
	var value2 = $("#projectSelID").val();
	if(value2.length > 0){
		
		$("#projectDiv").show();
		
		//方案内容清空
		$("#projectBase ul").remove();
		$("#projectEncrpty ul").remove();
		$("#projectServer ul").remove();
		$("#projectCallback ul").remove();
		
		RestfulClient.post("/configview/getProjectInfos",{
			"projectId" : value2
		},function(result) {
			
			projectinfos = result.dataList;
			
			if(projectinfos){

				//方案信息
				loadProjectinfo(projectinfos);
				//鉴权信息
				authencryptinfo = projectinfos.authEncryptDto;
				if(authencryptinfo != null)	loadAuthEncryptInfo(authencryptinfo,"projectEncrpty");
				//回调信息
				callbackinfo = projectinfos.callbackList;
				if(callbackinfo != null)	loadCallbackInfo(callbackinfo,"projectCallback");
				//方案服务关系信息
				authmappinfo = projectinfos.authmappList;
				if(authmappinfo != null)	loadAuthMappInfo(authmappinfo,"projectServer");
			}
		});
	}else{
		
		$("#projectDiv").hide();
	}
	
}

/*
 * 加载账户信息
 */
function loadAccountinfo(accountinfo){

	var info6 = $("#accountBasicUl").html();
	info66 = info6.replace("s61",$.trim(accountinfo.appId)).replace("s62",$.trim(accountinfo.appCode))
			.replace("s63",$.trim(accountinfo.accountName)).replace("s64",$.trim(accountinfo.mobile))
			.replace("s65",$.trim(accountinfo.email));
	$("#accountBase").append(info66);
	
}

/*
 * 加载渠道信息
 */
function loadDatasourceinfo(sourceInfo){
	
	var info5 = $("#sourceBasicUl").html();
	info55 = info5.replace("s51",$.trim(sourceInfo.dataSource)).replace("s52",$.trim(sourceInfo.sourceName));
	$("#sourceBase").append(info55);
}

/*
 * 加载方案信息
 */
function loadProjectinfo(projectinfos){
	
	var info4 = $("#projectBasicUl").html();
	info44 = info4.replace("s41",$.trim(projectinfos.projectCode)).replace("s42",$.trim(projectinfos.projectName));
	$("#projectBase").append(info44);
}

/*
 * 加载鉴权信息
 */
function loadAuthEncryptInfo(authencryptinfo,divName){
	
	var info3 = $("#sourceEncrptyUl").html();
	info33 = info3.replace("s11",$.trim(authencryptinfo.authType)).replace("s12",$.trim(authencryptinfo.authParam1))
			.replace("s13",$.trim(authencryptinfo.authParam2)).replace("s14",$.trim(authencryptinfo.encryptType))
			.replace("s15",$.trim(authencryptinfo.encryptParam1)).replace("s16",$.trim(authencryptinfo.encryptParam2));
	$("#"+divName).append(info33);
}

/*
 * 加载回调信息
 */
function loadCallbackInfo(callbackinfo,divName){

	var info2 = $("#sourceCallbackUl").html();
	$.each(callbackinfo, function( key, val ) {
		
		info22 = info2.replace("s4",$.trim(val.callbackEnv)).replace("s5",$.trim(val.callbackType)).replace("s6",$.trim(val.callbackUrl));
		$("#"+divName).append(info22);
	});
}

/*
 * 加载服务关系信息
 */
function loadAuthMappInfo(authmappinfo,divName){
	
	var info1 = $("#sourceServerUl").html();
	$.each(authmappinfo, function( key, val ) {
		
		info11 = info1.replace("s1",$.trim(val.serverEnv)).replace("s2",$.trim(val.serverSmallType)).replace("s3",$.trim(val.serverUrl));
		$("#"+divName).append(info11);
	});
}

