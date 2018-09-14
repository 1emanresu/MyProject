<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../page/page.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="../page/upage1.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../page/top1.jsp"></jsp:include>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>

			<jsp:include page="../page/left1.jsp"></jsp:include>

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a href="#">首页</a></li>
						<li class="active">帮助中心</li>
					</ul>
					<!-- .breadcrumb -->
				</div>

				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="widget-box">
								<div class="widget-header">
									<h4 class="smaller">帮助中心</h4>
								</div>

								<div class="widget-body">
									<div class="widget-main">
										<dl id="dt-list-1">
											<dt>1.什么是网络POS机？</dt>
											<dd>网络POS机是基于一个互联网收付款系统。为各类商户提供安全，快捷，方便的收银服务。同时网络POS类似于“支付宝”系统，还具备订单管理，订单查询，网上商店，电子商务，支持信用卡支付，马上提现到账（365天实时到账）等强大的收银企业管理功能。</dd>
											<br/>
											<dt>2.网络POS机有什么优势？</dt>
											<dd>
												<ol>
													<li>操作简单 —仅1台电脑，便可随时随地完成支付、收款</li>
													<li>超低费率 —最低低至0.25%费率，大大降低商户成本</li>
													<li>快速到帐 —最快的365天实时到帐，不分周末节假日，解决商户资金周转</li>
													<li>对私帐户 —马上实时到帐个人帐户</li>
													<li>开户快捷 —材料简单，当天马上开通使用</li>
													<li>支付广泛 —支持所有信用卡、借记卡，支付范围广（无限额支付）</li>
													<li>不限地域 —无需携带，跨地域消费支付，收款等</li>
													<li>资金安全 —取得国家央行支付牌照第三方支付，资金有安全保障</li>
												</ol>
												
											</dd>
											<dd>(结算时间：早8点～晚上10点，全天实时到账，不分双休日)</dd>
											<br/>
											<dt>3.网络POS机这个资金安全吗？</dt>
											<dd>
												<ol>
													<li>1.所有的资金都是由具有国家人行颁发支付牌照的第三方支付</li>
													<li>2.市面上80%的传统POS机都是第三方推出的，同样的都是第三方推出的产品，T+1隔天到账的安全，马上到账还不安全吗？</li>
												</ol>
											</dd>
											<br/>
											<dt>4.是否对卡有影响？会不会被降额？冻结？</dt>
											<dd>不会的，网络POS机的交易明细都是显示在线支付，网上支付。其性质和淘宝一样，难道天天淘宝买东西就会被降额，现在银行推出各种活动，鼓励线上支付，你觉的银行会这么吃力不讨好吗？网络POS机推出这么久没出现过类似问题。</dd>
											<br/>
											<dt>5.网络POS机合法吗？</dt>
											<dd>
												<ol>
													<li>1.第三方的支付牌照就值几个亿了，会推出不合法的东西吗？</li>
													<li>2.网络POS机的功能类似于支付宝，可以远程收付款，充值提现等等，不存在虚假交易行为。合法合规，受银行鼓励推广，就像支付宝越来越受到各大银行的推崇！</li>
												</ol>
											</dd>
											<br/>
											<dt>6.这个行业有那么好吗？</dt>
											<dd>网络POS机是电子商务与金融的结合体，电子商务的发展叫量化，金融投资的发展叫暴利，那么这个项目量化与暴利的结合体，机不可失，失不再来！</dd>
											<br/>
											<dt>7.这个行业能走多久？</dt>
											<dd>传统POS机已经走了4年时光了，现在还有人再代理推广。传统POS针对的商户群体都要走4年之久，那么庞大的个人群体呢？至少是8年，10年，乃至20年都可能……</dd>
										</dl>
										<!-- /.table-responsive -->
									</div>
								</div>
							</div>
						</div>
						<!-- /span -->
						<div class="col-sm-12">
							<jsp:include page="../page/jspfooter.jsp"></jsp:include>
						</div>
						<!-- /span -->
					</div>
				</div>
				<!-- /.page-content -->
			</div>
			<!-- /.main-content -->
			<jsp:include page="../page/switch.jsp"></jsp:include>
		</div>
		<!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->

	<!--/.fluid-container-->
</body>
</html>