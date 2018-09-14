package com.huifubao.Commond;

import java.security.MessageDigest;

public class Md5Tools {

	public static String MD5(String s){
		char[] hexDigits={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};//ʮ�����
		try{
			byte[] btInput=s.getBytes(WeiXinDataHelper.UTF8Encode);//��㸶������һ��
			MessageDigest mdInst=MessageDigest.getInstance("MD5");//���MD5ժҪ�㷨��messageDigest����
			mdInst.update(btInput);//ʹ��ָ�����ֽڸ���ժҪ
			byte[] md=mdInst.digest();//�������
			//������ת����ʮ����Ƶ��ַ���ʽ
			int j=md.length;
			char str[]=new char[j*2];
			int k=0;
			for(int i=0;i<j;i++){
				byte byte0=md[i];
				str[k++]=hexDigits[byte0>>>4& 0xf];
				str[k++]=hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
