package com.function;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.jfinal.core.Controller;

/**
 * 
 * 文件作用：http接口使用实例
 * 
 * 创建时间：2009-06-03
 * 
 * 
 * 100 发送成功 101 验证失败 102 短信不足 103 操作失败 104 非法字符 105 内容过多 106 号码过多 107 频率过快 108
 * 号码内容空 109 账号冻结 110 禁止频繁单条发送 111 系统暂定发送 112 有错误号码 113 定时时间不对 114 账号被锁，10分钟后登录
 * 115 连接失败 116 禁止接口发送 117 绑定IP不正确 120 系统升级
 */

public class Yunsms extends Controller{
	

	/**
	 * @param args
	 * @throws IOException
	 */
//	public static String sms(String phone, String contents) throws Exception{
		
/*//		String key = CryptTool.getPasswordOfNumber(6);
//		
//		// 发送内容
//		String content = "您好，您的验证码："+key+"  【浩松网络】";

		// 创建StringBuffer对象用来操作字符串
		//StringBuffer sb = new StringBuffer("http://http.yunsms.cn/tx/?");
		StringBuffer sb = new StringBuffer("http://www.dxton.com/webservice/sms.asmx/Submit?");
		// 向StringBuffer追加用户名
		sb.append("uid="+Types.uid);

		// 向StringBuffer追加密码（密码采用MD5 32位 小写）
		sb.append("&pwd="+Types.pwd);

		// 向StringBuffer追加手机号码
		sb.append("&mobile="+phone);

		sb.append("&encode=utf8");
		
		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content=" + URLEncoder.encode(contents));
		// System.out.println(sb.toString());

		
		// 向StringBuffer追加用户名
		sb.append("account=635355353");

		// 向StringBuffer追加密码（密码采用MD5 32位 小写）
		sb.append("&password=0985251f3d13076beec69aca778ea31f");

		// 向StringBuffer追加手机号码
		sb.append("&mobile="+phone);

		sb.append("&encode=utf8");
		
		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content=" + URLEncoder.encode(contents));
		// System.out.println(sb.toString());
		// 创建url对象
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));

		// 返回发送结果
		String inputline = in.readLine();

		// 返回结果为‘100’ 发送成功
//		System.out.println(inputline);
		return inputline;
		
*/		
//		System_interface systeminterface = System_interface.dao.findById(1);
//		String Url = systeminterface.getStr("system_interface_smsurl")+"?";
//		String account = systeminterface.getStr("system_interface_smsaccount");
//		String password = systeminterface.getStr("system_interface_smspassword");
//		String Url = "http://sms.253.com/msg/"+"?";
//		String account = "N3218638";
//		String password = "Mengfh568";
//		HttpClient client = new HttpClient(); 
//		PostMethod method = new PostMethod(Url); 
//			
//		//client.getParams().setContentCharset("GBK");		
//		client.getParams().setContentCharset("UTF-8");
//		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
	    
//		String Url = "http://sms.253.com/msg/"+"?";
//		String account = "N3218638";
//		String password = "Mengfh568";
//		String ex=null;
//		HttpClient client = new HttpClient(); 
//		PostMethod method = new PostMethod(Url); 
//			
//		//client.getParams().setContentCharset("GBK");		
//		client.getParams().setContentCharset("UTF-8");
//		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
	    
//		NameValuePair[] data = {//提交短信
//			    //new NameValuePair("account", account), 
//			    //new NameValuePair("password", password), 			    
//			    //new NameValuePair("mobile", phone), 
//			    //new NameValuePair("content", contents),			    
//			    new NameValuePair("un", account),
//				new NameValuePair("pw", password), 
//				new NameValuePair("phone", phone),
//				new NameValuePair("rd", "1"), 
//				new NameValuePair("msg", contents),
//				new NameValuePair("ex", ex), 
//		};
//		
//		method.setRequestBody(data);		
//		client.executeMethod(method);	
//		
//		String SubmitResult =method.getResponseBodyAsString();
//				
//		//System.out.println(SubmitResult);
//
//		Document doc = DocumentHelper.parseText(SubmitResult); 
//		Element root = doc.getRootElement();
//
//
//		String result = root.elementText("result");	
	/*	String msg = root.elementText("msg");	
		String smsid = root.elementText("smsid");	*/
		
		
		/*System.out.println(result);*/
		/*System.out.println(msg);
		System.out.println(smsid);*/
		
		/*if(result == "100"){
			System.out.println("短信提交成功");
		}*/
//		return result;
//	}
	
	public static String sms(String phone,String content) throws Exception {
		
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager());
		GetMethod method = new GetMethod();
		String url = "http://sms.253.com/msg/";
		String account = "N5583283";
		String password = "Ifve7CqluWe260";	
		String rd="1";
		String ex=null;
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "send", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("un", account),
					new NameValuePair("pw", password), 
					new NameValuePair("phone", phone),
					new NameValuePair("rd", rd), 
					new NameValuePair("msg", content),
					new NameValuePair("ex", ex), 
				});
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				String returnString = URLDecoder.decode(baos.toString(), "UTF-8");
				String[] vars = returnString.split(",");
				String results = vars[1].substring(0,1);
				String msgid = vars[1].substring(2);
				if(results.equalsIgnoreCase("1")){
					results= vars[1].substring(0,3);
				}else{
					results = "100";
				}
				return results;
			} else {
				return "连接通道失败";
			}
		} finally {
			method.releaseConnection();
		}		
	}
	
	public static void main(String[] args) {			
//		try {
//			System.out.println(sms("13392858905,13302988905", "【聚优支付】欢迎体验253云通讯产品验证码是2532536"));
//		} catch (Exception e) {
//			System.out.println("异常信息");
//			e.printStackTrace();
//		}
		
//		String returnStr = "<div style='padding-top: 100px; text-align: center; font-size: 15px; font-family: 'Microsoft YaHei'; color: #FF5500; font-weight: bold;'>su22ccess</div>";
//		String[] re = returnStr.split("\n");
//		for(String st:re){
//			st = st.trim();
//			if(st.length()>7){
//				st = st.substring(0, 7);
//			}
//			int succ1 = returnStr.indexOf("充值成功");
//			int succ2 = returnStr.indexOf("SUCCESS");
//			int succ3 = returnStr.indexOf("success");
//			//System.out.println(succ);
//			if(succ1==-1 && succ2==-1 && succ3==-1){
//				
//				System.out.println("异步通知不成功xxxxxxxxxx");
//			}else{
//				System.out.println("异步通知成功yyyyyyyyyyyy");
//			}			
//		}
		Date date = new Date();	
		long len = date.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssssss");
		String str = "10";
		BigDecimal a = new BigDecimal(str);
		System.out.println("---------"+a.setScale(2));
//		String today = sdf.format(date)+" 23:59:59";
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE,+1);
//		String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime())+" 00:00:00";	
		System.out.println("今天开始日期："+len+""+"-----------"+sdf.format(date));
		
//		String json = "sign=eb16c7e12d7a0ac2b0d08cf7fad5417e&pay_number=HQALI201705060042410421082111&amount=20&respCode=000000&WXOrderNo=2017050621001004170207781696&orderId=D0100100024086725&respInfo=%E4%BA%A4%E6%98%93%E6%88%90%E5%8A%9F";
//		String[] strs = json.split("&");
//		Map<String, String> m = new HashMap<String, String>();
//		for(String s:strs){
//		String[] ms = s.split("=");
//		m.put(ms[0], ms[1]);
//		}
//		System.out.println(m.get("amount"));
	}
}
