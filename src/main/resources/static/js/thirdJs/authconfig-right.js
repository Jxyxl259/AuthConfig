var leftTreeNodeId;		//点击左侧树节点的ID
var editSwitch = true;  //只能单个编辑比如渠道编辑未保存，则不能编辑其它内容
var authMappLists,singleAccountAuthMapps;
$(function(){
	leftTreeNodeId = numberId;	
//	alert("levelId-----"+levelId);
//	alert("numberId-----"+numberId);
	var accountInfo, authencryptinfo, callbackinfo, authmappinfo, serverSmallTypes, serverLists;
	var authTypes, encryptTypes, serverEnvTypes, serverTypes, interfaceTypes, callbackTypes;
	
	RestfulClient.post("/innerconfig/getAllTypesInfo", {}, 
	function(result) {
		maps = result.dataList;
		authTypes = maps.authTypes;					//获取鉴权类型
		encryptTypes = maps.encryptTypes;			//获取加密类型
		serverEnvTypes = maps.serverEnvTypes;		//获取环境类型列表
		serverTypes = maps.serverTypes;				//获取服务类型
		interfaceTypes = maps.interfaceTypesSustom;	//获取渠道对应的特定接口类型
		callbackTypes = maps.callbackTypes;			//获取回调类型
		serverMappingSmall = maps.serverMappingSmall;//获取所有服务小类
		
		$(".accountinfodiv").hide();
		$(".accountMappInfoDIV").hide();
		$(".info").hide();
		$(".addInfo").hide();
		
		if(levelId == 0){
			//点击左侧菜单，加载树
		}else if(levelId == 9){
			//点击渠道1,展示特定信息
			$(".info").show();
			$("#scheme_msg").remove();
			$("#authen_msg").remove();
			$("#encrpty_msg").remove();
			$("#callBack_msg").remove();
			loadElseInfos();
		}else if(levelId == 1){
			//点击账号，右侧显示账号信息
			$(".accountinfodiv").show();
			//自定义配置的账户信息中不展示此信息
			if(numberId > 1) $(".accountMappInfoDIV").show();
			loadAccount();
		}else if(levelId == 5 || levelId == 6){
			if(levelId == 6){ $("#span_title1").html("方案"); }
			$(".addInfo").show();
			add.init();
		}else if(levelId == 4){
		    $(".accountinfodiv").show();
			accountAdd();
		}else{
//			if(levelId == 3){ $("#span_title").html("方案"); }
			$(".info").show();
			loadElseInfos();
		}
		
		//加载"showTotalInfo"中的内容
		if(levelId == 0){
			$("#showTotalInfodiv").hide();
		}else{
			$("#showTotalInfodiv").show();
			showTotalInfo();
		}
	});



	//加载账户右侧信息
	function loadAccount(){
		//获取渠道信息
		RestfulClient.post("/accountAuth/getAccountAndMappingInfo", {
			"accountId": numberId
		},
		function(result) {
			returnInfos = result.dataList;
			accountInfo = returnInfos["returnAccount"];
			authMappLists = returnInfos["authMappLists"];
			singleAccountAuthMapps = returnInfos["singleAccountAuthMappLists"];
			accountEdit.init();
		});
	}
	
	
	
	//加载渠道与方案右侧信息
	function loadElseInfos(){
		//根据级别ID获取对应的信息
		if(levelId == 2 || levelId ==9){
			//获取渠道信息
			RestfulClient.post("/innerconfig/getFullDatasourceInfo", {
				"dataSourceId" : numberId
			}, 
			function(result) {
				//渠道所有信息
				infos = result.dataList;
				//方案所有信息
				projectinfos = infos.projectList;
				if(projectinfos){
					numberId = projectinfos[0].projectId;
					//鉴权信息
					authencryptinfo = projectinfos[0].authEncryptDto;
					//回调信息
					callbackinfo = projectinfos[0].callbackList;
					//方案服务关系信息
					authmappinfo = projectinfos[0].authmappList;
					edit.init();
				}else{
					alert("数据逻辑错误");
				}
				
//				alert("111---projectinfos----"+projectinfos);
			});
			
		}
		else if(levelId == 3){
			
			//获取方案信息
			RestfulClient.post("/innerconfig/getFullProjectInfo", {
				"projectId" : numberId
			}, 
			function(result) {
				//方案所有信息
				projectinfos = result.dataList;
				//鉴权信息
				authencryptinfo = projectinfos.authEncryptDto;
				//回调信息
				callbackinfo = projectinfos.callbackList;
				//方案服务关系信息
				authmappinfo = projectinfos.authmappList;
				edit.init();
//				alert("2222---projectinfos----"+projectinfos);
			});
			
		}
	}

	var add = {
		init:function(){
			this.loadAcct()
		},
		loadAcct:function(){
			var basicMsg;
			if(levelId == 5){ //渠道
				basicMsg = '<li class="first_info"><div class="d_info">渠道代码</div>：<div class="d_info"><input id="dataSource" name="dataSource" class="info_content_acct_add notNull rightCode" type="text" maxlength="30" /></div></li>'+
					'<li class="first_info"><div class="d_info">渠道名称</div>：<div class="d_info"><input id="sourceName" name="sourceName" class="info_content_acct_add notNull rightName" type="text" maxlength="30" /></div></li>';
			}else if(levelId == 6){ //方案
				basicMsg = '<li class="first_info"><div class="d_info">方案代码</div>：<div class="d_info"><input id="projectCode" name="projectCode" class="info_content_acct_add notNull rightCode" type="text" maxlength="30" /></div></li>'+
				'<li class="first_info"><div class="d_info">方案名称</div>：<div class="d_info"><input id="projectName" name="projectName" class="info_content_acct_add notNull rightName" type="text" maxlength="30" /></div></li>';
			}
			
			var schemeMsg = '<li class="first_info"><div class="d_info">鉴权类型</div>：<div class="d_info"><select id="authType" name="authType" class="info_content_acct_add" onchange="changeAuthType();" ></select></div></li>'+
				'<li class="first_info"><div class="d_info">鉴权参数1</div>：<div class="d_info"><input id="authParam1" class="info_content_acct_add rightParam maxLength500" type="text" /></div></li>'+
				'<li class="first_info"><div class="d_info">鉴权参数2</div>：<div class="d_info"><input id="authParam2" class="info_content_acct_add rightParam maxLength500" type="text" /></div></li>'+
				'<li class="first_info"><div class="d_info">加密类型</div>：<div class="d_info"><select id="encryptType" name="authType" class="info_content_acct_add" onchange="changeEncryptType();"></select></div></li>'+
				'<li class="first_info"><div class="d_info">加密参数1</div>：<div class="d_info"><input id="encryptParam1" class="info_content_acct_add rightParam maxLength500" type="text" /></div></li>'+
				'<li class="first_info"><div class="d_info">加密参数2</div>：<div class="d_info"><input id="encryptParam2" class="info_content_acct_add rightParam maxLength500" type="text" /></div></li>'+
				'<li class="li_info"><div class="d_info text_center"><button id="saveDatasource" onclick="saveDatasourProd();" class="edit_btn btn_Edit">确定</button></div></li>';
			$("#datasource_msg").append(basicMsg).append(schemeMsg);
			
			for (var i = 0 ; i < authTypes.length; i++) {
				$("#authType").append("<option value='"+authTypes[i][0]+"'>"+authTypes[i][1]+"</option>");
			}	
			for (var i = 0 ; i < encryptTypes.length; i++) {
				$("#encryptType").append("<option value='"+encryptTypes[i]+"'>"+encryptTypes[i]+"</option>");
			}
			
			changeEncryptType();
		}
	}
	
	
	var edit = {
		init:function(){
			this.tabEdit();//初始化显示
			this.addClick();//添加
			this.editFn();//编辑
			this.delLi();//删除
			this.saveFn();//保存
			
		},
		tabEdit:function(){
			//方案或者渠道基本信息 
			if(levelId == 3){
				
				schemeMsg = '<li class="first_li"><button class="head_btn"><span id="span_title">方案</span></button></li>';
				//方案基本信息
				schemeMsg += '<li class="first_info_wrap" id="'+projectinfos.projectId+'">'+
				    '<div class="first_info_left">'+
				    '<div class="d_info d_info_supper">方案代码 ：'+projectinfos.projectCode+'</div>'+
					'<div class="d_info d_info_supper" title="方案名称 ：'+projectinfos.projectName+'">方案名称 ：<input id="projectName" class="info_content add_text notNull rightName maxLength30" type="text" readonly value="'+projectinfos.projectName+'"/></div>'+				
					'</div>'+
					'<div class="first_info_right d_edit"><button id="editProject1" class="edit_btn btn_Edit">编辑</button></div></li>';
				$("#scheme_msg").append(schemeMsg);
				
				
			}else if(levelId == 2){
				
				schemeMsg = '<li class="first_li"><button class="head_btn"><span id="span_title">渠道</span></button></li>';
				//渠道基本信息
				schemeMsg += '<li class="first_info_wrap" id="'+infos.dataSourceId+'">'+
				    '<div class="first_info_left">'+
				    '<div class="d_info d_info_supper">渠道代码 ：'+infos.dataSource+'</div>'+
					'<div class="d_info d_info_supper" title="渠道名称 ：'+infos.sourceName+'">渠道名称 ：<input id="sourceName" class="info_content add_text notNull rightName maxLength30" type="text" readonly value="'+infos.sourceName+'"/></div>'+
					
					'</div>'+
					'<div class="first_info_right d_edit"><button id="editDatasource1" class="edit_btn btn_Edit">编辑</button></div></li>';
				$("#scheme_msg").append(schemeMsg);
			}
				
			//鉴权配置信息
			if(authencryptinfo && levelId != 9){
				var authenMsg = '<li class="first_li"><button class="head_btn">鉴权</button></li>'+
					'<li class="li_info li_info_wrap" id="'+authencryptinfo.authId+'">'+
					'<div class="li_info_left">'+	
					'<div class="d_info">鉴权类型&nbsp;：<select id="authType" name="authType" class="selectWidth" onchange="changeAuthType();" ></select></div>'+
					'<div class="d_info">鉴权参数1 ：<input id="authParam1" class="info_content rightParam maxLength500" type="text" readonly value="'+$.trim(authencryptinfo.authParam1)+'"/></div>'+
					'<div class="d_info">鉴权参数2 ：<input id="authParam2" class="info_content rightParam maxLength500" type="text" readonly value="'+$.trim(authencryptinfo.authParam2)+'"/></div>'+
					'</div><div class="li_info_right d_edit"><button id="editAuthencrypt" class="edit_btn btn_Edit">编辑</button></div></li>';
				$("#authen_msg ").append(authenMsg);
				
				var encrptyMsg = '<li class="first_li"><button class="head_btn">加密</button></li>'+
					'<li class="li_info li_info_wrap" id="'+authencryptinfo.authId+'">'+
					'<div class="li_info_left">'+	
					'<div class="d_info">加密类型&nbsp;：<select id="encryptType" name="encryptType" class="selectWidth" onchange="changeEncryptType();"></select></div>';
				if("NO_ENCRYPT" == authencryptinfo.encryptType){
					encrptyMsg += '<div class="d_info">加密参数1 ：<input id="encryptParam1" class="info_content rightParam maxLength500" type="text" disabled="disabled" readonly value=""/></div>'+
						'<div class="d_info">加密参数2 ：<input id="encryptParam2" class="info_content rightParam maxLength200" type="text" disabled="disabled" readonly value=""/></div>';
				}else{
					encrptyMsg += '<div class="d_info">加密参数1 ：<input id="encryptParam1" class="info_content rightParam maxLength500" type="text" readonly value="'+$.trim(authencryptinfo.encryptParam1)+'"/></div>'+
						'<div class="d_info">加密参数2 ：<input id="encryptParam2" class="info_content rightParam maxLength200" type="text" readonly value="'+$.trim(authencryptinfo.encryptParam2)+'"/></div>';
				}

				encrptyMsg += '</div><div class="li_info_right d_edit"><button id="editAuthencrypt1" class="edit_btn btn_Edit">编辑</button></div></li>';
				$("#encrpty_msg ").append(encrptyMsg);
				
				for (var i = 0 ; i < authTypes.length; i++) {
					$("#authType").append("<option value='"+authTypes[i][0]+"'>"+authTypes[i][1]+"</option>");
					if(authencryptinfo.authType == authTypes[i][0]){
						$("#authType").find("option[value='"+authencryptinfo.authType+"']").attr("selected",true); 
					}
				}	
				for (var i = 0 ; i < encryptTypes.length; i++) {
					$("#encryptType").append("<option value='"+encryptTypes[i]+"'>"+encryptTypes[i]+"</option>");
					if(authencryptinfo.encryptType == encryptTypes[i]){
						$("#encryptType").find("option[value='"+authencryptinfo.encryptType+"']").attr("selected",true); 
					}
				}
			}
				
				
			//选择服务
			var authenMsg1 = '<li class="first_li"><button class="head_btn">服务</button></li>'+
				'<li class="li_info">'+
			    '<div class="li_info_left">'+
				'<div class="d_info">环境类型 ：<select id="serverEnvType" name="serverEnvType" onchange="showServerLists();" class="selectWidthAble"></select></div>'+
				'<div class="d_info">服务类型 ：<select id="serverType" name="serverType" onchange="showSmallTypes();" class="selectWidthAble" ></select></div>'+
				'<div class="d_info">服务小类 ：<select id="serverSmallType" name="serverSmallType" onchange="showServerLists();" class="selectWidthAble" ></select></div>'+
				'<div class="d_info">服务地址 ：<select id="serverList" name="serverList" class="selectWidthAble" ></select></div>'+
				'</div>'+
				'<div class="li_info_right d_edit"><button class="btn_Edit add_btn server_addBtn">添加</button></div></li>'
			$("#server_msg ").append(authenMsg1);
			
			for (var i = 0 ; i < serverEnvTypes.length; i++) {
				$("#serverEnvType").append("<option value='"+serverEnvTypes[i]+"'>"+serverEnvTypes[i]+"</option>");
			}	
			for (var i = 0 ; i < serverTypes.length; i++) {
				$("#serverType").append("<option value='"+serverTypes[i][0]+"'>"+serverTypes[i][1]+"</option>");
			}	
			
			//加载下拉框中内容
			showSmallTypes();

			//服务配置信息
			if(authmappinfo){
				for (var i = 0 ; i < authmappinfo.length; i++) {
					mappid = authmappinfo[i].mappingId;
					
					var serverType2,serverSmallType2;
					for (var j = 0 ; j < serverTypes.length; j++) {
						if(serverTypes[j][0] == authmappinfo[i].serverType){
							serverType2 = serverTypes[j][1];
						}
					}
					for (var j = 0 ; j < serverMappingSmall.length; j++) {
						if(serverMappingSmall[j][0] == authmappinfo[i].serverSmallType){
							serverSmallType2 = serverMappingSmall[j][1];
						}
					}
					
					var server_msg = 
						'<li class="li_info" id="'+mappid+'">'+
						'<div class="li_info_left">';
						
					if(levelId == 3){
						server_msg += '<div class="d_info">接口类型 ：PROJECT</div>';
					}else if(levelId == 2){
						server_msg += '<div class="d_info">接口类型 ：DATA_SOURCE</div>';
					}else if(levelId == 9){
//						server_msg += '<div class="d_info">接口类型 ：<select id="requestType_'+mappid+'" name="requestType" class="selectWidth" ></select></div>';
						server_msg += '<div class="d_info">接口类型 ：HISTORY_CUSTOM</div>';
					}
					
						server_msg += '<div class="d_info" title=" 接口地址：'+authmappinfo[i].requestUrl+'">接口地址 ：<input id="requestUrl_'+mappid+'" class="info_content d_info_title notNull maxLength50" type="text" readonly value="'+$.trim(authmappinfo[i].requestUrl)+'"/></div>'+						
						'<div class="d_info">环境类型 ：'+authmappinfo[i].serverEnv+'</div>'+
//						'<div class="d_info">服务类型 ：'+serverType2+'</div>'+
						'<div class="d_info">服务小类 ：'+serverSmallType2+'</div>'+
						'<div class="d_info d_info_title" title="服务地址 ：'+authmappinfo[i].serverUrl+'">服务地址 ：'+authmappinfo[i].serverUrl+'</div>'+
						'</div>'+
						'<div class="li_info_right d_edit" id="'+mappid+'">'+
						'<button id="editAuthMapp_'+mappid+'" class="edit_btn btn_Edit">编辑</button><button class="btn_Edit del_btn">删除</button></div></li>'
						
						$("#server_msg").append(server_msg);
//					if(levelId == 9){
//						for (var j = 0 ; j < interfaceTypes.length; j++) {
//							$("#requestType_"+mappid).append("<option value='"+interfaceTypes[j]+"'>"+interfaceTypes[j]+"</option>");
//							
//							if(authmappinfo[i].requestType == interfaceTypes[j]){
//								$("#requestType_"+mappid).find("option[value='"+authmappinfo[i].requestType+"']").attr("selected",true); 
//							}
//						}
//					}
				}
			}
			//回调配置信息
			callBack_msg = '<li class="first_li"><button class="head_btn">回调</button><button class="btn_Edit add_btn" style="margin-right:10px;">添加</button></li>';
			$("#callBack_msg").append(callBack_msg);
			if(callbackinfo && levelId != 9){
				for (var i = 0 ; i < callbackinfo.length; i++) {
					callid = callbackinfo[i].callbackUrlId;
					
					callBack_msg1 = '<li class="li_info" id="'+callid+'">'+
					    '<div class="li_info_left">'+
					    '<div class="d_info">环境类型 ：<select id="callbackEnv_'+callid+'" name="callbackEnv" class="selectWidth" ></select></div>'+
						'<div class="d_info">回调类型 ：<select id="callbackType_'+callid+'" name="callbackType" class="selectWidth" ></select></div>'+
						//'<div class="d_info d_info_title" title="回调地址 ：'+callbackinfo[i].callbackUrl+'">回调地址 ：<input class="info_content" type="text" readonly value="'+callbackinfo[i].callbackUrl+'"/></div>'+
						'<div class="d_info d_info_supper" style="width:75%;" title="回调地址 ：'+callbackinfo[i].callbackUrl+'">回调地址 ：<input id="callbackUrl_'+callid+'" class="info_content d_info_title notNull maxLength100" type="text" readonly value="'+$.trim(callbackinfo[i].callbackUrl)+'"/></div>'+
						'</div>'+
						'<div class="li_info_right d_edit"><button id="editCallback_'+callid+'" class="edit_btn btn_Edit">编辑</button><button class="btn_Edit del_btn">删除</button></div></li>';
					
					$("#callBack_msg").append(callBack_msg1);
					
					for (var j = 0 ; j < callbackTypes.length; j++) {
						$("#callbackType_"+callid).append("<option value='"+callbackTypes[j]+"'>"+callbackTypes[j]+"</option>");
						
						if(callbackinfo[i].callbackType == callbackTypes[j]){
							$("#callbackType_"+callid).find("option[value='"+callbackinfo[i].callbackType+"']").attr("selected",true); 
						}
					}		
					
					for (var j = 0 ; j < serverEnvTypes.length; j++) {
						$("#callbackEnv_"+callid).append("<option value='"+serverEnvTypes[j]+"'>"+serverEnvTypes[j]+"</option>");
						
						if(callbackinfo[i].callbackEnv == serverEnvTypes[j]){
							$("#callbackEnv_"+callid).find("option[value='"+callbackinfo[i].callbackEnv+"']").attr("selected",true); 
						}
					}	
				}	
			}
			
			//所有下拉默认禁用
			$(".selectWidth").attr("disabled","disabled");
		},
		addClick:function(){
			$(".info_ul").on("click",".add_btn",function(){
				var that = $(this);
				var pId ="#"+$(this).parent().parent().parent().attr("id");
				if (pId == "#undefined"){
					pId ="#"+$(this).parent().parent().attr("id");
				}
				if($("#callbackType_a").length > 0 || $("#requestUrl_a").length > 0){
					alert("请先保存新增内容");
				}else {
					//判断下拉框中是否有内容
					if(pId == "#server_msg"){
						var type1 = $("#serverType").val(),type2 = $("#serverSmallType").val(),type3 = $("#serverList").val();
						if(type1 != null && type1.length > 0 
								&&  type2 != null && type2.length > 0
								&&  type3 != null && type3.length > 0){
							edit.tabAdd(pId);
						}else{
							alert("请先选择对应服务信息");
						}
//						edit.tabAdd(pId);
					}else
						edit.tabAdd(pId);
				}
			})
		},
		tabAdd:function(ulId){
			var tabTr = '';
			switch (ulId){
				case "#callBack_msg":
					tabTr ='<li class="li_info">'+
					    '<div class="li_info_left">'+
					    '<div class="d_info"><span class="span_title">环境类型 ：<select id="callbackEnv_a" name="callbackEnv" class="selectWidth"></select></div>'+
						'<div class="d_info"><span class="span_title">回调类型 ：<select id="callbackType_a" name="callbackType" class="selectWidth"></select></div>'+
						'<div class="d_info d_info_supper" style="width:75%;" >回调地址 ：<input id="callbackUrl_a" class="add_text notNull maxLength100" type="text" />'+
						'</div>'+
						'</div><div class="li_info_right d_edit"><button id="saveCallback_a" class="save_btn btn_Edit">保存</button>'+
						'<button class="btn_Edit del_btn">删除</button></div></li>'
					$("#callBack_msg li").eq(0).after(tabTr);
					for (var j = 0 ; j < callbackTypes.length; j++) {
						$("#callbackType_a").append("<option value='"+callbackTypes[j]+"'>"+callbackTypes[j]+"</option>");
					}
					for (var j = 0 ; j < serverEnvTypes.length; j++) {
						$("#callbackEnv_a").append("<option value='"+serverEnvTypes[j]+"'>"+serverEnvTypes[j]+"</option>");
					}
				break;
				case "#server_msg":
				    tabTr = '<li class="li_info">'+
						    '<div class="li_info_left">';
					if(levelId == 3){
						tabTr += '<div class="d_info">接口类型 ：PROJECT</div>';
					}else if(levelId == 2){
						tabTr += '<div class="d_info">接口类型 ：DATA_SOURCE</div>';
					}else if(levelId == 9){
						tabTr += '<div class="d_info">接口类型 ：HISTORY_CUSTOM</div>';
//						tabTr += '<div class="d_info"><span class="span_title">接口类型：</span><select id="requestType_a" name="requestType"class="selectWidth notNull maxLength100"></select></div>';
					}
					tabTr += '<div class="d_info  d_info_title">接口地址 ：<input id="requestUrl_a" class="add_text d_info_title notNull rightUrl maxLength100" type="text" /></div>'+
						'<div class="d_info">环境类型 ：'+$("#serverEnvType").val()+'</div>'+
//						'<div class="d_info">服务类型 ：'+$("#serverType option:selected").text()+'</div>'+
						'<div class="d_info" >服务小类 ：'+$("#serverSmallType option:selected").text()+'</div>'+
						'<div class="d_info d_info_title" title="服务地址 ：'+$("#serverList option:selected").text()+'">服务地址 ：'+$("#serverList option:selected").text()+'</div>'+
						'</div>'+
						'<div class="li_info_right d_edit">'+
						'<button id="saveRequest_a" class="save_btn btn_Edit">保存</button>'+
						'<button class="btn_Edit del_btn">删除</button></div></li>'
					$("#server_msg li").eq(1).after(tabTr);
					
//					if(levelId == 9){
//						for (var j = 0 ; j < interfaceTypes.length; j++) {
//							$("#requestType_a").append("<option value='"+interfaceTypes[j]+"'>"+interfaceTypes[j]+"</option>");
//						}
//					}
				break;
			}
			
		},
		//编辑
		editFn:function(){
			$(".info_ul").on("click",".d_edit .edit_btn",function(){
				if($(this).html() == '取消'){
					//还原
				    recoverOrigin(originalData,$(this)) 
				    $(this).remove();
				    editSwitch = true;
				    return;
			    }
				//var str = $(this).html();
				var str = $(this).html()=="编辑"?"确定":"编辑";
//				$(this).html(str); 
				var data;
				if(str =="确定"){
					if(!editSwitch){
						alert("亲！您有数据未保存，请先保存数据");
						$(this).html('编辑');
						return;
					}
					$(this).html(str); 
					editSwitch = false;
					var buttonId = $(this).attr('id');
					if(buttonId == "editProject1" || buttonId == "editDatasource1" || buttonId == "editAuthencrypt" || buttonId == "editAuthencrypt1"){
						//渠道、方案、鉴权、加密添加取消按钮
						var recoveButton = '<button class="edit_btn btn_Edit">取消</button>';
						$(this).after(recoveButton);
					}else{
						$(this).next().text("取消");
					}
					$(this).parent().siblings().find(".info_content").removeAttr("readonly").css("border","1px solid #ccc");
					$(this).parent().siblings().find(".selectWidth").removeAttr("disabled");
					//保存原始数据
					var lineId = $(this).parent().parent()[0].id;
					var params = $(this).parent().siblings().find(".info_content");
					var ulId = $(this).parent().parent().parent()[0].id;
					saveOriginalData(ulId, lineId, params)
				}else if(str=="编辑"){
					$(this).html("确定"); 
					var lineId = $(this).parent().parent()[0].id;
					var tt = $(this).parent().siblings().find(".info_content");
					var ulId = $(this).parent().parent().parent()[0].id;
					//编辑后保存
					updateSave(ulId,lineId,tt,$(this));
				}
			})
		},
		//删除
		delLi:function(){
				$(".info_ul").on("click",".d_edit .del_btn",function(){
					if($(this).html() == '取消'){
						recoverOrigin(originalData,$(this))
						editSwitch = true;
						return;
					}
				var ulId =$(this).parent().parent().parent().attr("id");
				var liId =$(this).parent().parent().attr("id");
				if((typeof(liId) != "undefined")){
					deleteInfo(ulId,liId,$(this));
				}else{
//					if("server_msg"==ulId){
//						$(this).parent().parent().prev().remove();
//					}
					$(this).parent().parent().remove();
				}
			})
		},
		//添加
		saveFn:function(){
			$(".info_ul").on("click",".d_edit .save_btn",function(){
				var strSave = $(this).html()=="保存"?"编辑":"保存";
				$(this).html(strSave);
				if(strSave =="编辑"){
					var tt = $(this).parent().siblings().find(".add_text");
					var ulId = $(this).parent().parent().parent()[0].id;
					addSave(ulId,tt,$(this));

				}else{
					$(this).parent().siblings().find(".add_text").removeAttr("readonly").css("border","1px solid #ccc");
				}
			})
		}
	}
	

	var mappingId = "";
	var accountEdit = {
		init:function(){
			this.loadAcct();
			this.editFn();
			this.accClick();
			this.saveFn();
			this.deleteFn();
		},
		loadAcct:function(){
			schemeMsg = '<li class="first_li"><button class="head_btn">账号</button></li>';
			schemeMsg += '<li class="first_info" id="'+accountInfo.accountId+'">'+
				'<div class="d_info">账号ID</div>：<div class="d_info"><input id="editAcctAppid" class="info_content_acct" type="text" readonly value="'+accountInfo.appId+'"/></div></li>'+
				'<li class="first_info"><div class="d_info">账号代码</div>：<div class="d_info"><input id="editAppCode" class="info_content_acct" type="text" readonly value="'+accountInfo.appCode+'"/></div></li>'+
				'<li class="first_info"><div class="d_info"><font style="color: red">*</font>账号名称</div>：<div class="d_info"><input id="editAcctName" class="info_content_acct notNull rightName maxLength30" type="text" readonly value="'+accountInfo.accountName+'"/></div></li>'+
				'<li class="first_info"><div class="d_info">渠道手机</div>：<div class="d_info"><input id="editAcctMobile" class="info_content_acct phone maxLength11" type="text" readonly value="'+$.trim(accountInfo.mobile)+'"/></div></li>'+
				'<li class="first_info"><div class="d_info">渠道邮箱</div>：<div class="d_info"><input id="editAcctEmail" class="info_content_acct email maxLength50" type="text" readonly value="'+$.trim(accountInfo.email)+'"/></div></li>'+
				'<li class="li_info"><div class="d_info text_center"><button id="editAccount" class="edit_btn btn_Edit">编辑</button></div></li>';
			$("#account_msg").append(schemeMsg);
			
			if(numberId > 1){	
				tabTr = '<li class="first_li"><button class="head_btn">服务</button><button class="add_btn addAccountAuthMapp">添加数据</button></li>';
				for (var i = 0 ; i < singleAccountAuthMapps.length; i++) {
					info4 = singleAccountAuthMapps[i];
					tabTr +='<li class="li_info" id="'+info4.id+'">'+
						'<div class="li_info_left">'+
						'<div class="d_info d_info_title" title="接口地址：'+info4.requestUrl+'" ><span class="span_title">接口地址 ：'+info4.requestUrl+'</div>'+
						'<div class="d_info"><span class="span_title">环境类型 ：'+info4.serverEnv+'</div>'+
						'<div class="d_info"><span class="span_title">接口类型 ：'+info4.requestType+'</div>'+
						'<div class="d_info"><span class="span_title">服务类型 ：'+info4.serverTypeStr+'</div>'+
						'<div class="d_info d_info_title" title="服务地址：'+info4.serverUrl+'" >服务地址：'+info4.serverUrl+'</div>'+
						'</div><div class="li_info_right d_edit"><button class="btn_Edit del_btn">删除</button></div></li>';
				}
				$("#account_mapp_msg").append(tabTr);
			}
			
		},
		editFn:function(){
			$("#editAccount").on("click",function(){
				var str = $(this).html();
				var data;
				if(str=='编辑'){
					$(this).html('确定');
					//添加删除
					var recoverMeessage = '&nbsp;&nbsp;<button id="recoverAccountId" class="edit_btn btn_Edit">取消</button>';
					$(this).after(recoverMeessage);
					$(".accountinfodiv").attr("id",accountInfo.accountId);
					$("#editAcctName").removeAttr("readonly").css("border","1px solid #ccc");
					$("#editAcctMobile").removeAttr("readonly").css("border","1px solid #ccc");
					$("#editAcctEmail").removeAttr("readonly").css("border","1px solid #ccc");
					//保存未修改信息
					var originalAccount = saveOriginalAccount()
					$("#recoverAccountId").on('click',function(){
						recoverOrigin(originalAccount,$(this));
					})
				}else if(str=="确定"){
					//编辑后保存
					updateAcctSave("edit",$(this));
				}
			});
		},
		accClick:function(){
			$(".addAccountAuthMapp").on("click",function(){

				if(!editSwitch){
					alert("请先保存新增内容");
					return ;
				}		
				editSwitch = false;
				var tabTr ='<li class="li_info" id="addAccountMapp_a">'+
				    '<div class="li_info_left">'+
				    '<div class="d_info"><span class="span_title">接口列表 ：<input id="accountMappList_a" type="text" placeholder="请输入接口地址名称..." "/></div>'+
					'<div class="d_info"><span class="span_title">环境类型 ：<span id="accountMappServerEnv"></span></div>'+
					'<div class="d_info"><span class="span_title">接口类型 ：<span id="accountMappRequestType"></span></div>'+
					'<div class="d_info"><span class="span_title">服务类型 ：<span id="accountMappServerType"></span></div>'+
					'<div class="d_info d_info_title" style="width:420px;" id="showUrl11">服务地址 ：<span id="accountMappServerUrl"></span></div>'+
					'</div><div class="li_info_right d_edit"><button id="saveAccountMapp_a" class="save_btn btn_Edit">保存</button><button class="btn_Edit del_btn">删除</button></div></li>';
				$("#account_mapp_msg li").eq(0).after(tabTr);
				
				var availableTags = new Array();
				
				for(var i=0;i < authMappLists.length; i++){
					var map = {};
					map["mappingId"] = authMappLists[i].mappingId;
					map["serverId"] = authMappLists[i].serverId;
					map["value"] = authMappLists[i].requestUrl;
					map["serverEnv"] = authMappLists[i].serverEnv;
					map["requestType"] = authMappLists[i].requestType;  //接口类型
					map["serverUrl"] = authMappLists[i].serverUrl;
					map["serverTypeStr"] = authMappLists[i].serverTypeStr;
					availableTags.push(map);					
				};
			
				$("#accountMappList_a").autocomplete({
				     source: availableTags,
				     select: function (e, ui) {//e是jquery对象,ui里只有一个我们选中的item对象		
				    	 	mappingId = ui.item.mappingId;
			                $('#accountMappServerEnv').html(ui.item.serverEnv);
							$("#accountMappRequestType").html(ui.item.requestType);
							$("#accountMappServerType").html(ui.item.serverTypeStr);
							$("#accountMappServerUrl").html(ui.item.serverUrl);
							$("#showUrl11").attr("title","服务地址:"+ui.item.serverUrl);
                
			            },
			          change: function (e, ui) {
			                //文本框里的值变化后要做的事,一般在这里可以控制如果文本框的值不是下拉框中的数据
			               if(ui.item == null){
			            	   alert("请在加载出的服务中选择");
			            	   $('#accountMappList_a').val('');
			            	   $('#accountMappServerEnv').html('');
								$("#accountMappRequestType").html('');
								$("#accountMappServerType").html('');
								$("#accountMappServerUrl").html('');
								$("#showUrl11").attr("title","服务地址:");
			               }
			            }
				});
				
//				for (var i = 0 ; i < authMappLists.length; i++) {
//					$("#accountMappList_a").append("<option value='"+authMappLists[i].mappingId+"'>"+authMappLists[i].requestUrl+"</option>");
//				}
				
//				changeAccountAuthMapp();
			});
		},
		//添加
		saveFn:function(){
			$(".info_ul").on("click",".d_edit .save_btn",function(){
				var selectVal1 = $("#accountMappList_a").val();
				if(selectVal1 != null){
					RestfulClient.post("/accountAuth/addAccountAuthmapp", 
					{
						"accountId" :numberId, 
						"mappingId" : mappingId
					},
					function(result) {
						alert(result.resultMsg);
						//判断结果
						if(result.resultCode != "9999"){
							editSwitch = true;
							$("#accountMappList_a").attr("disabled","disabled");
							$("#accountMappList_a").attr("id","accountMappList_"+result.resultCode);
							$("#addAccountMapp_a").attr("id",result.resultCode);
						}
			 		});
				}
			})
		},
		//删除
		deleteFn:function(){
			$(".info_ul").on("click",".d_edit .del_btn",function(){
				var m = $(this).parent().parent();
				var liId =m.attr("id");

				if(liId == "addAccountMapp_a"){
					m.remove();
				}else{
					RestfulClient.post("/accountAuth/deleteAccountAuthMapp", 
					{
						"id" :liId
					},
					function(result) {
						alert(result.resultMsg);
						//判断结果 失败不做任何处理
						if(result.resultCode != "9999"){
							m.remove();
						}
					});
				}
				
			});
		},
	}

})


