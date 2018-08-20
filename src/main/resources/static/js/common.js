//查询参数,请求根路径,最后一次表格每页行数
var searchParams,urlPath,lastPageSize=10;
//列表中是否展示编辑按钮,是否展示删除按钮,是否展示详情按钮
var showEditButton=true,showDeleteButton=true,showDetailButton=true;


$.fn.formData = function() {
    var o = {};
    var a = $("#us").serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};	

var columnDefs = '{ "bSortable": false, "aTargets": [0,2,3,4,5,6,7] },{ "visible": false}';

/*
 * 列表数据加载
 */
var commonObj = {		
	init:function(){
		this.tabInit();
		this.editTab();
		this.deleteTab();
		this.detailTab();
		this.selectAllTab();
		this.columnAlert();
	},
	//表格初始化
	tabInit:function(tabId){
		if(tabId != undefined){
			$("#tableId").DataTable({	
				processing: true,//刷新的那个对话框
		        serverSide: true,//服务器端获取数据
		        bLengthChange: true, //改变每页显示数据数量
		        paging: true,//开启分页
		        ordering:false,
		        ajax: {
		            "url": urlPath+"/list",
		            "type": "POST",
		            "contentType": "application/json",
		            "dataType" : "json",
		            "data": function (d) {
		            	searchParams.length = d.length;
		            	searchParams.start = d.start;
		            	searchParams.draw = d.draw;
		            	
		            	//删除多余请求参数
		                for(var key in d){
	                        delete d[key];
		                }
		            	 return JSON.stringify(searchParams);
		            },
		            "dataFilter": function (result) {//json是服务器端返回的数据
		                json = JSON.parse(result);
		                var returnData = {};
		                returnData.draw = json.draw;
		                lastPageSize = json.pageSize;
		                returnData.recordsTotal = json.recordsTotal;//返回数据全部记录
		                returnData.recordsFiltered = json.recordsTotal;//后台不实现过滤功能，每次查询均视作全部结果
		                returnData.data = json.dataList;//返回的数据列表
		                return JSON.stringify(returnData);//这几个参数都是datatable需要的，必须要
		            }
		        },
		        searching : false,
			    columns:columns,
			    aoColumnDefs: columnDefs,
				dom: '<"H"rfl > t <"F"ip >',
	            destroy: true,                                   //因为需要多次初始化，所以需要设置允许销毁实例
			    oLanguage: {
					"sLengthMenu": "每页显示 _MENU_ 条记录",
					"sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
					"sInfoEmpty": "",
					"sEmptyTable": "对不起，查询不到任何相关数据",
					"sInfoFiltered": "数据表中共为 _MAX_ 条记录)",
					"sProcessing": "正在加载中...",
					"sSearch": "搜索",
					"oPaginate": {
						"sFirst": "第一页",
						"sPrevious":" 上一页 ",
						"sNext": " 下一页 ",
						"sLast": " 最后一页 "
					} 
			    },
			    fnDrawCallback: function(tid){
			    	
			    	var lengthMenu = $("select[name='tableId_length']").val();
			    	var list = document.getElementsByClassName("paginate_button current");
			    	
			    	this.api().column(0).nodes().each(function(cell, i){
			    		cell.innerHTML='<input type="checkbox" name="checkes" value="">';
					});
					this.api().column(1).nodes().each(function(cell, i){
						tid = cell.textContent;
						$(cell).parent().attr('tid',tid);
						cell.innerHTML = (Number(lengthMenu)*(Number(list[0].innerHTML - 1))) + i + 1;
					});
					this.api().column(columns.length-2).nodes().each(function(cell, i){
						innerOperateId = $(this).parent().parent().find("td").eq(columns.length-1).html();
						var htmlStr='';
						if(showEditButton)
							htmlStr += '<button class="edi_btn uEditBtn" id='+innerOperateId+'>编辑</button>';
						if(showDetailButton)
							htmlStr += '<button class="edi_btn uDetailBtn">详情</button>';
						if(showDeleteButton)
							htmlStr += '<button class="edi_btn uDeleteBtn"><i class="Hui-iconfont">&#xe6e2;</i></button>';
						cell.innerHTML = htmlStr;
//						cell.innerHTML ='<button class="edi_btn uEditBtn" id='+innerOperateId+'>编辑</button><button class="edi_btn uDetailBtn">详情</button><button class="edi_btn uDeleteBtn"><i class="Hui-iconfont">&#xe6e2;</i></button>';
					})
			    }
			});
			//表格列宽拖动
			$("#tableId").resizableColumns({ 
				store: window.store 
			}); 
		}
	},
	// 表格行内编辑
	editTab:function(){
		$(".table").on("click",".uEditBtn",function(){
			innerOperateId = $(this).parent().parent().find("td").eq(columns.length-1).html();
//			alert("editTab====innerOperateId===="+innerOperateId);
			showLayerByType(innerOperateId,"edit");
		})
	},
	//表格内容删除
	deleteTab:function(){
		$(".table").on("click",".uDeleteBtn",function(){
			innerOperateId = $(this).parent().parent().find("td").eq(columns.length-1).html();
//			alert(urlPath + "deleteTab====innerOperateId===="+innerOperateId);
			deleteSingleById(innerOperateId);
		})
	},
	//当前行内容详情
	detailTab:function(){
		$(".table").on("click",".uDetailBtn",function(){
			innerOperateId = $(this).parent().parent().find("td").eq(columns.length-1).html();
//			alert("detailTab====innerOperateId===="+innerOperateId);
			showLayerByType(innerOperateId,"detail");
		})
	},
	//全选
	selectAllTab:function(){
		$("#tableId").on("click","input[name='checkAll']",function(){
			 if (this.checked){  
		         $("input[name='checkes']:checkbox").each(function(){
		             $(this).prop("checked", true);
		         });
		     } else {
		         $("input[name='checkes']:checkbox").each(function() {     
		             $(this).prop("checked", false);
		         });
		     }
		})
	},
	//列弹出框
	columnAlert:function(){
		$("#tableId").on("click",".roleResource",function(){
			columnId = $(this).attr("data-id");
			columnAlert(columnId);
		});
	}
}

