var zTreeObj, loadId,maxPage,currPage = 1,pageSize = 15;  

var setting = {  
    check: {  
        enable: false,  
    },  
    view : {  
        selectedMulti: false,  
//        addHoverDom: addHoverDom,
//        removeHoverDom: removeHoverDom,
        showIcon: true,  //设置是否显示节点图标  
        showLine: false,  //设置是否显示节点与节点之间的连线  
        addDiyDom: addDiyDom
    },
    callback: {  
    	onRightClick: OnRightClick,
        onClick: zTreeOnOnClick//点击事件  
    },  
    data: { // 必须使用data    
        simpleData: {  
            enable: true,  
            idKey: "id", // id编号命名 默认    
            pIdKey: "pId", // 父id编号命名 默认    
            rootPId: 0 // 用于修正根节点父节点数据，即 pIdKey 指定的属性值    
        }  
    }  
};  

var zNodes =[
    {name:"账户列表",id:"1", count:2000, page:0, pageSize:100, isParent:true}
];

$(document).ready(function(){  
	demoIframe = $("#testIframe");
	zTreeObj = $.fn.zTree.init($("#treeDemo"), setting,zNodes);
//	fuzzySearch('testIframe','#accountSeachBtnId',null,true); //搜索
});  

var loadUrl,loadData;
function zTreeOnOnClick(event, treeId, treeNode){
	
	var treeObj = $.fn.zTree.getZTreeObj(treeId);  
	var node = treeObj.getNodeByTId(treeNode.tId);
	var treeNodeLevelId = treeNode.level;
	var treeNodeId = treeNode.id;
	
	var href = "authconfig-right.html?levelId="+treeNodeLevelId+"&id="+treeNodeId;
	if(treeNodeLevelId == 2 && treeNodeId == 1)
		href = "authconfig-right.html?levelId=9&id=1";
	//右侧显示
	demoIframe.attr("src",href);

	if(treeNodeLevelId > 2) return;
	
	//没有子节点才去查询  
	if(node.children == null || node.children == "undefined"){

		if(treeNode.level == 0){
			loadData = { "pageNum":"1","pageSize":pageSize,"accountName":$.trim($('#accountSeachId').val())};
			loadUrl = "../../innerconfig/account_list";
		}else if(treeNode.level == 1)	{
			loadData = { "parentId":treeNodeId};
			loadUrl = "../../innerconfig/datasource_list";
		}else if(treeNode.level == 2){
			loadData = { "parentId":treeNodeId};
			loadUrl = "../../innerconfig/project_list";
		}	
		
		$.ajax({  
			type: "POST",  
			async:false,  
			url: loadUrl,  
			data:loadData, 
			dataType:"json",  
			success: function(data){  
				if(data.data !=null && data.data !=""){  
					//添加新节点  渠道
					newNode = treeObj.addNodes(node,data.data);  
					
					if(treeNode.level == 0){
						$("#ztree_pageInfo").html(data.pageInfo);
						maxPage = data.pages;
						demoIframe.attr("src","authconfig-right.html"+"?levelId=0&id=99");
					}
				}
			}
		});
	}
	
}

function addDiyDom(treeId, treeNode) {
	
	if (treeNode.level>0) return;
	
	var aObj = $("#" + treeNode.tId + "_a");
	if ($("#addBtn_"+treeNode.id).length>0) return;
	var addStr = "<span class='button lastPage' id='lastBtn_" + treeNode.id
		+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtn_" + treeNode.id
		+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtn_" + treeNode.id
		+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtn_" + treeNode.id
		+ "' title='first page' onfocus='this.blur();'></span>";
	aObj.after(addStr);
	
	var first = $("#firstBtn_"+treeNode.id);
	var prev = $("#prevBtn_"+treeNode.id);
	var next = $("#nextBtn_"+treeNode.id);
	var last = $("#lastBtn_"+treeNode.id);
	//treeNode.maxPage = Math.round(treeNode.count/treeNode.pageSize - .5) + (treeNode.count%treeNode.pageSize == 0 ? 0:1);
	first.bind("click", function(){
		if (!treeNode.isAjaxing) {
			//当前已是第一页,无需查询
			if(currPage == 1)	return;
			currPage = 1;
			goPage(treeNode, 1);
		}
	});
	last.bind("click", function(){
		if (!treeNode.isAjaxing) {
			//当前已是最后一页,无需查询
			if(currPage == maxPage)		return;
			currPage = maxPage;
			goPage(treeNode,maxPage);
		}
	});
	prev.bind("click", function(){
		if (!treeNode.isAjaxing) {
			//当前已是第一页,无需查询
			if(currPage == 1)	return;
			currPage = currPage - 1 < 1 ? 1 : currPage - 1;
			goPage(treeNode, currPage);
		}
	});
	next.bind("click", function(){
		if (!treeNode.isAjaxing) {
			//当前已是最后一页,无需查询
			if(currPage == maxPage)		return;
			currPage = currPage + 1 > maxPage ? maxPage : currPage + 1;
			goPage(treeNode, currPage);
		}
	});
	
};


