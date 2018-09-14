<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@page import="java.util.Date"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据输入页</title>
<SCRIPT LANGUAGE="JavaScript">
    function dosubmit(){
       form1.action = "OrderQuery.jsp";
       document.form1.submit();
    }
</SCRIPT>
</head>

<body>
<form method="post" name="form1" id="form1" >
<table>
<tr>
<td>商户号:</td>
<td><input width="350" type="text" name="MerchantID" value="100754"/></td><!--商户号不能为空-->
<tr>
<td>商户流水号:</td>
<td><input width="350" type="text"  name="TransID"/></td><!--订单号不能为空-->
</tr>
<tr>
<td colspan="2" align="center"><input type="button" name="button" value="提交" onClick="dosubmit()" /></td>
</tr>

</table>
</form>
</body>
</html>