function changeAccountAuthMapp(){
	var selectVal1 = $("#accountMappList_a").val();
	if(selectVal1 != null){
		for (var i = 0 ; i < authMappLists.length; i++) {
			info5 = authMappLists[i];
			if(info5.mappingId == selectVal1){
				
				$("#accountMappServerEnv").html(info5.serverEnv);
				$("#accountMappRequestType").html(info5.requestType);
				
				for (var j = 0 ; j < serverMappingSmall.length; j++) {
					if(serverMappingSmall[j][0] == info5.serverType){
						serverTypeStr = serverMappingSmall[j][1];
					}
				}
				$("#accountMappServerType").html(serverTypeStr);
				$("#accountMappServerUrl").html(info5.serverUrl);
				$("#showUrl11").attr("title","服务地址:"+info5.serverUrl);
			}
		}
	}
}


function accountAdd(){
	
	$("li").remove();
	
	var schemeMsg = '<li class="first_li"><button class="head_btn">新增账号</button></li>'+
		'<li class="first_info"><div class="d_info"><font style="color: red">*</font>账号ID</div>：<div class="d_info"><input id="editAcctAppid" class="info_content_acct_add notNull rightCode maxLength50" type="text" />'+
		'<input type="button" id="genAppid" name="genAppid" class="btn btn-primary  btn_Edit" onclick="genAppId(1);" value="生成账号ID"></div></li>'+
		'<li class="first_info"><div class="d_info"><font style="color: red">*</font>账号代码</div>：<div class="d_info"><input id="editAppCode" class="info_content_acct_add notNull rightCode maxLength30" type="text" /></div></li>'+
		'<li class="first_info"><div class="d_info"><font style="color: red">*</font>账号名称</div>：<div class="d_info"><input id="editAcctName" class="info_content_acct_add notNull rightName maxLength30" type="text" /></div></li>'+
		'<li class="first_info"><div class="d_info">渠道手机</div>：<div class="d_info"><input id="editAcctMobile" class="info_content_acct_add phone maxLength11" type="text"/></div></li>'+
		'<li class="first_info"><div class="d_info">渠道邮箱</div>：<div class="d_info"><input id="editAcctEmail" class="info_content_acct_add email maxLength50" type="text" /></div></li>'+
		'<li class="li_info"><div class="d_info text_center"><button id="addAccount" class="edit_btn btn_Edit">确定</button></div></li>';
	$("#account_msg").append(schemeMsg);
	
	$("#addAccount").on("click",function(){
		//新增账户
		updateAcctSave("add",$(this));
	});
}