//点击新增按钮
function addRow(){
	$("#fromTemplate").show();
	showLayerByType(0,"add");
}

/*
 * layer弹框title显示
 */
function showTitle(cid){
	if(parseInt(cid) > 0) 			return "修改信息";
	else if(parseInt(cid) == 0) 	return "添加信息";
	else 							return "详细信息";
//	 (cid ? "修改账户" : "添加账户"),'background-color: #F8F8F8;font-size:16px;border-bottom: 1px solid #eee;'
}
/*
 * layer弹框按钮显示
 */
function showBtn(cid){
	if(parseInt(cid) > 0)			return "修改";
	else if(parseInt(cid) == 0) 	return "添加";
	else 							return "关闭";
}

/*
 * 批量删除
 */
function deleteByIds(){
	var columnNum = columns.length;
	array = new Array();
	$("input[type='checkbox']:gt(0):checked").each(
	    function() {
	    	var accountId = $(this).parent().parent().find("td").eq(columnNum-1).html();
	    	array.push(accountId);
	    }
	);
	if (array == 0) {	alert("请勾选!!");	} 
	else {
		layer.confirm('确定删除选中行信息吗?', function(index){
			$.ajax({
				url: urlPath+"/deleteByIds",
				type:"POST",
				data:{"ids":array.toString()},
				success:function(result){
					alert(result.resultMsg);
					layer.close(index);
					selectinfo();
				}
			})
		});
	}
}

/*
 * 单行删除
 */
function deleteSingleById(deleteId){
	layer.confirm('确定删除选中行信息吗?', function(index){
		$.ajax({
			url: urlPath+"/deleteByIds",
			type:"POST",
			data:{"ids":deleteId},
			success:function(result){
				alert(result.resultMsg);
				layer.close(index);
				selectinfo();
			}
		})
	});
}

/*
 * 数据校验
 */
