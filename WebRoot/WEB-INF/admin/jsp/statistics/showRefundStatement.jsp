<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../page/page.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="<%=basePath%>/js/echarts-plain-map.js"></script>
</head>
<body>
	<div class="pageContent">
		<form action="<%=adminbasePath%>/statistics/showStatieRefund" class="pageForm required-validate" method="post"
			onsubmit="return divSearch(this, 'statisticsBox');">
			<div class="pageFormContent">
				<fieldset>
					<dl style="width: 200px">
						<dt style="width: 50px">时间：</dt>
						<dd style="width: 100px">
							<input type="text" name="time1" value="${time1}" class="date" dateFmt="yyyy-MM-dd" />
						</dd>
					</dl>
					<dl style="width: 200px">
						<dt style="width: 10px">-</dt>
						<dd style="width: 150px"><input type="text" name="time2" value="${time2}" class="date" dateFmt="yyyy-MM-dd" /></dd>
					</dl>
					<dl style="width: 100px">
						<dt>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">提交</button>
								</div>
							</div>
						</dt>
					</dl>
				</fieldset>
			</div>
		</form>
	</div>
	<div id="main"
		style="height: 520px; border: 1px solid #ccc; padding: 10px;"></div>
	<script type="text/javascript">
		// Step:3 echarts & zrender as a Global Interface by the echarts-plain.js.
		// Step:3 echarts和zrender被echarts-plain.js写入为全局接口
		var myChart = echarts.init(document.getElementById('main'));
		myChart.setOption({
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '提现金额']
			},
			toolbox : {
				show : true,
				feature : {
					mark : true,
					magicType : [ 'line', 'bar' ],
					restore : true,
					saveAsImage : true
				}
			},
			calculable : true,
			dataZoom : {
				show : true,
				realtime : true,
				start : 40,
				end : 60
			},
			xAxis : [ {
				type : 'category',
				boundaryGap : true,
				data : function() {
					return ${refundtime};
				}()
			} ],
			yAxis : [ {
				type : 'value',
				splitArea : {
					show : true
				}
			} ],
			series : [ {
				name : '提现金额',
				type : 'line',
				data : function() {
					return ${refund_amount};
				}()
			} ]
		});
	</script>
</body>
</html>