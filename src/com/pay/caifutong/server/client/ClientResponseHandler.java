package com.pay.caifutong.server.client;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.pay.caifutong.server.util.MD5Util;

/**
 * 后台应答类
 * ========================================================================
 * api说明：
 * getKey()/setKey(),获取/设置密钥
 * getContent() / setContent(), 获取/设置原始内容
 * getParameter()/setParameter(),获取/设置参数值
 * getAllParameters(),获取所有参数
 * isTenpaySign(),是否财付通签名,true:是 false:否
 * getDebugInfo(),获取debug信息
 * 
 * ========================================================================
 *
 */
public class ClientResponseHandler {
	
	/** 应答原始内容 */
	private String content;
	
	/** 应答的参数 */
	private SortedMap parameters; 
	
	/** debug信息 */
	private String debugInfo;
	
	/** 密钥 */
	private String key;
	
	/** 字符集 */
	private String charset;
	
	public ClientResponseHandler() {
		this.content = "";
		this.parameters = new TreeMap();
		this.debugInfo = "";
		this.key = "";
		this.charset = "GBK";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) throws Exception {
		this.content = content;
		
		this.doParse();
	}
	
	/**
	 * 获取参数值
	 * @param parameter 参数名称
	 * @return String 
	 */
	public String getParameter(String parameter) {
		String s = (String)this.parameters.get(parameter); 
		return (null == s) ? "" : s;
	}
	
	/**
	 * 设置参数值
	 * @param parameter 参数名称
	 * @param parameterValue 参数值
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if(null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}
	
	/**
	 * 返回所有的参数
	 * @return SortedMap
	 */
	public SortedMap getAllParameters() {
		return this.parameters;
	}	

	public String getDebugInfo() {
		return debugInfo;
	}
	
	/**
	*获取密钥
	*/
	public String getKey() {
		return key;
	}

	/**
	*设置密钥
	*/
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getCharset() {
		return this.charset;
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}	
	
	/**
	 * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * @return boolean
	 */
	public boolean isTenpaySign() {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		
		sb.append("key=" + this.getKey());
		
		//算出摘要
		String sign = MD5Util.MD5Encode(sb.toString(), this.charset).toLowerCase();
		
		String tenpaySign = this.getParameter("sign").toLowerCase();
		
		//debug信息
		this.setDebugInfo(sb.toString() + " => sign:" + sign +
				" tenpaySign:" + tenpaySign);
		
		return tenpaySign.equals(sign);
	}
	
	/**
	 * 是否财付通签名
	 * @param signParameterArray 签名的参数数组
	 * @return boolean
	 */
	protected boolean isTenpaySign(String signParameterArray[]) {

		StringBuffer signPars = new StringBuffer();
		for(int index = 0; index < signParameterArray.length; index++) {
			String k = signParameterArray[index];
			String v = this.getParameter(k);
			if(null != v && !"".equals(v)) {
				signPars.append(k + "=" + v + "&");
			}
		}
		
		signPars.append("key=" + this.getKey());
				
		//算出摘要
		String sign = MD5Util.MD5Encode(
				signPars.toString(), this.charset).toLowerCase();
		
		String tenpaySign = this.getParameter("sign").toLowerCase();
		
		//debug信息
		this.setDebugInfo(signPars.toString() + " => sign:" + sign +
				" tenpaySign:" + tenpaySign);
		
		return tenpaySign.equals(sign);
	}
	

	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
	
	/**
	 * 解析内容,子类一般要重写此方法。
	 */
	protected void doParse() throws Exception {
		
	}
	

}
