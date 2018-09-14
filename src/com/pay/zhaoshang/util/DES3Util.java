package com.pay.zhaoshang.util;

import java.io.UnsupportedEncodingException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class DES3Util {
    private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 3DES加密
     *
     * @param value     普通文本
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String encrypt3DES(String value, String secretKey) throws Exception {
        String plainText = null;
        byte[] keyBytes = newByte(24);
        to24Key(secretKey, keyBytes);
        KeySpec dks = new DESedeKeySpec(keyBytes);
        SecretKey secKey = SecretKeyFactory.getInstance(Algorithm).generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] srcBytes = value.getBytes(encoding);
        int srcLen = srcBytes.length;
        int valuelen = srcLen + 1;
        int encLen = ((valuelen % 8) == 0) ? valuelen : ((valuelen / 8 + 1) * 8);
        byte[] encBytes = newByte(encLen);
        encBytes[0] = (byte) srcLen;
        System.arraycopy(srcBytes, 0, encBytes, 1, srcLen);
        // 正式执行解密操作
        byte[] encryptBytes = cipher.doFinal(encBytes);
        plainText = Base64.encode(encryptBytes);
        return plainText;
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decrypt3DES(String encryptText, String secretKey) throws Exception {
        byte[] keyBytes = newByte(24);
        to24Key(secretKey, keyBytes);
        KeySpec dks = new DESedeKeySpec(keyBytes);
        SecretKey secKey = SecretKeyFactory.getInstance(Algorithm).generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] inputBytes = Base64.decode(encryptText);
        byte[] decryptBytes = cipher.doFinal(inputBytes);
        int length = decryptBytes[0];
        byte[] valueBytes = new byte[length];
        System.arraycopy(decryptBytes, 1, valueBytes, 0, length);
        return new String(valueBytes, encoding);
    }

    private static byte[] newByte(int length) {
        byte[] data = new byte[length];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) 0xFF;
        }
        return data;
    }

    private static void to24Key(String key, byte[] hex) throws UnsupportedEncodingException {
        byte[] inputBytes = key.getBytes("utf-8");
        for (int i = 0; i < key.length() && i < 24; i++) {
            hex[i] = inputBytes[i];
        }
    }
}
