package com.pay.zhaoshang.util;

import java.security.MessageDigest;

/**
 * Created by huad on 2016/3/2.
 */
public class MD5Util {

    public static String sign(String text)throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String sginString = byte2hex(md.digest(text.toString().getBytes("utf-8")));
        return sginString;
    }

    private static String byte2hex(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < paramArrayOfByte.length; ++i) {
            localStringBuffer.append(Integer
                    .toHexString(256 + (paramArrayOfByte[i] & 0xFF))
                    .substring(1).toUpperCase());
        }
        return localStringBuffer.toString();
    }
}
