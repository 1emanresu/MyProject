package com.tool;

public class HttpSenderTest {
	public static void main(String[] args) {
		int i=0;
		while(i<10) {
			sendSms();
			i++;
		}
		
	}

	private static void sendSms() {
		String url = "http://sms.253.com/msg/";// 应用地址
		String un = "N5583283";// 账号
		String pw = "Ifve7CqluWe260";// 密码
		String phone = "13377786910";// 手机号码，多个号码使用","分割
		int key = 335435;
		String msg = "【聚优支付】验证码是"+key+"";// 短信内容
		msg = "哈喽!聪啊几";
		String rd = "1";// 是否需要状态报告，需要1，不需要0
		String ex = null;// 扩展码
		
		try {
			String returnString = HttpSender.batchSend(url, un, pw, phone, msg, rd, ex);
			String[] vars = returnString.split(",");
			String results = vars[1].substring(0,1);
			String msgid = vars[1].substring(2);
			if(results.equalsIgnoreCase("1")){
				results= vars[1].substring(0,3);
			}else{
				results = "100";
			}
			System.out.println("results=:"+results);
			System.out.println("msgid=:"+msgid);

			//System.out.println(returnString);
			// TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			// TODO 处理异常
			e.printStackTrace();
		}
	}
}