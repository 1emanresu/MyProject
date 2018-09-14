var netpage = 0;
function wangyin(basePath,page,netstarttime,netendtime,netstate,netid) {
	$.ajax({
		url : basePath + "/exchange/getQueryCount",
		data : 'page=' + page + '&&netstarttime=' + netstarttime
		+ '&&netendtime=' + netendtime + '&&netstate=' + netstate
		+ '&&netid=' + netid,
		type : 'post',
		dataType : 'json',
		error : function() {
			alert('数据获取失败');
		},
		success : function(datas) {
			var data = datas[0];
			netpage = datas[1];
			var st = "<ul class='ajax-loaders'>";
			st += "<li><img src='" + basePath
			+ "/img/ajax-loaders/ajax-loader-7.gif'></li></ul>";
			$("#net").html(st);
			var str = "";					
			for (var i = 0; i < data.length; i++) {
				str += "<tr>";
				str += "<td><span Style='text-align:left'>" + data[i].p2_Order
				+ "</span></td>";
				str += "<td><span Style='text-align:left'>" + data[i].orderid
				+ "</span></td>";

				var amt = data[i].p3_Amt;				
				var amount = "0";
				if (data[i].amount != null) {
					amount = data[i].amount;
				}				
				var deducted = "0";
				if (data[i].deducted != null) {
					deducted = data[i].deducted;
				}
				str += "<td Style='text-align:center'><span>"
					+ amt + "</span></td>";
				str += "<td Style='text-align:center'><span>"
					+ amount + "</span></td>";
				str += "<td Style='text-align:center'><span>" + deducted + "</span></td>";
				str += "<td Style='text-align:center'><span>"
					+ data[i].CreateTime + "</span></td>";
				if (data[i].r1_Code == 1) {
					str += "<td class='center'><span class='label label-success'>成功</span></td>";
				} else if (data[i].r1_Code == 2) {
					str += "<td class='center'><span class='label label-warning'>处理中</span></td>";
				} else if (data[i].r1_Code == 3) {
					str += "<td class='center'><span class='label label-warning'>失败</span></td>";
				} else if (data[i].r1_Code == 4) {
					str += "<td class='center'><span class='label label-warning'>失效</span></td>";
				}
				if (data[i].r1_Code == 1) {
					str += "<td class='center'><a target='_blank' href='"
						+ basePath
						+ "/cibpay/retunet?orderid="
						+ data[i].orderid
						+ "'><span class='label label-important'>通知</span></a></td>";
				} else if (data[i].r1_Code == 2) {
					str += "<td class='center'></td>";
				} else if (data[i].r1_Code == 3) {
					str += "<td class='center'><span class='label label-warning'>失败</span></td>";
				} else if (data[i].r1_Code == 4) {
					str += "<td class='center'><span class='label label-warning'>失效</span></td>";
				}
				str += "</tr>";
			}
			$("#net").html(str);
		}
	});
}
var loadDataByPage = {
	initPage:function(itemCount){
		var $this = this;
		Page({
			num:itemCount,		//页码数
			startnum:1,				//指定页码
			elem:$('#tm_pagination'),		//指定的元素
			callback:function(n){	//回调函数
				$this.loadData(n);
			}
		});
	},
	loadData:function(pages){
		var netstarttime = $("#netstarttime").val();
		var netendtime = $("#netendtime").val();
		var netstate = $("#netstate").val();
		var netid = $("#netid").val();
		wangyin(basePath, pages, netstarttime, netendtime, netstate, netid);
		//UserSum(basePath, netstarttime, netendtime, netstate, netid);
	}
};
function UserSum(basePath,netstarttime,netendtime,netstate,netid,callback) {
	$.ajax({
		url : basePath + "/exchange/getUserCount",
		data : 'netstarttime=' + netstarttime
		+ '&&netendtime=' + netendtime+ '&&netstate=' + netstate + '&&netid=' + netid,
		type : 'post',
		dataType : 'json',
		error : function() {
			alert('数据获取失败');
		},
		success : function(data) {	
			var amttote = data.amttote;
			var amounttote=data.amounttote;
			if(amttote==null || amttote==""){
				amttote=0;
			}
			if(amounttote==null || amounttote==""){
				amounttote=0;
			}
			var str = "订单总数：<span id='pre-totalCount'>";
			str +=  data.tote + "</span>条 ";
			str += "充值金额："+ amttote + "（元）";
			str += "结算金额：" + amounttote + "（元）";				
			$("#my_count").html(str);
			var page_num = Math.ceil(data.tote/10);
			if(callback)callback(page_num);
		}
	});
}

function usercount(basePath,start_time,end_time){
	$.ajax({
		url : basePath + "/exchange/getMyCount",
		data : 'netstarttime=' + start_time
		+ '&&netendtime=' + end_time,
		type : 'post',
		dataType : 'json',
		error : function() {
			alert('数据获取失败');
		},
		success : function(data) {	
			var amttote = data.amttote;
			var amounttote=data.amounttote;
			if(amttote==null || amttote==""){
				amttote=0;
			}
			if(amounttote==null || amounttote==""){
				amounttote=0;
			}
			var str = "<tr>";
			str += "<td Style='text-align:center'><span>"
				+ data.tote + "</span></td>";
			str += "<td Style='text-align:center'><span>"
				+ amttote + "</span></td>";
			str += "<td Style='text-align:center'><span>" + amounttote + "</span></td>";				
			str += "</tr>";
			$("#usernet").html(str);
		}
	});
}