function checkParams(){
	
	var returnMessage = "";		//返回验证结果
	var message = "";		//每行验证结果信息
	//验证框 name ,class , value, class内容数组 ,最大长度 ,错误以弹出框展示
	var name,classInfo,value,array,showAlert;

	formInfo = $("#us").serializeArray();
	for (var i = 0 ; i <formInfo.length; i++) {
		name = formInfo[i].name;
		showAlert = $("#"+name).attr("showAlert");
		message = checkSingleParam(name,"");
		returnMessage += message;
		
		//需要弹框展示并且
		if(showAlert != null && showAlert == ""){
			//有错误信息
			if(message.length > 0){
				alert(message);
			}
		}
		//不需要弹框展示错误信息
		else{
			$("#"+name).parent().next().html("<span class='tdAfterShowInfo' style='float:left;color:red;'>"+message+"</span> ");
		}
	}
	
	return returnMessage;
}


function checkSingleParam(name,showName){

	message = "";
	classInfo = $("#"+name).attr("class");
	value = $("#"+name).val();
	

	array = classInfo.split(" ");
	for (var j = 0 ; j <array.length; j++) {
		
		//数据为空
		if(value == "" || value == null || value.length == 0){
			//验证非空
			if(array[j] == "notNull"){
				message += " 不能为空!"
			}
		}
		//数据不为空
		else if(value.length > 0){
			
			//验证手机号
			if(array[j] == "phone"){
				
				match = /^1[3578]\d{9}$/;
				if(!match.test(value)){
					message += " 输入不合法!"
				}
			}
			
			//验证邮箱
			if(array[j] == "email"){
				
				match = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
				if(!match.test(value)){
					message += " 输入不合法!"
				}
			}
			
			//只能输入字母数字下划线
			if(array[j] == "rightCode"){
				
				match = /^[A-Za-z0-9_\-]+$/;
				if(!match.test(value)){
					message += " 只能输入字母、数字、下划线、中间线!"
				}
			}
			
			//参数验证
			if(array[j] == "rightParam"){
				
//				match = /^[A-Za-z0-9_\-]+$/;
//				if(!match.test(value)){
//					message += " 只能输入字母、数字、下划线、中间线!"
//				}
			}
			
			//只能包含中文、英文、数字、下划线等字符
			if(array[j] == "rightName"){
				
				match = /^[a-zA-Z0-9\u4e00-\u9fa5-_\-]+$/;
				if(!match.test(value)){
					message += " 只能输入中文、英文、数字、下划线、中间线!"
				}
			}
			
			//服务版本
			if(array[j] == "serverVersion"){
				
				match = /^[A-Za-z0-9_.-]+$/
				if(!match.test(value)){
					message += " 只能输入数字、字母、下划线、点、中间线!"
				}
			}
			//校验URL
			if(array[j] == "rightUrl"){
				
				match = /^[A-Za-z0-9_:/.-]+$/
//				match = /^((ht|f)tps?):\/\/[\w\-]+(\.[\w\-]+)+([\w\-\.,@?^=%&:\/~\+#]*[\w\-\@?^=%&\/~\+#])?$/;
				if(!match.test(value)){
					message += " 只能输入数字、字母、下划线、斜线、点、冒号和中间线!"
				}
			}
			
			//验证最大长度
			if(array[j] == "maxLength10" || array[j] == "maxLength11" 
				|| array[j] == "maxLength20" || array[j] == "maxLength30"
					|| array[j] == "maxLength50" || array[j] == "maxLength100"
					|| array[j] == "maxLength200" || array[j] == "maxLength500"){
				
				var maxLength;
				if(array[j] == "maxLength10")	maxLength = 10;
				if(array[j] == "maxLength11")	maxLength = 11;
				if(array[j] == "maxLength20")	maxLength = 20;
				if(array[j] == "maxLength30")	maxLength = 30;
				if(array[j] == "maxLength50")	maxLength = 50;
				if(array[j] == "maxLength100")	maxLength = 100;
				
				if(value.length >0 && value.length > maxLength){
					message += " 长度不能超过 "+ maxLength + " ";
				}
			}
			
			//验证密码
//		    // 匹配密码，以字母开头，长度在6-16之间，只能包含字符、数字和下划线。      
//		    jQuery.validator.addMethod("isPwd", function(value, element) {       
//		         return this.optional(element) || /^[a-zA-Z]\\w{6,16}$/.test(value);       
//		    }, "以字母开头，长度在6-12之间，只能包含字符、数字和下划线。"); 
			
		}
	}
	
	if(message.length > 0) message = showName + message + "\r";
	return message;
}











