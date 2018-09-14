package com.apppay101.config;

public class GameConfig {
	// 商户appid
	public static String APPID = "2016051801414555";
	// 私钥 pkcs8格式的	
	public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCRe1LGAPd0MN0IcR7+4owtoWF6GNZMNiVHfl5BSfHSCEEWFl8jBSaP+4oTLCBJFMkiKF84eov4FLtlUQLsl+yOpBBqOOEow4aosgVj98qNoFbrpztItXcEn9xGnLIiBfL+GU+1bLUCXIC5iLpnzBJqEyXGi8bmfWNrp5kOebTPFQbSo0RjTEbKWWtSWJU6WNa7l881xXXDhD+JzbXD+cViSnuJIQ8CYKWzDNU0OWeyzDjxy4HLd2yZeJxZQ+Ggf1Fk+PcjDytFv5Shh83cqiZJlGJsf8CPb0cDSSJyhbbNCO8H9PZN2RUl7iRt788R68Vkx575+IIe8RdqbUTgRDLVAgMBAAECggEAFdLBFPC6LhTSY1zMwIFtc77cp3soUmBg8TuUmgiknNQeJ1kNxmwUX87k5IFfCndywIv71LYVXw6FGeqwHJuhobgcukJ0DDBgd9fUlS1rlhkyUMFSVFLcWrY8Td9dnVr1G4bifR2kfYkwyiQnikCDlqPuq7z2BkdS7ahDfndlVxbfb2ocf4PTEvVIwwV1TZFvjqb68IXTAjRe/WrITPT6AiNNQLP45gwPzhsseXwncy+2QUqHPzH9IAUK1ebrIZLLEDyXfCDHps6QyG35C99LGfne+vwSb7osqm5QQzQVwfIatDqQ7ReY8f8uUyAy5G931czURUy4EKLBp9vflNSgVQKBgQDJF3fJLWqmpPQp07h6cK2GS6weboYcIif6sQV24v9qUb3SGjwjOmFI1SiEbi/nMb3ounXKtwtV3Nn0o1VLJU3z7WoHkJJyLuU6DYmN67I3GodoPBKUEx0DNx03cVvVpdUrpYpExWptNAnCnXiJ9n4DaZPxrGwMR4V7Wn/P6T1TRwKBgQC5NKibK9+hlX0dp9CpM0S9NE0J+oAfvhKfXh0Db6Tx9sL/UC/mmUhbYMu+npiYCRXGfXdlojRNeQqYUosdJc80FqwU+RqwFxTnEH9EFe/CJ0qdhCuECZ/79pdks9DFeMNozvHdDge+oujGRNn2HG9W0cE6nntDrdbIccxmi+R/AwKBgQCmp8ZkejlLRqX8DDV1c/C7vfc1eU+3/S64+wCWWOZpDLmOaKOyZm292LqtlkwpBPZo7C83A6BnVpNjxgGoN5B1OiuHfW+BJhBfnlyDu/SE6n2SC0cyZOzEf2a9TjtXwwn6FEWVXS8otdno6my9mgUxesxbnBQn0RZ09rm6a4ihfQKBgCmjSxzHullLkHtwVRsVKakX7xIFPXTleK5XrFIvcXfssF5SGtLPN1ND6cqLQWc886ZSYtt4oiWgxt8D/76n13OwfTOh5XwdVFe2f8ZA4ExYJRBQvK/MsiklgVfkjVU4F0ai8AFwvBEB8y9QH2iTb/ym37hcFP5cQOODoh6T6OUdAoGAWjCmV39TrjP/mwCzk+bxzMC5SKM2rmziJI69UhHNTC03zljXL1IP31VCXfiiPL5wQfyPk4tMlv4CvGp7pp+o338+Jt42eOB4EWA52sObIWxW3+EMS1122rvKoy8VYy6MvhNplNuso0hOT6nF65lN+LPTEnO2Cm6TrqcEiiliIPA=";
	// 服务器异步通知页面路径 需http://或者httpsMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkXtSxgD3dDDdCHEe/uKMLaFhehjWTDYlR35eQUnx0ghBFhZfIwUmj/uKEywgSRTJIihfOHqL+BS7ZVEC7JfsjqQQajjhKMOGqLIFY/fKjaBW66c7SLV3BJ/cRpyyIgXy/hlPtWy1AlyAuYi6Z8wSahMlxovG5n1ja6eZDnm0zxUG0qNEY0xGyllrUliVOljWu5fPNcV1w4Q/ic21w/nFYkp7iSEPAmClswzVNDlnssw48cuBy3dsmXicWUPhoH9RZPj3Iw8rRb+UoYfN3KomSZRibH/Aj29HA0kicoW2zQjvB/T2TdkVJe4kbe/PEevFZMee+fiCHvEXam1E4EQy1QIDAQAB://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	
	public static String notify_url = "http://netpay.game731.cn/pay/callback_game731";
	
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://netpay.game731.cn/web/Result/";
	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu+5Y3q+DXHg1hBM26l9Tn9NW8oLceDQXTt3GSgMqZCGpCzYCdtzQFHy1jd7hjFkVOZVTcavM3FBh/+/Gan+p+nihhjfxaJ4sUPxbxKeQC4uCM7xmMmgYRZm6v5AD++5gXxm03pOlx5s9rMEGsyOrFBhwYjXkbum8+kCZ6lcrxViKOg+16h5JTEpoRSbQJSNLOOy8BUgnMXgZ1P/q600rUT5oieXXImWJH28iHMbbtph1vdE6vcGIin3Pq1965xpT2jjaorp94nYK3jclaFIH1w+gSdcG2rWP0R37g2c9dbhQW40g7nCi5gFu41HDrgW8QB+wddAredlEz8UO7rWHNQIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
	//支付宝用户号
	public static String seller_id="2088411762675874";
}
