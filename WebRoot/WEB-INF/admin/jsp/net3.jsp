<%@page import="com.vo.Employees"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Employees emp = (Employees) request.getSession().getAttribute("adminsession");
	int employeeid = emp.getInt("employeeid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	$("#netparticipate option[value='${netparticipate}']").attr("selected",
			true);
	$("#netstate option[value='${netstate}']").attr("selected", true);
	$("#netlock option[value='${netlock}']").attr("selected", true);

	function getDay(day) {
		var today = new Date();

		var targetday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24
				* day;
		today.setTime(targetday_milliseconds); //注意，这行是关键代码    
		var tYear = today.getFullYear();
		var tMonth = today.getMonth();
		var tDate = today.getDate();
		tMonth = doHandleMonth(tMonth + 1);
		tDate = doHandleMonth(tDate);
		return tYear + "-" + tMonth + "-" + tDate;
	}
	function doHandleMonth(month) {
		var m = month;
		if (month.toString().length == 1) {
			m = "0" + month;
		}
		return m;
	}

	function MyDate(v) {
		if (v == 1) {
			document.getElementById("netstarttime").value = getDay(0);
			document.getElementById("netendtime").value = getDay(1);
		} else if (v == 2) {
			document.getElementById("netstarttime").value = getDay(-1);
			document.getElementById("netendtime").value = getDay(0);
		} else {
			var date = new Date(); //当前日期
			var now = date.getDay();
			if (now == 0) {
				now = 7;
			}
			var first = 1 - now;
			document.getElementById("netstarttime").value = getDay(first);
			document.getElementById("netendtime").value = getDay(1);
		}
	}
	
	 function fun(){
		  alert("设置成功 ");			  
		  var zhi=$('#interval option:selected').val();
		  $('input[name="time"]').val(zhi);
		  var kk='<%=adminbasePath%>/system/MyQueryListNet';
		  kk=kk+'/';
		  kk=kk+zhi;
		  $('#auto').attr("href",kk);
		  alert(zhi);		  
		 }
		

		
		
</script>
<style type="text/css">
		#acc{
	text-decoration:none
	}
			#a2{
	text-decoration:none
	}
			#auto{
	text-decoration:none
	}
</style>
</head>
<body>
	<form id="pagerForm" method="post"
		action="<%=adminbasePath%>/system/MyQueryListNet/">
		<input type="hidden" name="pageNum" value="1" /> <input type="hidden"
			name="numPerPage" value="${numPerPage}" /> <input type="hidden"
			name="netstarttime" value="${netstarttime}" /> <input type="hidden"
			name="netendtime" value="${netendtime }" /> <input type="hidden"
			name="netparticipate" value="${netparticipate}" /> <input
			type="hidden" name="netstate" value="${netstate}" /> <input
			type="hidden" name="netorderid" value="${netorderid}" /> <input
			type="hidden" name="netname" value="${netname}" /> <input
			type="hidden" name="netlock" value="${netlock}" /><input
			type="hidden" name="p2_Order" value="${p2_Order}" />
	</form>

	<ul>
		<li>
			<form rel="pagerForm"
				action="<%=adminbasePath%>/system/MyQueryListAccount1/"
				onsubmit="return navTabSearch(this)" method="post">
				<input name="account" value="${account}" id="inp_acc" /> <a
					type="submit" id="acc" onclick="fun2()">设置显示条数</a>
			</form>
		</li>
		<li><input name="account" id="code" /> <a id="a2">设置处理中报警</a></li>
		<li><input name="account" id="interval" /> <a id="auto"
			target="navTab" href="javascript:return false;">设置刷新时间(是秒数噢)</a></li>
	</ul>
	<br>
	<a href="#" id="shezhi" onclick="fun3()">"设置"</a>
</body>
<script type="text/javascript">
 function fun3(){
		
		  var zhi1=$('#inp_acc').val();
		  var zhi2=$('#code').val();
		  var zhi=$('#interval').val();
		  $('input[name="time"]').val(zhi);
		  var kk='<%=adminbasePath%>';
		kk = kk + '/';
		kk = kk + zhi1;
		kk = kk + '-';
		kk = kk + zhi2;
		kk = kk + '-';
		kk = kk + zhi;
		$('#shezhi').attr("href", kk);

		var data = document.getElementById("inp_acc").value;
		if (data == "") {
			alert("设置显示条数不能为空哦!");
		}

		var data2 = document.getElementById("code").value;
		if (data2 == "") {
			alert("设置处理中报警不能为空哦!");
		}

		var data3 = document.getElementById("interval").value;
		if (data3 == "") {
			alert("设置刷新时间不能为空哦!");
		}

	}
</script>

</html>