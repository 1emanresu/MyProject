<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<div class="pageContent">
		<form action="<%=adminbasePath%>/gateway/insertGateway" class="pageForm required-validate" method="post"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<input type="hidden" name="addpersonToken" value="${addpersonToken}" />
			<div class="pageFormContent" layoutH="58">
				<fieldset>
					<legend>添加通道</legend>
					<dl class="nowrap">
						<dt>通道名称：</dt>
						<dd>
							<input type="text" name="gateway_name" id="gateway_name"/>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>商户编号：</dt>
						<dd>
							<input type="text" name="gateway_merid" id="gateway_merid" style="width:300px;"/>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>商户密钥：</dt>
						<dd>
							<input type="text" name="gateway_key" id="gateway_key" style="width:300px;"/>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>通道类型：</dt>
						<dd>
							<label><input type="checkbox" name="wxwap" value="y"/>wxwap</label>
							<label><input type="checkbox" name="weixin" value="y"/>weixin</label>
							<label><input type="checkbox" name="alipaywap" value="y"/>alipaywap</label>
							<label><input type="checkbox" name="alipay" value="y"/>alipay</label>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>通道请求地址：</dt>
						<dd>
							<input type="text" name="req_url" class="required url" style="width:420px;"/>
							<span class="info">请添加http://开头</span>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>通道回调地址：</dt>
						<dd>
							<input type="text" name="notify_url" class="required url" style="width:420px;"/>
							<span class="info">请添加http://开头</span>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>通道开启时间</dt>
						<dd>
							<input type="text" name="start_time" class="date" dateFmt="yyyy-MM-dd HH:mm:ss"/>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>通道关闭时间：</dt>
						<dd>
							<input type="text" name="end_time" class="date" dateFmt="yyyy-MM-dd HH:mm:ss"/>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>通道额度：</dt>
						<dd>
							<input type="text" name="amount_size"/>
						</dd>
					</dl>
					
					<dl class="nowrap">
						<dt>通道费率：</dt>
						<dd>
							<input type="text" name="gateway_rate"/>&nbsp;%
						</dd>
					</dl>
					
					
				</fieldset>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>