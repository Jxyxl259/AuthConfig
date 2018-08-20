var data;
var appId;
$(function() {
	
	urlPath = "/source";
    // 数据初始化
	selectinfo();
	//加载弹出框 下拉列表option
	var availableTags = new Array();
	RestfulClient.post("/account/listAll", {},
	function(result) {
		$.each(result.dataList, function( key, val ) {
			var map = {};
			map["value"] = val.accountName;
			map["appId"] = val.appId;
			availableTags.push(map);
		} );
		$( "#accountName" ).autocomplete({
		     source: availableTags,
		     select: function (e, ui) {
		    	 appId = ui.item.appId;
		     },
		     change: function (e, ui) {
	                //文本框里的值变化后要做的事,一般在这里可以控制如果文本框的值不是下拉框中的数据
	               if(ui.item == null){
	            	   alert("请在加载出的账号中选择");
	            	   $('#accountName').val('');
	            	   $('#sourceName').html('');
					   $("#dataSource").html('');

	               }
	            }
		});
	});
})

/** 列表搜索 */
$('#selectInfo').on( 'click', function () {
	selectinfo();
});

function selectinfo(){
	searchParams = { 
		"pageSize":lastPageSize,
	    "sourceName" : $.trim($("#sourceNameSel").val()),
		"dataSource" : $.trim($("#dataSourceSel").val())
	};
	commonObj.tabInit("#tableId");
}

var columsName = {
	"selectAll":"<input type='checkbox' name='checkAll' value=''>",
	"number":"编号",
//	"dataSourceId": "渠道ID",
	"dataSource": "渠道代码",
	"sourceName": "渠道名称",
    "appId": "账号ID",
//    "authId": "权限ID",
    "extn": "操作"
};
var columns = [
   {"data": "dataSourceId", "title": columsName["selectAll"],"width":"5%"},
   {"data": "dataSourceId", "title": columsName["number"],"width":"5%"},
   {"data": "dataSource", "title": columsName["dataSource"],"width":"21%"},
   {"data": "sourceName", "title": columsName["sourceName"],"width":"21%"},
   {"data": "appId", "title": columsName["appId"],"width":"21%"},
   {"data": "authId", "title": columsName["authId"],"width":"5%","sClass":"hidden"},
   {"data": "dataSourceId", "title": columsName["extn"],"width":"16%"},
   {"data": "dataSourceId", "title": columsName["bindId"],"width":"1%","sClass":"hidden"}
   ];
    
commonObj.init();

/*
 * 根据操作类型展示弹出框内容
 */
function showLayerByType(id,type){
	
	$("#accountName").removeAttr("disabled");
	$("#accountName").empty();
	$("#us").removeClass("detail_table");

//	for(var i in appIdSelects){
//		$("#selectBox").append("<option value='"+appIdSelects[i].appId+"'>"+appIdSelects[i].appId+"</option>");
//	}
	
	if(type == "edit" || type == "detail"){
		
		$("#fromTemplate").show();
		RestfulClient.post("/source/getInfo", {
			"dataSourceId": id
		},
		function(result) {
			data = result.dataList[0];
			var accountData = result.dataList[1];
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
			
//			for(var i in appIdSelects){
//				if(appIdSelects[i].appId == data.appId){
//					$("#selectBox").find("option[value='"+data.appId+"']").attr("selected",true); 
//					
//				}
//			}
			$("#accountName").val(accountData.accountName);
			appId = data.appId;
			$("#accountName").attr("disabled","disabled"); 
			 
			$("#sourceName").val(data.sourceName);
			$("#dataSource").val(data.dataSource);
			
			UserCommon.addInfos(event,id,type);
		});
	}else{
		
		id = 0;
		$("#accountName").val("");
		$(".disableN").removeAttr("readonly");
		$(".disableY").removeAttr("readonly").val("");
		$("#sourceName").val("");
		$("#dataSource").val("");
		
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
	                if(cid >= 0){	//0 add -1 detail 1edit
	                	
	                	var returnMessage = checkParams();
	                	
	                	if(returnMessage.length == 0){
	                     	RestfulClient.post("/source/changeInfo", 
	            			{
	                 			"dataSourceId" : cid,
		                		"appId" : appId,
		                		"dataSource" : $.trim(data.dataSource),
		                		"sourceName":$.trim(data.sourceName),
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

