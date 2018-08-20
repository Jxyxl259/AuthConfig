var data;

$(function() {
	
	urlPath = "/server";
	//操作列不显示按钮
	showDeleteButton = false;
    // 数据初始化
	selectinfo();
	//加载弹出框 下拉列表option
	selectBoxInfo();
	
//	selectBoxStatus();
})

/** 列表搜索 */
$('#selectInfo').on( 'click', function () {
	selectinfo();
});

function selectinfo(){
	searchParams = { 
		"pageSize":lastPageSize,
		"serverType" : $.trim($("#serverTypeSel").val()), 
		"serverVersion" : $.trim($("#serverVersionSel").val()),
		"serverEnv" : $.trim($("#serverEnvSel").val()), 
		"systemName" : $.trim($("#systemNameSel").val()), 
		"serverUrl" : $.trim($("#serverUrlSel").val())
	};
	commonObj.tabInit("#tableId");
}

var serverEnvTypes,serverTypes,serverStatus;
function selectBoxInfo(){
	RestfulClient.post("/innerconfig/getAllTypesInfo", {}, 
	function(result) {
		maps = result.dataList;
		serverEnvTypes = maps.serverEnvTypes;		//获取环境类型列表
		serverSmallTypes = maps.serverMappingSmall;				//获取服务类型
		serverStatus = maps.serverStatus;				//获取服务状态
		
		if(serverSmallTypes.length > 0){
			for(var i=0;i< serverSmallTypes.length;i++){
				$("#serverTypeSelect").append("<option value='"+serverSmallTypes[i][0]+"'>"+serverSmallTypes[i][1]+"</option>");
				$("#serverTypeSel").append("<option value='"+serverSmallTypes[i][0]+"'>"+serverSmallTypes[i][1]+"</option>");
			}
		}
		if(serverEnvTypes.length > 0){
			for(var i=0;i< serverEnvTypes.length;i++){
				$("#serverEnvSelect").append("<option value='"+serverEnvTypes[i]+"'>"+serverEnvTypes[i]+"</option>");
				$("#serverEnvSel").append("<option value='"+serverEnvTypes[i]+"'>"+serverEnvTypes[i]+"</option>");
			}
		}
		if(serverStatus.length > 0){
			for(var i=0;i< serverStatus.length;i++){
				$("#serverStatusSelect").append("<option value='"+serverStatus[i][0]+"'>"+serverStatus[i][1]+"</option>");
			}
		}
	});
}

var columsName = {
	"selectAll":"<input type='checkbox' name='checkAll' value=''>",
	"number":"编号",
	"serverEnv": "环境类型",
	"serverType":"服务类型",
	"serverStatus": "服务状态",
	"serverVersion": "服务版本号",
	"systemName": "系统名称",
	"serverUrl":"服务地址",
	"extn": "操作"
};
var columns = [
	{"data": "serverId", "title": columsName["selectAll"],"width":"5%"},
	{"data": "serverId", "title": columsName["number"],"width":"5%"},
	{"data": "serverEnv", "title": columsName["serverEnv"],"width":"8%"},
	{"data": "serverType", "title": columsName["serverType"],"width":"15%" },
	{"data": "serverStatus", "title": columsName["serverStatus"],"width":"8%"},
	{"data": "serverVersion", "title": columsName["serverVersion"],"width":"8%"},
	{"data": "systemName", "title": columsName["systemName"],"width":"10%"},
	{"data": "serverUrl", "title": columsName["serverUrl"],"width":"22%"},
	{"data": "serverId", "title": columsName["extn"],"width":"15%"},
	{"data": "serverId", "title": columsName["bindId"],"width":"1%","sClass":"hidden"}
];

commonObj.init();
	
/*
 * 根据操作类型展示弹出框内容
 */
