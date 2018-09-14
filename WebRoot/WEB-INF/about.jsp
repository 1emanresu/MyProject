<%@page import="com.vo.System_qq"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
	List<System_qq> systemqqlist = System_qq.dao.find("select * from system_qq");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="page/Plug.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		$("li#top7").addClass("active");
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
		<p><%=systemss.getStr("system_firm") %>网主要经营游戏充值、话费充值、水电煤缴费、机票预定等生活缴费和充值业务；</p>
		<p><%=systemss.getStr("system_firm") %>卡盟目前是国内领先的游戏装备、金币的卡交易平台；同时也是国内最大的储值卡在线寄售采购平台；</p>
		<p><%=systemss.getStr("system_company") %>是一家面向互联网企业提供客服外包和客服培训服务，同时为各大电商平台和大型淘宝网店提供代运营服务。</p>
		<p>公司主营业务有六块，分别是：</p>
		<p>1、话费、游戏在线充值，各种生活费用在线充缴和购买；平台为<%=systemss.getStr("system_firm") %>网；</p>
		<p>2、游戏装备的卡交易中介平台，游戏卡、话费卡及其它储值卡的寄售采购平台，平台为<%=systemss.getStr("system_firm") %>卡盟；</p>
		<p>3、全国各种储值卡的代理、批发、零售、推广、交易等；</p>
		<p>4、大型电商系统开发和联合运营；</p>
		<p>5、客服外包和客服培训；</p>
		<p>6、B2C电商平台及大型淘宝店铺代运营。</p>
		<h3><%=systemss.getStr("system_firm") %>的发展目标：</h3>
		<p>
			<strong><span>未来定位：</span></strong><span>为互联网用户搭建最好的游戏虚拟商品交易平台和网络金融服务平台。</span>
		</p>
		<p>
			<strong><span>奋斗目标：</span></strong><span>中期（3-5年）获得网络支付牌照或者和有支付牌照的公司深入战略合作，长期目标是实现未来定位。</span>
		</p>

		<div class="row">
			<div class="col-md-12">
				<div class="demo-browser">
					<div class="demo-browser-side">
					 <%
		              	System_qq  systemqq = null;
		              	for(int i=0; i<systemqqlist.size(); i++){
		              		if(i==4){
		              			break;
		              		}
		              		systemqq = systemqqlist.get(i);
		              %>
						<p>
							<%=systemqq.getStr("qq_name") %>：<a target="_blank"
								href="http://wpa.qq.com/msgrd?v=3&uin=<%=systemqq.getStr("qq_number") %>&site=qq&menu=yes"><img
								border="0" src="http://wpa.qq.com/pa?p=2:<%=systemqq.getStr("qq_number") %>:41"
								alt="商务合作咨询－恭候您咨询" title="<%=systemss.getStr("system_firm") %>客服－恭候您咨询"></a>
						</p>
						<%
		              	}
						%>

					</div>
					<div class="demo-browser-content">
						<h4><%=systemss.getStr("system_company") %></h4>
						<p>公司地址：<%=systemss.getStr("system_address") %></p>
						<p>官方客服电话：<%=systemss.getStr("system_telephone") %></p>
						<br> <br> <br> <br>
					</div>
				</div>
			</div>
		</div>
		<!-- /download area -->
	</div>
	<jsp:include page="page/footer.jsp"></jsp:include>
</body>
</html>