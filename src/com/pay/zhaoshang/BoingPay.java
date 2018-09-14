package com.pay.zhaoshang;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pay.zhaoshang.util.DSAUtil;
import com.pay.zhaoshang.util.RSAUtil;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.*;


public class BoingPay {

    private static final String METHOD_POST = "POST";
    private static final String ENCODING = "utf-8";
    private static final int BUFFER_SIZE = 1024;
    private static String serverUrl = "https://soa.boingpay.com/service/soa";  // 服务提交地址
    private static String appId;
    private static String version = "2.0";
    private static PrivateKey DSAPrivateKey;
    private static PublicKey DSAPublicKey;
    private static PrivateKey RSAPrivateKey;
    private static PublicKey RSAPublicKey;
    private static String token;
    private static String md5Key;

    private static BoingPay boingPay;

    protected BoingPay() {

    }

    public static BoingPay getCashier() {
        if (boingPay == null) {
            synchronized (BoingPay.class) {
                if (boingPay == null) {
                    boingPay = new BoingPay();
                }
            }
        }
        return boingPay;
    }

    public static BoingPay getService() {
        if (boingPay == null) {
            synchronized (BoingPay.class) {
                if (boingPay == null) {
                    boingPay = new BoingPay();
                }
            }
        }
        return boingPay;

    }

    public static void setServerUrl(String url) {
        if (url.lastIndexOf("service/soa") == -1 && url.endsWith("/"))
            url += "service/soa";
        else if (url.lastIndexOf("service/soa") == -1 && !url.endsWith("/"))
            url += "/service/soa";

        BoingPay.serverUrl = url;
    }

    public static void setApiVersion(String apiVersion) {
        BoingPay.version = apiVersion;
    }

    public static void setAppId(String appId) {
        BoingPay.appId = appId;
    }

    public static void setDSAKey(String DSAPublicKeyPath, String DSAPrivateKeyPath, String DSAPrivateKeyPass) throws Exception {
        BoingPay.DSAPrivateKey = DSAUtil.loadPrivateKey(DSAPrivateKeyPath, DSAPrivateKeyPass);
        BoingPay.DSAPublicKey = DSAUtil.loadPublicKey(DSAPublicKeyPath);
    }

    public static void setRSAKey(String RSAPublicKeyPath, String RSAPrivateKeyPath, String RSAPrivateKeyPass) throws Exception {
        BoingPay.RSAPrivateKey = RSAUtil.loadPrivateKey(RSAPrivateKeyPath, RSAPrivateKeyPass);
        BoingPay.RSAPublicKey = RSAUtil.loadPublicKey(RSAPublicKeyPath);
    }

    public static void setToken(String token) {
        BoingPay.token = token;
    }

    public static void setMd5Key(String md5Key) {
        BoingPay.md5Key = md5Key;
    }

