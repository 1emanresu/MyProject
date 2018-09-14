<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../page/page.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="../page/upage1.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		$("#active5").addClass("active");
		var pageNum = ${pageNum};
		var totalpage = ${totalpage};
		if(pageNum==1){
			$("li#previous").addClass("disabled");
		}else{
			$("li#previous").removeClass("disabled");
		}
		if (pageNum == totalpage) {
			$("li#next").addClass("disabled");
		} else {
			$("li#next").removeClass("disabled");
		}
	});
</script>
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
						<li class="active">公告列表</li>
					</ul>
					<!-- .breadcrumb -->
				</div>

				<div class="page-content" id="adminloading">
					<div class="row">
						<div class="col-sm-12">
							<div class="widget-box">
								<div class="widget-header">
									<h4 class="smaller">公告列表</h4>
								</div>

								<div class="widget-body">
									<div class="widget-main">
										<div class="table-responsive">
											<table id="sample-table-1"
												class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th>编号</th>
														<th>标题</th>
														<th>发布时间</th>
													</tr>
												</thead>

												<tbody>
													<c:forEach var="dynamic" items="${dynamiclist}">
														<tr>
															<td>${dynamic.dynamicid }</td>
															<td><c:out value="${dynamic.title}"></c:out></td>
															<td>${dynamic.createtime}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											<ul class="pagination">
												<li id="previous"><a id="previous"
													href="<%=basePath%>/user/getAnnouncement?pageNum=${pageNum-1}">上一页</a></li>
												<li class="disabled"><a href="#fakelink">${pageNum}</a></li>
												<li id="next"><a id="next"
													href="<%=basePath%>/user/getAnnouncement?pageNum=${pageNum+1}">下一页</a></li>
											</ul>
										</div>
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