function goPage(treeNode, page) {
	var treeObj = $.fn.zTree.getZTreeObj(1);
	$.ajax({  
		type: "POST",  
		url: "../../innerconfig/account_list",  
		data: {
			pageNum:page,
			pageSize:pageSize,
			accountName:$.trim($('#accountSeachId').val())
		},  
		dataType:"json",  
		success: function(data){  
			
			zTreeObj = $.fn.zTree.init($("#treeDemo"), setting,zNodes);
			
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");  
			var node = treeObj.getNodeByTId(treeNode.tId);  
			
			newNode = treeObj.addNodes(node, data.data);  
			$("#ztree_pageInfo").html(data.pageInfo);
		},  
	});
}


var treeNodeGlobal;
function OnRightClick(event, treeId, treeNode) {
	treeNodeGlobal = treeNode;
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.cancelSelectedNode(); //取消当前节点的选中状态
	showRMenu(treeNode,event.clientX, event.clientY);
}
function showRMenu(treeNode,x, y) {
	if(treeNode.level != null &&  treeNode.level> 2) return;
	$("#rMenu ul").show();
	if(treeNode.level == 0){
		$("#addRightProject").hide();
		$("#addRightDatasOource").hide();
		$("#addRightAccount").show();
	}else if(treeNode.level == 1){
		$("#addRightProject").hide();
		$("#addRightDatasOource").show();
		$("#addRightAccount").hide();
	}else if(treeNode.level == 2){
		$("#addRightDatasOource").hide();
		$("#addRightAccount").hide();
		$("#addRightProject").show();
	}
    y += document.body.scrollTop;
    x += document.body.scrollLeft;
    $("#rMenu").css({"top":y+"px", "left":x+"px", "visibility":"visible"});

	$("body").bind("mousedown", onBodyMouseDown);
}

function onBodyMouseDown(event){
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
		$("#rMenu").css({"visibility" : "hidden"});
	}
}
function hideRMenu() {
	if ($("#rMenu")){
		$("#rMenu").css({"visibility": "hidden"});
	}
	$("body").unbind("mousedown", onBodyMouseDown);
}

function addPage(){
	//添加页面
	hideRMenu();
	var treeNode = treeNodeGlobal;
	levelId = treeNode.level;
	treeNodeId = treeNode.id;
	if((levelId == 1 || levelId == 2) && treeNodeId == 1){
		alert("自定义_配置 中不可以添加渠道和方案");
		return ;
	}else{
		demoIframe.attr("src","authconfig-right.html?levelId="+(levelId+4)+"&id="+treeNodeId);
	}
	
}

function seachAccount(){
	var treeObj = $.fn.zTree.getZTreeObj('treeDemo');  
	//获取所有根结点数组。这里只有一个根结点
	var nodes = treeObj.getNodes()[0];
	var accountName = $.trim($('#accountSeachId').val());
	var loadData = { "pageNum":"1","pageSize":pageSize,"accountName":accountName};
	var loadUrl = "../../innerconfig/account_list";
	$.ajax({  
		type: "POST",  
		async:false,  
		url: loadUrl,  
		data:loadData, 
		dataType:"json",  
		success: function(data){  
			if(data.data !=null && data.data !=""){  
				treeObj.removeChildNodes(nodes);
				treeObj.addNodes(nodes,data.data,false);  
				$("#ztree_pageInfo").html(data.pageInfo);
				maxPage = data.pages;
				demoIframe.attr("src","authconfig-right.html"+"?levelId=0&id=99");
			}
		}
	});
}



