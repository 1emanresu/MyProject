<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">

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
	width: 250px;
}
</style>

	<div class="pageContent" style="padding: 5px">
		<div class="tabs">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:;"><span>报表统计</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<div>

					<div layoutH="50"
						style="float: left; display: block; overflow: auto; width: 240px; border: solid 1px #CCC; line-height: 21px; background: #fff">
						<ul class="tree treeFolder">
							<li><a href="javascript">报表统计</a>
								<ul>
									<li><a href="<%=adminbasePath %>/statistics/showStatistics?time1=${time1 }&&time2=${time2}" target="ajax" rel="statisticsBox">统计信息</a></li>
									<li><a href="<%=adminbasePath %>/statistics/showStatieNet?time1=${time1 }&&time2=${time2}" target="ajax" rel="statisticsBox">网银报表</a></li>
									<li><a href="<%=adminbasePath %>/statistics/showStatieCard?time1=${time1 }&&time2=${time2}" target="ajax" rel="statisticsBox">点卡报表</a></li>
									<li><a href="<%=adminbasePath %>/statistics/showStatieRecharge?time1=${time1 }&&time2=${time2}" target="ajax" rel="statisticsBox">充值报表</a></li>
									<li><a href="<%=adminbasePath %>/statistics/showStatieCirclip?time1=${time1 }&&time2=${time2}" target="ajax" rel="statisticsBox">销卡报表</a></li>
									<li><a href="<%=adminbasePath %>/statistics/showStatieRefund?time1=${time1 }&&time2=${time2}" target="ajax" rel="statisticsBox">退款报表</a></li>
								</ul>
							</li>
						</ul>
					</div>

					<div id="statisticsBox" class="unitBox" style="margin-left: 246px;">
						<!--#include virtual="list1.html" -->
					</div>
				</div>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</div>
</body>
</html>