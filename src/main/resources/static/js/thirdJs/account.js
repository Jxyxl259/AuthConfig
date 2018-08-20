var data;

$(function() {

	urlPath = "/account";
    // 数据初始化
	selectinfo();
})


/** 列表搜索 */
$('#selectInfo').on( 'click', function () {
	selectinfo();
});

function selectinfo(){
	searchParams = { 
		"pageSize":lastPageSize,
		"accountName":$.trim($("#accountNameSel").val()),
		"appCode":$.trim($('#appCodeSel').val()),
		"appId":$.trim($("#appIdSel").val())
	};
	commonObj.tabInit("#tableId");
}

var columsName = {
	"selectAll":"<input type='checkbox' name='checkAll' value=''>",
	"number":"编号",
//	"accountId": "accountId",
	"appId": "账号ID",
	"appCode": "账号代码",
    "accountName": "账号名称",
    "mobile": "渠道手机",
    "email": "渠道邮箱",
    "extn": "操作"
};

var columns = [
	{"data": "accountId", "title": columsName["selectAll"],"width":"5%"},
	{"data": "accountId", "title": columsName["number"],"width":"5%"},
	{"data": "appId", "title": columsName["appId"],"width":"16%"},
	{"data": "appCode", "title": columsName["appCode"],"width":"15%"},
	{"data": "accountName", "title": columsName["accountName"],"width":"16%"},
	{"data": "mobile", "title": columsName["mobile"],"width":"10%"},
	{"data": "email", "title": columsName["email"],"width":"12%"},
	{"data": "accountId", "title": columsName["extn"],"width":"16%"},
	{"data": "accountId", "title": columsName["ceshi"],"width":"1%","sClass":"hidden"}
];

commonObj.init();	

/*
 * 根据操作类型展示弹出框内容
 */
function showLayerByType(id,type){
	
	$("#fromTemplate").show();
	$("#genAppid").hide();
	$("#us").removeClass("detail_table");
	
	if(type == "edit" || type == "detail"){
		
		RestfulClient.post("/account/getInfo", {
			"accountId": id
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
				
				$(".detailShow").show();
				$("#us").addClass("detail_table");
				
				$("#createdUser").val(data.createdUser);
				$("#createdDate").val(data.createdDate);
				$("#updatedUser").val(data.updatedUser);
				$("#updatedDate").val(data.updatedDate);
			}
			
			$("#appId").val(data.appId);
			$("#accountName").val(data.accountName);
			$("#appCode").val(data.appCode);
			$("#mobile").val(data.mobile);
			$("#email").val(data.email);
			
			UserCommon.addInfos(event,id,type);
		});
	}else{
		
		id = 0;
		$("#genAppid").show();
		$(".disableN").removeAttr("readonly");
		$(".disableY").removeAttr("readonly").val("");
		$("#appId").val("");
		$("#appCode").val("");
		
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
	            area : [ '680px', '470px' ],
	            content :$("#fromTemplate") ,
	            btn:[showBtn(cid)],
	            yes : function(index, dom){
	            	var data = $("#us").formData();
//                  alert(JSON.stringify(data));
	                
	                if(cid >= 0){	//0 add -1 detail 1edit
	                	
	                	var returnMessage = checkParams();
//	                	alert("returnMessage-----"+returnMessage);
	                	
	                	if(returnMessage.length == 0){
	                		RestfulClient.post("/account/changeInfo", 
            				{
	                			"accountId" : cid, 
	                			"appId" : data.appId, 
	                			"accountName" : $.trim(data.accountName),
	                			"appCode" : $.trim(data.appCode),
	                			"mobile" : $.trim(data.mobile),
	                			"email" : $.trim(data.email),
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

/*
 * 生成APPID
 */
function genAppId(){
	RestfulClient.post("/authconfig/getNewAppid", {},
		 function(result) {
			data = result.data;
			$("#appId").val(data);
//			alert($("#appId").val());
 		}
	);
}
