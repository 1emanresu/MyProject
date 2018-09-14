package com.mustpay.config;

/* *
 *类名：MustpayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究MustPay接口使用，只是提供一个参考。
 */

public class MustpayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	//MustPay平台公钥
	public static String PLATE_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDO7CQpYHhEonv1g9YjRVGJDaCOu0bXogD7pBLQu2dDvJ8TGROCEw6ArIWgAWEEE1uEShPBa4MpCP4ZMjT5RMj45o0pb0Z8s4k6CpS9D1LFK9msNpsN8PyaRDQC86R6jxAVQMWgfIZ9cxfZR8Ple3GJGjwBfeRnzh75rE1DHCBOcwIDAQAB";

	//商户私钥
	public static String MER_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOlv5ksCpeEczgtsp1YOpcabdSrk4RB9W0Q8E6npfUnBe3TwE8jkKrHWUU421YtWxLv+4iYuhxOKy78N5jJ+hY/EbpkhzQpvu43pffqpJ59n63NNLWTjD/2vP4JU8BioSWUnpj/Lm9DNEs7KyyRjAQSc1E12Q74etOKhVXcGPiIpAgMBAAECgYBZh1gsppxWBQUlLovTHpUH4fGC/PswptNNDTmBbhJmKjWeOMFtx8x0Hj1aoP8vjJyxeWTkve2CWjYgVUVXUl0IdeFXWa4RcbevOeVpUR2rPCS9/tr0CoixMYWdtRijEwXPeDLf7ZG/NkT6SX0x+lhSQqXX3zhn8FAjkdr1jeTy/QJBAP7mZ18d6fEBnDffjAYumgjMlaXZTcF2vf73k1wGiZVLhWZ5KH1mK6/mAxFJY0q/QIxNFb35n6UDR/hyoTuP4HMCQQDqccj3f85gI7SraOMwOoma98Y+DoxYb/jErF2Nxdad+jZtybws/+QJZ2o9PrOf6wY0zl1iJFdciId7Pd/qx1fzAkAyYSWDNi1btf7DSEqrmiBVpuHY8rccirSIpTQZ1yzwNgn6s+2dw0jVLjfi/cdDKcO/NykZ8DFuctSRtU5KsGgjAkBdNMWIGEitEiT27Kk9+FfSw2UOMOx9EG8ficNb5OaRQfO3rDdi0I0RbArhtHbnQiHihpS7uuT8hc8PISdPiNcfAkEAsoMeJjx5t+srX/e6Hh7SBwzaZaKNnQ4CtuRpMEL4IOtTYOnGMpxlpX51wsRZLJNdo0NpX/kZXiFmimKQCIdPEw==";
	//测试商户apps_id
	public static String APPS_ID = "a7daa1cde26b47dc9ed9e687eeb96d1e";
	
	//测试商户mer_id
	public static String MER_ID = "17022520192512346";
	
	//异步回调URL 此地址必须外网可访问
	public static String NOTIFY_URL = "http://688.haofpay.com/pay/must_callback";
	
	//同步回调URL 此地址必须外网可访问
	public static String RETURN_URL = "";
	
	// 签名方式
	public static String SIGN_TYPE = "RSA";
		
	// 字符编码格式 目前支持utf-8
	public static String INPUT_CHARSET = "utf-8";
	
	//下单地址
	public static String ADD_ORDER_URL = "http://www.tongdexincheng.com/service/order/saveOrder";
	
	//订单查询地址
	public static String QUERY_ORDER_URL = "http://www.tongdexincheng.com/service/order/queryOrder";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

}