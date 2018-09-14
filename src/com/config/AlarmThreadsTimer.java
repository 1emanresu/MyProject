package com.config;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimerTask;

import com.function.Yunsms;
import com.vo.Gateway;
import com.vo.Order;

public class AlarmThreadsTimer extends TimerTask{
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void run() {
		System.out.println("预警系统开始扫描订单状态");		
		StringBuffer sql = new StringBuffer();
		sql.append("select b.*");
		sql.append(" from gateway b");
		sql.append(" where wxwap_isno='y' or alipaywap_isno='y' or weixin_isno='y' or alipay_isno='y'");
		List<Gateway> asyn = Gateway.dao.find(sql.toString());		
		for(Gateway ba:asyn){
			String wxwap_isno =ba.get("wxwap_isno");
			String alipaywap_isno = ba.getStr("alipaywap_isno");
			String weixin_isno =ba.get("weixin_isno");
			String alipay_isno =ba.get("alipay_isno");
			if(wxwap_isno.equals("y")){
				//System.out.println(ba.getStr("gateway_name")+"微信H5通道预警启用!");
				int wxsum = ba.getInt("wxsum"); 
				String telno = ba.get("telno");
				System.out.println(wxsum+"-----"+telno);
				String mysql1 = "select * from orders where pd_FrpId like '%WX%' and gateway_id="+ba.getInt("gateway_id")+" order by CreateTime DESC LIMIT "+wxsum;
				System.out.println("wxwap"+mysql1);	
				List<Order> myorder1 = Order.dao.find(mysql1.toString());
				boolean flag1=false;
				if(myorder1.size()>0){	
					for(Order od1:myorder1){
						if(od1.getInt("r1_Code")==1){ 
							System.out.println(ba.getStr("gateway_name")+"微信H5通道正常");
							flag1=true;
							continue;
						}					
					}
					if(!flag1){
						String content="【聚优支付】温馨提示：通道'"+ba.get("gateway_name")+"'微信H5在最近的"+wxsum+"条支付都没有成功，请核查！！！";
						System.out.println(content);
						try {
							Yunsms.sms(telno, content);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			if(alipaywap_isno.equals("y")){
				System.out.println(ba.getStr("gateway_name")+"----支付宝H5通道预警启用!");
				int aliwapsum = ba.getInt("aliwapsum"); 
				String telno = ba.get("telno");				
				String mysql2 = "select * from orders where pd_FrpId like '%ALI%' and  gateway_id="+ba.getInt("gateway_id")+" order by CreateTime DESC LIMIT "+aliwapsum;
				System.out.println("alipaywap=:"+mysql2);	
				List<Order> myorder2 = Order.dao.find(mysql2);
				boolean flag2=false;
				if(myorder2.size()>0){	
					for(Order od2:myorder2){
						if(od2.getInt("r1_Code")==1){
							flag2=true;
							continue;
						}else{
							//System.out.println(ba.getStr("gateway_name")+"支付宝H5通道正常");						
						}
					}
					if(!flag2){
						//System.out.println("----支付宝H5通道发送短信-----");
						String content="【聚优支付】温馨提示：通道'"+ba.get("gateway_name")+"'支付宝H5在最近的"+aliwapsum+"条支付都没有成功，请核查！！！";
						System.out.println(content);
						try {
							Yunsms.sms(telno, content);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			if(weixin_isno.equals("y")){
				//System.out.println(ba.getStr("gateway_name")+"微信扫码通道预警启用!");
				int weixinsum = ba.getInt("weixinsum"); 
				String telno = ba.get("telno");				
				String mysql3 = "select * from orders where pd_FrpId like '%WEISM%' and  gateway_id="+ba.getInt("gateway_id")+" order by CreateTime DESC LIMIT "+weixinsum;
				List<Order> myorder3 = Order.dao.find(mysql3);
				boolean flag3=false;
				if(myorder3.size()>0){	
					for(Order od3:myorder3){
						if(od3.getInt("r1_Code")==1){
							//System.out.println(ba.getStr("gateway_name")+"微信扫码通道正常");
							flag3=true;
							continue;
						}						
					}
					if(!flag3){
						String content="【聚优支付】温馨提示：通道'"+ba.get("gateway_name")+"'微信扫码在最近的"+weixinsum+"条支付都没有成功，请核查！！！";
						System.out.println(content);
						try {
							Yunsms.sms(telno, content);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			if(alipay_isno.equals("y")){
				//System.out.println(ba.getStr("gateway_name")+"支付宝扫码通道预警启用!");
				int alipaysum = ba.getInt("alipaysum"); 
				String telno = ba.get("telno");				
				String mysql4 = "select * from orders where pd_FrpId like '%ZHISM%' and  gateway_id="+ba.getInt("gateway_id")+" order by CreateTime DESC LIMIT "+alipaysum;
				List<Order> myorder4 = Order.dao.find(mysql4);
				boolean flag4=false;
				if(myorder4.size()>0){	
					for(Order od4:myorder4){
						if(od4.getInt("r1_Code")==1){
							//System.out.println(ba.getStr("gateway_name")+"支付宝扫码通道正常");
							flag4=true;
							continue;
						}
					}
					if(!flag4){
						String content="【聚优支付】温馨提示：通道'"+ba.get("gateway_name")+"'支付宝扫码在最近的"+alipaysum+"条支付都没有成功，请核查！！！";
						System.out.println(content);
						try {
							Yunsms.sms(telno, content);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

}
