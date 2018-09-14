package com.apppay111.config;

public class YHHConfig {
	// 商户appid
	public static String APPID = "2017050507122321";
	// 私钥 pkcs8格式的	
	public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCdG0i/CM+uVCOo8mMlxv4NLy2MUeNmp4noSqvZpkIjEA7SDTCcjm6M2W7DGcnnn2b1CVYrDlgSEnZidKU5UVniUBziuTcsPSdn4RokQsN9ekScx90YUeuAOdSET0fzemcWsiviNBj1+ixP3Pirgd1paou9nPJ/GQMCR5YHxCe/5gqAk3bRvKC5OP0vabX5Cs1wbE8pryvNfy1xJkfyJcYNkNnVS4Ly12ENKZwgcGmBI/ir11rTQj0Gs+2yISjDV4Q5Qk85YuH2PPb0+S5/pCqf4qeVhMcMN1q8wDIf1JtNGdhsejqARRz5Es1woqmcbU8GF/BH2rNQ8kmRbWp1PxsBAgMBAAECggEAPT8evAKmXBLHGBzuVBLghalHu3VNDn7v/1Pk7OOo0tx2uqKTueS46kX1rS+Hi7mTvKEYK47a/Kif6EALDW5OeTC8IhMMlD1++DtEIrC4X1lkGC6+XbXJ9mIVHEOADEZCnBF3lCa1OGeAEN5XPx1QgMEdmxztWGkayC+6KGrrCB78pnltmuBujjt0+jLu1l+AvqdEAn/l3Oz1YT7BH301jZA3OOQYmKF/gbqqlThFs3REY7y9Pqs+tTD50W40n4G1CDZ0LllGfPY3+bIk+TzjYl75cB7dPwMKdmlyrllbml9s8mGPM0q+iK0tfjYY79HIjIugHanRcOjLaEjbixY88QKBgQDRHyQmWyQ7f9TRzn8extgKmU+FJ1lqglzFQygl0PF0zqSl+/NAIlOVrSdJkeHDBrx3zI1wqvEV1c1h3sf5oCmVn/BMxsb4+b3fdaF97DamV2zTt36h5zgkx6+qME5qgYz+E80/K4ifi375q9wglXB7xHxpaN8e3QLp2XBHv4U4lQKBgQDAUyfUw4c3hDhhTzjWDFl3jwnsxU3n4dOOi0b2+KZ2336vvqiOW5S3fj9hibBg15ccHIeJoZBLjJD8U1z4nJaGZvH0Sp1iedlfTSQuMTvKnE2/6qVooVTtQgdfL73hzOmicmwEOu95J8LhF1/KAfC6OR5uVtopiuiRMkzBhEzBvQKBgQCaoKcvQVcyM5xeooy2ahtXAvOcpnLkYY5NQhIOnEfdsepm3TyJYJhjZUxWhurjNTjocDTpaUyoc5XQ11UQjFATVdbuPlDjym6OK8GEhcQHDT/n8JdPCdQmxgxrEBr7f92MBYJPQlZcel4iKOS0pyssKSZBHw1UGeHMnj36n2/XXQKBgQCbfh/D+1nFXtcehaiUFmP3L+R8U4ELKx+aLazwXFRp44RPnrw0bIKuYBnqSCIcCfIYPlCuy7sX+cv21YdjUWa5v117fyAD0DQYwwuvdQq8YubRdWrKTzWUpT/Ei5rfhoaWWiBOwz9+GtGKnxVJA4yXYzrhRsMXt0EUszAvfUF5mQKBgDqWpF2VFJW4uk4zy/+M2+No3EmXIYQxlfXKVIIPs37rp8U9KbLnNMJOHv0saPpJcR4jfySaJTjgigVPKAkp6ASTDqco1+htQRvwuaHHRqyOYdDECJ+khcY6gh/NGHXhL0MDf9M2uJhJcpxQZr13Th5hfRt+epflCENbGx+vN8VZ";
	// 服务器异步通知页面路径 需http://或者httpsMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkXtSxgD3dDDdCHEe/uKMLaFhehjWTDYlR35eQUnx0ghBFhZfIwUmj/uKEywgSRTJIihfOHqL+BS7ZVEC7JfsjqQQajjhKMOGqLIFY/fKjaBW66c7SLV3BJ/cRpyyIgXy/hlPtWy1AlyAuYi6Z8wSahMlxovG5n1ja6eZDnm0zxUG0qNEY0xGyllrUliVOljWu5fPNcV1w4Q/ic21w/nFYkp7iSEPAmClswzVNDlnssw48cuBy3dsmXicWUPhoH9RZPj3Iw8rRb+UoYfN3KomSZRibH/Aj29HA0kicoW2zQjvB/T2TdkVJe4kbe/PEevFZMee+fiCHvEXam1E4EQy1QIDAQAB://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	
	public static String notify_url = "http://vip2.swiftpasss.cn/pay/callback_yhh";
	
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://vip2.swiftpasss.cn/web/Result/";
	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhKY0Ien/R8n6GpRzjShAlH4lxD1s8CD/fIUubG25DF73eo/RVA5i0AMAXDczcVERRiz0Hb0pa9cl1SPOlRMnDSmtkDcBzIVpCICcDrse7uaHp3gbd/GFqo07iB3PN36dQQ4Omdm/4OnPdA2aJhpENanLMjK+KW6mKstpceYzZgFZHZOtcxhnm64fljbIf8moV20JHxwnTi8jIW11/FZGGq5hanAgKcaWDWualR1Rd5DGX+rM8f2zCxcSye+vg8plEfqxGL0LMh+NGSSPjzhZJOA5kbanHryFIYrlXGKK1dRdOvgZMjUA5DePMi67CoHWMgxbo++kGcMzbHFfL42B2wIDAQAB";
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
	//支付宝用户号
	public static String seller_id="2088621969942653";
}
