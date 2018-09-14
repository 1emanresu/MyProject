package com.apppay105.config;

public class GYF2Config {
	// 商户appid
	public static String APPID = "2017042506958148";
	// 私钥 pkcs8格式的	
	public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBx5zxsWghIbho92InnmgcDugYfzEKUyBp1q+F7Zqy2re1xAGWtYnBiEc3d35XfxjjQUBP9HMRDbKylgonPAQBtVhK/Ze4h7pUAJBWz2GWByHvoG+VHa3YCVfbxWSLiMAjsAPMWTU7506pLdXJuonU7yyjocmBDd2T4nyCGPdWX/jFOvfPaqGucJ7CYKCL7rBI1rbQUs+5kJu+Hdccxz6r7sDEPrvLActOALEKvXAIFESRRlh5eQHd9ce7+5YUv2qpQFdRCUBT5WtjpBDpq/pvdi4HEVG5UgqgXDkJch+Ek0f9ZCWkVcVA1LVj7JZsvnUccefQGZ+3uwsAS/iHvRyFAgMBAAECggEAH2ad6bjkGggfp34byLxCMm/E5pB33Ciq0hD43S3VXWsgQiGPxNhd4W7I1HncHu2IpwvVtMLPyJOI5j75a+7QhV1MZLChMivdAJA2XFrHINbO9jzaYrkQpBH2qR1Eq6JF4xymfp4jDEB2PubxMGMrEFNieKEfsVMahrqpJ41E4yfxzloCPa/9wqLAVBYVpxrMlTa7hMy0P8GCjEJ4uWVs32vjNZHIiho/agKQguJ4ydZVBjdLmDXrFRe/6hA6ZL3zHodndzbJxARAHMqtPC9p/sDf1SgfskeXGxbK/lR8Av/ncATehsn3+XWtDPy8IsgD7bpmlxB1rECmHvQBUpH+LQKBgQDakc4CgpeKsTXW+YK64nD+9NGSvYKg+TA97q63l1UDtc0J8VIgITkTuMnEUdl2vKa0rx+Et2uKnH8/Lqy5WqSSHGlSqyUXXP+ccf4RNySu0n9jSi3FprCRagkoSsXCgt3PVgYZw7cL8Q+bs04LPqtZmchllC+1L7H+11oECj2b8wKBgQCYAThJNPWlwlfXOKCFT1V/moRg3UJOSjyFbp3QC4nL8Iy1VM4gOc+JrjTNJyPg0J3NrMYDT5jyoVSdOD3Slr5K/UKi1f9EQF6+QAQm4b104A/SgLQiPY3Fb0A5Q9b+rxVxKgCDgxqzHxWaiv2NAxiZ6H64of5HQLBDlvdoSK9bpwKBgQCL8hN/0/IH4ZNhg/zKgDZtaH/Pfvvm6eaToQHJl2HdsEgKd7Nn0x3inMzDoMymlAABbKe3qGF5LN7kq5P4UitRSlO9lMt4/BjGKM7OYwpfzXO3NUW9ag9f7A7U95tMAdLX8HKTiJbpVH3z6Yis9oFsK2T+dTVew50qWOTP9WahNQKBgDwON1PNJcTA3P2+dMVWy9vni6fNo1XVRMYfJyC8fkviFWXLRMk8rf+ioV/wt98p7T8GpRFH0JEEJq8bOmnKfxKXr00wqXMoz4XxOvK1u6nCoN21VfY1RZ8czk4RvI7RU/Jq+tr64ICmpl1xzq147K5aSj7eWd2C9GEv8z3zgbz/AoGARBZWHRBB14xyPKn+dC9/Qrr78HvKq9iApACZBH0mKnO9Awg4fN8Ykse2PrJ5GNJlXq6Q2cu8pXVGsqnuU4TtYMuRJMRrJviQd6X0OIcLWlL5sK4xeN/I5BX9mFu3/JwXS+uMg9BtW0XZDy8Mh01g3rGsloPqkH01GLGlqoQhU2U=";
	// 服务器异步通知页面路径 需http://或者httpsMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkXtSxgD3dDDdCHEe/uKMLaFhehjWTDYlR35eQUnx0ghBFhZfIwUmj/uKEywgSRTJIihfOHqL+BS7ZVEC7JfsjqQQajjhKMOGqLIFY/fKjaBW66c7SLV3BJ/cRpyyIgXy/hlPtWy1AlyAuYi6Z8wSahMlxovG5n1ja6eZDnm0zxUG0qNEY0xGyllrUliVOljWu5fPNcV1w4Q/ic21w/nFYkp7iSEPAmClswzVNDlnssw48cuBy3dsmXicWUPhoH9RZPj3Iw8rRb+UoYfN3KomSZRibH/Aj29HA0kicoW2zQjvB/T2TdkVJe4kbe/PEevFZMee+fiCHvEXam1E4EQy1QIDAQAB://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	
	public static String notify_url = "http://b.baosteelhb.com/pay/callback_gyf2";
	
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://b.baosteelhb.com/web/Result/";
	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArZCmA2NlRyJ37gnwioQNxdUnKI0iuEDynpssUk50fTJcDF/2Eg9OyRZhzU+2Rq+gPMGIkIKP2KGrrCZChq7E96Yx7ElXASZiaiwzFTNsMPe+Oxn4D0BIWIF3ChI2ohmy3bSdyQRDFt1KhoTYuTcNjpQPJvV8JgGt9Z2MEzdbFHeA8X6HfWak/SdLnsF1o56SVBalGRYEMayRqBkljIE9OTbLQYUuYnnMMp5i2YgdDhC6Ui/OgCKyvx4bv3St0EZL45tmazmbZdlm2YIV+O4ZhkcBaJgF4Rhb5YP6tG6fL1fsyc/b8x3YmrXRJo0Ytf5uYGDwgaXdv+uk0qpAZlbAtwIDAQAB";
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
	//支付宝用户号
	public static String seller_id="2088021987308161";
}