function showLayerByType(id,type){
	
	$("#serverEnvSelect").removeAttr("disabled"); 
	$("#serverTypeSelect").removeAttr("disabled"); 
	$("#serverStatusSelect").removeAttr("disabled"); 
	$("#us").removeClass("detail_table");
	
	if(type == "edit" || type == "detail"){
		
		$("#fromTemplate").show();
		RestfulClient.post("/server/getInfo", {
			"serverId": id
		},
		function(result) {
			data = result.dataList;
			
			if(type == "edit"){
				
				$(".disableY").removeAttr("readonly");
				$(".disableN").attr("readonly","readonly");
			}else if(type == "detail"){
				
				id = -1;
				$(".disableY").attr("readonly","readonly");
				$(".disableN").attr("readonly","readonly");
				$("#us").addClass("detail_table");
				
				$(".detailShow").show();
				$("#createdUser").val(data.createdUser);
				$("#createdDate").val(data.createdDate);
				$("#updatedUser").val(data.updatedUser);
				$("#updatedDate").val(data.updatedDate);
			}
           
//			$("#serverEnvSelect").find("option[value='"+data.serverEnv+"']").attr("selected",true); 
//			$("#serverTypeSelect").find("option[value='"+data.serverType+"']").attr("selected",true); 
//			$("#serverStatusSelect").find("option[value='"+data.serverStatus+"']").attr("selected",true); 
			$("#serverEnvSelect").val(data.serverEnv);
			$("#serverTypeSelect").val(data.serverType);
			$("#serverStatusSelect").val(data.serverStatus);
			if(type == "detail"){
				$("#serverEnvSelect").attr("disabled","disabled"); 
				$("#serverTypeSelect").attr("disabled","disabled"); 
				$("#serverStatusSelect").attr("disabled","disabled"); 
			}
			
			$("#serverVersion").val(data.serverVersion);
			$("#systemName").val(data.systemName);
			$("#serverUrl").val(data.serverUrl);
			UserCommon.addInfos(event,id,type);
		});
	}else{
		
		id = 0;
		$(".disableN").removeAttr("readonly");
		$(".disableY").removeAttr("readonly").val("");
//		$("#serverVersion").val("");
//		$("#systemName").val("");
//		$("#serverUrl").val("");
		
		UserCommon.addInfos(event,id,type);
	}
}

var UserCommon = function(){
    return {
    	addInfos : function(event,cid,type){
	        layer.open({
	            type : 1,
	            fixed : true, // 不固定
	            shadeClose : false, // 点击遮罩关闭层
	            title : [showTitle(cid) ],
	            area : [ '700px', '470px' ],
	            content :$("#fromTemplate") ,
	            btn:[showBtn(cid)],
	            yes : function(index, dom){
	            	var data = $("#us").formData();
//                  alert(JSON.stringify(data));
	                if(cid >= 0){	//0 add -1 detail 1edit
	                	
	                	var returnMessage = checkParams();
	                	
	                	if(returnMessage.length == 0){
	                		reqData = {"serverId" : cid,"serverType" : $("#serverTypeSelect").val(),
                				"serverVersion" : $.trim(data.serverVersion),"serverEnv": $("#serverEnvSelect").val(),
                				"serverStatus": $("#serverStatusSelect").val(),"systemName" : $.trim(data.systemName),
                				"serverUrl" : $.trim(data.serverUrl),"reqType":"checkUrl"
                	   		};
	                		RestfulClient.post("/server/changeInfo",reqData,
                			function(result) {
	                			
	                			res = false;
	                			if(result.resultCode > 0){
	                				if(result.resultMsg.length > 0){
	                					res = confirm(result.resultMsg+"\n确定要这么做吗？");
	                				}else{
	                					res = true;
	                				}
	                			}else{
	                				res = true;
	                			}
	                			
	                			if(res){
	                				reqData.reqType = type;
	                				RestfulClient.post("/server/changeInfo",reqData,
	                				function(result1) {
	                					alert(result1.resultMsg);
	                					layer.close(index);
	                					selectinfo();
	                				});
	                			}else{
	                				layer.close(index);
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

	 