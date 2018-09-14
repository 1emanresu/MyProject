<%@page import="com.vo.Employees"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">
	
function changeKey(a,b){
	var me = document.getElementById(a).value;
	tome = me*100;
	fv = parseFloat(100-tome).toFixed(2);
	var md = document.getElementById(b);
	md.value = fv;
}

function changeKey2(a,b){
	var me = document.getElementById(a).value;
	tome = me/100;
	fv = parseFloat(1-tome).toFixed(3);
	var md = document.getElementById(b);
	md.value = fv;
}
	
	
</script>
</head>
<body>
	<style type="text/css">
ul.rightTools {
	float: right;
	display: block;
}

ul.rightTools li {
	float: left;
	display: block;
	margin-left: 5px
}

.td1 {
	width: 90px;
}
</style>

	<div class="pageContent" >
		<div style="display: block; overflow: auto;margin:5px; ">
			<div class="pageFormContent" style="border-width: 0px 0 0 0;">
				<form action="<%=adminbasePath %>/person/upRate" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
					<input type="hidden" name="id" value="${rate.id}">
					<fieldset >
					<legend>用户费率</legend>
						<dl>
							<dt>qq钱包费率</dt>
							<dd><input type="text" name="qqwx" value="${rate.qqwx }"/></dd>
						</dl>
					
						<dl>
							<dt>微信费率</dt>
							<dd><input type="text" name="cibsm" value="${rate.cibsm }"/></dd>
						</dl>
						<dl>
							<dt>支付宝费率</dt>
							<dd><input type="text" name="mustali" value="${rate.mustali }"/></dd>
						</dl>
						
						<dl>
							<dt>网银支付</dt>
							<dd>
								<input style="" type="text" name="banking" id="banking" value="${rate.banking}" onkeyup="changeKey('banking','banking2')"/>
								<%--  <input style="width:10px;text-align:center;background:#FFF;border:0px;" type="text" name="banking1" value="——"/>
								<input style="width:50px;" type="text" name="banking2" id="banking2" value="${(1-rate.banking)*100 }" onkeyup="changeKey2('banking2','banking')"/>
								% --%>
							</dd>
						</dl>
						
						<dl>
							<dt>提现手续费</dt>
							<dd><input type="text" name="refund" value="${rate.refund}"/></dd>
						</dl>
					</fieldset>
					<br/><br/>
					<div style="">
						<input type="submit" value="提交" style="margin-left:10px;"></input>
					</div>
				</form>
			</div>
		</div>
				
	</div>
</body>
</html>