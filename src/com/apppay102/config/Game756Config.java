package com.apppay102.config;

public class Game756Config {
	// 商户appid
	public static String APPID = "2017042406942845";
	// 私钥 pkcs8格式的	
	public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCmkPBtIg6PEqkSokTpdieLr2PJYLNO1Nph3ACqriXLwdT1uzL/+WrZnWMhU6ELtoHPKaPLRD+17giCgFZ7NUXcyN8r7XLyfAd0/SvvV3HpgKNz0xvXiB3y6TbJGhjQrUQ49PznmtcoiLYB3D9K0zTx1Z9SJbEtLxDwvDEnv8GtOZ60Vw/8QtlFOrmVWCI01f1spwDw+FBAuRR3YFWMdljiJ/DJuiVjy8/B9UT6wRh5YuuqjmdImNFt9Dz28m+ZJqtX4BjTWc0iNvXk2RUtyWR4H1WCFUAU/Kk0g8exnowDZ88dh2CU+r/mOqm6YTt4arlfEarkhzymsF+dUTOwMXmlAgMBAAECggEAXNSYemoprQlcWzs+ptKn9Yy5U2lmZX/xeeqwIG/QNr//XTiIkurB5yh1MAS405tSu09crxOJYm6YJYs7J42oMwVsg8Hhd00yprvWuZyFqvp7njjA4p1E+7WISAoYXGzEge3f6YB9e2S980l5qM0PS9yf35uMJD9/9Hsd9iZD3bM94qTV8k+9OAeBNqudC2aZtCoX14hbkyXh0MV2Q8sDMkpPOXTz+O00MJazi66++lDbd/xqeSvQuuZmBcMO+WnBuLlYXLpRRJV5pp4g8rF2CetPYdOnsnhFZKYTqT8DAOADqhZYz06Cg7uaf9l755he290To6k5YIhr8Dw7UKLaHQKBgQD59XUedvmsDsFMLFZwsDDNXdM/EcpGYwGAfo60LsJjW+PicIxRo8oIm4onSBQkh7VqoBCWq/9RP4IhPzzSg4yfW7uMCt33Pflch+oiKvhWrhu/uBdbOV2vzAbx1HhvTomYK27nLdB9yjW/dhrVFnoH+2Zr+Pqjltym1E6CgxcY7wKBgQCql4P8Koei3aNzdvLSkm8rXuCRzOY0HoLbeqnst4btTtQ0VpWgPEfNklllhb8bu66u3cLwwBrLZyWV7j6+msll4nuULVDkW86lhdbAJg++SqHAt7LDDZg7jwa57l89avdRFMaKeqXG/UxiGgaFxl5oM2YraMAHZ5RxMt8o/oVOqwKBgQCQCzKmDrwMuigMhJR/thk5j/M3qkHd1Bo+eTA1h6i/52loyaEjPvcjhLbVskThVE9olbtZiN7GbInKLQ4Y5nQ4NX3b0ztcypsUBsMXU5nvIcCd+DGFgYaK01HBNMb+pczHI2hy0sUOaGQW5LHL0HscdHTujfmqIjsf7MMdlPvPgwKBgGJSk+rqZGTVlBi/zTLa3aJEu/CtBV2y3D8+EuRx3x212o8T/IdvVv9AQ7BFByIn6YW2IV57irYDjGE/AQEA2+WSRSHhgIct9wvl+SGkjlxiTZpzBmBv3Hcvd3QA1PnkRgmAjPJRwGzRn43HbEz8SXuLbG7GoC9kXuVjqcXJ9PGjAoGBAK7c+6NTPPA8bm9ukrl/BPjmFOeGpWlTmGpexuBi3o/0+TAggKEcCBLr3Rna7PxIYl9i76RZ+UpHemKt2ce/beX+hjNMA4/+HS+7nB1LYpnTlVjCH0pk7+BCOMqSQV1NWh//YQMLkB+eV4ZoizzDPQQM8S2yD1Dhc7q8euGhscCy";
	// 服务器异步通知页面路径 需http://或者httpsMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkXtSxgD3dDDdCHEe/uKMLaFhehjWTDYlR35eQUnx0ghBFhZfIwUmj/uKEywgSRTJIihfOHqL+BS7ZVEC7JfsjqQQajjhKMOGqLIFY/fKjaBW66c7SLV3BJ/cRpyyIgXy/hlPtWy1AlyAuYi6Z8wSahMlxovG5n1ja6eZDnm0zxUG0qNEY0xGyllrUliVOljWu5fPNcV1w4Q/ic21w/nFYkp7iSEPAmClswzVNDlnssw48cuBy3dsmXicWUPhoH9RZPj3Iw8rRb+UoYfN3KomSZRibH/Aj29HA0kicoW2zQjvB/T2TdkVJe4kbe/PEevFZMee+fiCHvEXam1E4EQy1QIDAQAB://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://game1.68qp.net/pay/callback_game756";
	
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://game1.68qp.net/web/Result/";
	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw0I2NQsZphiualW0P2YN4cFPIq1J+jYQeqc22wscJvWOAm7vRPXp5I8wbhqzat7RYlSYaOt1LwAQRYmVpnO2YzDEeVNoIOUKY4KTEKSPau3hA5Zs4qs36RFvTxeEVviL2IvTWSfxRtBc3BitooDR/Un5c96CwU3O+kYAtxLcLiDlI69ZJRJ3022BSnGU3FgPQoDh5upKSQc0IoupC+OdH1wCTaExxWBjxqP4FAqpi6GuigoTfl2BAWQoh17WrJC1xDYuaeMR6W0fl4CjCcMn+CqV0rCY6L2v72gMnyNuZ2ajnZh5wtif6enV5Im6o+g5T6jqL5TvsXQuVDH13tDNNQIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
	//支付宝用户号
	public static String seller_id="2088621904402521";
}
