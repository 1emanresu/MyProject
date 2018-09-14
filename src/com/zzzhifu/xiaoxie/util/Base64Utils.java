package com.zzzhifu.xiaoxie.util;
import org.apache.commons.codec.binary.Base64;


 
public class Base64Utils {
	 /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;
     
    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     * 
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64){
        return new Base64().decode(base64.getBytes());
    }
     
    /**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     * 
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes){
        return new String(new Base64().encode(bytes));
    }
}
