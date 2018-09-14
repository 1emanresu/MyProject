<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body><center>
<h4>您的卡已接收。在处理中！请耐心等待</h4>
<table>
            <tr><td>商户号:</td><td><%=request.getAttribute("usercode") %></td></tr>
			<tr><td>卡号:</td><td><%=request.getAttribute("orderno") %></td></tr>
			<tr><td>时间:</td><td><%=request.getAttribute("datetime") %></td></tr>
			<tr><td>回调地址:</td><td><%=request.getAttribute("retaction") %></td></tr>
</table>
		</form></center>
</body>
</html>