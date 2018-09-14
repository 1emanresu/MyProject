package com.tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
/**
 * Created by IntelliJ IDEA.
 * User: zhengzhg
 * Mail: snake_country@sina.com
 * Date: 2004-10-13
 * Time: 15:30:28
 * To change this template use File | Settings | File Templates.
 * 常用工具包。包括生成各种密码随机串，加密解密，编码解码，执行url等
 */
public class CryptTool {
    /**
     * 生成密码.
     * @param count 密码位数
     * @param letters 是否包含字符
     * @param numbers 是否包含数字
     * @return String password
     */
    public static String getPassword(int count, boolean letters, boolean numbers) {
        return RandomStringUtils.random(count, letters, numbers);
    }
    /**
     * 生成字符数字混合的密码.
     * @param count 密码位数
     * @return String password
     */
    public static String getPassword(int count) {
        return getPassword(count, true, true);
    }
    /**
     * 生成纯数字密码.
     * @param count 密码位数
     * @return String password
     */
    public static String getPasswordOfNumber(int count) {
        return getPassword(count, false, true);
    }
    /**
     * 生成纯字符密码.
     * @param count 密码位数
     * @return String password
     */
    public static String getPasswordOfCharacter(int count) {
        return getPassword(count, true, false);
    }
    /**
     * 生成3DES密钥.
     * @param key_byte seed key
     * @throws Exception
     * @return javax.crypto.SecretKey Generated DES key
     */
    public static javax.crypto.SecretKey genDESKey(byte[] key_byte) throws Exception {
        SecretKey k = new SecretKeySpec(key_byte, "DESede");
        return k;
    }
    /**
     * 3DES 解密(byte[]).
     * @param key SecretKey
     * @param crypt byte[]
     * @throws Exception
     * @return byte[]
     */
    public static byte[] desDecrypt(javax.crypto.SecretKey key, byte[] crypt) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(crypt);
    }
    /**
     * 3DES 解密(String).
     * @param key SecretKey
     * @param crypt byte[]
     * @throws Exception
     * @return byte[]
     */
    public static String desDecrypt(javax.crypto.SecretKey key, String crypt) throws Exception {
        return new String(desDecrypt(key, crypt.getBytes()));
    }
    /**
     * 3DES加密(byte[]).
     * @param key SecretKey
     * @param src byte[]
     * @throws Exception
     * @return byte[]
     */
    public static byte[] desEncrypt(javax.crypto.SecretKey key, byte[] src) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(src);
    }
    /**
     * 3DES加密(String).
     * @param key SecretKey
     * @param src byte[]
     * @throws Exception
     * @return byte[]
     */
    public static String desEncrypt(javax.crypto.SecretKey key, String src) throws Exception {
        return new String(desEncrypt(key, src.getBytes()));
    }
    /**
     * MD5 摘要计算(byte[]).
     * @param src byte[]
     * @throws Exception
     * @return byte[] 16 bit digest
     */
    public static byte[] md5Digest(byte[] src) throws Exception {
        java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
        // MD5 is 16 bit message digest
        return alg.digest(src);
    }
    /**
     * MD5 摘要计算(String).
     * @param src String
     * @throws Exception
     * @return String
     */
    public static String md5Digest(String src) throws Exception {
        return new String(md5Digest(src.getBytes()));
    }
    /**
     * BASE64 编码.
     * @param src String inputed string
     * @return String returned string
     */
    public static String base64Encode(String src) {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return encoder.encode(src.getBytes());
    }
    /**
     * BASE64 编码(byte[]).
     * @param src byte[] inputed string
     * @return String returned string
     */
    public static String base64Encode(byte[] src) {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return encoder.encode(src);
    }
    /**
     * BASE64 解码.
     * @param src String inputed string
     * @return String returned string
     */
    public static String base64Decode(String src) {
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            return new String(decoder.decodeBuffer(src));
        } catch (Exception ex) {
            return null;
        }
    }
    /**
     * BASE64 解码(to byte[]).
     * @param src String inputed string
     * @return String returned string
     */
    public static byte[] base64DecodeToBytes(String src) {
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            return decoder.decodeBuffer(src);
        } catch (Exception ex) {
            return null;
        }
    }
    /**
     * 对给定字符进行 URL 编码GB2312.
     * @param src String
     * @return String
     */
    public static String urlEncode(String src) {
        return urlEncode(src, "GB2312");
    }
    /**
     * 对给定字符进行 URL 解码GB2312
     * @param value 解码前的字符串
     * @return 解码后的字符串
     */
    public static String urlDecode(String value) {
        return urlDecode(value, "GB2312");
    }
    /**
     * 对给定字符进行 URL 编码.
     * @param src String
     * @param coder 字符编码格式（GB2312/GBK）
     * @return String
     */
    public static String urlEncode(String src, String coder) {
        try {
            src = java.net.URLEncoder.encode(src, coder);
            return src;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return src;
    }
    /**
     * 对给定字符进行 URL 解码
     * @param value 解码前的字符串
     * @param coder 字符编码格式（GB2312/GBK）
     * @return 解码后的字符串
     */
    public static String urlDecode(String value, String coder) {
        try {
            return java.net.URLDecoder.decode(value, coder);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }
    /**
     * 执行给定url
     * @param urlString 给定的url
     * @return 返回值
     */
    public static String executeURL(String urlString) throws Exception {
        StringBuffer document = new StringBuffer();
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null)
            document.append(line + "/n");
        reader.close();
        
        return document.toString();
    }
    
    /**
     * 获取截取手机号
     * @param phone
     * @return
     */
    public static String getphone(String phone){
    	String phone1 = phone.substring(0,3);
    	String phone2 = phone.substring(phone.length()-4,phone.length());
    	String phone3 = phone1+"*****"+phone2;
    	return phone3;
    }
    
    public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}
    
    public static String getLocalIP(){     
    	InetAddress addr = null;     
    	try {  
    	     addr = InetAddress.getLocalHost();  
    	    } catch (UnknownHostException e) {  
    	            // TODO Auto-generated catch block  
    	          e.printStackTrace();  
    	     }     
    	
       byte[] ipAddr = addr.getAddress();     
       String ipAddrStr = "";     
            for (int i = 0; i < ipAddr.length; i++) {     
                  if (i > 0) {     
                      ipAddrStr += ".";     
                   }     
                  ipAddrStr += ipAddr[i] & 0xFF;     
                }     
           //System.out.println(ipAddrStr);     
        return ipAddrStr;     
   }    

}