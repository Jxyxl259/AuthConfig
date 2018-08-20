var data;
var treeObj;
var userCode;
var editUserId;

$(function() {
	
	urlPath = "/user";
    // 数据初始化
	selectinfo();
})

function selectinfo(){
	searchParams = { 
		"pageSize":lastPageSize,
		"userCode" : $.trim($("#userCodeSel").val()), 
		"userCname" : $.trim($("#userCnameSel").val())
	};
	commonObj.tabInit("#tableId");
}

var columsName = {
	"selectAll":"<input type='checkbox' name='checkAll' value=''>",
	"number":"编号",
	"userId": "用户ID",
	"userCode":"用户代码",
	"userCname":"简体中文名称",
	"mobile": "手机号码",
	"passwordExpireDate": "密码过期时间",
	"extn": "操作",
	"projectid": "操作"
};

var columns = [
	{"data": "userId", "title": columsName["selectAll"],"width":"5%"},
	{"data": "userId", "title": columsName["number"],"width":"5%"},
	{"data": "userId", "title": columsName["userId"],"width":"20%"},
	{"data": "userCode", "title": columsName["userCode"],"width":"14%"},
	{"data": "userCname", "title": columsName["userCname"],"width":"14%"},
	{"data": "mobile", "title": columsName["mobile"],"width":"12%"},
	{"data": "passwordExpireDate", "title": columsName["passwordExpireDate"],"width":"13%"},
	{"data": "userId", "title": columsName["extn"],"width":"15%"},
	{"data": "userId","title": columsName["projectid"],"width":"1%","sClass":"hidden"}
];

columnDefs = [
	{ "bSortable": false, "aTargets": [0,2,3,4,] },
	{ "visible": false},
    {"aTargets":[3],"mRender":function(data,type,full){
        return " <a href='javascript:;' title='分配角色' name='roleResource' class='roleResource' value='"+data+"'  data-id='" + data + "'  style='text-decoration:none;color:green;'><font  style='color:blue;'>"+data+"</font></a>";
    }}
];

commonObj.init();
/*
 * 根据操作类型展示弹出框内容
 */
function showLayerByType(id,type){
	
	$("#us").removeClass("detail_table");
	$(".detailShow1").hide();	
	
	if(type == "edit" || type == "detail"){
		
		$("#fromTemplate").show();
		RestfulClient.post("/user/getInfo", {
			"userId": id
		},
		function(result) {
			data = result.dataList;
			
			if(type == "edit"){
				
				editUserId = id;
				id = 1;
				$(".disableY").removeAttr("readonly");
				$(".disableN").attr("readonly","readonly");
				$("#remark").removeAttr("disabled","disabled");
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

			
			$("#passwordExpireDate").val(data.passwordExpireDate);
			$("#userCode").val(data.userCode);
			$("#userCname").val(data.userCname);
			$("#userEname").val(data.userEname);
			$("#mobile").val(data.mobile);
			$("#password").val(data.password);
			$("#passwordSetDate").val(data.passwordSetDate);
			$("#address").val(data.address);
			$("#email").val(data.email);
			$("#remark").val(data.remark);
			
			if(type == "detail"){
				$("#passwordExpireDate").attr("disabled","disabled"); 
				$("#remark").attr("disabled","disabled"); 
			}
			
			UserCommon.addInfos(event,id,type);
		});
	}else{
		
		id = 0;
		$(".detailShow1").show();
		$(".disableN").removeAttr("readonly").val("");
		$(".disableY").removeAttr("readonly").val("");
		
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
	            area : [ '660px', '455px' ],
	            content :$("#fromTemplate") ,
	            btn:[showBtn(cid)],
	            yes : function(index, dom){
	            	var data = $("#us").formData();
//                  alert(JSON.stringify(data));
	                if(cid >= 0){	//0 add -1 detail 1edit
	                	
	                	var returnMessage = checkParams();
//	                	alert("returnMessage-----"+returnMessage);
	                	
	                	if(returnMessage.length == 0){
	                		RestfulClient.post("/user/changeInfo", 
                			{
                    			"userId" : editUserId, 
                    			"userCode" : $.trim(data.userCode),
	            				"userCname" : $.trim(data.userCname),
	            				"userEname" : $.trim(data.userEname), 
	            				"password" : $.trim(data.password), 
	            				"passwordSetDate" : $.trim(data.passwordSetDate), 
	            				"passwordExpireDate" : $.trim(data.passwordExpireDate), 
	            				"mobile" : $.trim(data.mobile),
	            				"email" : $.trim(data.email), 
	            				"remark" : $.trim(data.remark), 
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
 * 用户分配角色
 */
function columnAlert(userCode){
	
	$(".role_right").html(userCode);
	
	RestfulClient.post("/userRole/getRoles", 
	{
		"userCode":userCode
	},
	function(result) {
		var zNodes = result.allRoles;
   	    var setting = {
			view: {selectedMulti: false},
			check: {enable: true},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "0",
					rootPId:null
				},
				key: {
					name: "text"
				}
			}
		};
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);

        treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        for(i=0; i<result.myRoles.length; i++){	
    	    var empNode=treeObj.getNodeByParam("id",result.myRoles[i].id,null);
	        if(empNode != null && empNode != undefined){
		        treeObj.checkNode(empNode,true,true);
	        }
	    }         
	}); 
	$(".tree_Box").show();
	$(".tree_BoxBg").show();
}


function confirmSave(){
	var nodes = treeObj.getCheckedNodes(true);
	var ids = "";
	// 获取所有选中的id,并剔除一级菜单
	$.each(nodes, function(index, value) {
		if (value.level == 0) {
			ids += value.id + ",";
		}
	});

	userCode = $(".role_right").text();
	
	layer.confirm('确定保存?', function(index) {
		$.ajax({
			url : "/userRole/saveUserRoles",
			type : "POST",
			data : {
				"ids" : ids,
				"userCode" : userCode
			},
			success : function(result) {
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
	 