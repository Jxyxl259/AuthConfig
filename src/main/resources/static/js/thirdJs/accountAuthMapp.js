var data,serverEnvTypes,serverTypes,serverStatus,interfaceTypes,serverMappingSmall,authTypes, encryptTypes;

$(function() {
	
	urlPath = "/accountAuth";
    // 数据初始化
	selectinfo();
	
	//加载弹出框 下拉列表option
	selectBoxInfo();
})


/** 列表搜索 */
$('#selectInfo').on( 'click', function () {
	selectinfo();
});

function selectinfo(){
	searchParams = { 
		"pageSize":lastPageSize,
		"serverEnv" : $.trim($("#serverEnvSel").val()), 
		"serverType" : $.trim($("#serverSmallTypeSel").val()),
		"serverStatus" : $.trim($("#serverStatusSel").val()),
		"requestType" : $.trim($("#requestTypeSel").val()),
		"requestUrl" : $.trim($("#requestUrlSel").val())
	};
	commonObj.tabInit("#tableId");
}

var columsName = {
	"selectAll":"<input type='checkbox' name='checkAll' value=''>",
	"number":"编号",
	"serverEnv": "环境类型",
	"requestType": "接口类型",
	"requestUrl": "接口地址",
	"serverTypeStr": "服务类型",
    "serverStatusStr": "服务状态",
   	"accountId": "操作"
};

var columns = [
	{"data": "mappingId", "title": columsName["number"],"width":"5%"},
	{"data": "mappingId", "title": columsName["number"],"width":"5%"},
	{"data": "serverEnv", "title": columsName["serverEnv"],"width":"8%"},
	{"data": "requestType", "title": columsName["requestType"],"width":"17%"},
	{"data": "requestUrl", "title": columsName["requestUrl"],"width":"17%"},
	{"data": "serverTypeStr", "title": columsName["serverTypeStr"],"width":"20%"},
	{"data": "serverStatusStr", "title": columsName["serverStatusStr"],"width":"8%"},
	{"data": "mappingId", "title": columsName["accountId"],"width":"17%"},
	{"data": "mappingId", "title": columsName["ceshi"],"width":"1%","sClass":"hidden"}
];

commonObj.init();	

/*
 * 根据操作类型展示弹出框内容
 */
function showLayerByType(id,type){
	
	$("#fromTemplate").show();
	$("#us").removeClass("detail_table");
	$("#fromTemplate select").removeAttr("disabled"); 
	
	$(".hisEncrptyShow").show();
	$("#authTypeSelect").val('');
	$("#authParam1").val('');
	$("#authParam2").val('');
	$("#encryptTypeSelect").val('');
	$("#encryptParam1").val('');
	$("#encryptParam2").val('');
	$(".hisEncrptyShow").hide();
	
	if(type == "edit" || type == "detail"){
		
		RestfulClient.post("/accountAuth/getInfo", {
			"mappingId": id
		},
		function(result) {

			data = result.dataList;

			if(data[1].authType != null && data[1].authType.length > 0){
				showAuthEncrptyInfo(data[1]);
			}
			
			if(data[0].requestType == 'HISTORY_ENCRYPT'){
				$(".hisEncrptyShow").show();
			}
			
			if(type == "edit"){
				
				$(".disableY").removeAttr("readonly");
				$("#authMappAuthId").val(data[1].authId);
			}else if(type == "detail"){
				
				id = -1;
				$(".disableY").attr("readonly","readonly");
				
				$(".detailShow").show();
				$("#us").addClass("detail_table");
				
				$("#createdUser").val(data[0].createdUser);
				$("#createdDate").val(data[0].createdDate);
				$("#updatedUser").val(data[0].updatedUser);
				$("#updatedDate").val(data[0].updatedDate);
			}
			
			$("#serverEnvSelect").val(data[0].serverEnv);
			sServerType = data[0].serverType;
			if(sServerType != null && sServerType.length >0){
				$("#serverTypeSelect").val(sServerType.substring(0,2));
			}
			$("#serverSmallTypeSelect").val(data[0].serverType);
			$("#requestTypeSelect").val(data[0].requestType);
//			$("#serverStatusSelect").val(data[0].serverStatus);
			$("#requestUrl").val(data[0].requestUrl);
			
			showServerLists(data[0].serverId,type);
			
			if(type == "detail"){
				$("#fromTemplate select").attr("disabled","disabled"); 
			}else{
				changeAuthType();
				
				changeEncryptType();
			}
			
			UserCommon.addInfos(event,id,type);
		});
	}else{
		id = 0;
		$(".disableY").removeAttr("readonly").val("");
		$('select').prop('selectedIndex', 0);
		showSmallTypes(id,type);
		
		UserCommon.addInfos(event,id,type);
	}
}

