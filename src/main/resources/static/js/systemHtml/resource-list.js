$(function(){
	inint();
})

var zTreeObj;

// 初始化加载树
function inint() {
	RestfulClient.post("/resource/list", {},
	function(result) {
   		var zNodes = result.menuResourceTree;
		zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
		zTreeObj.expandAll(true);
	
    });
};

var setting = {
		view : {
			enable : true,
			showLine : true,
			addHoverDom : addHoverDom,
			removeHoverDom : removeHoverDom,
			showIcon: true,
			selectedMulti: false
		},
		edit : {
			enable : true,
			showRemoveBtn : true,
			showRenameBtn : true,
			removeTitle : "删除节点",
		    renameTitle: "编辑节点"
		},
		data : {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId:null
			},
			key: {
				name: "text"
			}
		},
//		check: {
//			enable: true
//		},
		callback : {
			beforeRemove : beforeRemove,
			beforeEditName: beforeEditName,
			onRemove : zTreeOnRemove
		}	
	};

/*
 * 鼠标移入,显示图标
 */
function addHoverDom(treeId, treeNode) {
//	alert("treeNode.isParent----"+treeNode.isParent+"------"+treeNode.parentId);
     //如果节点不是父节点并且父节点为空
	if (!treeNode.isParent || treeNode.pId !=null) {
		return false;
	} else {
		var sObj = $("#" + treeNode.tId + "_span");
		var addBtn = $("#addBtn_" + treeNode.tId)
		if (treeNode.editNameFlag || addBtn.length > 0)
			return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);
		
		if (btn){
			btn.bind("click",function() {
				showLayerByType(treeNode.id,"add");	
			});
		}
	}
}


/*
 * 编辑
 */
function beforeEditName(treeId, treeNode) {
	
	var editObj = $("#" + treeNode.tId + "_edit");
	if(editObj){
		editObj.bind("click", function(event){
			showLayerByType(treeNode.id,"edit");
		});				
	}
	return false;	
}

/*
 * 根据操作类型展示弹出框内容 	主键id	操作类型	
 */
function showLayerByType(id,type){
	if(type == "edit"){
		
		$("#fromTemplate").show();
		$(".detailShow").show();
		
		RestfulClient.post("/resource/getInfo", {
			"resourceId": id
		},
		function(result) {
			data = result.dataList;
			
			$(".disableY").removeAttr("readonly");
			$(".disableN").attr("readonly","readonly");
			
			$("#resourceId").val(data.resourceId);
			$("#resourceName").val(data.resourceName);
			$("#actionUrl").val(data.actionUrl);
//			$("#resourceIconClass").val(data.resourceIconClass);
//			$("#displayOrder").val(data.displayOrder);
			
			alertWindow(id,type);
		});
	}else{
		
		$(".detailShow").hide();
		$(".disableN").removeAttr("readonly");
		$(".disableY").removeAttr("readonly").val("");
		
		alertWindow(id,type);
	}
}

function alertWindow(cid,type){
    layer.open({
        type : 1,
        fixed : true, // 不固定
        shadeClose : false, // 点击遮罩关闭层
        title : [showTitle1(type) ],
        area : [ '660px', '400px' ],
        content :$("#fromTemplate") ,
        btn:[showBtn1(type)],
        yes : function(index, dom){
            	
        	var returnMessage = checkParams();
//        	alert("returnMessage-----"+returnMessage);
        	
        	if(returnMessage.length == 0){
	        	RestfulClient.post("/resource/changeInfo", 
				{
	    			"resourceId" : cid, 
					"resourceName" : $.trim($("#resourceName").val()),
					"actionUrl" : $.trim($("#actionUrl").val()),
//					"resourceIconClass" : $.trim($("#resourceIconClass").val()),
//					"displayOrder" : $.trim($("#displayOrder").val()),
					"reqType": type
		   		},
				function(result) {
		   			alert(result.resultMsg);
					layer.close(index);
		 		});
        	}
        },
        end : function() {
//        	$(".tdAfterShowInfo").remove();
        	document.location.reload();// 当前页面
        }
    });
}

/*
 * 鼠标移出,隐藏图标
 */
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
}

/*
 * 删除节点
 */
function beforeRemove(treeId, treeNode) {
	if (treeNode.isParent) {
		alert("请先删除子节点");
		return false;
	}
	return true;
}

/*
* 删除
*/
function zTreeOnRemove(event, treeId, treeNode) {
	layer.confirm("确认删除 " + treeNode.text + " 节点吗？", {
		icon : 3,
		title : '提示'
	}, function(index) {
		$.ajax({
			type : "post",
			url :  "/resource/deleteResource?resourceId="+ treeNode.id,
			dataType : "json",
			success : function(data) {
				if (data.flag == "Y") {
					alert(data.msg);
					document.location.reload();// 当前页面 //删除之后刷新数据
				} else {
					alert(data.msg);
				}
			},
			error : function(data) {
				document.location.reload();// 当前页面
			}
		});
	},function(){
		document.location.reload();// 当前页面
	});
}

/*
 * layer弹框title显示
 */
function showTitle1(type){
	if(type == "edit") 		return "修改信息";
	else 					return "添加信息";
}
/*
 * layer弹框按钮显示
 */
function showBtn1(type){
	if(type == "edit") 		return "修改";
	else 					return "添加";
}

