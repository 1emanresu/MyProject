<%@page import="java.util.List"%>
<%@page import="com.vo.Dynamic"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../page/page.jsp"%>
<%
	List<Dynamic> dynamiclist = Dynamic.dao.find("SELECT * FROM dynamic d ORDER BY d.createtime DESC");
	Dynamic dynamic = null;
	if(dynamiclist.size()>0){
		dynamic = dynamiclist.get(0);
	}
			
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<div class="navbar navbar-default" id="navbar">
		<script type="text/javascript">
			try {
				ace.settings.check('navbar', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="navbar-container" id="navbar-container">
			<div class="navbar-header pull-left">
				<a href="#" class="navbar-brand"> <small> <i
						class="icon-leaf"></i> 万鑫支付
				</small>
				</a>
				<!-- /.brand -->
			</div>
			<!-- /.navbar-header -->
			<%
				if(dynamic!=null){
			%>
			<div class="navbar-header" align="center" style="padding-left: 150px">
				<a target="_blank" href="<%=basePath%>/dynamic/getDynamic?dynamicid=<%=dynamic.getInt("dynamicid") %>" class="navbar-brand">
					<MARQUEE scrollAmount=2><small style="font-size:14px;">【公告】<%=dynamic.getStr("title") %></small></MARQUEE>
				</a>
			</div>
			<%
				}
			%>
			<!-- /.navbar-header -->
		</div>
		<!-- /.container -->
	</div>
</body>
</html>