function showAuthEncrptyInfo(data){

	$("#authTypeSelect").val(data.authType);
	$("#authParam1").val(data.authParam1);
	$("#authParam2").val(data.authParam2);
	$("#encryptTypeSelect").val(data.encryptType);
	$("#encryptParam1").val(data.encryptParam1);
	$("#encryptParam2").val(data.encryptParam2);
}
	
var UserCommon = function(){
    return {
    	addInfos : function(event,cid,type){
	        layer.open({
	            type : 1,
	            fixed : true, // 不固定
	            shadeClose : false, // 点击遮罩关闭层
	            title : [showTitle(cid) ],
	            area : [ '680px', '470px' ],
	            content :$("#fromTemplate") ,
	            btn:[showBtn(cid)],
	            yes : function(index, dom){
	            	
	            	if(cid >= 0){	//0 add -1 detail 1edit
	                	
	                	var returnMessage = checkParams();
//	                	alert("returnMessage-----"+returnMessage);
	                	if(returnMessage.length == 0){
	                		
	                		RestfulClient.post("/authmapping/changeInfo", 
        					{
        						"mappingId" : cid,
        						"requestType" : $("#requestTypeSelect").val(),
        						"requestUrl" : $("#requestUrl").val(),
        						"serverId" : $("#serverListSelect").val(),
        						"reqType": type
        					},
            				function(result) {
        						if(result.resultCode > 0){
        							if(type == "add"){
        								$("#authMappAuthId").val("");
        							}
        							cid = result.resultCode;
        							if('HISTORY_ENCRYPT' == $("#requestTypeSelect").val()){
        								deditAuthData = { 
            	        						"authId":$("#authMappAuthId").val(),
            	        						"appId":cid,"encryptType":$("#encryptTypeSelect").val(),
            	        						"encryptParam1":$("#encryptParam1").val(),"encryptParam2":$("#encryptParam2").val(),
            	        						"reqType":type+":authMapp",
            	        						"authType":$("#authTypeSelect").val(),
            	        						"authParam1":$("#authParam1").val(),"authParam2":$("#authParam2").val()};
            							//添加鉴权数据
            							RestfulClient.post("/authEncrypt/changeInfo",deditAuthData,
    		            				function(result1) {
            								alert(result1.resultMsg);
            								layer.close(index);
                        					selectinfo();
    		        					});
        							}else{
        								alert(result.resultMsg);
        								layer.close(index);
                    					selectinfo();
        							}
        						}else{
        							alert(result.resultMsg);	
        						}
            				});
	                	}
	                }else{
	                	layer.close(index);
	                }
	            },
	            end : function() {
	            	$(".detailShow").hide();
	            	$(".tdAfterShowInfo").remove();
	            }
	        });
        }
    }
}();

function selectBoxInfo(){
	
	RestfulClient.post("/innerconfig/getAllTypesInfo", {}, 
		 function(result) {
			maps = result.dataList;
			serverEnvTypes = maps.serverEnvTypes;			//获取环境类型列表
			serverTypes = maps.serverTypes;					//获取服务类型
			serverStatus = maps.serverStatus;				//获取服务状态
			interfaceTypes = maps.interfaceTypesSustom;		//获取渠道对应的特定接口类型
			serverMappingSmall = maps.serverMappingSmall;	//获取所有服务小类
			authTypes = maps.authTypes;					//获取鉴权类型
			encryptTypes = maps.encryptTypes;			//获取加密类型
			
			for(var i in serverEnvTypes){
				$("#serverEnvSel").append("<option value='"+serverEnvTypes[i]+"'>"+serverEnvTypes[i]+"</option>");
				$("#serverEnvSelect").append("<option value='"+serverEnvTypes[i]+"'>"+serverEnvTypes[i]+"</option>");
			}
			for(var i in serverTypes){
//				$("#serverTypeSel").append("<option value='"+serverTypes[i][0]+"'>"+serverTypes[i][1]+"</option>");
				$("#serverTypeSelect").append("<option value='"+serverTypes[i][0]+"'>"+serverTypes[i][1]+"</option>");
			}
			for(var i in serverMappingSmall){
				$("#serverSmallTypeSel").append("<option value='"+serverMappingSmall[i][0]+"'>"+serverMappingSmall[i][1]+"</option>");
				$("#serverSmallTypeSelect").append("<option value='"+serverMappingSmall[i][0]+"'>"+serverMappingSmall[i][1]+"</option>");
			}
			for(var i in serverStatus){
				$("#serverStatusSel").append("<option value='"+serverStatus[i][0]+"'>"+serverStatus[i][1]+"</option>");
//				$("#serverStatusSelect").append("<option value='"+serverStatus[i][0]+"'>"+serverStatus[i][1]+"</option>");
			}
			for(var i in interfaceTypes){
				$("#requestTypeSel").append("<option value='"+interfaceTypes[i]+"'>"+interfaceTypes[i]+"</option>");
				$("#requestTypeSelect").append("<option value='"+interfaceTypes[i]+"'>"+interfaceTypes[i]+"</option>");
			}
			for(var i in authTypes){
				$("#authTypeSelect").append("<option value='"+authTypes[i][0]+"'>"+authTypes[i][1]+"</option>");
			}
			for(var i in encryptTypes){
				$("#encryptTypeSelect").append("<option value='"+encryptTypes[i]+"'>"+encryptTypes[i]+"</option>");
			}
			
 		}
	);
}

