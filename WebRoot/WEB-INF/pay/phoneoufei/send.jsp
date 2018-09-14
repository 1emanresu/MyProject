<%@page language="java" contentType="text/html;charset=gbk"%>
<%!	String formatString(String text){ 
			if(text == null) {
				return ""; 
			}
			return text;
		}
%>
<%
	request.setCharacterEncoding("GBK");
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String userid = (String)request.getAttribute("userid");
	String userpws = (String)request.getAttribute("userpws");
	String cardid = "140101";
	String cardnum = (String)request.getAttribute("cardnum");
	String sporder_id = (String)request.getAttribute("sporder_id");
	String sporder_time = (String)request.getAttribute("sporder_time");
	String game_userid = (String)request.getAttribute("game_userid");
	String md5_str = (String)request.getAttribute("md5_str");
	String ret_url = (String)request.getAttribute("ret_url");
	String version= "6.0";
%>
<html>
<head>
<style>
body{text-align:center;margin:0 atuto;}
.pzs{
	margin: 0;font-family: 'Karla', sans-serif;
  font-weight: bold;
  color: #317eac;
  text-rendering: optimizelegibility;
}
</style>
</head>
	<body>
	<div style="margin-top: 250px" align="center" >
		<p class="pzs">Ã·Ωª÷–°£°£</p>
		<img src="<%=basePath %>/img/ajax-loaders/ajax-loader-7.gif" />
	</div>
		<form name="yeepay" action='http://api2.ofpay.com/onlineorder.do' method='POST'>
			<input type='hidden' name='userid'   value='<%=userid%>'>
			<input type='hidden' name='userpws' value='<%=userpws%>'>
			<input type='hidden' name='cardid' value='<%=cardid%>'>
			<input type='hidden' name='cardnum'   value='<%=cardnum%>'>
			<input type='hidden' name='sporder_id'   value='<%=sporder_id%>'>
			<input type='hidden' name='sporder_time'  value='<%=sporder_time%>'>
			<input type='hidden' name='game_userid' value='<%=game_userid%>'>
			<input type='hidden' name='md5_str'   value='<%=md5_str%>'>
			<input type='hidden' name='ret_url'   value='<%=ret_url%>'>
			<input type='hidden' name='version'    value='<%=version%>'>
		</form>
	</body>
	<script type="text/javascript">
		function sub(){
			document.yeepay.submit();
		}
		setTimeout(sub,1000);
	</script>
</html>
