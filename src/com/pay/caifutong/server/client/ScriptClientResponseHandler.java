package com.pay.caifutong.server.client;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pay.caifutong.server.util.HttpClientUtil;


/**
 * 返回script内容后台应答类
 * ========================================================================
 * api说明：
 * 
 * ========================================================================
 *
 */
public class ScriptClientResponseHandler extends ClientResponseHandler {

	protected void doParse() throws UnsupportedEncodingException {

		Pattern pattern = Pattern.compile("window.location.href.*=.*(http://.*|https://.*)['|\"]");
		Matcher matcher = pattern.matcher(this.getContent());
		
		if(matcher.find()) {
			//获取url
			String url = matcher.group(1);
			
			String queryString = HttpClientUtil.getQueryString(url);
			Map m = HttpClientUtil.queryString2Map(queryString);
			if(null != m && !m.isEmpty()) {
				String charset = this.getCharset();
				Iterator it = m.keySet().iterator();
				while(it.hasNext()) {
					String k = (String) it.next();
					String v = (String) m.get(k);
					this.setParameter(k, URLDecoder.decode(v, charset));
				}
			}
			
			return;
		}

		//this.setParameter("pay_result", "99");
		//this.setParameter("pay_info", "返回包格式错误，请检查协议是否改变！");
		
	}
	
}
