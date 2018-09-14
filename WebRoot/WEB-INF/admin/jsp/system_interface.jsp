<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<h2 class="contentTitle">系统接口信息</h2>
	<div class="pageFormContent" layoutH="42">
		<div style="border: 1px solid #B8D0D6; padding: 5px; margin: 5px">
			<form action="<%=adminbasePath%>/system/setSystemInterface" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
				<fieldset>
					<legend>系统接口信息</legend>
					<dl class="nowrap">
						<dt >殴飞合作账号：</dt>
						<dd>
							<input style="width: 300px" class="required"  type="text" name="system_interface_usercode" value="${systemInterface.system_interface_usercode }" />
						</dd>
					</dl >
					<dl class="nowrap">
						<dt>殴飞密钥：</dt>
						<dd>
							<input style="width: 300px" class="required"  type="text" name="system_interface_md5key" value="${systemInterface.system_interface_md5key }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>块钱证书密码：</dt>
						<dd>
							<input style="width: 300px" class="required" type="text" name="system_interface_fastmoneykey" value="${systemInterface.system_interface_fastmoneykey }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>财付通账号：</dt>
						<dd>
							<input style="width: 300px" class="required" type="text" name="system_interface_caifutongemial" value="${systemInterface.system_interface_caifutongemial }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>支付宝账号：</dt>
						<dd>
							<input style="width: 300px" class="required" type="text" name="system_interface_alipayemail" value="${systemInterface.system_interface_alipayemail }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>网站网址：</dt>
						<dd>
							<input style="width: 300px" class="required" type="text" name="system_interface_httpurl" value="${systemInterface.system_interface_httpurl }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>短信接口：</dt>
						<dd>
							<input style="width: 300px" class="required" type="text" name="system_interface_smsurl" value="${systemInterface.system_interface_smsurl }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>短信接口账号：</dt>
						<dd>
							<input style="width: 300px" class="required" type="text" name="system_interface_smsaccount" value="${systemInterface.system_interface_smsaccount }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>短信接口密码：</dt>
						<dd>
							<input style="width: 300px" class="required" type="text" name="system_interface_smspassword" value="${systemInterface.system_interface_smspassword }" />
						</dd>
					</dl>
					<dl>
						<dd>
							<div class="button">
								<div class="buttonContent">
									<button type="submit">修改</button>
								</div>
							</div>
						</dd>
					</dl>
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>