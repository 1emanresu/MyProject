function isEmpty(val) {
	val = $.trim(val);
	if (val == null)
		return true;
	if (val == undefined || val == 'undefined')
		return true;
	if (val == "")
		return true;
	if (val.length == 0)
		return true;
	if (!/[^(^\s*)|(\s*$)]/.test(val))
		return true;
	return false;
}
function isNotEmpty(val){
	return !isEmpty(val);
}
function selectData(){
	var userstarttime = $('#userstarttime').val();
	var userendtime = $('#userendtime').val();
	var payType = $("#paytype").val();
	if(isNotEmpty(userstarttime) && isNotEmpty(userendtime)){
		if(compareDate(userstarttime,userendtime) > 32){
			$("#warn-message").text("对不起，时间间隔不能超过一个月！").stop().fadeIn("slow").fadeOut(3000);
			return false;
		}
		if(compareDate(userstarttime,userendtime) <= 1){
			$("#warn-message").text("别闹！是不是时间输错了，你再看一下").stop().fadeIn("slow").fadeOut(3000);
			return false;
		}
		if(compareDate(userstarttime,formatDate(new Date())) <= 1){
			$("#warn-message").text("起始时间不能超过当前时间！").stop().fadeIn("slow").fadeOut(3000);
			return false;
		}
		var jsonData = {"userstarttime":userstarttime,"userendtime":userendtime,"payType":payType};
		$.ajax({
			type:"post",
			data:jsonData,
			url:basePath+"/user/findbytime",
			error:function(){
				alert("对不起！数据获取失败");
			},
			success:function(data){
				$("#amount").text(data.amount);
				$("#listsize").text(data.list.length);
				$("#result").text(data.result);
				if((data.dateType).indexOf("ALI") != -1){
					$("#date-type").text("日期(支付宝)").css("color","red");
				}else if((data.dateType).indexOf("WX") != -1){
					$("#date-type").text("日期(微信)").css("color","red");
				}
				var list=data.list;
				var result;
				var CreateTime;
				var name;
				var p3_Amt;
				var rate=data.rate;
				var resultlist="";
				for(var m=0;m<list.length;m++){
					CreateTime=list[m].CreateTime;
					result=list[m].result;
					name=list[m].name;
					p3_Amt=list[m].p3_Amt;
					resultlist+="<tr><td>"+CreateTime+"</td>";
					resultlist+="<td>"+name+"</td>";
					resultlist+="<td>"+p3_Amt+"</td>";
					resultlist+="<td>"+rate+"</td>";
					resultlist+="<td>"+result+"</td></tr>";
				}
				$("#resultlist").html(resultlist);
			}
		});
	}else{
		$("#warn-message").text("别闹！！请输入起始时间,再点查询按钮").stop().fadeIn("slow").fadeOut(3000);
		return false;
	}
};
var now = new Date();
var nowYear = now.getFullYear();
var nowMonth = now.getMonth();
var lastMonth = nowMonth-1;
function MyDate(val){
	if(val == 1){
		//上月
		$("#userstarttime").val(getLastMonthStartDate());
		$("#userendtime").val(getMonthStartDate());
	}else{
		//本月
		$("#userstarttime").val(getMonthStartDate());
		$("#userendtime").val(formatDate(now));
	}
};
//获得本月的开始日期
function getMonthStartDate(){
    var monthStartDate = new Date(nowYear, nowMonth, 1);
    return formatDate(monthStartDate);
};
//获得上月开始时间
function getLastMonthStartDate(){
    var lastMonthStartDate = new Date(nowYear, lastMonth, 1);
    return formatDate(lastMonthStartDate);
};
//获得上月结束时间
function getLastMonthEndDate(){
    var lastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
    return formatDate(lastMonthEndDate);
};
//格式化日期：yyyy-MM-dd
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth()+1;
    var myweekday = date.getDate();
    if(mymonth < 10){
        mymonth = "0" + mymonth;
    }
    if(myweekday < 10){
        myweekday = "0" + myweekday;
    }
    return (myyear+"-"+mymonth + "-" + myweekday);
};
//获得某月的天数
function getMonthDays(myMonth){
    var monthStartDate = new Date(nowYear, myMonth, 1);
    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    var days = (monthEndDate - monthStartDate)/(1000   *   60   *   60   *   24);
    return days;
};
//计算两个日期的时间间隔 
function compareDate(start,end){
    if(start==null||start.length==0||end==null||end.length==0){
        return 0;
    }
    var arr=start.split("-");
    var starttime=new Date(arr[0],arr[1],arr[2]);
    var starttimes=starttime.getTime();
    var arrs=end.split("-");
    var endtime=new Date(arrs[0],arrs[1],arrs[2]);
    var endtimes=endtime.getTime();
    var intervalTime = endtimes-starttimes;//两个日期相差的毫秒数 一天86400000毫秒
    var Inter_Days = ((intervalTime).toFixed(2)/86400000)+1;//加1，是让同一天的两个日期返回一天
    return Inter_Days;
}