/*
 * 鉴权类型为COMMON,需在鉴权参数文本框后添加 自动生成appId按钮
 * 新增的时候添加 按钮,
 */
function changeAuthType(){
	
	var slectedVal = $("#authType").val();
	var paramValue = $("#authParam1").val();
	
	if(slectedVal == "COMMON_AUTH"){
		$("#authParam1").val(genAppId(2));
		$("#authParam2").val(genAppId(3));
		//只有新增的时候有效
		if(levelId >= 5){
			var genButton1 = "<input type='button' id='genAppid' name='genAppid' class='btn btn-primary btn_Edit' onclick='genAppId(2);' value='生成鉴权参数'>";
			$("#authParam1").after(genButton1);
			var genButton2 = "<input type='button' id='genAppid' name='genAppid' class='btn btn-primary btn_Edit' onclick='genAppId(3);' value='生成鉴权参数'>";
			$("#authParam2").after(genButton2);
		}
	}else{
		$("#genAppid").remove();
		$("#authParam1").val(""); 
		$("#authParam2").val(""); 
	}
}

/*
 * 加密类型为 NO-ENCRTPT,需将加密参数置为不可输入状态,并在文本框后添加说明
 */
function changeEncryptType(){
	var slectedVal = $("#encryptType").val();
	var paramValue = $("#encryptParam1").val();
	if(slectedVal == "NO_ENCRYPT"){
		$("#encryptParam1").attr("disabled","disabled"); 
		$("#encryptParam2").attr("disabled","disabled"); 
		$("#encryptParam1").val(""); 
		$("#encryptParam2").val(""); 
	}else{
		$("#encryptParam1").removeAttr("disabled","disabled"); 
		$("#encryptParam1").val(paramValue); 
		$("#encryptParam2").removeAttr("disabled","disabled"); 
		$("#encryptParam2").val(paramValue); 
	}
}




