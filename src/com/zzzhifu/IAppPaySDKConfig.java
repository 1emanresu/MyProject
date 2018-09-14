package com.zzzhifu;

/**
 *应用接入iAppPay云支付平台sdk集成信息 
 */
public class IAppPaySDKConfig{

	/**
	 * 应用名称：
	 * 应用在iAppPay云支付平台注册的名称
	 */
	public final static  String APP_NAME = "h5";

	/**
	 * 应用编号：
	 * 应用在iAppPay云支付平台的编号，此编号用于应用与iAppPay云支付平台的sdk集成 
	 */
	public final static  String APP_ID = "3013677303";

	/**
	 * 商品编号：
	 * 应用的商品在iAppPay云支付平台的编号，此编号用于iAppPay云支付平台的sdk到iAppPay云支付平台查找商品详细信息（商品名称、商品销售方式、商品价格）
	 * 编号对应商品名称为：1
	 */
	public final static  int WARES_ID_1=1;

	/**
	 * 应用私钥：
	 * 用于对商户应用发送到平台的数据进行加密
	 */
	public final static String APPV_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJZ9aFYzAAXVm/g1ESS/tSZ/xwEBJaGIrCisyMdavTMnNDyH1N4VAc2j6WHZL4gmOsIhpulOV3ZgqUgtoYcniSwasRKFlOaw/VuLL1HXKkC9XeyGU/5oL9evttWmBt6XFqXzYS6wRgO4Yi5atLWf6F18uPOnUZeGpFtjGh0hpaeFAgMBAAECgYBNNjDHMUmMtMlecxPFsBqokxyulkERfQcfo28sYZD7Ytz5RZPhEhQnxzah/oXVrA+vtQZnY8uaMAQKxWa/6ngFAHhc3naqwL5iKeXVnmpVY6bxTB5IZjyZFcpL0xqtXfLDtSdGN3Ftyr7ofDVpGvg9kfjizcqlm2T6aUSCVV1grQJBAMo6FPdGVDJfmvRLObuMgR7GESOGgNhS98pIOLur0LURv9lpE0Q4Ldmos9lb1qmnicz+Nt4sqrihX/nQh8vnZl8CQQC+gYB/8aobscqbQta618WHELhFxqwsvI+aPzzgj6DwL8evxS2qeKRMRpkzl6vdmTTglL+VELVdH6H5mYbivNSbAkEApkHKKuFvJ1rikiWmeUKyhRJh1Msn8rGDdpKZ0fEXzUWibKOua0+komXINtMCUMikyhvWECK42MITndaLTmAYEQJAEFnbJqAeYpwi8KqO9aB5crtLpamEs55HkQymc/u/632eXQbGgaAzMdzJX3VAKhfxQulhfDDf8Cs74sl7m1BrywJBAKqnXvmgZWFi2JUsoItSoo2gdD6G475UBMwOH2yVMxa3+vSSZcmcgLcyRXSFxY3WOPE0MaV0H6ezej5PCJPAte8=";
	/**
	 * 平台公钥：
	 * 用于商户应用对接收平台的数据进行解密
	
	 */
	public final static String PLATP_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCOCQeTNcWu0HgKy/2hWKc07wPxwZ0YJFhxy+qOSOlqCvhh+KC9pDyCRAmmsMT3sFsm7gOXy4RAUZhDjyNt8BoQvou3E2RYqIG+5QHZxGa1MPNc4x+NGYxm4rd3kXdsOAYgpnd7LfiaegDXDAV0oRXP4pglXfiTGIxyEHHB6DThrQIDAQAB";
	
}