var data;
var treeObj;
var roleResourceId;

$(function() {
	
	urlPath = "/role";
    // 数据初始化
	selectinfo();
})

/** 列表搜索 */
$('#selectInfo').on( 'click', function () {
	selectinfo();
});

/*
 * 加载列表数据
 */
function selectinfo(){
	searchParams = { 
		"pageSize":lastPageSize,
		"roleId" : $.trim($("#roleIdSel").val()), 
		"roleName" : $.trim($("#roleNameSel").val())
	};
	commonObj.tabInit("#tableId");
}

var columsName = {
	"selectAll":"<input type='checkbox' name='checkAll' value=''>",
	"number":"编号",
	"roleId": "角色编号",
	"roleName":"角色名称",
	"extn": "操作"
};

var columns = [
	{"data": "roleId", "title": columsName["selectAll"],"width":"5%"},
	{"data": "roleId", "title": columsName["number"],"width":"5%"},
	{"data": "roleId", "title": columsName["roleId"],"width":"10%"},
	{"data": "roleName", "title": columsName["roleName"],"width":"15%","sClass":"hidden"},
	{"data": "roleId", "title": columsName["roleId"],"width":"10%","sClass":"hidden"},
	{"data": "roleName", "title": columsName["roleName"],"width":"15%"},
	{"data": "roleId", "title": columsName["extn"],"width":"10%"},
	{"data": "roleId", "title": columsName["ceshi"],"width":"3%","sClass":"hidden"}
];

columnDefs = [
   { "bSortable": false, "aTargets": [0,2,3,4,] },
   { "visible": false},
   {"aTargets":[2],"mRender":function(data,type,full){
       return " <a href='javascript:;' title='分配资源' name='roleResource' class='roleResource' value='"+data+"'  data-id='" + data + "'  style='text-decoration:none;color:green;'><font  style='color:blue;'>"+data+"</font></a>";
   }}
];

commonObj.init();	

/*
 * 根据操作类型展示弹出框内容
 */
function showLayerByType(id,type){
	
	$("#us").removeClass("detail_table");
	
	if(type == "edit" || type == "detail"){
		
		$("#fromTemplate").show();
		
		RestfulClient.post("/role/getInfo", {
			"roleId": id
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
				$("#createdUser").val(data.createdBy);
				$("#createdDate").val(data.createdDate);
				$("#updatedUser").val(data.updatedBy);
				$("#updatedDate").val(data.updatedDate);
			}
			
			$("#roleId").val(data.roleId);
			$("#roleName").val(data.roleName);
			
			UserCommon.addInfos(event,id,type);
		});
	}else{
		
		id = 0;
		RestfulClient.post("/role/getNewRoleId", {},
		function(result) {
			data = result.resultMsg;
			$(".disableY").removeAttr("readonly").val("");
			$("#roleId").attr("readonly","readonly").val(data);
			
			UserCommon.addInfos(event,id,type);
		});
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
	            area : [ '660px', '455px' ],
	            content :$("#fromTemplate"),
	            btn:[showBtn(cid)],
	            yes : function(index, dom){
	            	var data = $("#us").formData();
//	            	alert(JSON.stringify(data));
	                if(cid >= 0){	//0 add -1 detail 1edit

	                	var returnMessage = checkParams();
//	                	alert("returnMessage-----"+returnMessage);
	                	
	                	if(returnMessage.length == 0){
		                	RestfulClient.post("/role/changeInfo", 
		        			{
		            			"roleId" : $.trim(data.roleId), 
		        				"roleName" : $.trim(data.roleName),
		        				"reqType": type
		        	   		},
		        			 function(result) {
								alert(result.resultMsg);
								layer.close(index);
		        	 		});
	                	}
	                }else{
	                	layer.close(index);
	                }
	            },
	            end : function() {
	            	$(".detailShow").hide();
	            	$(".tdAfterShowInfo").remove();
	            	selectinfo();
	            }
	        });
        }
    }
}();

/*
 * 分配资源
 */
function columnAlert(roleResourceId){
	$(".role_right").html(roleResourceId);
	
	RestfulClient.post("/roleRes/getResource", 
	{"roleId" : roleResourceId },
	function(result) {
	    $("from[name='roleId']").val(result.roleId);
	    var zNodes = result.menuResourceList;
   	        
   	    var setting = {
			view: {selectedMulti: false},
			check: {enable: true},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parentId",
					rootPId:null
				},
				key: {
					name: "text"
				}
			}
		};
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);

        treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        for(i=0; i<result.resources.length; i++){	
    	    var empNode=treeObj.getNodeByParam("id",result.resources[i].id,null);
	        if(empNode != null && empNode != undefined){
		        treeObj.checkNode(empNode,true,false);
	        }
	    }         
	}); 
	$(".tree_Box").show();
	$(".tree_BoxBg").show();
}


function confirmSave(){

	var nodes = treeObj.getCheckedNodes(true);
	var ids = "";
	//获取所有选中的id,并剔除一级菜单
	$.each(nodes,function(index,value){
		ids += value.id+",";
	});
	
	roleResourceId = $(".role_right").text();
	
	layer.confirm('确定要更新角色资源关系吗?', function(index){
		layer.close(index);
		$.ajax({
			url: "/roleRes/saveRoleResource",
			type:"POST",
			data:{"ids":ids,"roleId":roleResourceId},
			success:function(result){
				alert(result.resultMsg);
				$(".tree_Box").hide();
				$(".tree_BoxBg").hide();
				selectinfo();
			}
		})
	});
}

/*
 * 关闭DIV弹框
 */
function closeModelDiv(){
	
	$(".tree_Box").hide();
	$(".tree_BoxBg").hide();
}



