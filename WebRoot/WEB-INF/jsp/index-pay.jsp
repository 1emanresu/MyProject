<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<title>统一预下单接口调用</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="css/index.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/WEB-INF/jsp/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$('input[name=out_trade_no]').val(new Date().getTime());
			$('.hideClass').hide();
			
			$('input[name=time_start]').val(getCurrentDate()); 
		});
		function getCurrentDate(){
			var date = new Date();
			return date.getFullYear() + '' + formatString(date.getMonth() + 1) + formatString(date.getDay()) + formatString(date.getHours()) + formatString(date.getMinutes()) + formatString(date.getSeconds()); 
		}
		function formatString(value){
			if(parseInt(value) < 10){
				return  0 + '' + value; 
			}
			return value;
		}
		//验证ip
		function isIP(ip) {  
		    var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;  
		    if (reSpaceCheck.test(ip)) {  
		        ip.match(reSpaceCheck);  
		        if (RegExp.$1<=255&&RegExp.$1>=0  
		          &&RegExp.$2<=255&&RegExp.$2>=0  
		          &&RegExp.$3<=255&&RegExp.$3>=0  
		          &&RegExp.$4<=255&&RegExp.$4>=0) {  
		            return true;   
		        } else {  
		            return false;  
		        }  
		    } else {  
		        return false;  
		    }  
		}  
		function doSubmit(){
			alert("bbbbbbbbbbbbbbbbbbbb3333");			
			$('form').submit();
		}
	</script>
</head>
<body text=#000000 bgColor="#ffffff" leftMargin=0  topMargin=4>
	<div id="main">
        <div class="cashier-nav">
            <ol>
				<li class="current">1、提交信息（统一预下单） </li> 
            </ol>
        </div>
        <form action="testPay" method="post"  target="_blank">
            <div id="body" style="clear:left">
                <dl class="content">
                    <dt class="hideClass">接口类型：</dt>
					<dd class="hideClass">
						<input name="service" value="unified.trade.pay" readonly="readonly" maxlength="32"  placeholder="长度32"/>
						<span class="null-star">(长度32)*</span>
						<span></span>
					</dd>
					<dt class="hideClass">版本号：</dt>
					<dd class="hideClass">
						<span class="null-star"></span>
						<input size="30" name="version" value="1.0" readonly="readonly" maxlength="8"  placeholder="长度8"/>
						<span>(长度8)</span>
						<span></span>
					</dd>
					<dt class="hideClass">字符集：</dt>
					<dd class="hideClass">
						<span class="null-star"></span>
						<input size="30" name="charset" value="UTF-8" readonly="readonly" maxlength="8"  placeholder="长度8"/>
						<span>(长度8)</span>
						<span></span>
					</dd>
                    <dt class="hideClass">签名方式：</dt>
                    <dd class="hideClass">
                        <span class="null-star"></span>
                        <input size="30" name="sign_type" value="MD5" readonly="readonly" maxlength="8"  placeholder="长度8"/>
						<span>(长度8)</span>
                        <span></span>
                    </dd>
                    <dt>商户订单号：</dt>
                    <dd>
                        <span class="null-star"></span>
                        <input name="out_trade_no" value="" maxlength="32" size="30"  placeholder="长度32"/>
                        <span class="null-star">(长度32)*</span>
                        <span></span>
                    </dd>
                    <dt>商品描述：</dt>
                    <dd>
                        <span class="null-star"></span>
                        <input name="body" value="测试购买商品" maxlength="127" size="30"  placeholder="长度127"/>
                        <span class="null-star">(长度127)*</span>
                        <span></span>
                    </dd>
                    
                    <dt>总金额：</dt>
                    <dd>
                        <span class="null-star"></span>
                        <input name="total_fee" value="1"  placeholder="单位：分"/> 
                        <span class="null-star">(单位：分 整型)*</span>  
                        <span></span>
                    </dd>
                    <dt>终端IP：</dt>
                    <dd>
                        <span class="null-star"></span>
                        <input name="mch_create_ip" value="127.0.0.1" maxlength="16"  placeholder="长度16"/>
                        <span class="null-star">(长度16)*</span>
                        <span></span>
                    </dd>
                    
                    <dd>
                        <span class="new-btn-login-sp">
                            <button class="new-btn-login" type="submit" " style="text-align:center;">接口调用</button>
                        </span>
                    </dd>
                </dl>
            </div>
		</form>
        <div id="foot">
			<ul class="foot-ul">
				<li><font class="note-help">如果您点击“确认”按钮，即表示您同意该次的执行操作。 </font></li>
				<li>
					威富通版权所有 7
				</li>
			</ul>
		</div>
	</div>
</body>
</html>