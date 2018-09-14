package com.pay.caifutong.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pay.caifutong.server.util.MD5Util;
import com.pay.caifutong.server.util.TenpayUtil;

/**
 * 委托退款签约应答类
 * ========================================================================
 * api说明：
 * isTenpaySign(),是否财付通签名,true:是 false:否
 * ========================================================================
 */

public class TrustRefundResponseHandler extends ResponseHandler {
  
	public TrustRefundResponseHandler(HttpServletRequest request,
			HttpServletResponse response) {
		
		super(request, response);
		
	}
	
	/**
	 * 是否财付通签名
	 * @Override
	 * @return boolean
	 */

	public boolean isTenpaySign(){
		
	    //获取参数
	    String cmdno = getParameter("cmdno");
            String spid = getParameter("spid");
            String uin = getParameter("uin");
            String status = getParameter("status");
            String tenpaySign = getParameter("cftsign");
	    String key = this.getKey();
			
	    //组织签名串
	    StringBuffer sb = new StringBuffer();
	    sb.append("cmdno=" + cmdno + "&");
            sb.append("spid=" + spid + "&");
            sb.append("uin=" + uin + "&");
            sb.append("status=" + status);
	    sb.append(key);
		
	    //算出摘要
	    String enc = TenpayUtil.getCharacterEncoding(this.getHttpServletRequest(), this.getHttpServletResponse());
	    String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
		
	    //debug信息
	    this.setDebugInfo(sb.toString() + " => sign:" + sign +
				" tenpaySign:" + tenpaySign);
		
	    return tenpaySign.equals(sign);
	}
}
