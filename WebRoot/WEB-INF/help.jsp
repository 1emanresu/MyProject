<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="page/Plug.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		$("li#top5").addClass("active");
		$("#content").html('${dynamic.content}');
	});
</script>
</head>
<body style="font-family: Microsoft YaHei">
	<div class="row demo-row">
		<div class="col-md-12">
			<jsp:include page="navigation.jsp"></jsp:include>
		</div>
	</div>
	<div class="container">
		<div class="demo-row typography-row">
			<div class="demo-title">
				<h3 class="demo-panel-title"></h3>
			</div>
			<div class="demo-content">
				<h1 align="center">为您服务我们深感荣幸</h1>
				<p>
					很多人都说他们足够耐心，但你多问几句，他们就不耐烦；很多人开口闭口都是“专业”，当你细问、细看时，都发现它处处业余；很多人都说他们是做事的，可是他们宁可对着你辩解大半天，却不愿意实质解决问题。<b>我们只想脚踏实地为您做点事，并且因此而身心愉悦。</b>
				</p>
				<p>在线帮助中心</p>
				<div class="panel-group" id="accordion">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse"
									data-parent="#accordion" href="#collapseOne">如何成为<%=systemss.getStr("system_firm") %>的用户？</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse in">
							<div class="panel-body">客户在线注册<%=systemss.getStr("system_firm") %>的帐号，等待<%=systemss.getStr("system_firm") %>审核，审核通过的即为<%=systemss.getStr("system_firm") %>的用户。如果注册后长时间未被审核，请及时联系在线QQ客服帮助审核。</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse"
									data-parent="#accordion" href="#collapseTwo">如何在线提交卡号和密码？</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse">
							<div class="panel-body">用户在后台提交卡密可以按照如下操作：<br />
<%=systemss.getStr("system_firm") %>后台－－我要卡密充值－－单卡充值（多卡可以选择批量充值，批量充值卡必须为同品同面额的卡）－－选择类型和面额（面额别选错了哦）－－把卡号和密码粘贴到对应的输入框－－确认充值。<br />
<br />
用户提交完卡密可以到后台&ldquo;订单查询&rdquo;中查询出订单状态，查看卡密是否验证正确；提交卡密过程中有任何疑问请及时联系在线QQ客服。<br />
<br />
特别提示：提交卡密的时候一定要认真核对卡的面值，以免因为错误选择面值造成不必要的损失！</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse"
									data-parent="#accordion" href="#collapseThree">如何申请结算？</a>
							</h4>
						</div>
						<div id="collapseThree" class="panel-collapse collapse">
							<div class="panel-body">用户账户余额大于0的时候即可申请结算，申请结算后十分钟内可以到帐。申请结算的操作步骤如下：<br />
<%=systemss.getStr("system_firm") %>后台－－我的收益－－结算账户管理（确保已经添加了结算银行/支付宝，如果没有请先添加结算账号）－－申请结算－－按提示进行设置－－提交申请。 			<br />
<br />
提交结算申请十分钟内未到帐，请及时联系在线QQ客服！</p>
<p>备注：绑定结算账户的用户，第二天绑定生效以后，就可以随时结算；未绑定结算账户的用户，则是隔天结算，请知悉！</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse"
									data-parent="#accordion" href="#collapsefour">帐号的密码忘记了怎办？</a>
							</h4>
						</div>
						<div id="collapsefour" class="panel-collapse collapse">
							<div class="panel-body">&nbsp;&nbsp; 当遇到帐号密码异常或者遗忘，请及时联系在线QQ客服进行信息验证，验证成功后会初始化密码。建议用户妥善保管好自己的帐号密码，特别是账户里余额的用户哦！</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse"
									data-parent="#accordion" href="#collapsefive">提交的卡密何时能消耗掉？</a>
							</h4>
						</div>
						<div id="collapsefive" class="panel-collapse collapse">
							<div class="panel-body">用户在线提交的神州行话费充值卡、联通充值卡等卡密可以实现10秒内飞速消耗，并及时将金额转到用户的<%=systemss.getStr("system_firm") %>帐号里。部分游戏卡由于过程复杂，消耗过程可能需要1-2分钟，超过三分钟没有反馈消耗信息的请及时联系在线QQ客服处理。</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse"
									data-parent="#accordion" href="#collapsesix">余额放在<%=systemss.getStr("system_firm") %>安全吗？结算需要什么条件？</a>
							</h4>
						</div>
						<div id="collapsesix" class="panel-collapse collapse">
							<div class="panel-body"><p>余额放在<%=systemss.getStr("system_firm") %>帐号里安全吗？</p>
<p>答：余额放在<%=systemss.getStr("system_firm") %>卡盟是安全的，<%=systemss.getStr("system_firm") %>的系统使用国际顶级的安全加密技术，可以有效预防网络盗窃行为，另外<%=systemss.getStr("system_firm") %>诚信经营，口碑行内人人皆知！另外实在不放心，随时可以结算！</p>
<p>结算需要什么条件？</p>
<p>答：绑定结算账户的用户，第二天绑定生效以后，就可以随时结算；未绑定结算账户的用户，则是隔天结算。请知悉！</p></div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.demo-content-wide -->
		</div>
		<!-- /.demo-row -->

	</div>
	<jsp:include page="page/footer.jsp"></jsp:include>
</body>
</html>