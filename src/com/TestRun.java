package com;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.zzzhifu.xiaoxie.util.Md5Util;
import com.zzzhifu.xiaoxie.util.SignUtil;

public class TestRun {
	
	private static final int timeOut = 10 * 1000;
    private static CloseableHttpClient httpClient = null;
    private final static Object syncLock = new Object();
    
    private static void config(HttpRequestBase httpRequestBase) {
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeOut)
                .setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
        httpRequestBase.setConfig(requestConfig);
    }
	public static void main(String[] args) {
		try {
            String privateKey_test="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMsTH3wuhcE4D5FA" +
                    "5FdzRyzb8nlUYyGtndIo3EFRlaX1rOnWFH10x8FuXKyaiG5fd11xccOz649dlZWa" +
                    "GkuL6e1pNlgTOE34ZeSVQcXTRcElSBzZLuaLaWMBY/FApNVDjU2dT35i7+ENLI33" +
                    "h/zoP2akmmYt9MftlnByUPZl/Z8hAgMBAAECgYAWFkZeY1D46vgGfGBZgphDMMj2" +
                    "mU8O4qs/qGIMWKtGGaps5HPmEUWIrFA4FbaxCoEZELWm63K7bRpP4yro/Ja8oe2B" +
                    "6B7cGWD6iKbd9XBlur6FkELYwGhRQLaPN+FTxhYbziNWwwMk6T/IH0Yg6APHkDKw" +
                    "Sr34zyh1GzRoUYWAAQJBAPZmoGdOrBSvMff1u3qE2lkI3XsmX++blo2CJcZg8R8n" +
                    "GetFVvixN42vxXYAHNsIdK9TKGCF9O2MkTNC9jZVaYECQQDS/Ga7sARuTkLKEXo+" +
                    "9De1ZXMg0eYrIxKFakXuUmnEFIkp4cDm30aAwWcjpAaebdaFJL7mfeysq8QbX3jv" +
                    "FsWhAkEAkAvVEC/s57a4BpawwGJ0Z4KIQFth0Gjr7dQ9EylPLQAl5eXGf2LS8FDm" +
                    "piJQvDfIR0rdGviVBF6d8BLgb0klgQJBALCjiA8SqmL/OoOaZlJWfF1t9hlAIgdz" +
                    "cYopEysX7FyxJLTfwHSJ8ajSQttf8/qn0rXdscWfH4rgs2qHX+QrmIECQCkOLUf5" +
                    "rwdjAd3oe/1oDt6Zbkd9hTaf2qheZNllXUO2U4ZE4l1paNQpi2iGYrkwNUDPd07y" +
                    "ga8uL2aXDODFu4E=";
            String publicKey_test="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLEx98LoXBOA+RQORXc0cs2/J5" +
                    "VGMhrZ3SKNxBUZWl9azp1hR9dMfBblysmohuX3ddcXHDs+uPXZWVmhpLi+ntaTZY" +
                    "EzhN+GXklUHF00XBJUgc2S7mi2ljAWPxQKTVQ41NnU9+Yu/hDSyN94f86D9mpJpm" +
                    "LfTH7ZZwclD2Zf2fIQIDAQAB";

            String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKqDtgUtVhGXbmgW" +
                    "pDZSy6ZV/RaASx0d40tscRgBQacZA4S4kP7S+J9Nyy92WnqKDrqW6AjeCAiBR1T5" +
                    "+X4hPNd6IC51L7r55xgsDN4rRuwaJyGT6CgfdZIuO03Wev2CGpc7KY4Z4Ryz7a02" +
                    "sJFKCAB1ElixHrQRCTQi9daOvETXAgMBAAECgYAR7dV2x+ST5vNR+Id9usj1DXeW" +
                    "JRlAz4K1zIo7tu4bTtMC4pFEahSAU6K4s94homCYGAP/4rC/jDBe7AXi0EXgSmCV" +
                    "uNoDLlpqze1HkDa9Oe0WRa56pbTtd9th/Tse/b1H2eWSDHnhPWJsF046P32kKXKm" +
                    "6F1MZs34+y1YgtjMIQJBANZBR5O3lnjA8nRLO5MeIYGwOurmw94+y4wi1gjltnou" +
                    "ATNrR6lYFGunZQkgA2qLuv/swB8klHvKl+eAUtaJx2cCQQDLvLhj1pORshXOCw/y" +
                    "A4OAyc/u20N7S4lFAOItdoJTz7LaG4YAWGBkX1co1bTtkcHCBFVnD63TfnEYfSRP" +
                    "uGERAkBX/OMKNODkokhvnd3PYxZbjiYEBdT2Vk99M2k2qi+wKWhw12PMldF9DHef" +
                    "sbf1b4DSTUXxBDK+S8rqVXaviFGNAkAV0sU9jIKKHLVROMYgelfft75aK4py7ohp" +
                    "p8qSbBtRtvHFgyU7bDwHBF9ltF6JBA/pJGWxgHByMx0SLnVxRKLBAkEAmn5gPPJd" +
                    "2Wd1bVYuA+6QZafY65l3d7Yczkn1jHWN6nCr6kw65rGwMbwaK5OsmeepBffag19G" +
                    "v8zRaysGnY2oDw==";
            String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqg7YFLVYRl25oFqQ2UsumVf0W" +
                    "gEsdHeNLbHEYAUGnGQOEuJD+0vifTcsvdlp6ig66lugI3ggIgUdU+fl+ITzXeiAu" +
                    "dS+6+ecYLAzeK0bsGichk+goH3WSLjtN1nr9ghqXOymOGeEcs+2tNrCRSggAdRJY" +
                    "sR60EQk0IvXWjrxE1wIDAQAB";
            String url="http://61.129.71.108:38081/service/pmpf/api/h5outPay";
            String url_prod="https://pay.sandgate.cn/service/pmpf/api/h5outPay";
            //代理商ID
            String agencyId="888002189990010";
            //接口请求报文
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("notify_url","www.baidu.com");
            map.put("outOrderId","345454656557678879809");
            map.put("subject","20170719买买买");
            map.put("totalAmount","0.01");
            map.put("paytype","01");
            map.put("merid","666666879930014");
            map.put("spbillCreateIp","192.168.12.58");

            Map<String,Object> m1=new HashMap<String,Object>();
            Map<String,Object> m2=new HashMap<String,Object>();
            m2.put("type","Wap");
            m2.put("wap_url","https://m.jd.com");
            m2.put("wap_name","京东");
            m1.put("h5_info",m2);
            map.put("sceneInfo",m1);
            
            String json= JSON.toJSONString(map);
            System.out.println(json);
            //签名前原文
            String text= Md5Util.MD5(agencyId+json);
            System.out.println("----------text："+text);
            String sign= SignUtil.sign(text,privateKey);
            System.out.println("----------sign："+sign);
            //签名后密文
            sign= URLEncoder.encode(sign);
            System.out.println("----------urlencode--sign："+sign);
            //请求url拼接
            url_prod=url_prod+"?agencyId="+agencyId+"&sign="+sign;
            //使用http请求工具类进行接口调用

            HttpPost httppost = new HttpPost(url_prod);
            config(httppost);
            InputStreamEntity reqEntity = new InputStreamEntity(new ByteArrayInputStream(json.getBytes()));
            reqEntity.setContentType("application/json;charset=UTF-8");
            httppost.setEntity(reqEntity);
            CloseableHttpResponse response = null;
            String result =null;
            try {
                response = HttpClients.custom().build().execute(httppost, HttpClientContext.create());
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "utf-8");
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (response != null){
                        response.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //将返回json转成map
            Map<String,Object> resMap=(Map<String,Object>)JSON.parseObject(result);
            //获取data字符串
            String resData=(String)resMap.get("data");
            //获取sign签名串
            String resSign=(String)resMap.get("sign");
            //还原签名原文
            String resText=Md5Util.MD5(agencyId+resData);
            //开始对返回的数据进行验签
            Boolean b=SignUtil.verify(resSign,resText,publicKey);
            if(b){
                System.out.println("验签通过");
            }else{
                System.out.println("验签不通过");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
	}
}