var data;
$(function() {
	
	urlPath = "/project";
    // 数据初始化
	selectinfo();
	//加载弹出框 下拉列表option
	selectBoxInfo();
	
})

function loadSourceInfos(){
	
	var availableTags = new Array();
	
	RestfulClient.post("/source/listAll",{},
	 function(result) {
		$.each(result.dataList, function( key, val ) {
			availableTags.push(val.dataSource+":"+val.sourceName);
		} );
		
		$( "#sourceNameSel" ).autocomplete({
		     source: availableTags
		});
	});
}

//$("#sourceNameSel").on('input propertychange',functionName);
//function functionName(){
//	alert("3453543543===="+$("#sourceNameSel").val());
//}

/** 列表搜索 */
$('#selectInfo').on( 'click', function () {
	selectinfo();
});

function selectinfo(){
	searchParams = { 
		"pageSize":lastPageSize,
		"projectCode" : $.trim($("#projectCodeSel").val()), 
		"projectName" : $.trim($("#projectNameSel").val()),
		"isDefault" : $.trim($("#isDefaultSel").val()),
		"sourceName": $.trim($("#sourceNameSel").val())
	};
	commonObj.tabInit("#tableId");
}


var columsName = {
	"selectAll":"<input type='checkbox' name='checkAll' value=''>",
	"number":"编号",
	"projectId":"方案ID",
	"projectCode": "方案代码",
	"projectName": "方案名称",
	"sourceName": "渠道名称",
	"isDefault": "默认方案",
	"extn": "操作",
	"projectid": "操作"
};

var columns = [
	{"data": "projectId", "title": columsName["selectAll"],"width":"5%"},
	{"data": "projectId", "title": columsName["number"],"width":"5%"},
	{"data": "projectCode", "title": columsName["projectCode"],"width":"20%"},
	{"data": "projectName", "title": columsName["projectName"],"width":"20%"},
	{"data": "sourceName", "title": columsName["sourceName"],"width":"20%"},
	{"data": "isDefault", "title": columsName["isDefault"],"width":"10%"},
	{"data": "projectId", "title": columsName["extn"],"width":"16%"},
	{"data": "projectId","title": columsName["bindId"],"width":"3%","sClass":"hidden"}
];

commonObj.init();

/*
 * 根据操作类型展示弹出框内容
 */
function showLayerByType(id,type){
	
	$("#selectBox").removeAttr("disabled").val(" ");
	$("#us").removeClass("detail_table");
	
	if(type == "edit" || type == "detail"){
		
		$("#fromTemplate").show();
		RestfulClient.post("/project/getInfo", {
			"projectId": id
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

			$("#selectBox").val(data.dataSourceId);
			$("#selectBox").attr("disabled","disabled"); 
			
			$("#projectName").val(data.projectName);
			$("#projectCode").val(data.projectCode);
			
			UserCommon.addInfos(event,id,type);
		});
	}else{
		
		id = 0;
		$(".disableN").removeAttr("readonly");
		$(".disableY").removeAttr("readonly").val("");
		$("#projectName").val("");
		$("#projectCode").val("");
		
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
//	                	alert("returnMessage-----"+returnMessage);
	                	
	                	if(returnMessage.length == 0){
	                		RestfulClient.post("/project/changeInfo", 
                			{
                    			"projectId" : cid, 
                    			"projectCode" : $.trim(data.projectCode), 
                				"projectName" : $.trim(data.projectName),
                				"dataSourceId": $("#selectBox").val(),
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


function selectBoxInfo(){
	
	var availableTags = new Array();
	
	RestfulClient.post("/source/listAll", {},
		 function(result) {
			data = result.dataList.sources;
			for(var i in data){
				$("#selectBox").append("<option value='"+data[i].dataSourceId+"'>"+data[i].sourceName+"</option>");
			}
			
			$.each(data, function( key, val ) {
				availableTags.push(val.dataSource+":"+val.sourceName);
			} );
			
			$( "#sourceNameSel" ).autocomplete({
			     source: availableTags
			});
 		}
	);
	
}

	
	 