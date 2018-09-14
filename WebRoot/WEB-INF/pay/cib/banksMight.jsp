<%@page import="com.pay.yeepay.server.Configuration"%>
<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	String neturl = Configuration.getInstance().getValue("neturl");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>点卡寄售,点卡储值,点卡充值网站,点卡交易,点卡交易平台,游戏点卡交易,点卡回收,卡类消耗接口,神州行充值卡回收,盛大一卡通回收-浩付卡盟</title>
<link href="<%=basePath%>/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="<%=basePath%>/css/validform.css" rel="stylesheet">
<!-- Loading Flat UI -->
<link href="<%=basePath%>/css/flat-ui.css" rel="stylesheet">
<link href="<%=basePath%>/css/demo.css" rel="stylesheet">

<link rel="shortcut icon" href="<%=basePath%>/images/favicon.ico">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
	  <script src="<%=basePath%>/js/respond.js"></script>
      <script src="<%=basePath%>/js/html5shiv.js"></script>
<![endif]-->
<script src="<%=basePath%>/js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>/js/jquery.ui.touch-punch.min.js"></script>
<script src="<%=basePath%>/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/bootstrap-select.js"></script>
<script src="<%=basePath%>/js/bootstrap-switch.js"></script>
<script src="<%=basePath%>/js/flatui-checkbox.js"></script>
<script src="<%=basePath%>/js/flatui-radio.js"></script>
<script src="<%=basePath%>/js/jquery.tagsinput.js"></script>
<script src="<%=basePath%>/js/jquery.placeholder.js"></script>
<script src="<%=basePath%>/js/jquery.stacktable.js"></script>
<script src="<%=basePath%>/js/application.js"></script>
<script src="<%=basePath%>/js/Validform_v5.3.2_min.js"></script>
</head>
<body style="font-family: Microsoft YaHei">
	<div class="row demo-row">
		<div class="col-md-12">
			<div class="navbar navbar-inverse" style="margin-bottom: -10px">
				<div class="navbar-collapse collapse navbar-collapse-01 container">
					<ul class="nav navbar-nav navbar-left">
						<li><a href="#fakelink"><img style="height: 20px"
								src="<%=basePath%>/images/esulogo.png"
								class="img-rounded img-responsive"></a></li>
						<li class="active"><a href="<%=basePath%>/">首页</a></li>
						<li><a href="<%=basePath%>/dynamic?pageNum=1">公告动态</a>
						<li><a href="#fakelink">安全保障</a></li>
						<li><a href="#fakelink">支持卡种</a></li>
						<li><a href="#fakelink">帮助</a></li>
						<li><a href="#fakelink">接口合作</a></li>
						<li><a href="#fakelink">关于我们</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="row demo-tiles">
		<div class="col-md-4" style="margin-top: 25px">
			<div class="tile">
				<p align="left" style="margin-left: 25px">
					<strong>商 家：${name } </strong><br> <strong>商 品：
						${p5_Pid } </strong><br> <strong>时 间：${time } </strong><br> <strong>订单号：${p2_Order }</strong><br>
					<strong>金 额： ￥${p3_Amt }</strong><br>
				</p>
			</div>
		</div>
		<div class="col-md-8">
			<h3 class="demo-panel-title">在线支付</h3>
			<div class="mrl">
				<form action="<%=basePath + neturl%>/node" method="post">
					<input type="hidden" name="pd" value="1">
					<input type="hidden" name="p0_Cmd" value="${p0_Cmd }" /> <input
						type="hidden" name="p1_MerId" value="${p1_MerId }" /> <input
						type="hidden" name="p2_Order" value="${p2_Order }" /> <input
						type="hidden" name="p3_Amt" value="${p3_Amt }" /> <input
						type="hidden" name="p4_Cur" value="${p4_Cur }" /> <input
						type="hidden" name="p5_Pid" value="${p5_Pid }" /> <input
						type="hidden" name="p6_Pcat" value="${p6_Pcat }" /> <input
						type="hidden" name="p7_Pdesc" value="${p7_Pdesc }" /> <input
						type="hidden" name="p8_Url" value="${p8_Url }" /> <input
						type="hidden" name="p9_SAF" value="${p9_SAF }" /> <input
						type="hidden" name="pa_MP" value="${pa_MP }" /> <input
						type="hidden" name="pr_NeedResponse" value="${pr_NeedResponse }" />
					<input type="hidden" name="hmac" value="${hmac }">
					<table class="table">
						<tbody>
							<tr>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="CCB-NET-B2C" data-toggle="radio"
										checked=""> <img
										src="<%=basePath%>/images/banklogo/jianshe.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="ICBC-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/gongshang.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="CMBCHINA-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/zhaohang.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="ABC-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/nongye.gif">
								</label></td>
							</tr>
							<tr>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="BOCO-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/jiaotong.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="BOC-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/zhongguo.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="POST-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/youzheng.gif">
								</label></td>

								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="HKBEA-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/dongya.gif">
								</label></td>

							</tr>
							<tr>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="CEB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/guangda.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="GDB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/guangfa.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="HZBANK-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/hangzhou.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="SCCB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/hebei.gif">
								</label></td>
							</tr>
							<tr>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="BCCB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/beijing.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="CBHB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/buohai.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="CMBC-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/minsheng.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="NJCB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/nanjing.gif">
								</label></td>
							</tr>
							<tr>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="NCBBANK-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/nanyanbank.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="NBCB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/ningbo.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="BJRCB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/nongcunshangye.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="ECITIC-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/zhongxin.gif">
								</label></td>
							</tr>
							<tr>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="PINGANBANK-NET" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/pingan.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="SHB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/shanghaibank.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="SRCB-NET-B2C" data-toggle="radio">
										<img width="154" height="33"
										src="<%=basePath%>/images/banklogo/shangnongshang.jpg">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="SPDB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/shangpufa.gif">
								</label></td>
							</tr>
							<tr>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="SDB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/shenfa.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="ZJTLCB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/tailong.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="CIB-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/xingye.gif">
								</label></td>
								<td><label class="radio"> <input type="radio"
										name="pd_FrpId" value="CZ-NET-B2C" data-toggle="radio">
										<img src="<%=basePath%>/images/banklogo/zheshang.gif">
								</label></td>
							</tr>
						</tbody>
					</table>
					<button class="btn btn-success" type="submit">确认支付</button>
				</form>
			</div>
		</div>
	</div>
	<!-- /tiles -->
	<footer>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<p align="center">
					Copyright ? 2009-2013 haofpay.com海阳浩付网络科技有限公司版权所有<br />
					网站备案号：鲁ICP备13026900号-1<br /> 电信增值业务经营许可证号：鲁13026900号-1
					鲁13026900号-1
				</p>
				<p align="center">
					<img src="<%=basePath%>/images/110.jpg"> <img
						src="<%=basePath%>/images/alipay.jpg"> <img
						src="<%=basePath%>/images/dianshang.jpg"> <img
						src="<%=basePath%>/images/jingying.jpg"> <img
						src="<%=basePath%>/images/tdflag1.jpg">
				</p>
			</div>


		</div>
	</div>
	</footer>
</body>
</html>