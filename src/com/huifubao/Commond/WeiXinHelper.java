package com.huifubao.Commond;

import com.huifubao.model.WeiXinPayModel;

public class WeiXinHelper {

	//MD5�ַ�ƴ�Ӽ���
	public static String signMd5(String key,WeiXinPayModel model){
		StringBuilder _sbString=new StringBuilder();
		_sbString.append("version="+model.get_version())
			.append("&agent_id="+model.get_agent_id())
			.append("&agent_bill_id="+model.get_agent_bill_id())
			.append("&agent_bill_time="+model.get_agent_bill_time())
			.append("&pay_type="+model.get_pay_type())
			.append("&pay_amt="+model.get_pay_amt())
			.append("&notify_url="+model.get_notify_url())
			.append("&return_url="+model.get_return_url())
			.append("&user_ip="+model.get_user_ip())
			.append("&key="+key);
		return Md5Tools.MD5(_sbString.toString()).toLowerCase();
	}
	//MD5�ַ�ƴ�Ӽ���
		public static String GetsignString(String key,WeiXinPayModel model){
			StringBuilder _sbString=new StringBuilder();
			_sbString.append("version="+model.get_version())
				.append("&agent_id="+model.get_agent_id())
				.append("&agent_bill_id="+model.get_agent_bill_id())
				.append("&agent_bill_time="+model.get_agent_bill_time())
				.append("&pay_type="+model.get_pay_type())
				.append("&pay_amt="+model.get_pay_amt())
				.append("&amp;notify_url="+model.get_notify_url())
				.append("&return_url="+model.get_return_url())
				.append("&user_ip="+model.get_user_ip())
				.append("&key="+key);
			return _sbString.toString().toLowerCase();
		}
	//�ύ��ַ
	public static String GatewaySubmitUrl(String sign,WeiXinPayModel model){
		StringBuilder _sbString=new StringBuilder();
		_sbString.append("https://pay.heepay.com/Payment/Index.aspx?");
		_sbString.append("version="+model.get_version())
		.append("&agent_id="+model.get_agent_id())
		.append("&agent_bill_id="+model.get_agent_bill_id())
		.append("&agent_bill_time="+model.get_agent_bill_time())
		.append("&pay_type="+model.get_pay_type())
		.append("&pay_amt="+model.get_pay_amt())
		.append("&amp;notify_url="+model.get_notify_url())
		.append("&return_url="+model.get_return_url())
		.append("&user_ip="+model.get_user_ip())
		.append("&goods_name="+model.get_goods_name())
		.append("&goods_num="+model.get_goods_num())
		.append("&goods_note="+model.get_goods_note())
		.append("&remark="+model.get_remark())
		.append("&sign="+sign);
		return _sbString.toString();
	}
	
}