function rechar(basePath, page, rechstarttime, rechendtime, rechparticipate,
		rechstate, rechid) {
	$
	.ajax({
		url : basePath + "/payment/getRech",
		data : 'rechpage=' + page + '&&rechstarttime=' + rechstarttime
		+ '&&rechendtime=' + rechendtime + '&&rechparticipate='
		+ rechparticipate + '&&rechstate=' + rechstate
		+ '&&rechid=' + rechid,
		type : 'post',
		dataType : 'json',
		error : function() {
			alert('数据获取失败');
		},
		success : function(datas) {
			var data = datas[0];
			rechpage = datas[1];
			if (page == 1) {
				$("li#rechprev").addClass("active");
			} else {
				$("li#rechprev").removeClass("active");
			}
			if (page == rechpage) {
				$("li#rechnext").addClass("active");
			} else {
				$("li#rechnext").removeClass("active");
			}
			var prev = page - 1;
			var next = page + 1;
			$("a#rechprev").attr("onclick", "recharge(" + prev + ")");
			$("a#rechnum").html(page);
			$("a#rechnext").attr("onclick", "recharge(" + next + ")");
			var st = "<ul class='ajax-loaders'>";
			st += "<li><img src='" + basePath
			+ "/img/ajax-loaders/ajax-loader-7.gif'></li></ul>";
			$("#recharge").html(st);
			var str = "";
			for (var i = 0; i < data.length; i++) {
				str += "<tr>";
				str += "<td><span class='label'>" + data[i].rechargeid
				+ "</span></td>";
				str += "<td class='center'><span class='label'>"
					+ data[i].CreateTime + "</span></td>";
				var pd_FrpId = data[i].pd_FrpId;
				str += "<td class='center'><span class='label'>"
					+ pd_FrpId + "</span></td>";
				str += "<td class='center'><span class='label'>"
					+ data[i].p3_Amt + "</span></td>";
				var amount = data[i].amount;
				if (!amount && typeof (amount) != "undefined"
					&& amount != 0) {
					amount = "0";
				}
				str += "<td class='center'><span class='label'>"
					+ amount + "</span></td>";
				if (data[i].r1_Code == 1) {
					str += "<td class='center'><span class='label label-success'>成功</span></td>";
				} else if (data[i].r1_Code == 2) {
					str += "<td class='center'><span class='label label-warning'>处理中</span></td>";
				} else if (data[i].r1_Code == 3) {
					str += "<td class='center'><span class='label label-warning'>失败</span></td>";
				} else if (data[i].r1_Code == 4) {
					str += "<td class='center'><span class='label label-warning'>失效</span></td>";
				}
				str += "</tr>";
			}
			$("#recharge").html(str);
		}
	});
}

function refunds(basePath, page, refundstarttime, refundendtime, refundstate,
		refundid,account_name) {
	$.ajax({
		url : basePath + "/settlement/getRefund",
		data : 'refundpage=' + page + '&&refundstarttime='
		+ refundstarttime + '&&refundendtime=' + refundendtime
		+ '&&refundstate=' + refundstate + '&&refundid='
		+ refundid +'&&account_name=' + account_name,
		type : 'post',
		dataType : 'json',
		error : function() {
			alert('数据获取失败');
		},
		success : function(datas) {
			var data = datas[0];
			refundpage = datas[1];
			if (page == 1) {
				$("li#refundprev").addClass("active");
			} else {
				$("li#refundprev").removeClass("active");
			}
			if (page == refundpage) {
				$("li#refundnext").addClass("active");
			} else {
				$("li#refundnext").removeClass("active");
			}
			var prev = page - 1;
			var next = page + 1;
			$("a#refundprev").attr("onclick", "refund(" + prev + ")");
			$("a#refundnum").html(page);
			$("a#refundnext").attr("onclick", "refund(" + next + ")");
			var st = "<ul class='ajax-loaders'>";
			st += "<li><img src='" + basePath
			+ "/img/ajax-loaders/ajax-loader-7.gif'></li></ul>";
			$("#refund").html(st);
			var str = "";
			for (var i = 0; i < data.length; i++) {
				str += "<tr>";
				str += "<td><span class='label'>" + data[i].refund_id
				+ "</span></td>";
				str += "<td class='center'><span class='label'>"
					+ data[i].createtime + "</span></td>";
				str += "<td class='center'><span class='label'>"
					+ data[i].refund_amount + "-"
					+ data[i].refund_fees + "="
					+ data[i].refund_actual + "</span></td>";
				str += "<td class='center'><span class='label'>"
					+ data[i].net_name + "</span></td>";
				str += "<td class='center'><span class='label'>"
					+ data[i].explain + "</span></td>";
				if (data[i].refund_state == '1') {
					str += "<td class='center'><span class='label label-success'>成功</span></td>";
				} else if (data[i].refund_state == '2') {
					str += "<td class='center'><span class='label label-warning'>处理中</span></td>";
				} else if (data[i].refund_state == '3') {
					str += "<td class='center'><span class='label label-warning'>失败</span></td>";
				}
				str += "</tr>";
			}
			$("#refund").html(str);
		}
	});
}
