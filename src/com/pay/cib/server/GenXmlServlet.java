package com.pay.cib.server;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.swiftpass.config.SwiftpassConfig;
import com.swiftpass.util.SignUtils;
import com.swiftpass.util.XmlUtils;
import com.vo.Order;

/**
 * 接口商会重复发送是否成功的数据，平台验证后，接收XLM请求并处理
 */
public class GenXmlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override	
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        try {	        	
	            req.setCharacterEncoding("utf-8");
	            resp.setCharacterEncoding("utf-8");
	            resp.setHeader("Content-type", "text/html;charset=UTF-8");
	            String resString = XmlUtils.parseRequst(req);
	            System.out.println("通知内容：" + resString);
	            
	            String respString = "fail";
	            if(resString != null && !"".equals(resString)){
	                Map<String,String> map = XmlUtils.toMap(resString.getBytes(), "utf-8");
	                String res = XmlUtils.toXml(map);
	                System.out.println("通知内容：" + res);
	                if(map.containsKey("sign")){
	                    if(!SignUtils.checkParam(map, SwiftpassConfig.key)){
	                        res = "验证签名不通过";
	                        respString = "fail";
	                    }else{
	                        String status = map.get("status");
	                        if(status != null && "0".equals(status)){
	                            String result_code = map.get("result_code");
	                            if(result_code != null && "0".equals(result_code)){	                                
	                                String out_trade_no = map.get("out_trade_no");
	                                Order order = Order.dao.findById(out_trade_no);	                                
	                                if(order.get("p0_Cmd")==null || order.get("p0_Cmd")==""){
	                                	respString = "fail";
	                                }else{
	                                 //更新数据库
	                                 respString = "fail";
	                                }
	                                //System.out.println(TestPayServlet.orderResult);
	                            } 
	                        } 
	                        respString = "success";
	                    }
	                }
	            }
	            resp.getWriter().write(respString);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req,resp);
    }
}
