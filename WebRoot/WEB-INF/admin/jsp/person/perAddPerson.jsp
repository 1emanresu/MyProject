<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
	.add-customer{height:95%;}
	.add-customer .content-left{float:left;width:50%;height:100%;}
	.add-customer .content-right{float:right;width:50%;height:100%;}
	.add-customer dl{margin-bottom:14px;}
	.add-customer h5{text-align:center;color:#a03c4d;margin-bottom:7px;}
</style>
</head>
<body>
	<div class="pageContent">
		<form action="<%=adminbasePath%>/person/addPerson" class="pageForm required-validate" method="post"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<input type="hidden" name="addpersonToken" value="${addpersonToken}" />
			<div class="pageFormContent" layoutH="58">
				<fieldset class="add-customer">
					<legend>添加客户</legend>
					<div class="content-left">
						<h5><span class="red">*</span>为必填内容</h5>
						<dl>
							<dt>邮箱：</dt>
							<dd>
								<input type="text" name="email" class="required email" />
							</dd>
						</dl>
						<dl>
							<dt>密码：</dt>
							<dd>
								<input type="password" name="password" id="password" class="required alphanumeric" minlength="6" maxlength="20" />
							</dd>
						</dl>
						<dl>
							<dt>确认密码：</dt>
							<dd>
								<input type="password" name="repassword" class="required" equalto="#password" />
							</dd>
						</dl>
						<dl>
							<dt>支付密码：</dt>
							<dd>
								<input type="password" name="payment" class="required" minlength="6" maxlength="20" />
							</dd>
						</dl>
						<dl>
							<dt>网站名称：</dt>
							<dd>
								<input type="text" name="webName" class="required" />
								<span class="info">* 个人填写姓名公司填写公司名称</span>
							</dd>
						</dl>
						<dl>
							<dt>网站网址：</dt>
							<dd>
								<span style="float:left;font-size:16px;">http://</span><input type="text" name="website" class="required"/>
							</dd>
						</dl>
						<dl>
							<dt>所属行业：</dt>
							<dd>
								<select name="herolist">
									<option value="0" selected="selected">党政机关</option>
									<option value="1">商业</option>
									<option value="2">制造业</option>
									<option value="3">服务业</option>
									<option value="4">农业牧渔业</option>
									<option value="5">其它行业</option>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>用户类型：</dt>
							<dd>
								<select name="huge">
									<option value="0" selected="selected">个人</option>
									<option value="1">企业</option>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>手机号码：</dt>
							<dd>
								<input type="text" name="phone" class="required phone"/>
							</dd>
						</dl>
					</div>
					<div class="content-right">
						<h5>&nbsp;</h5>
						<dl>
							<dt>公司/个人：</dt>
							<dd>
								<input type="text" name="name"  class="required"/>
								<span class="info">请输入个人或公司名称</span>
							</dd>
						</dl>
						<dl>
							<dt>联 系 人：</dt>
							<dd>
								<input type="text" name="contacts"  class="required"/>
							</dd>
						</dl>
						<dl>
							<dt>身 份 证：</dt>
							<dd>
								<input type="text" name="idcard"  />
							</dd>
						</dl>
						<dl>
							<dt>营业执照：</dt>
							<dd>
								<input type="text" name="licence" />
							</dd>
						</dl>
						<dl>
							<dt>联系地址：</dt>
							<dd>
								<input type="text" name="address"  class="required"/>
							</dd>
						</dl>
						<dl>
							<dt>QQ号码：</dt>
							<dd>
								<input type="text" name="qq"  class="required"/>
							</dd>
						</dl>
						
						<dl>
							<dt>是否是代理人：</dt>
							<dd>
								<input type="checkbox" name="isAgent" value="1" onChange="showPerm(this);"/>
							</dd>
						</dl>
						

						
						<dl style="display:none;">
							<dt>下级用户代理权限：</dt>
							<dd>
								<input type="checkbox" name="permission" value="1"/>
							</dd>
						</dl>
						
						<dl>
							<dt>上级代理人id：</dt>
							<dd>
								<input type="text" name="superior"/>
							</dd>
						</dl>
						
						
					</div>
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

<script>

function showPerm(ele){
	
		$(ele).parent().parent().next().toggle();
		$(ele).parent().parent().next().find("input[type=checkbox]")[0].checked=false;
	
}

</script>
</html>