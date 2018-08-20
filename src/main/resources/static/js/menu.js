$(function(){
//	var menuArr = 
//	[{"resourceId":1,"resourceName":"第三方管理","pic":"&#xe616;",
//	"child": [
//		{ "resourceId": "1","resourceName": "用户管理","actionUrl":"snsHtml/thirdHtml/account.html"},
//		{ "resourceId": "2","resourceName": "渠道管理","actionUrl":"snsHtml/thirdHtml/datasource.html" },
//		{ "resourceId": "3","resourceName": "方案管理","actionUrl":"snsHtml/thirdHtml/project.html" },
//		{ "resourceId": "4","resourceName": "服务管理","actionUrl":"snsHtml/thirdHtml/server-list.html" },
//		{ "resourceId": "5","resourceName": "配置管理","actionUrl":"snsHtml/thirdHtml/authconfig.html" },
//		{ "resourceId": "6","resourceName": "用户token列表 ","actionUrl":"snsHtml/thirdHtml/token-list.html" }]
//	},
//	{"resourceId":2,"resourceName":"系统设置","pic":"&#xe613;",
//	"child": [
//		{ "resourceId": "1","resourceName": "用户管理","actionUrl":"snsHtml/systemHtml/user-list.html" },
//		{ "resourceId": "2","resourceName": "角色管理","actionUrl":"snsHtml/systemHtml/role-list.html" },
//		{ "resourceId": "3","resourceName": "资源管理","actionUrl":"snsHtml/systemHtml/resource-list.html" },
//	]
//	}];
	
	
	var menuArr;
	$.ajax({  
		type: "POST",  
		url: "../../resource/menu",  
		data:{},  
		dataType:"json",  
		async:false,
		success: function(data){  
			menuArr = data.dataList;
			snsObj.init(menuArr);
		},  
	}); 
})

var snsObj = {
	init:function(menuArr){
		this.menuFn(menuArr);
		this.clickFn();
	},
	menuFn:function(menuArr){
		if(menuArr.length !=0){
			for (var i = 0 ; i < menuArr.length;i++) {
		         var firDd = '<dl  id="menu_'+ menuArr[i].resourceId+'">'
			         +'<dt><i class="Hui-iconfont">&#xe616</i> '+menuArr[i].resourceName+'<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>'
			         +'<dd><ul fmenu='+menuArr[i].resourceName+'></ul></dd></dl>';
//			         +'<dt class="selected"><i class="Hui-iconfont">&#xe616</i> '+menuArr[i].resourceName+'<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>'
//			         +'<dd style="display: block;"><ul fmenu='+menuArr[i].resourceName+'></ul></dd></dl>';
				$(".menu_dropdown").append(firDd);
		        for (var j = 0; j < menuArr[i].child.length; j++) {
		        	 var lis = '<li><a data-href='+menuArr[i].child[j].actionUrl+' data-title="'+menuArr[i].child[j].resourceName+'" href="javascript:void(0)">'+menuArr[i].child[j].resourceName+'</a></li>'
		            $(".menu_dropdown").find("dl").eq(i).find("ul").append(lis);
		        }   
			}
		}	
	},
	clickFn:function(){
		$(".menu_dropdown dd ul li").on("click","a",function(){
            var that = $(this);
            var menuTitle = that.attr("data-title");         //获取二级菜单标题
            var pId = that.parent().parent().attr('fmenu');  //获取一级菜单标题
				that.parent().addClass("menu_li");
				that.parent().siblings().removeClass("menu_li"); 
				snsObj.navTopFn(pId,menuTitle);
	    })
		
	},
	navTopFn:function(a,b){
		var navT = '<span class="c-gray en">&gt;</span>'+a+' <span class="c-gray en">&gt;</span>'+b;
		$(".breadcrumb").append(navT);
        $(".breadcrumb").html("sss")
	}
}
