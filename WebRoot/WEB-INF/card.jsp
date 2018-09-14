<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="page/Plug.jsp"></jsp:include>
<style type="text/css">
s {
	display: inline-block;
	*zoom: 1;
	*display: inline;
	margin: 2px 2px 0 0;
	width: 40px;
	height: 28px;
	line-height: 28px;
	border: 2px solid #dedfe0;
	text-align: center;
	cursor: default;
	font-family: Arial;
	text-decoration: none;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("li#top4").addClass("active");
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
				<h3 class="demo-panel-title">支持卡种</h3>
			</div>
			<div class="demo-content">
				<div class="row demo-tiles">
					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/shenzhouxingchongzhika.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">神州行充值卡（移动）</p>
							<p><s>500</s><s>300</s><s>100</s><s>50</s><s>30</s><s>20</s><s>10</s></p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/01212954i38j.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">盛大一卡通</p>
							<p><p><s>350</s><s>100</s><s>50</s><s>45</s><s>35</s><s>30</s><s>10</s><s>5</s></p></p>
						</div>
					</div>

					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/01213736w258.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">久游一卡通</p>
							<p><s>100</s><s>50</s><s>30</s><s>25</s><s>20</s><s>15</s><s>10</s></p>
						</div>
					</div>

					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/01213706654x.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">Q币充值卡</p>
							<p><s>200</s><s>100</s><s>60</s><s>30</s><s>15</s><s>10</s><s>5</s></p>
						</div>
					</div>
				</div>
				
				<div class="row demo-tiles">
					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/01213609m6tm.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">网易一卡通</p>
							<p><s>50</s><s>30</s><s>20</s><s>15</s><s>10</s><s>5</s></p>
						</div>
					</div>
					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/01213529cphl.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">巨人一卡通</p>
							<p><s>500</s><s>300</s><s>100</s><s>60</s><s>50</s><s>30</s><s>25</s><s>等</s></p>
						</div>
					</div>

					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/01213355hy8v.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">欧飞一卡通</p>
							<p><s>100</s><s>50</s><s>30</s><s>15</s><s>10</s><s>5</s></p>
						</div>
					</div>

					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/012133147dgp.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">联通话费充值卡</p>
							<p><s>500</s><s>300</s><s>100</s><s>50</s><s>30</s><s>20</s><s>10</s></p>
						</div>
					</div>
				</div>
				
				<div class="row demo-tiles">
					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/01213225ovrj.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">骏网一卡通</p>
							<p><s>500</s><s>200</s><s>100</s><s>50</s><s>30</s><s>10</s></p>
						</div>
					</div>
					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/0121315615oa.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">完美一卡通</p>
							<p><s>100</s><s>50</s><s>30</s><s>15</s></p>
						</div>
					</div>

					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/06222535aeo2.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">电信充值卡</p>
							<p><s>100</s><s>50</s></p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="tile">
							<img
								src="<%=basePath%>/images/cardlogo/152132521j69.jpg"
								class="img-rounded img-responsive">
							<p class="tile-title">更多卡种详见会员中心</p>
							<p><s>100</s><s>50</s><s>30</s></p>
						</div>
					</div>
				</div>
				<!-- /tiles -->
			</div>
			<!-- /.demo-content-wide -->
		</div>
		<!-- /.demo-row -->

	</div>
	<jsp:include page="page/footer.jsp"></jsp:include>
</body>
</html>