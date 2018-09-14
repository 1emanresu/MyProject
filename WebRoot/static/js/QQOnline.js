//QQ在线状态获取及更改
var QQs = ["2355716880","2355716881","2355716882","2355716883","2355716884","2355716885","2355716886","2355716887","2355716888","2355716889"];
function getQQState() {
	$.ajax({
		type : "post",
		url : "ajax/getQQCustomerServiceStatus/"+get_time(),
		async: true,
		success: function(data){
			updateQQState(data);
		}
	});
}

function updateQQState(data) {
	var online= new Array();
	eval(data);
	for(var i=0; i<online.length; i++) {
		if(online[i] == 1) {
			$("img[qq='"+QQs[i]+"']").attr("src","static/images/online" + QQs[i] + ".gif");
		}
	}
}

function getQQStatus() {
	$.ajax({
		type : "post",
		url : "ajax/getQQCustomerServiceStatus/"+get_time(),
		async: true,
		success: function(data){
			updateQQStatus(data);
		}
	});
}

function updateQQStatus(data) {
	var online= new Array();
	eval(data);
	for(var i=0; i<online.length; i++) {
		if(online[i] == 1) {
			$("img[qq='"+QQs[i]+"']").attr("src","static/images/qq2.jpg");
			$("img[qq='"+QQs[i]+"']").parent().parent().addClass("qq2");
		}
	}
}