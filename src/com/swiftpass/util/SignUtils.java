/**
 * Project Name:payment
 * File Name:SignUtils.java
 * Package Name:cn.swiftpass.utils.payment.sign
 * Date:2014-6-27下午3:22:33
 *
*/

package com.swiftpass.util;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;



/**
 * ClassName:SignUtils
 * Function: 签名用的工具箱
 * Date:     2014-6-27 下午3:22:33 
 * @author    
 */
public class SignUtils {

    /** <一句话功能简述>
     * <功能详细描述>验证返回参数
     * @param params
     * @param key
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean checkParam(Map<String,String> params,String key){
        boolean result = false;
        if(params.containsKey("sign")){
            String sign = params.get("sign");
            params.remove("sign");
            StringBuilder buf = new StringBuilder((params.size() +1) * 10);
            SignUtils.buildPayParams(buf,params,false);
            String preStr = buf.toString();
            String signRecieve = MD5.sign(preStr, "&key=" + key, "utf-8");
            result = sign.equalsIgnoreCase(signRecieve);
        }
        return result;
    }
    
    /**
     * 过滤参数
     * @author  
     * @param sArray
     * @return
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>(sArray.size());
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    
    /** <一句话功能简述>
     * <功能详细描述>将map转成String
     * @param payParams
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String payParamsToString(Map<String, String> payParams){
        return payParamsToString(payParams,false);
    }
    
    public static String payParamsToString(Map<String, String> payParams,boolean encoding){
        return payParamsToString(new StringBuilder(),payParams,encoding);
    }
    /**
     * @author 
     * @param payParams
     * @return
     */
    public static String payParamsToString(StringBuilder sb,Map<String, String> payParams,boolean encoding){
        buildPayParams(sb,payParams,encoding);
        return sb.toString();
    }
    
    /**
     * @author 
     * @param payParams
     * @return
     */
    public static void buildPayParams(StringBuilder sb,Map<String, String> payParams,boolean encoding){
        List<String> keys = new ArrayList<String>(payParams.keySet());
        Collections.sort(keys);
        for(String key : keys){
            sb.append(key).append("=");
            if(encoding){
                sb.append(urlEncode(payParams.get(key)));
            }else{
                sb.append(payParams.get(key));
            }
            sb.append("&");
        }
        sb.setLength(sb.length() - 1);
    }
    
    public static String urlEncode(String str){
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Throwable e) {
            return str;
        } 
    }
    
    
    public static Element readerXml(String body,String encode) throws DocumentException {
        SAXReader reader = new SAXReader(false);
        InputSource source = new InputSource(new StringReader(body));
        source.setEncoding(encode);
        Document doc = reader.read(source);
        Element element = doc.getRootElement();
        return element;
    }
    
    
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
    	System.out.println("数字签名组合串=："+text);
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    
    public static boolean verify(String text, String sign, String key, String input_charset) {
    	text = text + key;
    	System.out.println("数字签名组合串=："+text);
    	/**
    	 * 对签名值进行大写转换
    	 */
    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset)).toUpperCase();
    	System.out.println("计算的数字签名是=："+mysign);
    	if(mysign.equals(sign)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public static boolean checkSign(Map<String, String> map,String key){
		try {
			if (!map.isEmpty()) {				
				String sign = map.get("sign");
				System.out.println("返回的签名是=："+sign);
				map.remove("sign");// 移除sign
				String preStr  = formatUrlMap(map);	
				System.out.println("组合字符串是=："+preStr);
				return verify(preStr, sign, "&key="+key, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}
    
    /**
	 * 
	 * @Description: 
	 * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
	 * @author mm
	 * @date 2017年3月23日
	 * @param paraMap 要排序的Map对象
	 * @param urlEncode 是否需要URLENCODE
	 * @param keyToLower 是否需要将Key转换为全小写
	 * 		  true:key转化成小写，false:不转化
	 * @return
	 */
	public static String formatUrlMap(Map<String, String> paraMap) {
		boolean urlEncode = false ;
		boolean keyToLower = false ;
		String buff = "";
		Map<String, String> tmpMap = paraMap;
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
					tmpMap.entrySet());
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds,new Comparator<Map.Entry<String, String>>() {
						@Override
						public int compare(Map.Entry<String, String> o1,
								Map.Entry<String, String> o2) {
							return (o1.getKey()).toString().compareTo(o2.getKey());
						}
					});
			// 构造URL 键值对的格式
			StringBuilder buf = new StringBuilder();
			for (Map.Entry<String, String> item : infoIds) {
				if (isNotEmpty(item.getKey())) {
					String key = item.getKey();
					String val = item.getValue();
					if(isNotEmpty(val)){
						if (urlEncode) {
							val = URLEncoder.encode(val, "utf-8");
						}
						if (keyToLower) {
							buf.append(key.toLowerCase() + "=" + val);
						} else {
							buf.append(key + "=" + val);
						}
						buf.append("&");
					}
				}
			}
			buff = buf.toString();
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			return null;
		}
		return buff;
	}
	
	public static boolean isNotEmpty(String sTr){
		if(null != sTr && "".equals(sTr) ==false && sTr.length() >0){
			return true ;
		}
		return false ;
	}
	
	public static Map<String, String> toMap(Element element){
        Map<String, String> rest = new HashMap<String, String>();
        List<Element> els = element.elements();
        for(Element el : els){
            rest.put(el.getName(), el.getTextTrim());
        }
        return rest;
    }
}