/*
 * 改变服务类型,服务小类与服务列表随之改变
 */
function showSmallTypes(mapId,type){

	$.ajax({
		url:"/innerconfig/getServerSmallTypes",
		type:"POST",
		data:{"serverType":$("#serverTypeSelect").val()},
		dataType:"json",  
		success:function(result){

			serverSmallTypes=result.dataList;
			$("#serverSmallTypeSelect").empty();
			$("#serverListSelect").empty();
			for (var i = 0 ; i < serverSmallTypes.length; i++) {
				$("#serverSmallTypeSelect").append("<option value='"+serverSmallTypes[i][0]+"'>"+serverSmallTypes[i][1]+"</option>");
			}

			showServerLists(mapId,type);
		}
	})
}

/*
 * 改变服务小类或服务类型或者服务环境,加载服务列表数据
 */
function showServerLists(mapId,type){
	
	var type1 = $("#serverTypeSelect").val(),type2 = $("#serverSmallTypeSelect").val(),type3 = $("#serverEnvSelect").val();
	if(type1 != null && type1.length > 0 
			&&  type2 != null && type2.length > 0
			&&  type3 != null && type3.length > 0){
		$.ajax({
			url:"/innerconfig/getServerList",
			type:"POST",
			data:{
				"serverEnv":type3,
				"serverType":type1,
				"serverSmallType":type2
			},
			success:function(result){
				serverLists=result.dataList;
				$("#serverListSelect").empty();
				for (var i = 0 ; i < serverLists.length; i++) {
					$("#serverListSelect").append("<option value='"+serverLists[i].serverId+"'>"+serverLists[i].serverUrl+"</option>");
				}
				
				if(!isNaN(mapId) && mapId > 0){
					$("#serverListSelect").val(mapId);
				}
			}
		})
	}
}

/*
 * 接口类型为 HISTORY_ENCRYPT,显示鉴权信息
 */
function changeRequestType(){
	
	var slectedVal = $("#requestTypeSelect").val();
	if(slectedVal == "HISTORY_ENCRYPT"){
		$(".hisEncrptyShow").show();
		$("#encryptParam1").attr("disabled","disabled"); 
		$("#encryptParam2").attr("disabled","disabled"); 
	}else{
		$(".hisEncrptyShow").hide();
	}
}

/*
 * 鉴权类型为COMMON,需在鉴权参数文本框后添加 自动生成appId按钮
 * 新增的时候添加 按钮,
 */
function changeAuthType(){
	var slectedVal = $("#authTypeSelect").val();
	var paramValue = $("#authParam1").val();
	
	if(slectedVal == "COMMON_AUTH"){
		$("#authParam1").val(genAppId(2));
		$("#authParam2").val(genAppId(3));
		var genButton1 = "<input type='button' id='genAppid1' name='genAppid1' class='btn btn-primary btn_Edit' onclick='genAppId(2);' value='生成鉴权参数'>";
		$("#one").html(genButton1);
		var genButton2 = "<input type='button' id='genAppid2' name='genAppid2' class='btn btn-primary btn_Edit' onclick='genAppId(3);' value='生成鉴权参数'>";
		$("#two").html(genButton2);
	}else{
		$("#genAppid1").remove();
		$("#genAppid2").remove();
		$("#authParam1").val(""); 
		$("#authParam2").val(""); 
	}
}

/*
 * 生成APPID
 */
function genAppId(type){
	RestfulClient.post("/authconfig/getNewAppid", {},
	function(result) {
		data = result.data;
		if(type == 1)	$("#editAcctAppid").val(data);
		else if(type == 2)	$("#authParam1").val(data);
		else if(type == 3)	$("#authParam2").val(data);
	});
}

/*
 * 加密类型为 NO-ENCRTPT,需将加密参数置为不可输入状态,并在文本框后添加说明
 */
function changeEncryptType(){
	var slectedVal = $("#encryptTypeSelect").val();
	var paramValue1 = $("#encryptParam1").val();
	var paramValue2 = $("#encryptParam2").val();
	if(slectedVal == "NO_ENCRYPT"){
		$("#encryptParam1").val(""); 
		$("#encryptParam2").val(""); 
		$("#encryptParam1").attr("disabled","disabled"); 
		$("#encryptParam2").attr("disabled","disabled"); 
	}else{
		$("#encryptParam1").removeAttr("disabled","disabled"); 
		$("#encryptParam1").val(paramValue1); 
		$("#encryptParam2").removeAttr("disabled","disabled"); 
		$("#encryptParam2").val(paramValue2); 
	}
}
