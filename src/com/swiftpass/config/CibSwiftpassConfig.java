package com.swiftpass.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CibSwiftpassConfig {
	 /**
     * 兴业银行交易密钥
     */
    public static String key ;
    
    /**
     * 兴业银行商户号
     */
    public static String mch_id;
    
    /**
     * 兴业银行请求url
     */
    public static String req_url;
    
    /**
     * 通知url
     */
    public static String notify_url;
    
    static{
        Properties prop = new Properties();   
        InputStream in = SwiftpassConfig.class.getResourceAsStream("/cibconfig.properties");   
        try {   
            prop.load(in);   
            key = prop.getProperty("key").trim();   
            mch_id = prop.getProperty("mch_id").trim();   
            req_url = prop.getProperty("req_url").trim();   
            notify_url = prop.getProperty("notify_url").trim();   
        } catch (IOException e) {   
            e.printStackTrace();   
        } 
    }
}