/*
 * 新建保存渠道或方案信息
 */
function saveDatasourProd(){
	
	var returnMessage = "";
	if(levelId == 5){
		
		addRole = "datasource";
		returnMessage = checkSingleParam("dataSource","渠道代码") + checkSingleParam("sourceName","渠道名称")
				+ checkSingleParam("authParam1","鉴权参数1") + checkSingleParam("authParam2","鉴权参数2")
				+ checkSingleParam("encryptParam1","加密参数") + checkSingleParam("encryptParam2","加密参数2");
	} 
	if(levelId == 6) {
		
		addRole = "project";
		returnMessage = checkSingleParam("projectCode","方案代码") + checkSingleParam("projectName","方案名称")
				+ checkSingleParam("authParam1","鉴权参数") + checkSingleParam("authParam2","鉴权参数2")
				+ checkSingleParam("encryptParam1","加密参数") + checkSingleParam("encryptParam2","加密参数2");
	}
	if(returnMessage.length > 0){
		alert(returnMessage);
		$("#saveDatasource").text("确定");
	}else{
		RestfulClient.post("/innerconfig/saveDatasourProject", 
		{
			"primaryId" : numberId,  
			"dataSource" : $.trim($("#dataSource").val()),  
			"sourceName" : $.trim($("#sourceName").val()), 
			"projectCode" : $.trim($("#projectCode").val()), 
			"projectName" : $.trim($("#projectName").val()), 
			"authType" : $.trim($("#authType").val()), 
			"authParam1" : $.trim($("#authParam1").val()), 
			"authParam2" : $.trim($("#authParam2").val()), 
			"encryptType" : $.trim($("#encryptType").val()), 
			"encryptParam1" : $.trim($("#encryptParam1").val()),
			"encryptParam2" : $.trim($("#encryptParam2").val()),
			"addRole": addRole
		},
		function(result) {
			alert(result.resultMsg);
			//判断结果
			if(result.resultCode != "9999"){

				changeLeftTree("add","","");
				//隐藏DIV
				$(".addInfo").hide();
			}
		});
	}
}