    /**
     * 支付请求
     *
     * @param order
     * @return
     * @throws Exception
     */
    public String requestPay(Map<String, Object> order) throws Exception {
        try {
            JSONObject value = request("cod.pay.order.request_pay", new JSONObject(order));
            // RSA 解密
            JSONObject response = value.getJSONObject("response");
            String status = response.getString("status");
            if (status.equals("OK")) {
                // 解密
                JSONObject result = response.getJSONObject("result");
                JSONObject pay_token = result.getJSONObject("pay_token");

                String encrypt_key = pay_token.getString("encrypt_key");
                encrypt_key = RSAUtil.dencrypt((java.security.interfaces.RSAPrivateKey) BoingPay.RSAPrivateKey, encrypt_key);
                pay_token.put("encrypt_key", encrypt_key);

                String sign_key = pay_token.getString("sign_key");
                sign_key = RSAUtil.dencrypt((RSAPrivateKey) BoingPay.RSAPrivateKey, sign_key);
                pay_token.put("sign_key", sign_key);

                pay_token.put("app_id", BoingPay.appId);
                return pay_token.toString();
            } else {
                String error_code = response.getString("error_code");
                String message = response.getString("message");
                throw new Exception(error_code + ":" + message);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public JSONObject createPayQRCode(Map<String, Object> order) throws Exception {
        JSONObject value = request("cod.pay.order.qr_code.create", new JSONObject(order));
        return getResult(value);
    }

    /**
     * 条码支付
     *
     * @param order
     * @return
     * @throws Exception
     */
    public JSONObject barcodeScanPay(Map<String, Object> order) throws Exception {
        JSONObject value = request("cod.pay.user_payment_code.scan", new JSONObject(order));
        return getResult(value);
    }

    /**
     * 查询订单
     *
     * @param orderNo
     * @return
     * @throws Exception
     */
    public JSONObject getPayStatus(String appId, String orderNo) throws Exception {
        JSONObject params = new JSONObject();
        params.put("mch_app_id", appId);
        params.put("order_no", orderNo);
        JSONObject value = request("cod.pay.order.pay_status", params);
        return getResult(value);
    }

    /**
     * 退款
     *
     * @param order
     * @return
     * @throws Exception
     */
    public JSONObject refund(Map<String, Object> order) throws Exception {
        JSONObject value = request("cod.pay.order_refund", new JSONObject(order));
        return getResult(value);
    }

    /**
     * 查询退款
     *
     * @param appId
     * @param orderNo
     * @param refundOrderNo
     * @return
     * @throws Exception
     */
    public JSONObject getRefundStatus(String appId, String orderNo, String refundOrderNo) throws Exception {
        JSONObject params = new JSONObject();
        params.put("mch_app_id", appId);
        params.put("order_no", orderNo);
        params.put("refund_order_no", refundOrderNo);
        JSONObject value = request("cod.pay.order.refund_status", params);
        return getResult(value);
    }

    /**
     * 关闭订单
     *
     * @param appId
     * @param orderNo
     * @return
     * @throws Exception
     */
    public JSONObject close(String appId, String orderNo) throws Exception {
        JSONObject params = new JSONObject();
        params.put("mch_app_id", appId);
        params.put("order_no", orderNo);
        JSONObject value = request("cod.pay.order.close", params);
        return getResult(value);
    }


    /**
     * 撤销订单
     *
     * @param appId
     * @param orderNo
     * @return
     * @throws Exception
     */
    public JSONObject reverse(String appId, String orderNo) throws Exception {
        JSONObject params = new JSONObject();
        params.put("mch_app_id", appId);
        params.put("order_no", orderNo);
        JSONObject value = request("cod.pay.order.reverse", params);
        return getResult(value);
    }

    public JSONObject getNotifyResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject params = new JSONObject();
        Map requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        String signStr = params.getString("app_id") + params.getString("timestamp") + params.getString("version") + params.getString("event");
        String sign = params.getString("sign");
        if (sign != null && !"".equals(sign)) {
            boolean isVerify = DSAUtil.verify(BoingPay.DSAPublicKey, signStr, sign);
            if (!isVerify) {
                throw new Exception("签名验证失败");
            }
        }
        if (params.containsKey("event")) {
            JSONObject event = JSONObject.parseObject(params.getString("event"));
            params.put("event", event);
        }
        return params;


    }

    /**
     * 其他服务调用
     *
     * @param service
     * @param order
     * @return
     * @throws Exception
     */
    public JSONObject call(String service, Map<String, Object> order) throws Exception {
        JSONObject value = request(service, new JSONObject(order));
        return getResult(value);
    }

    /**
     * 向IME服务器发送请求
     *
     * @param service 服务对象名称
     * @param param   服务参数
     * @return 返回结果，status存放调用结果(OK:成功; error:失败), returnValue存放返回结果
     * @throws Exception
     */
    public JSONObject request(String service, JSONObject param) throws Exception {
        String strParam = param.toString();
        strParam = strParam.replace("\r\n", "");

        Map<String, Object> req = new HashMap<String, Object>();
        req.put("params", strParam);
        req.put("app_id", BoingPay.appId);
        req.put("version", BoingPay.version);
        req.put("service", service);
        if (BoingPay.token != null && !"".equals(BoingPay.token)) {
            req.put("token", BoingPay.token);
        }
        String timestamp = String.valueOf(new Date().getTime() / 1000);
        String sign = this.sign(BoingPay.appId, service, timestamp, strParam);
        req.put("timestamp", timestamp);
        req.put("sign", sign);

        String result = request(req);
        if (result == null)
            throw new Exception("返回数据错误");
        return verifyReturn(JSON.parseObject(result));
    }

    private String request(Map<String, Object> args) throws Exception {
    	//System.out.println(args.toString());
        String result = "";
        HttpPost post = new HttpPost(BoingPay.serverUrl);
        post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : args.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        HttpEntity entity = new UrlEncodedFormEntity(nvps, ENCODING);
        post.setEntity(entity);
        RequestConfig.Builder customReqConf = RequestConfig.custom();
        customReqConf.setConnectTimeout(10000);
        customReqConf.setSocketTimeout(10000);
        post.setConfig(customReqConf.build());
        HttpClient httpClient = createSSLInsecureClient();
        HttpResponse httpResponse = httpClient.execute(post);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            result = IOUtils.toString(httpResponse.getEntity().getContent(), ENCODING);
            return result;
        } else {
            throw new Exception("Unexpected server response: " + statusCode);
        }
    }

    private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext);
        return HttpClients.custom().setSSLSocketFactory(sslSF).build();
    }

    private String sign(String app_id, String service, String timestamp, String req) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(app_id).append(timestamp).append(BoingPay.version).append(service).append(req);
        if (BoingPay.token != null && !"".equals(BoingPay.token)) {
            String text = sb.toString() + BoingPay.md5Key;
            MessageDigest md = MessageDigest.getInstance("MD5");
            return DSAUtil.byte2hex(md.digest(text.getBytes(ENCODING)));
        } else {
            if (BoingPay.DSAPrivateKey == null)
                throw new Exception("找不到私钥");
            return DSAUtil.sign(BoingPay.DSAPrivateKey, sb.toString());
        }
    }

    private JSONObject verifyReturn(JSONObject result) throws Exception {
        if (result.containsKey("response")) {
            String response = result.getString("response");
            String sign = result.getString("sign");
            if (sign != null && !"".equals(sign)) {
                if (BoingPay.token != null && !"".equals(BoingPay.token)) {
                    response = response + BoingPay.md5Key;
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    String sginString = DSAUtil.byte2hex(md.digest(response.getBytes(ENCODING)));
                    if (!sign.equalsIgnoreCase(sginString)) {
                        throw new Exception("签名验证错误");
                    }
                } else {
                    if (!DSAUtil.verify(BoingPay.DSAPublicKey, response, sign))
                        throw new Exception("签名验证错误");
                }
                String value = result.getString("response");
                JSONObject ret = JSON.parseObject(value);
                result.put("response", ret);
            }
        }
        return result;
    }


    //字节码转换成16进制字符串
    private String byte2hex(byte bytes[]) {
        StringBuffer retString = new StringBuffer();
        for (int i = 0; i < bytes.length; ++i) {
            retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1).toUpperCase());
        }
        return retString.toString();
    }

    //将16进制字符串转换成字节码
    private byte[] hex2byte(String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bts;
    }

    private JSONObject getResult(JSONObject value) throws Exception {
    	System.out.println(value.toString());
        JSONObject response = value.getJSONObject("response");
        String status = response.getString("status");
        if (status.equals("OK")) {
            JSONObject result = response.getJSONObject("result");
            return result;
        } else {
            String error_code = response.getString("error_code");
            String message = response.getString("message");
            throw new Exception(error_code + ":" + message);
        }
    }

    public PrivateKey getDSAPrivateKey() {
        return DSAPrivateKey;
    }

    public PublicKey getDSAPublicKey() {
        return DSAPublicKey;
    }

    public PrivateKey getRSAPrivateKey() {
        return RSAPrivateKey;
    }

    public PublicKey getRSAPublicKey() {
        return RSAPublicKey;
    }

    public String getAppId(){
        return appId;
    }
}