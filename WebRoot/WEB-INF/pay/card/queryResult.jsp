<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>用户查询</title>
</head>

<body>
<center>
<h2>查询成功</h2> 
        <form name="ofpay" action="" method="POST" >
        <table><tr>
			<td><label>商 户 号:</label></td><td><%=request.getAttribute("usercode") %></tr>
			<tr><td><label>商户订单号:</label></td><td><%=request.getAttribute("orderno") %></td></tr>
			<tr><td><label>欧飞流水号:</label></td><td><%=request.getAttribute("billid") %></td></tr>
			<tr><td><label>充值卡面值:</label></td><td><%=request.getAttribute("value") %></td></tr>
			<tr><td><label>结算金额:</label></td><td><%=request.getAttribute("accountvalue") %></td></tr>
			<tr><td><label>返回码:</label></td><td><%=request.getAttribute("result") %></td></tr>
	    	<tr><td><label>信息:</label></td><td><%=request.getAttribute("info") %></td></tr>
			<tr><td><label>时间:</label></td><td><%=request.getAttribute("datetime") %></td></tr>
			</table>
		</form>
		</center>
</body>
</html>