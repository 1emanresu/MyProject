package com.jsp.payment.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.jsp.payment.dao.PaymentDao;
import com.pay.cardyee.server.XmlStringParse;
import com.pay.cardyee.server.util.EncodeUtils;
import com.pay.yeepay.server.Configuration;
import com.vo.Balance;
import com.vo.Employee_Person;
import com.vo.Employees;
import com.vo.Rate;
import com.vo.Recharge;
import com.vo.System_interface;

public class PaymentService extends PaymentDao {
	public static PaymentService service = new PaymentService();
	
	public void netcallback(String r2_TrxId, String r6_Order, String ro_BankOrderId, String rp_PayDate, String ru_Trxtime){
		Recharge order = Recharge.dao.findById(r6_Order);
		int r1_Code = order.getInt("r1_Code");
		if(r1_Code!=1){
			BigDecimal p3_Amt = order.getBigDecimal("p3_Amt");
			int p1_MerId = order.getInt("p1_MerId");
			BigDecimal banking = Rate.dao.findById(p1_MerId).getBigDecimal("banking");
			BigDecimal amount = p3_Amt.multiply(banking);
			BigDecimal deducted = p3_Amt.subtract(amount);
			order.set("r1_Code", 1);
			order.set("r2_TrxId", r2_TrxId);
			order.set("ro_BankOrderId", ro_BankOrderId);
			order.set("rp_PayDate", rp_PayDate);
			order.set("ru_Trxtime", ru_Trxtime);
			order.set("amount", amount);
			order.set("deducted", deducted);
			order.update();
			Balance balance = Balance.dao.findById(p1_MerId);
			int settlementauthority = balance.getInt("settlementauthority");
			balance.set("amount", balance.getBigDecimal("amount").add(amount));
			balance.set("netamount", balance.getBigDecimal("netamount").add(amount));
			balance.set("deductednet", balance.getBigDecimal("deductednet").add(deducted));
			if(settlementauthority==1){
				balance.set("settlement", balance.getBigDecimal("settlement").add(amount));
			}else if(settlementauthority==2){
				balance.set("newamount", balance.getBigDecimal("newamount").add(amount));
			}
			balance.update();
			Employee_Person empper = Employee_Person.dao.findById(p1_MerId);
			if(empper!=null){
				int employeeid = empper.getInt("employeeid");
				Employees emp = Employees.dao.findById(employeeid);
				emp.set("haveroyalty", emp.getBigDecimal("haveroyalty").add(amount.multiply(emp.getBigDecimal("commission"))));
				emp.update();
			}
		}
	}
	
	public void failure(String r6_Order, String ru_Trxtime){
		Recharge order = Recharge.dao.findById(r6_Order);
		order.set("r1_Code", 3);
		order.set("ru_Trxtime", ru_Trxtime);
		order.update();
	}
	
	public XmlStringParse getCirclip(String orderno){
		XmlStringParse xml = null;
		try {
			System_interface systeminterface = System_interface.dao.findById(1);
			String usercode = systeminterface.getStr("system_interface_usercode");
			String mode = "r";
			String version = "1.0";
			// String retaction = request.getParameter("retaction");
			String sign = "";
			String format = "xml";
			String md5key = systeminterface.getStr("system_interface_md5key");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHMMSS");
			String datetime = sdf.format(new Date());
			String md5src = usercode + mode + version + orderno + format;
			sign = EncodeUtils.testDigest(md5src + md5key);
			
			HttpClient hClient = new HttpClient();
			HttpConnectionManagerParams managerParams = hClient
					.getHttpConnectionManager().getParams();
			// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(10000);
			// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(10000);
			PostMethod post = null;
			String query = Configuration.getInstance().getValue("query");
			post = new PostMethod(query);
			NameValuePair[] nvp = { new NameValuePair("mode", mode),
					new NameValuePair("version", version),
					new NameValuePair("usercode", usercode),
					new NameValuePair("orderno", orderno),
					new NameValuePair("format", format),
					new NameValuePair("datetime", datetime),
					new NameValuePair("sign", sign) };
			post.setRequestBody(nvp);
			post.setRequestHeader("Connection", "close");
			hClient.executeMethod(post);
			String returnStr = post.getResponseBodyAsString();
			System.out.println("提交收卡支付返回:" + returnStr);
			xml = new XmlStringParse(returnStr);
			String retusercode = xml.getParameter("usercode");
			String retmode = xml.getParameter("mode");
			String retversion = xml.getParameter("version");
			String retorderno = xml.getParameter("orderno");
			String retbillid = xml.getParameter("billid");
			String retresult = xml.getParameter("result");
			String retinfo = xml.getParameter("info");
			String retdatetime = xml.getParameter("datetime");
			String retsign = xml.getParameter("sign");
			String retvalue = xml.getParameter("value");
			String retaccountvalue = xml.getParameter("accountvalue");
			
			System.out.println(xml);
			md5src = usercode + mode + version
			+ orderno + retbillid + retresult + retinfo + retvalue + retaccountvalue
			+ retdatetime;
			if (!retsign.equals(EncodeUtils.testDigest(md5src + md5key))) {
				System.out.println("加密验证失败");
			}
			post.releaseConnection();
			post = null;
			hClient = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}
}
