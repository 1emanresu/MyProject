<%@page import="com.vo.System_qq"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.Systemss"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../page/page.jsp"%>
<%
	Systemss systemss = Systemss.dao.findById(1);
	List<System_qq> systemqqlist = System_qq.dao.find("select * from system_qq");
%>
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
						<li class="active">关于我们</li>
					</ul>
					<!-- .breadcrumb -->
				</div>

				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="widget-box">
								<div class="widget-header">
									<h4 class="smaller">关于我们</h4>
								</div>

								<div class="widget-body">
									<div class="widget-main">
										<p><%=systemss.getStr("system_telephone") %> </p>
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