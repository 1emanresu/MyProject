package com.pay.zhaoshang.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

public class DSAUtil {
    private DSAPrivateKey privateKey;
    private DSAPublicKey publicKey;

    public DSAUtil(DSAPublicKey paramDSAPublicKey,
                   DSAPrivateKey paramDSAPrivateKey) {
        this.publicKey = paramDSAPublicKey;
        this.privateKey = paramDSAPrivateKey;
    }

    public void setPrivateKey(DSAPrivateKey paramDSAPrivateKey) {
        this.privateKey = paramDSAPrivateKey;
    }


    /**
     * 用私钥对信息进行数字签名
     * 加密数据
     *
     * @param text 私钥-base64加密的
     * @return
     * @throws Exception
     */
    public String sign(String text) throws Exception {
        // 用私钥对信息进行数字签名
        Signature signature = Signature.getInstance("DSA");
        signature.initSign(privateKey);
        signature.update(text.getBytes("utf8"));
        return Base64.encode(signature.sign());
    }

    /**
     * 校验数字签名
     * <p>
     * 加密数据
     *
     * @param verifyStr
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public boolean verify(String verifyStr, String sign) throws Exception {
        Signature signature = Signature.getInstance("DSA");
        signature.initVerify(publicKey);
        signature.update(verifyStr.getBytes("utf8"));
        return signature.verify(Base64.decode(sign));
    }

    public static DSAPrivateKey getPrivateKey(String keyString)
            throws Exception {
        byte[] keyBytes = hex2byte(keyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance("DSA");
        PrivateKey priKey = factory.generatePrivate(keySpec);
        return (DSAPrivateKey) priKey;
    }

    public static DSAPublicKey getPublicKey(String keyString) throws Exception {
        byte[] keyBytes = hex2byte(keyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        return (DSAPublicKey) pubKey;
    }

    public static String getPrivateKeyString(DSAPrivateKey privateKey)
            throws Exception {
        return byte2hex(privateKey.getEncoded());
    }

    public static String getPublicKeyString(DSAPublicKey publicKey)
            throws Exception {
        return byte2hex(publicKey.getEncoded());
    }

    public static String byte2hex(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < paramArrayOfByte.length; ++i) {
            localStringBuffer.append(Integer
                    .toHexString(256 + (paramArrayOfByte[i] & 0xFF))
                    .substring(1).toUpperCase());
        }
        return localStringBuffer.toString();
    }

    // 将16进制字符串转换成字节码
    public static byte[] hex2byte(String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bts;
    }

    /**
     * 从证书文件中装入私钥
     *
     * @param path     证书路径
     * @param password 证书密码
     * @return 私钥
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String path, String password) throws Exception {
        FileInputStream ksfis = null;
        try {
            KeyStore ks = KeyStore.getInstance("pkcs12");
            ksfis = new FileInputStream(path);
            char[] storePwd = password.toCharArray();
            char[] keyPwd = password.toCharArray();
            ks.load(ksfis, storePwd);
            Enumeration enumas = ks.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements()) {// we are readin just one certificate.
                keyAlias = (String) enumas.nextElement();
            }
            // 从密钥仓库得到私钥
            return (PrivateKey) ks.getKey(keyAlias, keyPwd);
        } finally {
            if (ksfis != null)
                ksfis.close();
        }
    }

    public static PublicKey loadPublicKey(String path) throws Exception {
        File file = new File(path);
        InputStream inStream = new FileInputStream(file);
        // 创建X509工厂类  
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // 创建证书对象  
        X509Certificate oCert = (X509Certificate) cf.generateCertificate(inStream);
        PublicKey pk = oCert.getPublicKey();
        return pk;
    }

    /**
     * 从证书文件中装入公钥
     *
     * @param alias    证书别名
     * @param path     证书路径
     * @param password 证书密码
     * @return 公钥
     * @throws Exception
     */
    public static PublicKey loadPublicKey(String alias, String path, String password) throws Exception {
        FileInputStream ksfis = null;
        try {
            KeyStore ks = KeyStore.getInstance("pkcs12");
            ksfis = new FileInputStream(path);
            char[] storePwd = password.toCharArray();
            ks.load(ksfis, storePwd);
            // 从密钥仓库得到私钥
            return ks.getCertificate(alias).getPublicKey();
        } finally {
            if (ksfis != null)
                ksfis.close();
        }
    }

    /**
     * 私钥签名
     *
     * @param privateKey 私钥
     * @param text       签名内容
     * @return 签名
     * @throws Exception
     */
    public static String sign(PrivateKey privateKey, String text) throws Exception {
        // 用私钥对信息进行数字签名
        Signature signature = Signature.getInstance("DSA");
        signature.initSign(privateKey);
        signature.update(text.getBytes("utf8"));
        return Base64.encode(signature.sign());
    }

    /**
     * 用公钥对签名进行验证
     *
     * @param publicKey 公钥
     * @param text      签名的原始内容
     * @param sign      签名
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(PublicKey publicKey, String text, String sign) throws Exception {
        Signature signature = Signature.getInstance("DSA");
        signature.initVerify(publicKey);
        signature.update(text.getBytes("utf8"));
        return signature.verify(Base64.decode(sign)); // 验证签名
    }
}
