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
	<h2 class="contentTitle">系统信息</h2>
	<div class="pageFormContent" layoutH="42">
		<div style="border: 1px solid #B8D0D6; padding: 5px; margin: 5px">
			<form action="<%=adminbasePath%>/system/upEmp" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
				<fieldset>
					<legend>最高权限密码修改</legend>
					<dl class="nowrap">
						<dt>密码：</dt>
						<dd>
							<input type="password" name="password" id="password" class="required alphanumeric" minlength="6" maxlength="20" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>确认密码：</dt>
						<dd>
							<input type="password" name="repassword" class="required" equalto="#password" />
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
		<div style="border: 1px solid #B8D0D6; padding: 5px; margin: 5px">
			<form action="<%=adminbasePath%>/system/setSystemss" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
				<fieldset>
					<legend>系统信息</legend>
					<dl class="nowrap">
						<dt>网站名称：</dt>
						<dd>
							<input style="width: 300px" class="required"  type="text" name="system_webName" value="${systemss.system_webName }" />
						</dd>
					</dl >
					<dl class="nowrap">
						<dt>网站关键字：</dt>
						<dd>
							<input style="width: 300px" class="required"  type="text" name="system_keyword" value="${systemss.system_keyword }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>备案号：</dt>
						<dd>
							<input style="width: 300px" type="text" name="system_filings" value="${systemss.system_filings }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>公司简称：</dt>
						<dd>
							<input style="width: 300px" class="required" type="text" name="system_firm" value="${systemss.system_firm }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>公司具体名称：</dt>
						<dd>
							<input style="width: 300px" class="required" type="text" name="system_company" value="${systemss.system_company }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>公司地址：</dt>
						<dd>
							<input style="width: 300px" class="required" type="text" name="system_address" value="${systemss.system_address }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>客服电话：</dt>
						<dd>
							<input style="width: 300px" class="required" type="text" name="system_telephone" value="${systemss.system_telephone }" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>流量代码：</dt>
						<dd>
							<textarea name="system_included" cols="80" rows="6">${systemss.system_included }</textarea>
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