/*
 * 改变服务类型,服务小类与服务列表随之改变
 */
function showSmallTypes(){

	$.ajax({
		url:"/innerconfig/getServerSmallTypes",
		type:"POST",
		data:{"serverType":$("#serverType").val()},
		dataType:"json",  
		success:function(result){

			serverSmallTypes=result.dataList;
			$("#serverSmallType").empty();
			$("#serverList").empty();
			for (var i = 0 ; i < serverSmallTypes.length; i++) {
				$("#serverSmallType").append("<option value='"+serverSmallTypes[i][0]+"'>"+serverSmallTypes[i][1]+"</option>");
			}

			showServerLists();
		}
	})
}

/*
 * 改变服务小类或服务类型或者服务环境,加载服务列表数据
 */
function showServerLists(){
	
	var type1 = $("#serverType").val(),type2 = $("#serverSmallType").val(),type3 = $("#serverEnvType").val();
	if(type1 != null && type1.length > 0 
			&&  type2 != null && type2.length > 0
			&&  type3 != null && type3.length > 0){
		$.ajax({
			url:"/innerconfig/getServerList",
			type:"POST",
			data:{
				"serverEnv":$("#serverEnvType").val(),
				"serverType":$("#serverType").val(),
				"serverSmallType":$("#serverSmallType").val()
			},
			success:function(result){

				serverLists=result.dataList;
				$("#serverList").empty();
				for (var i = 0 ; i < serverLists.length; i++) {
					$("#serverList").append("<option value='"+serverLists[i].serverId+"'>"+serverLists[i].serverUrl+"</option>");
				}
			}
		})
	}else{
		alert("1233");
	}
	
}

	/*
	 * 账户编辑后保存
	 */
	function updateAcctSave(changeType,e){
//		alert("updateAcctSave---"+$(".accountinfodiv").attr("id")+"-----"+$("#editAppCode").val()+"-----"
//				+$("#editAcctName").val()+"-------"+$("#editAcctMobile").val()+"-----"+$("#editAcctEmail").val());
		var returnMessage = checkSingleParam("editAcctAppid","账号ID")
				+ checkSingleParam("editAppCode","账号代码") 
				+ checkSingleParam("editAcctName","账号名称")
				+ checkSingleParam("editAcctMobile","渠道手机") 
				+ checkSingleParam("editAcctEmail","渠道邮箱");

		if(returnMessage.length > 0){
			alert(returnMessage);
		}else{
			
			RestfulClient.post("/account/changeInfo", 
			{
				"accountId" : $(".accountinfodiv").attr("id"), 
				"appId" : $.trim($("#editAcctAppid").val()), 
				"appCode" : $.trim($("#editAppCode").val()), 
				"accountName" : $.trim($("#editAcctName").val()), 
				"mobile" : $.trim($("#editAcctMobile").val()), 
				"email" : $.trim($("#editAcctEmail").val()), 
				"reqType":changeType
			},
			function(result) {
				alert(result.resultMsg);
				//判断结果
				if(result.resultCode != "9999"){
					
					$("#editAppCode").attr("readonly","readonly").css("border","none");
					$("#editAcctName").attr("readonly","readonly").css("border","none");
					$("#editAcctMobile").attr("readonly","readonly").css("border","none");
					$("#editAcctEmail").attr("readonly","readonly").css("border","none");
					
					if("edit" == changeType){
						changeLeftTree("edit",$("#editAppCode").val(),$("#editAcctName").val());
						
						//编辑成功，保存变编辑
						e.text('编辑');
					    //移除取消按钮
						e.next().remove();
					}
					else{
						changeLeftTree("add","","");
						$(".accountinfodiv").hide();
					}
				}
				else{
					//var m = $(".accountinfodiv").find("readonly");
//					回复原数据
					//alert("失败");
				}
	 		});
		}

	}
	/*
	 * 渠道和方案编辑后保存
	 */
	function updateSave(ulId,lineId,params,e){
//		alert("updateSave--------"+ulId+"-----"+lineId+"-------"+params.length);
		//基本信息编辑保存 
		if("scheme_msg"==ulId){
			if(levelId == 2){
				
				var returnMessage = checkSingleParam("sourceName","渠道名称");
				if(returnMessage.length > 0){
					alert(returnMessage);
					$("#editDatasource1").text("确定");
				}else{
					RestfulClient.post("/source/changeInfo", 
					{
						"dataSourceId" :lineId, 
//						"dataSource" : $("#dataSource").val(),
						"sourceName" : $("#sourceName").val(),
						"reqType":"edit"
					},
					function(result) {
						alert(result.resultMsg);
						//判断结果
						if(result.resultCode != "9999"){
							e.parent().siblings().find(".info_content").attr("readonly","readonly").css("border","none");
							e.parent().siblings().find(".selectWidth").attr("disabled","disabled");
							changeLeftTree("edit",$("#dataSource").val(),$("#sourceName").val());
							e.html("编辑"); 
							e.next().remove();
							editSwitch = true;
						}
						else
							e.parent().parent().remove();
						////回复原数据
						
			 		});
				}
			}else {
				
				var returnMessage = checkSingleParam("projectName","方案名称");
				if(returnMessage.length > 0){
					alert(returnMessage);
					$("#editProject1").text("编辑");
				}else{
					RestfulClient.post("/project/changeInfo", 
					{
						"projectId" : lineId, 
//						"projectCode" : $("#projectCode").val(),
						"projectName" : $("#projectName").val(),
						"reqType":"edit"
					},
					function(result) {
						alert(result.resultMsg);
						//判断结果
						if(result.resultCode != "9999"){
							e.parent().siblings().find(".info_content").attr("readonly","readonly").css("border","none");
							e.parent().siblings().find(".selectWidth").attr("disabled","disabled");
	                        //更新左侧树信息
	                        changeLeftTree("edit",$("#projectCode").val(),$("#projectName").val());	
	                        e.html("编辑"); 
	                        e.next().remove();
	                        editSwitch = true;
						}
						else
							e.parent().parent().remove();
						////回复原数据
						
			 		});
				}
			}
		}
		
		//鉴权编辑保存 
		if("authen_msg"==ulId || "encrpty_msg" == ulId){

			if("authen_msg"==ulId){
				returnMessage = checkSingleParam("authParam1","鉴权参数1")+checkSingleParam("authParam2","鉴权参数2");
				deditAuthData = { 
						"authId":lineId,"authType":$("#authType").val(),
						"authParam1":$("#authParam1").val(),"authParam2":$("#authParam2").val(),
						"reqType":"edit"};
			}
			else{
				returnMessage = checkSingleParam("encryptParam1","加密参数1")+checkSingleParam("encryptParam2","加密参数2");
				deditAuthData = { 
						"authId":lineId,"encryptType":$("#encryptType").val(),
						"encryptParam1":$("#encryptParam1").val(),"encryptParam2":$("#encryptParam1").val(),
						"reqType":"edit"};
			}
			if(returnMessage.length > 0){
				alert(returnMessage);
//				$("#editDatasource1").text("确定");
			}else{
				RestfulClient.post("/authEncrypt/changeInfo",deditAuthData,
				function(result) {
					alert(result.resultMsg);
					//判断结果
					if(result.resultCode != "9999"){
						
						e.parent().siblings().find(".info_content").attr("readonly","readonly").css("border","none");
						e.parent().siblings().find(".selectWidth").attr("disabled","disabled");
						e.html("编辑"); 
						e.next().remove();
						editSwitch = true;
					}
//					else
//						e.parent().parent().remove();
					////回复原数据
		 		});
			}
		}
		
		//方案服务关系编辑保存功能
		if("server_msg"==ulId){
			
			var returnMessage = checkSingleParam("requestUrl_"+lineId,"接口地址");
			if(returnMessage.length > 0){
				alert(returnMessage);
				$("#editAuthMapp_"+lineId).text("确定");
			}else{
				if(levelId == 2)	requestType = "DATA_SOURCE";
				else if(levelId == 3)	requestType = "PROJECT";
				else if(levelId == 9)	requestType = "HISTORY_CUSTOM";
//				else	requestType = $("#requestType_"+lineId).val();

				RestfulClient.post("/authmapping/changeInfo", 
				{
					"mappingId" : lineId, 
					"projectId" : numberId,
					"requestType" : requestType,
					"requestUrl" : $.trim(params[0].value),
					"reqType":"edit"
				},
				function(result) {
					alert(result.resultMsg);
					//判断结果
					if(result.resultCode != "9999"){
						
						e.parent().siblings().find(".info_content").attr("readonly","readonly").css("border","none");
						e.parent().siblings().find(".selectWidth").attr("disabled","disabled");
						e.html('编辑'); 
						//取消变删除
						e.next().text('删除')
						editSwitch = true;
					}
					else{
//						e.parent().parent().remove();
						//$("#editAuthMapp_"+lineId).text("确定");
					}
				});
			}
			
		}
		
		//回调编辑保存功能
		else if("callBack_msg"==ulId){
			var returnMessage = checkSingleParam("callbackUrl_"+lineId,"回调地址");
			if(returnMessage.length > 0){
				alert(returnMessage);
				$("#editCallback_"+lineId).text("确定");
			}else{
				var backType = $("#callbackType_"+lineId).val();
				RestfulClient.post("/callback/changeInfo", 
				{
					"callbackUrlId" : lineId, 
					"projectId" : numberId,
					"callbackEnv" : $("#callbackEnv_"+lineId).val(),
					"callbackType" : backType,
					"callbackUrl" : $.trim(params[0].value),
					"reqType":"edit"
				},
				function(result) {
					alert(result.resultMsg);
					//判断结果
					if(result.resultCode != "9999"){
						
						e.parent().siblings().find(".info_content").attr("readonly","readonly").css("border","none");
						e.parent().siblings().find(".selectWidth").attr("disabled","disabled");
						e.html("编辑"); 
						//取消变删除
						e.next().text('删除')
						editSwitch = true;
					}
					else{
//						e.parent().parent().remove();
						//$("#editCallback_"+lineId).text("确定");
					}
					
				});
			}
		}
		
		
	}

	/*
	 * 添加后保存
	 */
	function addSave(ulId,params,e){
//		alert("addSave--------"+ulId+"-----"+params.length+"-------");
		//方案服务关系
		if("server_msg"==ulId){

			var returnMessage = checkSingleParam("requestUrl_a","接口地址");
			if(returnMessage.length > 0){
				alert(returnMessage);
				$("#saveRequest_a").text("保存");
			}else{
				var serverId = $("#serverList").val();
				
				if(levelId == 2)	requestType = "DATA_SOURCE";
				else if(levelId == 3)	requestType = "PROJECT";
				else if(levelId == 9)	requestType = "HISTORY_CUSTOM";
//				else	requestType = $("#requestType_a").val();
				
				if(serverId != null){
					RestfulClient.post("/authmapping/changeInfo", 
					{
						"projectId" : numberId,
						"requestType" : requestType,
						"requestUrl" : $.trim(params[0].value),
						"serverId" : serverId,
						"reqType":"add"
					},
					function(result) {
						alert(result.resultMsg);
						//判断结果
						if(result.resultCode != "9999"){
							e.parent().siblings().find(".add_text").addClass("info_content").removeClass("add_text");
							e.parent().siblings().find(".add_text").attr("readonly","readonly").css("border","none");
							e.parent().siblings().find(".selectWidth").attr("disabled","disabled");
							e.parent().parent().attr("id",result.resultCode);
							
							e.parent().parent().find("#requestType_a").attr("id","requestType_"+result.resultCode);
							e.parent().parent().find("#requestUrl_a").attr("id","requestUrl_"+result.resultCode);
							
							//将其class改为编辑的
							e.addClass("edit_btn").removeClass("save_btn");
						}
						else{
//							e.parent().parent().prev().remove();
							//e.parent().parent().remove();
							$("#saveRequest_a").text("保存");
						}
						
					});
				}else{
					alert("请选择服务");
					e.parent().parent().remove();
				}
			}
			
		}
		
		//回调
		else if("callBack_msg"==ulId){
			
			var returnMessage = checkSingleParam("callbackUrl_a","回调地址");
			if(returnMessage.length > 0){
				alert(returnMessage);
				$("#saveCallback_a").text("保存");
			}else{
				var backType = $("#callbackType_a").val();
				RestfulClient.post("/callback/changeInfo", 
				{
					"projectId" : numberId,
					"callbackEnv" : $("#callbackEnv_a").val(),
					"callbackType" : backType,
					"callbackUrl" : $.trim(params[0].value),
					"reqType":"add"
				},
				function(result) {
					alert(result.resultMsg);
					//判断结果
					if(result.resultCode != "9999"){ 
						e.parent().siblings().find(".add_text").addClass("info_content").removeClass("add_text");
						e.parent().siblings().find(".add_text").attr("readonly","readonly").css("border","none");
						e.parent().siblings().find(".selectWidth").attr("disabled","disabled");
						e.parent().parent().attr("id",result.resultCode);
						
						e.parent().parent().find("#callbackType_a").attr("id","callbackType_"+result.resultCode);
						e.parent().parent().find("#callbackUrl_a").attr("id","callbackUrl_"+result.resultCode);
						e.parent().parent().find("#callbackEnv_a").attr("id","callbackEnv_"+result.resultCode);
						
						//将其class改为编辑的
						e.addClass("edit_btn").removeClass("save_btn");
					}
					else{
//						e.parent().parent().remove();
						$("#saveCallback_a").text("保存");
					}
					
				});
			}
		}
		
	}
	
	
	/*
	 * 删除
	 */
	function deleteInfo(ulId,liId,e){
//		alert("deleteInfo--------"+ulId+"-----"+liId+"-------");
		//方案服务关系删除
		if("server_msg"==ulId){
			$.ajax({
				url:"/authmapping/deleteById",
				type:"POST",
				data:{"mappingId":liId},
				success:function(result){
					alert(result.resultMsg);
					//判断结果 失败不做任何处理
					if(result.resultCode != "9999"){
//						e.parent().parent().prev().remove();
						e.parent().parent().remove();
					}
				}
			})
		}
		
		//回调删除
		else if("callBack_msg"==ulId){
			
			$.ajax({
				url:"/callback/deleteById",
				type:"POST",
				data:{"callbackUrlId":liId},
				success:function(result){
					alert(result.resultMsg);
					//判断结果 失败不做任何处理
					if(result.resultCode != "9999"){
						e.parent().parent().remove();
					}
				}
			})
		}
		
	}

	/*
	 * 生成APPID
	 */
	function genAppId(type){
		RestfulClient.post("/authconfig/getNewAppid", {},
		function(result) {
			data = result.data;
			if(type == 1)	$("#editAcctAppid").val(data);
			else if(type == 2)	$("#authParam1").val(data);
			else if(type == 3)	$("#authParam2").val(data);
 		});
	}
	
