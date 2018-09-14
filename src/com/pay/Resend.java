package com.pay;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.jfinal.core.Controller;
import com.vo.Order;
import com.vo.Payment;
import com.yeepay.DigestUtil;

public class Resend extends Controller{
	
	public static boolean asynchronous(String orderid){
        boolean boo = false;
        try {
            for(int i=0; i<3; i++){
                Order order = Order.dao.findById(orderid);
                int p1_MerId=order.getInt("p1_MerId");
                Payment payment = Payment.dao.findById(p1_MerId);                
                String key=payment.getStr("key");
                
                String r0_Cmd = order.getStr("p0_Cmd");
                int r1_Code = order.getInt("r1_Code");
                String r3_Amt = order.getBigDecimal("p3_Amt").toString();
                String r4_Cur = order.getStr("p4_Cur");
                String r5_Pid = "0";
                String r6_Order = order.getStr("p2_Order");
                String r7_Uid = "0";
                String r8_MP = "0";
                String r9_BType = "1";
                String rp_PayDate = "";
                String p8_Url = DelNull(order.getStr("p8_Url"));
                
                String sb = "";
    			sb += p1_MerId;
    			sb += r0_Cmd;
    			sb += r1_Code;
    			sb += orderid;//平台流水号
    			sb += r3_Amt;
    			sb += r4_Cur;
    			sb += r5_Pid;
    			sb += r6_Order;
    			sb += r7_Uid;
    			sb += r8_MP;
    			sb += r9_BType;        
    			sb += rp_PayDate;
    			String rehmac = DigestUtil.hmacSign(sb, key); //返回给客户的数据签名
    			System.out.println("生成的客户数字签名字符串:"+sb);
    			System.out.println("==返回时==生成的客户数字签名=："+rehmac);

    			String result = "";
    			result += p8_Url;
    			result += "?r0_Cmd=" + r0_Cmd;
    			result += "&r1_Code=" + r1_Code;
    			result += "&r2_TrxId=" + orderid;
    			result += "&r3_Amt=" + r3_Amt;
    			result += "&r4_Cur=" + r4_Cur;
    			result += "&r5_Pid=" + r5_Pid;
    			result += "&r6_Order=" + r6_Order;
    			result += "&r7_Uid=" + r7_Uid;
    			result += "&r8_MP=" + r8_MP;
    			result += "&r9_BType=" + r9_BType;
    			result += "&rp_PayDate=" +rp_PayDate;
    			result += "&hmac=" + rehmac;  			
    			System.out.println("回调地址带参数："+result);

    			HttpClient hClient = new HttpClient();
				//hClient.getParams().setContentCharset( "GBK "); 
				HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();
				// 设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(1110000);
				// 设置读数据超时时间(单位毫秒)
				managerParams.setSoTimeout(3011000);
				PostMethod post = null;
				post = new PostMethod(result);
				post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
				post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				String returnStr = "";
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
				if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
					System.out.println("链接客户服务器失败或无返回值");
				}else{
					boo=true;
				}
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接客户URL异常");
        }
        return boo;
    }
	
	public static String DelNull(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}

}
