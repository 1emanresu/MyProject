var netpage = 0;
var cardpages = 0;
var rechpage = 0;
var circpage = 0;
var refundpage = 0;
function wangyin(basePath, page, netstarttime, netendtime, netparticipate,
		netstate, netid) {
	$
			.ajax({
				url : basePath + "/exchange/getNet",
				data : 'page=' + page + '&&netstarttime=' + netstarttime
						+ '&&netendtime=' + netendtime + '&&netparticipate='
						+ netparticipate + '&&netstate=' + netstate
						+ '&&netid=' + netid,
				type : 'post',
				dataType : 'json',
				error : function() {
					//alert('数据获取失败');
				},
				success : function(datas) {
					var data = datas[0];
					netpage = datas[1];
					if (page == 1) {
						$("li#netprev").addClass("active");
					} else {
						$("li#netprev").removeClass("active");
					}
					if (page == netpage) {
						$("li#netnext").addClass("active");
					} else {
						$("li#netnext").removeClass("active");
					}
					var prev = page - 1;
					var next = page + 1;
					$("a#netprev").attr("onclick", "net(" + prev + ")");
					$("a#netnum").html(page);
					$("a#netnext").attr("onclick", "net(" + next + ")");
					var st = "<ul class='ajax-loaders'>";
					st += "<li><img src='" + basePath
							+ "/img/ajax-loaders/ajax-loader-7.gif'></li></ul>";
					$("#net").html(st);
					var str = "";
					for (var i = 0; i < data.length; i++) {
						str += "<tr>";
						str += "<td><span class='label'>" + data[i].orderid
								+ "</span></td>";
						str += "<td class='center'><span class='label'>"
								+ data[i].CreateTime + "</span></td>";
						
						var amt = data[i].p3_Amt;
						var amount = "0";
						if (data[i].amount != null) {
							amount = data[i].amount;
						}
						var deducted = "0";
						if (data[i].deducted != null) {
							deducted = data[i].deducted;
						}
						str += "<td class='center'><span class='label'>"
							+ amt + "</span></td>";
						str += "<td class='center'><span class='label'>"
							+ amount + "</span></td>";
						str += "<td class='center'><span class='label'>" + deducted + "</span></td>";
						if (data[i].r1_Code == 1) {
							str += "<td class='center'><span class='label label-success'>成功</span></td>";
						} else if (data[i].r1_Code == 2) {
							str += "<td class='center'><span class='label label-warning'>处理中</span></td>";
						} else if (data[i].r1_Code == 3) {
							str += "<td class='center'><span class='label label-warning'>失败</span></td>";
						} else if (data[i].r1_Code == 4) {
							str += "<td class='center'><span class='label label-warning'>失效</span></td>";
						}
						if (data[i].success == 1) {
							str += "<td class='center'><span class='label label-success'>是</span></td>";
						} else {
							str += "<td class='center'><span class='label label-warning'>否</span></td>";
						}
						if (data[i].r1_Code == 1) {
							str += "<td class='center'><a target='_blank' href='"
									+ basePath
									+ "/yeepay/retunet?orderid="
									+ data[i].orderid
									+ "'><span class='label label-important'>通知</span></a></td>";
						} else if (data[i].r1_Code == 2) {
							str += "<td class='center'><span class='label label-warning'>处理中</span></td>";
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
function cards(basePath, page, cardstarttime, cardendtime, cardparticipate,
		cardstate, cardid) {
	$
			.ajax({
				url : basePath + "/exchange/getCard",
				data : 'cardpages=' + page + '&&cardstarttime=' + cardstarttime
						+ '&&cardendtime=' + cardendtime + '&&cardparticipate='
						+ cardparticipate + '&&cardstate=' + cardstate
						+ '&&cardid=' + cardid,
				type : 'post',
				dataType : 'json',
				error : function() {
					alert('数据获取失败');
				},
				success : function(datas) {
					var data = datas[0];
					cardpages = datas[1];
					if (page == 1) {
						$("li#cardprev").addClass("active");
					} else {
						$("li#cardprev").removeClass("active");
					}
					if (page == cardpages) {
						$("li#cardnext").addClass("active");
					} else {
						$("li#cardnext").removeClass("active");
					}
					var prev = page - 1;
					var next = page + 1;
					$("a#cardprev").attr("onclick", "card(" + prev + ")");
					$("a#cardnum").html(page);
					$("a#cardnext").attr("onclick", "card(" + next + ")");
					var st = "<ul class='ajax-loaders'>";
					st += "<li><img src='" + basePath
							+ "/img/ajax-loaders/ajax-loader-7.gif'></li></ul>";
					$("#card").html(st);
					var str = "";
					for (var i = 0; i < data.length; i++) {
						str += "<tr>";
						str += "<td><span class='label'>" + data[i].orderid
								+ "</span></td>";
						str += "<td class='center'><span class='label'>"
								+ data[i].CreateTime + "</span></td>";
						var pd_FrpId = data[i].pd_FrpId;
						if (!pd_FrpId && typeof (pd_FrpId) != "undefined"
								&& pd_FrpId != 0) {
							str += "<td class='center'><span class='label'>网上银行</span></td>";
						} else {
							str += "<td class='center'><span class='label'>"
									+ pd_FrpId + "</span></td>";
						}
						var p8_Url = data[i].p8_Url;
						var urls = p8_Url.split("/");
						str += "<td class='center'><span class='label'>网址："
								+ urls[2] + "</span></td>";
						var amt = data[i].p3_Amt;
						var amount = "0";
						if (data[i].amount != null) {
							amount = data[i].amount;
						}
						var deducted = "0";
						if (data[i].deducted != null) {
							deducted = data[i].deducted;
						}
						str += "<td class='center'><span class='label'>" + amt
								+ "-" + deducted + "=" + amount
								+ "</span></td>";
						if (data[i].r1_Code == 1) {
							str += "<td class='center'><span class='label label-success'>提交成功</span></td>";
						} else {
							str += "<td class='center'><span class='label label-warning'>提交失败</span></td>";
						}
						if (data[i].success == 1) {
							str += "<td class='center'><span class='label label-success'>是</span></td>";
						} else {
							str += "<td class='center'><span class='label label-warning'>否</span></td>";
						}
						if (data[i].r1_Code == 1) {
							str += "<td class='center'><a href='"
									+ basePath
									+ "/exchange/cardReturn?orderid="
									+ data[i].orderid
									+ "'><span class='label label-important'>通知</span></a></td>";
						} else {
							str += "<td class='center'><span class='label label-warning'>提交失败</span></td>";
						}
						str += "<td><a class='btn btn-small btn-success btn-card' onclick='showcard(\""
								+ data[i].orderid + "\")'>查看点卡状态</a></td>";
						str += "</tr>";
					}
					$("#card").html(str);
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
function circ(basePath, page, circstarttime, circendtime, circparticipate,
		circstate, circid) {
	$
			.ajax({
				url : basePath + "/payment/getCirc",
				data : 'circpage=' + page + '&&circstarttime=' + circstarttime
						+ '&&circendtime=' + circendtime + '&&circparticipate='
						+ circparticipate + '&&circstate=' + circstate
						+ '&&circid=' + circid,
				type : 'post',
				dataType : 'json',
				error : function() {
					alert('数据获取失败');
				},
				success : function(datas) {
					var data = datas[0];
					circpage = datas[1];
					if (page == 1) {
						$("li#circprev").addClass("active");
					} else {
						$("li#circprev").removeClass("active");
					}
					if (page == circpage) {
						$("li#circnext").addClass("active");
					} else {
						$("li#circnext").removeClass("active");
					}
					var prev = page - 1;
					var next = page + 1;
					$("a#circprev").attr("onclick", "circlip(" + prev + ")");
					$("a#circnum").html(page);
					$("a#circnext").attr("onclick", "circlip(" + next + ")");
					var st = "<ul class='ajax-loaders'>";
					st += "<li><img src='" + basePath
							+ "/img/ajax-loaders/ajax-loader-7.gif'></li></ul>";
					$("#circlip").html(st);
					var str = "";
					for (var i = 0; i < data.length; i++) {
						str += "<tr>";
						str += "<td><span class='label'>" + data[i].circlipid
								+ "</span></td>";
						str += "<td class='center'><span class='label'>"
								+ data[i].datetime + "</span></td>";
						var cardcode = data[i].cardcode;
						str += "<td class='center'><span class='label'>"
								+ cardcode + "</span></td>";
						str += "<td class='center'><span class='label'>"
								+ data[i].amount + "</span></td>";
						var actual = data[i].actual;
						if (!actual && typeof (actual) != "undefined"
								&& actual != 0) {
							actual = "0";
						}
						str += "<td class='center'><span class='label'>"
								+ actual + "</span></td>";
						var deducted = data[i].deducted;
						if (!deducted && typeof (deducted) != "undefined"
								&& deducted != 0) {
							deducted = "0";
						}
						str += "<td class='center'><span class='label'>"
								+ deducted + "</span></td>";
						if (data[i].result == '2000') {
							str += "<td class='center'><span class='label label-success'>成功</span></td>";
						} else if (data[i].result == '2001') {
							str += "<td class='center'><span class='label label-warning'>处理中</span></td>";
						} else {
							str += "<td class='center'><span class='label label-warning'>失败</span></td>";
						}
						str += "<td class='center'><span class='label'>"
								+ data[i].info + "</span></td>";
						str += "</tr>";
					}
					$("#circlip").html(str);
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
		var refundstarttime = $("#refundstarttime").val();
		var refundendtime = $("#refundendtime").val();
		var refundstate = $("#refundstate").val();
		var refundid = $("#refundid").val();
		var account_name = $("#account_name").val();
		refunds(basePath, pages, refundstarttime, refundendtime, refundstate,refundid,account_name);
	}
};
function refunds(basePath, page, refundstarttime, refundendtime, refundstate,refundid, account_name) {
	$.ajax({
		url : basePath + "/settlement/getRefund",
		data : 'refundpage=' + page + '&&refundstarttime='
				+ refundstarttime + '&&refundendtime=' + refundendtime
				+ '&&refundstate=' + refundstate + '&&refundid='
				+ refundid + "&&account_name=" + account_name,
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
			var xlh=1;
			for (var i = 0; i < data.length; i++) {
				
				xlh = xlh+i;
				if (data[i].refund_state == '1') {
					str += "<tr style='background:#4169E1'>";
				}else{
					str += "<tr>";
				}
				str += "<td>" + xlh
				+ "</td>";
				str += "<td class='center'><span>"
						+ data[i].refund_amount + "-"
						+ data[i].refund_fees + "="
						+ data[i].refund_actual + "</span></td>";
				str += "<td class='center'><span>"
						+ data[i].account_name + "</span></td>";
				str += "<td class='center'><span>"
					+ data[i].net_name + "</span></td>";
				str += "<td class='center'><span>"
					+ data[i].account + "</span></td>";
				str += "<td class='center'><span>"
					+ data[i].branch + "</span></td>";
				str += "<td class='center'><span>"
						+ data[i].explain + "</span></td>";
				str += "<td class='center'><span style='font-size:13px;color：#fff;text-align:center'>"
					+ data[i].createtime + "</span></td>";
				str += "<td class='center'><span>"
					+ data[i].remark + "</span></td>";
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


function RefundsSum(basePath,refundstarttime,refundendtime,refundstate,refundid,account_name,callback) {
	$.ajax({
		url : basePath + "/exchange/getRefundCount",
		data : 'refundstarttime=' + refundstarttime
		+ '&&refundendtime=' + refundendtime+ '&&refundstate=' + refundstate + '&&refundid=' + refundid + '&&account_name=' + account_name,
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
			var str = "提款记录：";
			str +=  data.tote + " 条 ";
			str += "提款金额："+ amttote + "（元）";				
			$("#my_count").html(str);
			var page_num = Math.ceil(data.tote/10);
			if(callback)callback(page_num);
		}
	});
}

function sing(basePath, singid) {
	$
			.ajax({
				url : basePath + "/exchange/getSing",
				data : 'singid=' + singid,
				type : 'post',
				dataType : 'json',
				error : function() {
					alert('数据获取失败');
				},
				success : function(data) {
					var st = "<ul class='ajax-loaders'>";
					st += "<li><img src='" + basePath
							+ "/img/ajax-loaders/ajax-loader-7.gif'></li></ul>";
					$("#singlecard").html(st);
					var str = "";
					for (var i = 0; i < data.length; i++) {
						str += "<tr>";
						str += "<td><span class='label'>" + data[i].orderno
								+ "</span></td>";
						str += "<td><span class='label'>" + data[i].cardno
								+ "</span></td>";
						str += "<td class='center'><span class='label'>"
								+ data[i].datetime + "</span></td>";
						str += "<td class='center'><span class='label'>"
								+ data[i].payable + "</span></td>";
						str += "<td class='center'><span class='label'>"
								+ data[i].value + "</span></td>";
						var actual = data[i].actual;
						if (!actual && typeof (actual) != "undefined"
								&& actual != 0) {
							actual = "0";
						}
						str += "<td class='center'><span class='label'>"
								+ actual + "</span></td>";
						var deducted = data[i].deducted;
						if (!deducted && typeof (deducted) != "undefined"
								&& deducted != 0) {
							deducted = "0";
						}
						str += "<td class='center'><span class='label'>"
								+ deducted + "</span></td>";
						if (data[i].result == '2000') {
							str += "<td class='center'><span class='label label-success'>成功</span></td>";
						} else if (data[i].result == '2001') {
							str += "<td class='center'><span class='label label-warning'>处理中</span></td>";
						} else {
							str += "<td class='center'><span class='label label-warning'>失败</span></td>";
						}
						str += "<td class='center'><span class='label'>"
								+ data[i].info + "</span></td>";
						str += "</tr>";
					}
					$("#singlecard").html(str);
				}
			});
}
function gettransfer(basePath, page, starttime, endtime, transferid) {
	$.ajax({
		url : basePath + "/transfer/getTransfer",
		data : 'page=' + page + '&&starttime='
		+ starttime + '&&endtime=' + endtime
		+ '&&transferid=' + transferid,
		type : 'post',
		dataType : 'json',
		error : function() {
			alert('数据获取失败');
		},
		success : function(datas) {
			var data = datas[0];
			refundpage = datas[1];
			if (page == 1) {
				$("li#tranprev").addClass("active");
			} else {
				$("li#tranprev").removeClass("active");
			}
			if (page == refundpage) {
				$("li#trannext").addClass("active");
			} else {
				$("li#trannext").removeClass("active");
			}
			var prev = page - 1;
			var next = page + 1;
			$("a#tranprev").attr("onclick", "transferpa(" + prev + ")");
			$("a#trannum").html(page);
			$("a#trannext").attr("onclick", "transferpa(" + next + ")");
			var st = "<ul class='ajax-loaders'>";
			st += "<li><img src='" + basePath
					+ "/img/ajax-loaders/ajax-loader-7.gif'></li></ul>";
			$("#transfer").html(st);
			var str = "";
			for (var i = 0; i < data.length; i++) {
				str += "<tr>";
				str += "<td><span class='label'>" + data[i].transfer_id
						+ "</span></td>";
				str += "<td class='center'><span class='label'>"
						+ data[i].pname + "</span></td>";
				str += "<td class='center'><span class='label'>"
						+ data[i].pename + "</span></td>";
				str += "<td class='center'><span class='label'>"
						+ data[i].transfer_amount + "</span></td>";
				str += "<td class='center'><span class='label'>"
						+ data[i].transfer_time + "</span></td>";
				str += "<td class='center'><span class='label'>"
					+ data[i].transfer_explain + "</span></td>";
				str += "</tr>";
			}
			$("#transfer").html(str);
		}
	});
}
$(document).ready(function() {
	$("#cardcode").change(function() {
		var cardcode = $("#cardcode").val();
		if (cardcode == 30) {
			$("#face5").fadeIn();
			$("#face6").fadeIn();
			$("#face10").fadeIn();
			$("#face15").fadeIn();
			$("#face20").fadeIn();
			$("#face30").fadeIn();
			$("#face50").fadeIn();
			$("#face100").fadeIn();
			$("#face120").fadeIn();
			$("#face200").fadeIn();
			$("#face300").fadeIn();
			$("#face500").fadeIn();
			$("#face1000").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face9").fadeOut();
			$("#face25").fadeOut();
			$("#face35").fadeOut();
			$("#face40").fadeOut();
			$("#face45").fadeOut();
			$("#face60").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
		} else if (cardcode == 31) {
			$("#face1").fadeIn();
			$("#face2").fadeIn();
			$("#face3").fadeIn();
			$("#face5").fadeIn();
			$("#face9").fadeIn();
			$("#face10").fadeIn();
			$("#face15").fadeIn();
			$("#face25").fadeIn();
			$("#face30").fadeIn();
			$("#face35").fadeIn();
			$("#face45").fadeIn();
			$("#face50").fadeIn();
			$("#face100").fadeIn();
			$("#face300").fadeIn();
			$("#face350").fadeIn();
			$("#face1000").fadeIn();
			$("#face6").fadeOut();
			$("#face20").fadeOut();
			$("#face40").fadeOut();
			$("#face60").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face120").fadeOut();
			$("#face200").fadeOut();
			$("#face468").fadeOut();
			$("#face500").fadeOut();
		} else if (cardcode == 32) {
			$("#face10").fadeIn();
			$("#face20").fadeIn();
			$("#face30").fadeIn();
			$("#face50").fadeIn();
			$("#face100").fadeIn();
			$("#face200").fadeIn();
			$("#face300").fadeIn();
			$("#face500").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face5").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face15").fadeOut();
			$("#face25").fadeOut();
			$("#face35").fadeOut();
			$("#face40").fadeOut();
			$("#face45").fadeOut();
			$("#face60").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face120").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
			$("#face1000").fadeOut();
		} else if (cardcode == 33) {
			$("#face10").fadeIn();
			$("#face15").fadeIn();
			$("#face20").fadeIn();
			$("#face25").fadeIn();
			$("#face30").fadeIn();
			$("#face50").fadeIn();
			$("#face60").fadeIn();
			$("#face100").fadeIn();
			$("#face300").fadeIn();
			$("#face468").fadeIn();
			$("#face500").fadeIn();
			$("#face1000").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face5").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face35").fadeOut();
			$("#face40").fadeOut();
			$("#face45").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face120").fadeOut();
			$("#face200").fadeOut();
			$("#face350").fadeOut();
		} else if (cardcode == 34) {
			$("#face5").fadeIn();
			$("#face10").fadeIn();
			$("#face15").fadeIn();
			$("#face30").fadeIn();
			$("#face60").fadeIn();
			$("#face100").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face20").fadeOut();
			$("#face25").fadeOut();
			$("#face35").fadeOut();
			$("#face40").fadeOut();
			$("#face45").fadeOut();
			$("#face50").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face120").fadeOut();
			$("#face200").fadeOut();
			$("#face300").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
			$("#face500").fadeOut();
			$("#face1000").fadeOut();
		} else if (cardcode == 35) {
			$("#face10").fadeIn();
			$("#face20").fadeIn();
			$("#face30").fadeIn();
			$("#face50").fadeIn();
			$("#face100").fadeIn();
			$("#face200").fadeIn();
			$("#face300").fadeIn();
			$("#face500").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face5").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face15").fadeOut();
			$("#face25").fadeOut();
			$("#face35").fadeOut();
			$("#face40").fadeOut();
			$("#face45").fadeOut();
			$("#face60").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face120").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
			$("#face1000").fadeOut();
		} else if (cardcode == 36) {
			$("#face5").fadeIn();
			$("#face10").fadeIn();
			$("#face20").fadeIn();
			$("#face25").fadeIn();
			$("#face30").fadeIn();
			$("#face50").fadeIn();
			$("#face100").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face15").fadeOut();
			$("#face35").fadeOut();
			$("#face40").fadeOut();
			$("#face45").fadeOut();
			$("#face60").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face120").fadeOut();
			$("#face200").fadeOut();
			$("#face300").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
			$("#face500").fadeOut();
			$("#face1000").fadeOut();
		} else if (cardcode == 37) {
			$("#face5").fadeIn();
			$("#face10").fadeIn();
			$("#face15").fadeIn();
			$("#face20").fadeIn();
			$("#face30").fadeIn();
			$("#face50").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face25").fadeOut();
			$("#face35").fadeOut();
			$("#face40").fadeOut();
			$("#face45").fadeOut();
			$("#face60").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face100").fadeOut();
			$("#face120").fadeOut();
			$("#face200").fadeOut();
			$("#face300").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
			$("#face500").fadeOut();
			$("#face1000").fadeOut();
		} else if (cardcode == 38) {
			$("#face15").fadeIn();
			$("#face30").fadeIn();
			$("#face50").fadeIn();
			$("#face100").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face5").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face10").fadeOut();
			$("#face20").fadeOut();
			$("#face25").fadeOut();
			$("#face35").fadeOut();
			$("#face40").fadeOut();
			$("#face45").fadeOut();
			$("#face60").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face120").fadeOut();
			$("#face200").fadeOut();
			$("#face300").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
			$("#face500").fadeOut();
			$("#face1000").fadeOut();
		} else if (cardcode == 39) {
			$("#face5").fadeIn();
			$("#face10").fadeIn();
			$("#face15").fadeIn();
			$("#face30").fadeIn();
			$("#face40").fadeIn();
			$("#face100").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face20").fadeOut();
			$("#face25").fadeOut();
			$("#face35").fadeOut();
			$("#face45").fadeOut();
			$("#face50").fadeOut();
			$("#face60").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face120").fadeOut();
			$("#face200").fadeOut();
			$("#face300").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
			$("#face500").fadeOut();
			$("#face1000").fadeOut();
		} else if (cardcode == 40) {
			$("#face10").fadeIn();
			$("#face20").fadeIn();
			$("#face30").fadeIn();
			$("#face50").fadeIn();
			$("#face100").fadeIn();
			$("#face300").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face5").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face15").fadeOut();
			$("#face25").fadeOut();
			$("#face35").fadeOut();
			$("#face40").fadeOut();
			$("#face45").fadeOut();
			$("#face60").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face120").fadeOut();
			$("#face200").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
			$("#face500").fadeOut();
			$("#face1000").fadeOut();
		} else if (cardcode == 41) {
			$("#face10").fadeIn();
			$("#face15").fadeIn();
			$("#face30").fadeIn();
			$("#face50").fadeIn();
			$("#face100").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face5").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face20").fadeOut();
			$("#face25").fadeOut();
			$("#face35").fadeOut();
			$("#face40").fadeOut();
			$("#face45").fadeOut();
			$("#face60").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face120").fadeOut();
			$("#face200").fadeOut();
			$("#face300").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
			$("#face500").fadeOut();
			$("#face1000").fadeOut();
		} else if (cardcode == 42) {
			$("#face10").fadeIn();
			$("#face20").fadeIn();
			$("#face30").fadeIn();
			$("#face40").fadeIn();
			$("#face50").fadeIn();
			$("#face60").fadeIn();
			$("#face70").fadeIn();
			$("#face80").fadeIn();
			$("#face90").fadeIn();
			$("#face100").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face5").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face15").fadeOut();
			$("#face25").fadeOut();
			$("#face35").fadeOut();
			$("#face45").fadeOut();
			$("#face120").fadeOut();
			$("#face200").fadeOut();
			$("#face300").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
			$("#face500").fadeOut();
			$("#face1000").fadeOut();
		} else if (cardcode == 43) {
			$("#face5").fadeIn();
			$("#face10").fadeIn();
			$("#face15").fadeIn();
			$("#face30").fadeIn();
			$("#face50").fadeIn();
			$("#face100").fadeIn();
			$("#face1").fadeOut();
			$("#face2").fadeOut();
			$("#face3").fadeOut();
			$("#face6").fadeOut();
			$("#face9").fadeOut();
			$("#face20").fadeOut();
			$("#face25").fadeOut();
			$("#face35").fadeOut();
			$("#face40").fadeOut();
			$("#face45").fadeOut();
			$("#face60").fadeOut();
			$("#face70").fadeOut();
			$("#face80").fadeOut();
			$("#face90").fadeOut();
			$("#face120").fadeOut();
			$("#face200").fadeOut();
			$("#face300").fadeOut();
			$("#face350").fadeOut();
			$("#face468").fadeOut();
			$("#face500").fadeOut();
			$("#face1000").fadeOut();
		}
	});
});