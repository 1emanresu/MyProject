package com.admin.gateway.controller;

import java.util.ArrayList;
import java.util.List;

import com.admin.gateway.service.AdGatewayService;
import com.admin.persongateway.service.AdPersonGatewayService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tool.StringUtil;
import com.vo.Gateway;
import com.vo.Order;
import com.vo.PersonGateway;

@Before(AdLoginSeInterceptor.class)
public class AdGatewayController extends Controller {

	public void index() {
		try {
			String gateway_id = "";
			String gateway_name = "";
			String gateway_no = "";
			String gateway_status = "";
			int pageNum=1;
			int numPerPage=20;
			
			if(StringUtil.isNotEmpty(getPara("pageNum"))){
				pageNum = getParaToInt("pageNum");
			}
			
			if(StringUtil.isNotEmpty(getPara("numPerPage"))){
				numPerPage = getParaToInt("numPerPage");
			}
			
			if(StringUtil.isNotEmpty(getPara("gateway_id"))){
				gateway_id = getPara("gateway_id").trim();
			}
			if(StringUtil.isNotEmpty(getPara("gateway_name"))){
				gateway_name = getPara("gateway_name").trim();
			}
			if(StringUtil.isNotEmpty(getPara("gateway_no"))){
				gateway_no = getPara("gateway_no").trim();
			}
			if(StringUtil.isNotEmpty(getPara("gateway_status"))){
				gateway_status = getPara("gateway_status").trim();
			}
			//List<Gateway> gatewaylist = AdGatewayService.service.getGateway(gateway_id,gateway_name,gateway_no,gateway_status);
			Page<Record> page=AdGatewayService.service.gatewayByPage(gateway_id, gateway_name, gateway_no, gateway_status, pageNum, numPerPage);
			setAttr("gateway_id",gateway_id);
			setAttr("gateway_name",gateway_name);
			setAttr("gateway_no",gateway_no);
			setAttr("gateway_status",gateway_status);
			setAttr("gatewaylist",page.getList());
			setAttr("numPerPage",numPerPage);
			setAttr("pageNum",pageNum);
			setAttr("totalCount",page.getTotalRow());
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/gateway.jsp");
	}
	
	
	
	public void gatewayRate() {
		try {
			String gateway_id = "";
			String gateway_name = "";
			String gateway_no = "";
			String gateway_status = "";
			int pageNum=1;
			int numPerPage=20;
			if(StringUtil.isNotEmpty(getPara("pageNum"))){
				pageNum = getParaToInt("pageNum");
			}
			if(StringUtil.isNotEmpty(getPara("numPerPage"))){
				numPerPage = getParaToInt("numPerPage");
			}
			if(StringUtil.isNotEmpty(getPara("gateway_id"))){
				gateway_id = getPara("gateway_id").trim();
			}
			if(StringUtil.isNotEmpty(getPara("gateway_name"))){
				gateway_name = getPara("gateway_name").trim();
			}
			if(StringUtil.isNotEmpty(getPara("gateway_no"))){
				gateway_no = getPara("gateway_no").trim();
			}
			if(StringUtil.isNotEmpty(getPara("gateway_status"))){
				gateway_status = getPara("gateway_status").trim();
			}
			//List<Gateway> gatewaylist = AdGatewayService.service.getGateway(gateway_id,gateway_name,gateway_no,gateway_status);
			Page<Record> page=AdGatewayService.service.gatewayByPage(gateway_id, gateway_name, gateway_no, gateway_status, pageNum, numPerPage);
			setAttr("gateway_id",gateway_id);
			setAttr("gateway_name",gateway_name);
			setAttr("gateway_no",gateway_no);
			setAttr("gateway_status",gateway_status);
			setAttr("gatewaylist",page.getList());
			setAttr("numPerPage",numPerPage);
			setAttr("pageNum",pageNum);
			setAttr("totalCount",page.getTotalRow());
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/gateway/gatewayRate.jsp");
	}
	
	public void getGatewayGetAll() {
		try {
			String starttime = getPara("starttime");
			String endtime = getPara("endtime");
			String btn = getPara("btn");
			if (btn.equals("wx")) {
				setAttr("orderlist1",AdGatewayService.service.getGatewayWxwapByDate(starttime, endtime));
			} else if (btn.equals("ali")) {
				setAttr("orderlist1",AdGatewayService.service.getGatewayAlipaywapByDate(starttime, endtime));
			}else if(btn.equals("all")){
				setAttr("orderlist1",AdGatewayService.service.getGatewayAllpaywapByDate(starttime, endtime));
			}
			setAttr("btn", btn);
			setAttr("starttime", starttime);
			setAttr("endtime", endtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/gatewaygetall/gatewaygetall.jsp");
	}

	public void getEmployeeGetAll() {
		try {
			String starttime = getPara("starttime1");
			String endtime = getPara("endtime1");
			String btn = getPara("btn1");
			List<Order> list = null;
			List<Order> wx=null;
			List<Order> ali=null;
			System.out.println(starttime+endtime+btn);
			if(starttime!=null&&endtime!=null){
				if (btn.equals("")||btn==null) {
					wx=Order.dao.find("(select o.p1_MerId,sum(o.p3_Amt) as 'wxamt',sum(o.amount) as 'wxamo' from orders o WHERE o.orderid like '%WX%' and o.r1_Code=1  "
									+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime + "' group by o.p1_MerId)");
					ali=Order.dao.find("(select o.p1_MerId,sum(o.p3_Amt) as 'aliamt',sum(o.amount) as 'aliamo' from orders o WHERE o.orderid like '%ALI%' and o.r1_Code=1  "
							+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime + "' group by o.p1_MerId)");
					list = Order.dao.find("select p.id,p.name,sum(o.p3_Amt) as amt,sum(o.amount) as amount,sum(o.deducted) as deducted from person p ,orders o where o.r1_Code=1 and p.id=o.p1_MerId "
									+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime + "' group by p.id");
				} else{
					Integer btn1=null;
					
					try {
						 btn1=Integer.parseInt(btn);
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
					list = Order.dao
							.find("select p.id,p.name,sum(o.p3_Amt) as amt,sum(o.amount) as amount,sum(o.deducted) as deducted from person p ,orders o where o.r1_Code=1 and p.id=o.p1_MerId "
									+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime + "' " + "and p.id="
									+ btn1 + " group by p.id");
					wx=Order.dao.find("(select o.p1_MerId,sum(o.p3_Amt) as 'wxamt',sum(o.amount) as 'wxamo' from orders o WHERE o.orderid like '%WX%' and o.r1_Code=1  "
							+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime + "' " + "and o.p1_MerId="
									+ btn1 + " group by o.p1_MerId)");
			
					ali=Order.dao.find("(select o.p1_MerId,sum(o.p3_Amt) as 'aliamt',sum(o.amount) as 'aliamo' from orders o WHERE o.orderid like '%ALI%' and o.r1_Code=1  "
							+ "and o.CreateTime >= '" + starttime + "' and o.CreateTime < '" + endtime + "' " + "and o.p1_MerId="
									+ btn1 + " group by o.p1_MerId)");
			
				}
			}
			
			if(list!=null){
				for (Order lis : list) {
					lis.put("wxamt", "0.000");
					lis.put("wxamo", "0.000");
					lis.put("aliamo", "0.000");
					lis.put("aliamt", "0.000");
					int lisid=lis.getInt("id");
					if(wx!=null){
						for (Order w : wx) {
							if(lisid==w.getInt("p1_MerId")){
								String wxamt=w.get("wxamt").toString();
								String wxamo=w.get("wxamo").toString();
								if(wxamt.equals("")||wxamt==null){
									lis.put("wxamt", "0.000");
								}
								if(wxamo.equals("")||wxamo==null){
									lis.put("wxamo", "0.000");
								}
								if(!(wxamt.equals("")||wxamt==null)){
									lis.put("wxamt", wxamt);
								}
								if(!(wxamo.equals("")||wxamo==null)){
									lis.put("wxamo", wxamo);
								}
								break;
							}
						}
					}
					if(ali!=null){
						for (Order al : ali) {
							if(lisid==al.getInt("p1_MerId")){
								String aliamt=al.get("aliamt").toString();
								String aliamo=al.get("aliamo").toString();
								lis.put("aliamt", aliamt);
								lis.put("aliamo", aliamo);
								if(aliamt.equals("")||aliamt==null){
									lis.put("aliamt", "0.000");
								}
								if(aliamo.equals("")||aliamo==null){
									lis.put("aliamo", "0.000");
								}
								if(!(aliamt.equals("")||aliamt==null)){
									lis.put("aliamt", aliamt);
								}
								if(!(aliamo.equals("")||aliamo==null)){
									lis.put("aliamo", aliamo);
								}
								break;
							}
						}
					}
				}
			}
			setAttr("orderlist1", list);
			setAttr("starttime", starttime);
			setAttr("endtime", endtime);
			setAttr("btn", btn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/employeegetall/employeegetall.jsp");
	}

	public void personRate() {
		
		
		String id = "";
		if(StringUtil.isNotEmpty(getPara("id"))){
			id=getPara("id");
			setAttr("id", id);
		}
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		
		try {
			
			Page<Record> page = AdPersonGatewayService.dao.getPersonGateway20171010(pageNum,numPerPage,id);
			List<Gateway> alilist = AdGatewayService.service.getGatewayAlipaywap();
			List<Gateway> wxlist = AdGatewayService.service.getGatewayWxwap();
			List<Gateway> weixinlist = AdGatewayService.service.getGatewayWeixin();
			
			alilist.add(new Gateway().set("gateway_id", 0).set("gateway_name","已关闭"));
			wxlist.add(new Gateway().set("gateway_id", 0).set("gateway_name","已关闭"));
			weixinlist.add(new Gateway().set("gateway_id", 0).set("gateway_name","已关闭"));
			setAttr("getView1", page.getList());
			setAttr("alipaylist", alilist);
			setAttr("wxpaylist", wxlist);
			setAttr("weixinlist", weixinlist);
		
			/*JSONArray ja=new JSONArray(page.getList()); 
			System.out.println(ja);*/
			
			setAttr("totalCount",page.getTotalRow());
			setAttr("pageNum", page.getPageNumber());
			setAttr("numPerPage", page.getPageSize());
			setAttr("pageNumShown", page.getTotalPage());
			
			renderJsp("/WEB-INF/admin/jsp/persongateway/personRate.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getPersonGateway() {
		try {
			List<PersonGateway> list = AdPersonGatewayService.dao.getPersonGateway();
			List<Gateway> alilist = AdGatewayService.service.getGatewayAlipaywap();
			List<Gateway> wxlist = AdGatewayService.service.getGatewayWxwap();
			List<Gateway> weixinlist = AdGatewayService.service.getGatewayWeixin();
			if(list!=null){
				for (PersonGateway p : list) {
					String wxid = "不存在";
					String wxname = "不存在";
					String weixinid = "不存在";
					String weixinname = "不存在";
					String aliid = "不存在";
					String aliname = "不存在";
					String title = "";
					if(!p.get("title").equals("")||p.get("title")!=null){
						title = p.get("title");
						
						String[] strs = title.split(";");
						for (String string : strs) {
							if (string.contains("alipaywap")) {
								aliid = string.split("-")[0];
								if(alilist!=null){
									for (Gateway gateway : alilist) {
										if(aliid.equals("0")){
											aliname = "已关闭";
											break;
										}
										if (gateway.getInt("gateway_id").toString().equals(aliid)) {
											aliname = gateway.getStr("gateway_name");
											break;
										}
									}
								}
							}
							if (string.contains("wxwap")) {
								wxid = string.split("-")[0];
								if(wxlist!=null){
									for (Gateway gateway : wxlist) {
										if(wxid.equals("0")){
											wxname = "已关闭";
											break;
										}
										if (gateway.getInt("gateway_id").toString().equals(wxid)) {
											wxname = gateway.getStr("gateway_name");
											break;
										}
									}
								}
							}
							if (string.contains("weixin")) {
								weixinid = string.split("-")[0];
								if(weixinlist!=null){
									for (Gateway gateway : weixinlist) {
										if(weixinid.equals("0")){
											weixinname = "已关闭";
											break;
										}
										if (gateway.getInt("gateway_id").toString().equals(weixinid)) {
											weixinname = gateway.getStr("gateway_name");
											break;
										}
									}
								}
							}
						}
					}
					System.out.println(title);
					p.put("wxid", wxid);
					p.put("wxname", wxname);
					p.put("weixinid", weixinid);
					p.put("weixinname", weixinname);
					p.put("aliid", aliid);
					p.put("aliname", aliname);
				}
			}
			alilist.add(new Gateway().set("gateway_id", 0).set("gateway_name","已关闭"));
			wxlist.add(new Gateway().set("gateway_id", 0).set("gateway_name","已关闭"));
			weixinlist.add(new Gateway().set("gateway_id", 0).set("gateway_name","已关闭"));
			setAttr("getView1", list);
			setAttr("alipaylist", alilist);
			setAttr("wxpaylist", wxlist);
			setAttr("weixinlist", weixinlist);
			renderJsp("/WEB-INF/admin/jsp/persongateway/persongateway.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upView() {
		try {
			String[] view1list = getParaValues("view1list");
			for (String string : view1list) {
				//System.out.println("====="+string);
			}
			if(view1list!=null){
				String s = getPara(0);
				if (s != null) {
					s += ("-" + getPara(1));
				} else {
					renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
					return;
				}
				for (String string : view1list) {
					PersonGateway personGateway1 = PersonGateway.dao.findById(Integer.parseInt(string));
					if(personGateway1!=null){
						String title = personGateway1.get("title");
						boolean con = false;
						List<String> lists = new ArrayList<String>();
						for (String string2 : title.split(";")) {
							if (string2.contains(s.split("-")[1])) {
								string2 = s;
								con = true;
							}
							if (string2 != null && !string2.equals("")) {
								lists.add(string2);
							}
						}
						if (!con) {
							lists.add(s);
						}
						String pingjie = "";
						
						for (int i = 0; i < lists.size(); i++) {
							pingjie += lists.get(i);
							if (i < (lists.size() - 1)) {
								pingjie += ";";
							}
						}
						personGateway1.set("title", pingjie);
						personGateway1.update();
					}
					renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"nav31\", \"forwardUrl\":\"\", \"rel\":\"nav31\"}");
			}
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
	}

	/**
	 * @description 通道管理的编辑controller<br/>
	 * @methodName editGateway<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月6日下午5:36:31<br/>
	 */
	public void editGateway() {
		try { 
			String gateway_id = getPara("gateway_id");
			List<Gateway> gatewaylist = AdGatewayService.service.findByGatewayId(gateway_id);
			Gateway gateway = gatewaylist.get(0);
			setAttr("amount_size",gateway.getBigDecimal("amount_size"));
			setAttr("gateway_merid",gateway.get("gateway_merid"));
			setAttr("gateway_id", gateway.get("gateway_id"));
			setAttr("end_time",gateway.get("end_time"));
			setAttr("start_time",gateway.get("start_time"));
			setAttr("status", gateway.get("status"));
			setAttr("gateway_key",gateway.get("gateway_key"));
			setAttr("gateway_name", gateway.get("gateway_name"));
			setAttr("ali", gateway.get("ali"));
			setAttr("weixin", gateway.get("weixin"));
			setAttr("qq", gateway.get("qq"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp("/WEB-INF/admin/jsp/gateway/editgateway.jsp");
	}
	
	/**
	 * @description 通道管理的更新操作Controller<br/>
	 * @methodName upGateway<br/>
	 * @author jackZhou<br/>
	 * @createTime 2017年9月6日下午5:32:17<br/>
	 */
	public void upGateway() {
		try { 
			int gateway_id = getParaToInt("gateway_id");
			String amount_size = getPara("amount_size");
			String gateway_merid = getPara("gateway_merid");
			String start_time = getPara("start_time");
			String end_time = getPara("end_time");
			String status = getPara("status");
			String gateway_key = getPara("gateway_key");
			String ali = getPara("ali");
			String weixin = getPara("weixin");
			String qq = getPara("qq");
			boolean boo = AdGatewayService.service.upGateway(gateway_id, amount_size, gateway_merid, start_time, end_time, status, gateway_key, ali, weixin, qq);
			if (boo) {
				renderJson("{\"statusCode\":\"200\", \"message\":\"恭喜您！操作成功\", \"navTabId\":\"nav22\", \"forwardUrl\":\"\", \"rel\":\"nav22\"}");
			} else {
				renderJson("{\"statusCode\":\"300\", \"message\":\"修改失败\"}");
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"修改失败\"}");
			e.printStackTrace();
		}
	}

	/**
	 * @description 添加通道的Controller<br/>
	 * @methodName addGateway<br/>
	 * @author jack<br/>
	 * @createTime 2017年9月6日下午5:38:43<br/>
	 */
	public void addGateway(){
		renderJsp("/WEB-INF/admin/jsp/gateway/addgateway.jsp");
	}
	
	public void insertGateway(){
		String gateway_name = "";
		String gateway_merid = "";
		String gateway_key = "";
		String wxwap = "";
		String weixin = "";
		String alipaywap = "";
		String alipay = "";
		String req_url = "";
		String notify_url = "";
		String start_time = "";
		String end_time = "";
		String amount_size = "";
		String gateway_rate = "";
		try {
			gateway_name = getPara("gateway_name");
			gateway_merid = getPara("gateway_merid");
			gateway_key = getPara("gateway_key");
			wxwap = getPara("wxwap");
			weixin = getPara("weixin");
			alipaywap = getPara("alipaywap");
			alipay = getPara("alipay");
			req_url = getPara("req_url");
			notify_url = getPara("notify_url");
			start_time = getPara("start_time");
			end_time = getPara("end_time");
			amount_size = getPara("amount_size");
			gateway_rate = getPara("gateway_rate");
			//System.out.println("======"+gateway_key);
			boolean result = AdGatewayService.service.insertIntoGateway(gateway_name,
					gateway_merid, gateway_key, wxwap, weixin, alipaywap,
					alipay, req_url, notify_url, start_time, end_time,
					amount_size,gateway_rate);
			if(result){
				renderJson("{\"statusCode\":\"200\", \"message\":\"恭喜您！操作成功\", \"navTabId\":\"nav22\", \"forwardUrl\":\"\", \"rel\":\"nav22\"}");
			}else{
				renderJson("{\"statusCode\":\"300\", \"message\":\"删除失败\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void AlarmUpdate() {
		try {
			System.out.println("预警管理开始运行");
			int gateway_id = getParaToInt("gateway_id");
			String type = getPara("type");
			String sum = getPara("sum");
			String telno = getPara("telno");
			String isno = getPara("isno");
			System.out.println("开始运行=:" + type + "---" + sum + "----" + telno + "----" + isno);

			Gateway gateway = Gateway.dao.findById(gateway_id);
			if (type.equals("wxwap_isno")) {
				gateway.set("wxwap_isno", isno);
				gateway.set("wxsum", sum);
				gateway.set("telno", telno);
			} else if (type.equals("weixin_isno")) {
				gateway.set("weixin_isno", isno);
				gateway.set("weixinsum", sum);
				gateway.set("telno", telno);
			} else if (type.equals("alipaywap_isno")) {
				gateway.set("alipaywap_isno", isno);
				gateway.set("aliwapsum", sum);
				gateway.set("telno", telno);
			} else if (type.equals("alipay_isno")) {
				gateway.set("alipay_isno", isno);
				gateway.set("alipaysum", sum);
				gateway.set("telno", telno);
			} else {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
			boolean boo = gateway.update();
			if (boo) {
				renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\"}");
			} else {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			}
		} catch (Exception e) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
			e.printStackTrace();
		}
	}

}
