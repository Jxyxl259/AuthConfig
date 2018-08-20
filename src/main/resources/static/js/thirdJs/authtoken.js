var data;

$(function() {
	
	urlPath = "/authtoken";
	
	//操作列不显示按钮
	showEditButton = false;
	showDeleteButton = false;
    // 数据初始化
	selectinfo();
})

function selectinfo(){
	searchParams = { 
		"pageSize":lastPageSize,
		"appId" : $.trim($("#appIdSel").val()), 
		"appSecret" : $.trim($("#appSecretSel").val()),
		"accessToken" : $.trim($("#accessTokenSel").val()), 
		"refreshToken" : $.trim($("#refreshTokenSel").val())
	};
	commonObj.tabInit("#tableId");
}

var columsName = {
	"number":"编号",
	"tokenId":"tokenId",
	"appId":"APP_ID",
	"appSecret":"APP_SECRET",
	"openId":"OPEN_Id",
	"accessToken":"ACCESS_TOKEN",
	"refreshToken":"REFRESH_TOKEN",
	"expiretime":"EXPIRETIME",
	"refExpireTime":"REF_EXPIRE_TIME",
    "extn":"操作"
};

var columns = [
	{"data": "tokenId","title":columsName["number"],"width":"4%"},
	{"data": "tokenId","title":columsName["tokenId"],"width":"4%"},
	{"data": "appId","title":columsName["appId"],"width":"16%"},
	{"data": "appSecret","title":columsName["appSecret"],"width":"16%"},
	{"data": "accessToken","title":columsName["accessToken"],"width":"16%"},
	{"data": "refreshToken","title":columsName["refreshToken"],"width":"16%"},
	{"data": "expiretime","title":columsName["expiretime"],"width":"8%"},
	{"data": "refExpireTime","title":columsName["refExpireTime"],"width":"8%"},
	{"data": "tokenId","title":columsName["extn"],"width":"8%"},
	{"data": "tokenId","title":columsName["bindId"],"width":"1%","sClass":"hidden"}
];
 
commonObj.init();	

/*
 * 根据操作类型展示弹出框内容
 */
function showLayerByType(id,type){
	
	$("#us").addClass("detail_table");
		
	RestfulClient.post("/authtoken/getInfo", {
		"tokenId": id
	},
	function(result) {
		data = result.dataList;

		$("#createdUser").val(data.createdUser);
		$("#createdDate").val(data.createdDate);
		$("#updatedUser").val(data.updatedUser);
		$("#updatedDate").val(data.updatedDate);
		
		$("#appId").val(data.appId);
		$("#appSecret").val(data.appSecret);
		$("#openId").val(data.openId);
		$("#accessToken").val(data.accessToken);
		$("#refreshToken").val(data.refreshToken);
		$("#expiretime").val(data.expiretime);
		$("#refExpireTime").val(data.refExpireTime);
		
		UserCommon.addInfos(event);
	});
}

var UserCommon = function(){
    return {
    	addInfos : function(event){
	        layer.open({
	            type : 1,
	            fixed : true, // 不固定
	            shadeClose : false, // 点击遮罩关闭层
	            title : ["详细信息" ],
	            area : [ '640px', '470px' ],
	            content :$("#fromTemplate") ,
	            btn:["关闭"],
	            end : function() {
	            	layer.close();
	            }
	        });
        }
    }
}();
	
function selectBoxInfo(){
	RestfulClient.post("/account/listAll", {},
		 function(result) {
			data = result.dataList;
			for(var i in data){
				$("#selectBox").append("<option value='"+data[i].appId+"'>'"+data[i].appId+"'</option>");
			}
 		}
	);
}

