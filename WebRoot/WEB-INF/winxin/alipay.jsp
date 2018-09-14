<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>支付宝支付</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<link href="css/style.css" type="text/css" rel="stylesheet" />

	<script type="text/javascript">
	   var timer;
		$(function(){
			var handler = function(){
				var out_trade_no = $('input[name=out_trade_no]').val();
				$.post("testPayResultQuery?out_trade_no="+out_trade_no,null,function(msg){
					//alert(msg);
					if(msg == '1'){
// 						alert("支付成功");
						document.location.href="pay_success.jsp";
						clearInterval(timer);
					}
				});
			}
			timer = setInterval(handler , 5000);
		});
	</script>
</head>
<body>
<div align="center" bgcolor="#666666">
   <div>
<!--扫描代码-->
	<input type="hidden" name="out_trade_no"  value="${out_trade_no}"/>
      <div class="s-con" id="codem">
	   <div class="m26">
               <h1>订单提交成功，请您尽快付款！</h1>
               <div class="num"><span class="color1 ml16" style="font-size:15px;">订单号：<label id="out_trade_no" class="orange">${out_trade_no}</label></span><span class="color1 ml16">请您在提交订单后 <span class="orange">5分钟</span> 内完成支付，否则订单会自动取消。</span></div>
         </div>
         <div class="title"><span class="color1 ml16" style="font-size:15px;">商品名称：<label class="orange">${body}</label></span></br><span class="color1 ml16" style="font-size:15px;">订单金额：<label class="orange">${total_fee/100}</label>元</span>
		 
		 
		 </div>
         <div class="scan"><img src="${code_img_url}"/></div>
         <div class="question">
            <div class="new">支付宝支付如何绑定银行卡</div>
         </div>
      </div>
<!--扫描代码结束-->
<!--底部代码-->
      <div class="s-foot">7x24小时客服QQ:1904178861</div>
<!--底部代码结束-->
   </div>
</div>
</body>
</html>