/*
 * 右侧添加后编辑后,修改左侧树节点内容
 */
function changeLeftTree(dotype,resultCode,resultName){

	var treeObj = window.parent.zTreeObj;
	if("edit" == dotype){
		var node1 = treeObj.getNodesByParam("level",levelId, null);
		if(node1 != null && node1.length > 0){
			for(var i = 0;i<node1.length;i++){
				if(node1[i].id == leftTreeNodeId){
					nodeInfo = node1[i];
					resultCode = nodeInfo.name.split(":")[0];
					nodeInfo.name = resultCode +" : "+resultName;
				}
			}
		}
		treeObj.updateNode(nodeInfo);
		
	}else if("add" == dotype){
		
		var reloadUrl,reloadData;
		var node1 = treeObj.getNodeByParam("id",leftTreeNodeId, null);
		//判断节点的子节点是否已加载
		if(node1.check_Child_State < 0) return;
		
		if(levelId == 4){
			reloadData = { "pageNum":"0","pageSize":window.parent.pageSize};
			reloadUrl = "../../innerconfig/account_list";
		}else if(levelId == 5)	{
			reloadData = { "parentId":leftTreeNodeId};
			reloadUrl = "../../innerconfig/datasource_list";
		}else if(levelId == 6){
			reloadData = { "parentId":leftTreeNodeId};
			reloadUrl = "../../innerconfig/project_list";
		}	
		
		$.ajax({  
			type: "POST",  
			async:false,  
			url: reloadUrl,  
			data:reloadData,  
			dataType:"json",  
			success: function(data){ 
				if(data.data !=null && data.data !=""){  
					//删除节点下的所有旧节点
					treeObj.removeChildNodes(node1);
					//添加新节点  渠道
					newNode = treeObj.addNodes(node1, data.data); 
				}
			}
		});
	}
}

