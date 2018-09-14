<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="page/jcpage.jsp"></jsp:include>
<script type="text/javascript" src="<%=basePath%>/layer/layer.js"></script>
<script type="text/javascript">
$(function(){
	DWZ.init("<%=basePath%>/admin/dwz.frag.xml", {
//		loginUrl:"login.jsp", loginTitle:"登录",	// 弹出登录对话框
		loginUrl:"<%=adminbasePath%>",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"<%=basePath%>/admin/themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
	$("#divTime").jclock({withDate:true, withWeek:true});
	 getData(); //首次立即加载  
     window.setInterval(getData, 1000*60*3); //循环执行！！  
});
function getData(){
	$.ajax({
		url :  "<%=adminbasePath %>/getRefundcount",
		type : 'post',
		dataType : 'json',
		error : function() {
			alert('数据获取失败');
		},
		success : function(datas) {
			layer.msg('您有新的提现信息共'+datas+'条', 5,{
			    rate: 'top',
			    type: 9,
			    shade: false
			});
		}
	})
}



function getDay(day) {
	var today = new Date();
	var targetday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24* day;
	today.setTime(targetday_milliseconds); //注意，这行是关键代码    
	var tYear = today.getFullYear();
	var tMonth = today.getMonth();
	var tDate = today.getDate();
	tMonth = doHandleMonth(tMonth + 1);
	tDate = doHandleMonth(tDate);
	return tYear + "-" + tMonth + "-" + tDate;
}
function doHandleMonth(month) {
	var m = month;
	if (month.toString().length == 1) {
		m = "0" + month;
	}
	return m;
}

//今天，昨天，本周  按钮
function MyDate(v,ele) {
	
	if (v == 1) {
		
		$(ele).parents(".pageHeader").find(".netstarttime").val(getDay(0));
		$(ele).parents(".pageHeader").find(".netendtime").val(getDay(1));
	
	} else if (v == 2) {
		
		$(ele).parents(".pageHeader").find(".netstarttime").val(getDay(-1));
		$(ele).parents(".pageHeader").find(".netendtime").val(getDay(0));
		
	} else {
		var date = new Date(); //当前日期
		var now = date.getDay();
		if(now==0){
			now = 7;	
		}
		var first = 1 - now;
		$(ele).parents(".pageHeader").find(".netstarttime").val(getDay(first));
		$(ele).parents(".pageHeader").find(".netendtime").val(getDay(1));
	}
}




</script>
</head>
<body scroll="no">
	<audio id="audio" src="<%=basePath %>\jb.wav"></audio>
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#"><%=systemss.getStr("system_company") %></a>
				<ul class="nav">
					<li><a>${employees.name}</a></li>
					<li><a><div id="divTime"></div></a></li>
					<li><a href="<%=adminbasePath%>/login/exit">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<!--<li theme="red"><div>红色</div></li>-->
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
			</div>

			

		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>主菜单</h2>
					<div>收缩</div>
				</div>
				<div class="accordion" fillSpace="sidebar">
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="<%=adminbasePath %>/adminindex" target="navTab" rel="main">主页</a></li>
							<c:forEach items="${navlist}" varStatus="l" var="nav">
								<c:if test="${nav.node==1}">  
								  	<li><a><c:out value="${nav.name}"></c:out></a>
								  		<ul>
											<c:forEach items="${navlist}" var="navl">
												<c:if test="${nav.navid==navl.ownership}">
												  	<li><a href="<%=adminbasePath %>${navl.url}" target="navTab" rel="nav${navl.navid}" reloadFlag=1><c:out value="${navl.name}"></c:out></a></li>
												</c:if>
											</c:forEach>
										</ul>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div layoutH="0">
<!-- 						<iframe width="100%" height="100%" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no" allowtransparency="true" src="http://xcalendar.duapp.com?bd_user=1778419697&amp;bd_sig=b2520a22994c7a5fa832c87dbdb67e69&amp;canvas_pos=platform"></iframe> -->
							<iframe src="<%=adminbasePath %>/adminindex" width="100%" height="100%" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe> 
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div id="footer">
		Copyright ? 2009-2013 <%=systemss.getStr("system_company") %>版权所有 网站备案号：<%=systemss.getStr("system_filings") %>
	</div>
</body>
</html>