package com.zzzhifu.xiaoxie.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class SignUtil {

	public static final String RSA_ALGORITHMS = "SHA1withRSA";

	public static final String KEY_ALGORITHM = "RSA";

	public static String sign(String str, String privateKey) throws Exception {
		RSAPrivateKey privateK = getPrivateKey(privateKey);
		Signature signature = Signature.getInstance(RSA_ALGORITHMS);
		signature.initSign(privateK);
		signature.update(str.getBytes("utf-8"));
		return Base64Utils.encode(signature.sign());
	}

	public static RSAPrivateKey getPrivateKey(String privateKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes = Base64Utils.decode(privateKey);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		return (RSAPrivateKey) keyFactory.generatePrivate(spec);
	}

	public static boolean verify(String signed, String text, String publicKey)
			throws Exception {
		RSAPublicKey publicK = getPublicKey(publicKey);
		Signature signature = Signature.getInstance(RSA_ALGORITHMS);
		signature.initVerify(publicK);
		signature.update(text.getBytes("utf-8"));
		return signature.verify(Base64Utils.decode(signed));
	}

	public static RSAPublicKey getPublicKey(String publicKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes = Base64Utils.decode(publicKey);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		return (RSAPublicKey) keyFactory.generatePublic(spec);
	}

	public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, Object> sortMap = new TreeMap<String, Object>(
				new MapValueComparator());
		sortMap.putAll(map);
		return sortMap;
	}

	public static String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = StringUtils.substringBeforeLast(s, "&");
		}
		return s;
	}
}