/*
 * 顶部导航条内容
 */
function showTotalInfo(){
	var showTotalInfos = "     ";
	var treeObj = window.parent.zTreeObj;
	var node2 = treeObj.getNodesByParam("id",leftTreeNodeId, null);
	
	if(node2 != null && node2.length > 0){
		var levelId1 = levelId;
		if(levelId == 9) levelId1 = 2;
		for(var i = 0;i<node2.length;i++){
			if(node2[i].level == levelId1){
				if(levelId1 == 3){
					/*showTotalInfos = node2[i].getParentNode().getParentNode().name
							+">>"+node2[i].getParentNode().name
							+">>"+ node2[i].name;*/
					showTotalInfos1 = node2[i].getParentNode().getParentNode().name
					showTotalInfos2 = node2[i].getParentNode().name;
					showTotalInfos3 = node2[i].name;
					$(".showTotalInfo1").text(showTotalInfos1+" >");
					$(".showTotalInfo2").text(showTotalInfos2+" >");
					$(".showTotalInfo3").text(showTotalInfos3);
				}else if(levelId1 == 2){
					/*showTotalInfos = node2[i].getParentNode().name 
							+">>"+ node2[i].name;*/
					showTotalInfos1 = node2[i].getParentNode().name;
					showTotalInfos2 = node2[i].name;
					$(".showTotalInfo1").text(showTotalInfos1+" >");
					$(".showTotalInfo2").text(showTotalInfos2);
				}else if(levelId1 == 1){
					showTotalInfos1 = node2[i].name;
					$(".showTotalInfo1").text(showTotalInfos1);
				}
				break;
			}
		}
	}

}
	
	

