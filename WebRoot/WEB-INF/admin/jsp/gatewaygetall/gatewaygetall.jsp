
<%@page import="com.vo.Employees"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Employees emp = (Employees)request.getSession().getAttribute("adminsession");
	int employeeid = emp.getInt("employeeid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$('#wx').click(
		function() {
			var kk=$('input[name=btn]').val('wx');
			
		});
$('#ali').click(
		function() {
		var kk=$('input[name=btn]').val('ali');
		
		});
$('#all').click(
		function() {
		var kk=$('input[name=btn]').val('all');
		
		});




var idTmr;  
function method1(tableid) {//整个表格拷贝到EXCEL中  
   var curTbl = document.getElementById(tableid); 
    var oXL = new ActiveXObject("Excel.Application"); 
    //创建AX对象excel   
    var oWB = oXL.Workbooks.Add();  
    //alert(oWB);
    //获取workbook对象   
    var xlsheet = oWB.Worksheets(1); 
    //激活当前sheet   
    var sel = document.body.createTextRange();  
    sel.moveToElementText(curTbl);  
    //把表格中的内容移到TextRange中   
    sel.select();  
    //全选TextRange中内容   
    sel.execCommand("Copy");  
    //复制TextRange中内容    
    xlsheet.Paste();  
    //粘贴到活动的EXCEL中         
    oXL.Visible = true;  
    //设置excel可见属性  

    try {  
        var fname = oXL.Application.GetSaveAsFilename("将Table导出到Excel.xls", "Excel Spreadsheets (*.xls), *.xls");  
    } catch (e) {  
        print("Nested catch caught " + e);  
      
    } finally {  
        oWB.SaveAs(fname);  

        oWB.Close(savechanges = false);  
        //xls.visible = false;  
        oXL.Quit();  
        oXL = null;  
        //结束excel进程，退出完成  
        //window.setInterval("Cleanup();",1);  
        idTmr = window.setInterval("Cleanup();", 1);  

    }  
    var url ="<%=adminbasePath%>";
    this.location.href = url;
}  
function Cleanup() {  
    window.clearInterval(idTmr);  
    CollectGarbage();  
}  
function zuotian(){
	 
    var zuo=GetDateStr(-1); 
    var jin=GetDateStr(0); 
    $('input[name="starttime"]').val(zuo);
    $('input[name="endtime"]').val(jin);
}
function jintian(){

    var jin =GetDateStr(0);
    var	ming=GetDateStr(1);
    $('input[name="starttime"]').val(jin);
    $('input[name="endtime"]').val(ming);
}
function GetDateStr(AddDayCount) { 
var dd = new Date(); 
dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
var y = dd.getFullYear(); 
var m = dd.getMonth()+1;//获取当前月份的日期 
var d = dd.getDate(); 
return y+"-"+m+"-"+d; 
} 
function upload(){
	var url = "<%=basePath%>/settlement/showExpor2";
	
	var starttime = $('input[name="starttime"]').val();
	var endtime = $('input[name="endtime"]').val();
	var btn = $('input[name=btn]').val();
	alert(btn)
	url = url + "?starttime=" + starttime + "&endtime=" + endtime
			+ "&btn=" + btn;
	
	this.location.href=url;
}
</script>
</head>
<body onload="ha()">
	<div class="pageHeader">
		<form action="<%=adminbasePath%>/gateway/getGatewayGetAll"
			onsubmit="return navTabSearch(this)" method="post">
			<div class="searchBar">
				<table class="searchContent">
<tr>
						
						<td>日期：<input type="text" name="starttime"
							value="${starttime}" class="date" dateFmt="yyyy-MM-dd" /> --<input
							type="text" name="endtime" value="${endtime}" class="date"
							dateFmt="yyyy-MM-dd" /> --<input type="button" value="昨天" onclick='zuotian()'/> --<input type="button" value="今天" onclick='jintian()'/></td> 			</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
						<input name="btn" type="hidden" value="${btn}"/>
								<div class="buttonContent">
									<button id="wx" type="submit" style="margin:0 10px 0 10px">微信通道</button>
								</div>
								<div class="buttonContent">
									<button id="ali" type="submit" style="margin:0 10px 0 10px">支付宝通道</button>
								</div>
								<div class="buttonContent">
									<button id="all" type="submit" style="margin:0 10px 0 10px">全部通道 </button>
								
								</div>
							
							</div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent" id="ta">
		<h1 style="margin: 0 30%">通道金额统计  开始日期 ${starttime} 结束日期 ${endtime}</h1>
			
		<table class="table" width="1200" layoutH="138">
			<thead>
				
				<tr>
					<th>通道编号</th>
					<th>通道名称</th>
					<th>交易金额</th>
					<th>实际金额</th>
					<th>收入</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${orderlist1}" var="order">
					<tr>
						<td><c:out value="${order.gateway_id}"></c:out></td>
						<td><c:out value="${order.gateway_name}"></c:out></td>
						<td><c:out value="${order.amt}"></c:out></td>
						<td><c:out value="${order.amount}"></c:out></td>
						<td><c:out value="${order.deducted}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

<form>
<input id="Button1" type="button" value="导出EXCEL"   
        onclick="javascript:upload()" />  
      
</body>
</html>