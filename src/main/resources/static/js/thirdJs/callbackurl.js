var data;

$(function() {
	
	urlPath = "/callback";
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
		"callbackUrl" : $.trim($("#callbackUrlSel").val()), 
		"projectId" : $.trim($("#projectIdSel").val()),
		"callbackType" : $.trim($("#callbackTypeSel").val()),
		"callbackEnv" : $.trim($("#callbackEnvSel").val())
	};
	commonObj.tabInit("#tableId");
}

var serverEnvTypes,serverTypes;
function selectBoxInfo(){
	RestfulClient.post("/project/listAll", {},
	function(result) {
		data = result.dataList;
		for(var i in data){
			$("#projectIdSelect").append("<option value='"+data[i].projectId+"'>"+data[i].projectName+"</option>");
			$("#projectIdSel").append("<option value='"+data[i].projectId+"'>"+data[i].projectName+"</option>");
		}
	});
	RestfulClient.post("/innerconfig/getAllTypesInfo", {}, 
	function(result) {
		maps = result.dataList;
		callbackTypes = maps.callbackTypes;		//获取回调类型列表
		serverEnvTypes = maps.serverEnvTypes;		//获取环境类型列表
		
		if(callbackTypes.length > 0){
			for(var i=0;i< callbackTypes.length;i++){
				$("#callbackTypeSelect").append("<option value='"+callbackTypes[i]+"'>"+callbackTypes[i]+"</option>");
				$("#callbackTypeSel").append("<option value='"+callbackTypes[i]+"'>"+callbackTypes[i]+"</option>");
			}
		}
		if(serverEnvTypes.length > 0){
			for(var i=0;i< serverEnvTypes.length;i++){
				$("#callbackEnvSelect").append("<option value='"+serverEnvTypes[i]+"'>"+serverEnvTypes[i]+"</option>");
				$("#callbackEnvSel").append("<option value='"+serverEnvTypes[i]+"'>"+serverEnvTypes[i]+"</option>");
			}
		}
	});
}

var columsName = {
	"selectAll":"<input type='checkbox' name='checkAll' value=''>",
	"number":"编号",
	"projectName":"方案名称",
	"callbackEnv":"环境类型",
	"callbackType":"回调类型",
	"callbackUrl":"回调地址",
//	"callbackMethod":"回调方法",
    "extn":"操作"
};
var columns = [
	{"data": "callbackUrlId", "title": columsName["selectAll"],"width":"5%"},
	{"data": "callbackUrlId", "title": columsName["number"],"width":"5%"},
	{"data": "projectName", "title": columsName["projectName"],"width":"20%"},
	{"data": "callbackEnv", "title": columsName["callbackEnv"],"width":"6%"},
	{"data": "callbackType", "title": columsName["callbackType"],"width":"20%"},
	{"data": "callbackUrl", "title": columsName["callbackUrl"],"width":"20%"},
//	{"data": "callbackMethod", "title": columsName["callbackMethod"],"width":"10%"},
	{"data": "callbackUrlId", "title": columsName["extn"],"width":"15%"},
	{"data": "callbackUrlId", "title": columsName["bindId"],"width":"1%","sClass":"hidden"}
];
 
commonObj.init();

/*
 * 根据操作类型展示弹出框内容
 */
function showLayerByType(id,type){
	
	$("#callbackTypeSelect").removeAttr("disabled"); 
	$("#callbackEnvSelect").removeAttr("disabled");
	$("#projectIdSelect").removeAttr("disabled"); 
	$("#us").removeClass("detail_table");

	if(type == "edit" || type == "detail"){
		
		$("#fromTemplate").show();
		RestfulClient.post("/callback/getInfo", {
			"callbackUrlId": id
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

//			$("#callbackTypeSelect").find("option[value='"+data.callbackType+"']").attr("selected",true); 
//			$("#projectIdSelect").find("option[value='"+data.projectId+"']").attr("selected",true); 
			$("#callbackEnvSelect").val(data.serverEnv);
			$("#callbackTypeSelect").val(data.callbackType); 
			$("#projectIdSelect").val(data.projectId);
			
			if(type == "detail"){
				$("#callbackEnvSelect").attr("disabled","disabled"); 
				$("#callbackTypeSelect").attr("disabled","disabled"); 
				$("#projectIdSelect").attr("disabled","disabled"); 
			}
			
			$("#callbackUrl").val(data.callbackUrl);
			$("#callbackMethod").val(data.callbackMethod);
			
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
	            area : [ '660px', '400px' ],
	            content :$("#fromTemplate") ,
	            btn:[showBtn(cid)],
	            yes : function(index, dom){
	            	var data = $("#us").formData();
//                  alert(JSON.stringify(data));
	                if(cid >= 0){	//0 add -1 detail 1edit
	                	
	                	var returnMessage = checkParams();
//	                	alert("returnMessage-----"+returnMessage);
	                	
	                	if(returnMessage.length == 0){
	                		RestfulClient.post("/callback/changeInfo", 
                			{
                				"callbackUrlId" : cid,
                				"projectId" : $("#projectIdSelect").val(),
                				"callbackEnv": $("#callbackEnvSelect").val(),
                				"callbackType": $("#callbackTypeSelect").val(),
                				"callbackUrl" : $.trim(data.callbackUrl),
                				"callbackMethod" : $.trim(data.callbackMethod),
                				"reqType": type
                	   		},
                			 function(result) {
    							alert(result.resultMsg);
    							layer.close(index);
    							selectinfo();
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

	