function saveOriginalAccount(){
	var originalAccount = {};
	originalAccount.type = 'account'
	originalAccount.accountId = $(".accountinfodiv").attr("id");
	originalAccount.appId = $(".accountinfodiv").attr("id");
	originalAccount.appCode = $.trim($("#editAppCode").val());
	originalAccount.accountName = $.trim($("#editAcctName").val());
	originalAccount.mobile = $.trim($("#editAcctMobile").val());
	originalAccount.email = $.trim($("#editAcctEmail").val());
	return originalAccount;
}
var originalData = {};
function saveOriginalData(ulId, lineId, params) {
	originalData = {};
	if ("scheme_msg" == ulId) {
		if (levelId == 2) {
			originalData.dataSourceId = lineId;
			originalData.type = 'dataSource';
			originalData.dataSource = $("#dataSource").val();
			originalData.sourceName = $("#sourceName").val();
		} else {
			originalData.type = 'project';
			originalData.projectId = lineId;
			originalData.projectCode = $("#projectCode").val();
			originalData.projectName = $("#projectName").val();
		}
	}else if ("authen_msg" == ulId) {
		originalData.type = 'authtoken';
		originalData.authId = lineId;
		originalData.authType = $("#authType").val();
		originalData.authParam1 = $("#authParam1").val();
		originalData.authParam2 = $("#authParam1").val();
	}else if("encrpty_msg" == ulId){
		originalData.type = 'encrptytoken';
		originalData.authId = lineId;
		originalData.encryptType = $("#encryptType").val();
		originalData.encryptParam1 = $("#encryptParam1").val();
		originalData.encryptParam2 = $("#encryptParam2").val();
	}else if ("server_msg" == ulId) {
		//方案服务关系编辑保存功能
		originalData.type = 'mapping';
		originalData.mappingId = lineId;
		originalData.projectId = numberId;
		originalData.requestUrl = $.trim(params[0].value);
	}else if ("callBack_msg" == ulId) {
		originalData.type = 'callback';
		originalData.callbackUrlId = lineId;
		originalData.projectId = numberId;
		originalData.callbackUrl = $.trim(params[0].value);
	}
}
//点击取消，恢复到原来的状态
function recoverOrigin(originalData,e) {
	if(originalData.type == 'account'){
		$('#editAcctName').val(originalData.accountName);
		$('#editAcctMobile').val(originalData.mobile);
		$('#editAcctEmail').val(originalData.email);
		$("#editAppCode").attr("readonly","readonly").css("border","none");
		$("#editAcctName").attr("readonly","readonly").css("border","none");
		$("#editAcctMobile").attr("readonly","readonly").css("border","none");
		$("#editAcctEmail").attr("readonly","readonly").css("border","none");
		e.prev().html('编辑');
		e.remove();
		return;
	}
	if (originalData.type == 'dataSource') {
//		$('#dataSource').val(originalData.dataSource);
		$('#sourceName').val(originalData.sourceName);
		$('#editDatasource1').text('编辑');
	} else if(originalData.type == 'project'){
//		$('#projectCode').val(originalData.projectCode);
		$('#projectName').val(originalData.projectName);
		$('#editProject1').text('编辑');
	} else if (originalData.type == 'authtoken') {
		$('#authType').val(originalData.authType);
		$('#authParam1').val(originalData.authParam1);
        $('#authParam2').val(originalData.authParam2);
        $('#editAuthencrypt').text('编辑');
	} else if(originalData.type == 'encrptytoken'){
		$('#encryptType').val(originalData.encryptType);
		$('#encryptParam1').val(originalData.encryptParam1);
        $('#encryptParam2').val(originalData.encryptParam2);
        $('#editAuthencrypt1').text('编辑');
	}else if (originalData.type == 'mapping') {
		$('#requestType_'+originalData.mappingId+'' ).val(originalData.mappingId);
		$('#requestUrl_'+originalData.mappingId+'').val(originalData.requestUrl)
		e.prev().html('编辑');
		e.html('删除');
	} else if (originalData.type == 'callback') {
		$('#callbackEnv_'+originalData.callbackUrlId+'').val(originalData.callbackEnv);
		$('#callbackType_'+originalData.callbackUrlId+'').val(originalData.callbackType);
        $('#callbackUrl_'+originalData.callbackUrlId+'').val(originalData.callbackUrl);
        e.prev().html('编辑');
        e.html('删除');
	} else {
		alert('未知异常');
	}
	e.parent().siblings().find(".info_content").attr("readonly","readonly").css("border","none");
	e.parent().siblings().find(".selectWidth").attr("disabled","disabled");
}
