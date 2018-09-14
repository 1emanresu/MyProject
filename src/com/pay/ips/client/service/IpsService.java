package com.pay.ips.client.service;

import IpsBankList.ServiceSoap;
import IpsBankList.ServiceSoapProxy;

import com.pay.ips.client.dao.IpsDao;
import com.tool.MD5Utils;

public class IpsService extends IpsDao {
	public static IpsService service = new IpsService();
	
	public String getbankebh(String MerCode, String key, String payable){
		String participate = "";
		try {  
            //换成对应的proxy类  
            ServiceSoapProxy proxy = new ServiceSoapProxy();  
            proxy.setEndpoint("http://webservice.ips.com.cn/web/Service.asmx?wsdl");  
            //换成获取对应的serice  
            ServiceSoap servicesoap =  proxy.getServiceSoap();
            String signMD5 = MD5Utils.createMD5(MerCode+key);
            //调用web service提供的方法  
            String result = servicesoap.getBankList(MerCode, signMD5);
            result = java.net.URLDecoder.decode(result, "utf-8");   
            String[] banks = result.split("#");
            
            for(String bank : banks){
            	String[] ban = bank.split("\\|");
            	if(ban[0].contains(payable)){
            		participate = ban[2];
            	}
            }
            //System.out.println(participate);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		//#北京银行|北京银行|00050
		//#广东发展银行|银行卡支付(全国范围)|00052
		//#广州市农村信用社|麒麟借记卡(广州地区)|00011
		//#广州市商业银行|羊城万事顺卡借记卡(广州地区)|00011
		//#华夏银行|华夏借记卡(全国范围)|00041
		//#交通银行|太平洋卡(全国范围)|00005
		//#民生银行|民生卡(全国范围)|00013
		//#平安银行|银行卡支付(全国范围)|00006
		//#浦东发展银行|东方卡(全国范围)|00032
		//#上海农村商业银行|如意借记卡(上海地区)|00030
		//#深圳发展银行|发展卡支付(全国范围)|00023
		//#深圳市农村商业银行|借记卡(深圳地区)|00011
		//#顺德农村信用合作社|顺德信用合作社借记卡(顺德地区)|00011
		//#兴业银行|在线兴业(全国范围)|00016
		//#邮政储蓄|邮政储蓄(全国范围)|00051
		//#招商银行|银行卡支付(全国范围)|00021
		//#中国工商银行|工行手机支付(仅限工行手机签约客户)|00026
		//#中国工商银行|网上签约注册用户(全国范围)|00004
		//#中国光大银行|银行卡支付(全国范围)|00057
		//#中国建设银行|网上银行签约客户(全国范围)|00003
		//#中国农业银行|网上银行签约客户(全国范围)|00017
		//#中国银行|银行卡支付(全国范围)|00083
		//#中信银行|银行卡支付(全国范围)|00054

		return participate;
	}
	
	public static void main(String[] args) {
		service.getbankebh("000166","cQ0YAtyVNoiEeKrZJ5F5Qp2pMohwspfv6XoiU3wHYtcc1YOEhJ3SjDVMylmwmbD7jMhg5ifqjX67mzNN02p8MmlSb1KIqa5XF4TEQHjZEmJmhyAYxyVJMeLWBofdxwaj","中国工商银行");
	}
}
