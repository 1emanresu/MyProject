package com.jsp.user.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.admin.person.service.AdPersonService;
import com.function.Yunsms;
import com.interceptor.LoginInterceptor;
import com.interceptor.SmsInterceptor;
import com.interceptor.UpdateInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.jsp.dynamic.service.DynamicService;
import com.jsp.exchange.service.ExchangeService;
import com.jsp.register.service.RegisterService;
import com.jsp.user.service.UserService;
import com.tool.CryptTool;
import com.tool.MD5Utils;
import com.tool.StringUtil;
import com.vo.Dynamic;
import com.vo.Employee_Person;
import com.vo.Employees;
import com.vo.Imgfile;
import com.vo.Logrecord;
import com.vo.Order;
import com.vo.Payment;
import com.vo.Person;
import com.vo.Rate;
@Before(LoginInterceptor.class)
public class UserController extends Controller {
	/**
	 * 初始化
	 */
	public void index() {
		try {
			Person per = getSessionAttr("session");
			List<Logrecord> logs = UserService.userService.getLogrecords(per.getInt("id"));
			setAttr("logs", logs);
			setAttr("balance", UserService.userService.getBalance(per.getInt("id")).get(0));
			setAttr("todayamount", AdPersonService.service.getNewAccount(per.getInt("id")));
			setAttr("rate", UserService.userService.getRate(per.getInt("id")).get(0));
			setAttr("payment", UserService.userService.getPaymList(per.getInt("id")).get(0));
			setAttr("ordercount", UserService.userService.getOrdersCount(per.getInt("id")));
			setAttr("cardordercount", UserService.userService.getCardOrdersCount(per.getInt("id")));
			setAttr("circlipcount", UserService.userService.getCirclipCount(per.getInt("id")));
			setAttr("rechargecount", UserService.userService.getRechargeCount(per.getInt("id")));
			renderJsp("/WEB-INF/jsp/user1.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void upRate() {
		int id = getParaToInt("id");
		String qqwx = getPara("qqwx");
		String cibsm = getPara("cibsm");
		String mustali = getPara("mustali");
		String banking = getPara("banking");
		String refund = getPara("refund");
		
		if (qqwx.equals("") && cibsm.equals("") && mustali.equals("") && banking.equals("") && refund.equals("")  ) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"信息没有填写完整!\"}");
		} else {
			try {
				Rate rate = Rate.dao.findById(id);
				
				if(!qqwx.equals("")){
					rate.set("qqwx", 1-Double.parseDouble(qqwx)/100);
				}
				if(!cibsm.equals("")){
					rate.set("cibsm", 1-Double.parseDouble(cibsm)/100);
				}
				if(!mustali.equals("")){
					rate.set("mustali", 1-Double.parseDouble(mustali)/100);
				}
				if(!banking.equals("")){
					rate.set("banking", 1-Double.parseDouble(banking)/100);
				}
				if(!refund.equals("")){
					rate.set("refund", Double.parseDouble(refund));
				}
				Person person_session=getSessionAttr("session");
				Rate rate_session=Rate.dao.findById(person_session.getInt("id"));
				
				if(rate_session.getDouble("qqwx")<rate.getDouble("qqwx")||rate_session.getDouble("cibsm")<rate.getDouble("cibsm")
						||rate_session.getDouble("mustali")<rate.getDouble("mustali")||rate_session.getDouble("banking")<rate.getDouble("banking")){
					renderJson("{\"statusCode\":\"200\", \"message\":\"操作失败，费率过低！\", \"navTabId\":\"person" + id+ "\", \"rel\":\"person" + id + "\"}");
				}else{
					rate.update();
					renderJson("{\"statusCode\":\"200\", \"message\":\"操作成功\", \"navTabId\":\"person" + id+ "\", \"rel\":\"person" + id + "\"}");
				}
			} catch (Exception e) {
				renderJson("{\"statusCode\":\"300\", \"message\":\"操作失败\"}");
				e.printStackTrace();
			}
		}
	}

	public void getannouncement() {
		int pageNum = getParaToInt("pageNum");
		Page<Dynamic> dynamicPage = DynamicService.service.getDynamicPage(pageNum, 10);
		setAttr("pageNum", dynamicPage.getPageNumber());
		setAttr("totalpage", dynamicPage.getTotalPage());
		setAttr("dynamiclist", dynamicPage.getList());
		renderJsp("/WEB-INF/jsp/announcement.jsp");
	}

	public void getHelp() {
		renderJsp("/WEB-INF/jsp/help.jsp");
	}

	public void getAbout() {
		renderJsp("/WEB-INF/jsp/about.jsp");
	}

	public void count() {
		renderJsp("/WEB-INF/jsp/count.jsp");
	}
	
	public void agent() {
		createToken("addpersonToken", 30 * 60);

		Person person = getSessionAttr("session");
		List<Person> list=UserService.userService.getAll(person.getInt("id"),1);
		if(list!=null){
			setAttr("childList",AdPersonService.service.getPersonlist(list));
		}
		renderJsp("/WEB-INF/jsp/agent.jsp");
	}

	/**
	 * 获取短信
	 */
	@Before(SmsInterceptor.class)
	public void getSms() {
		Person person = getSessionAttr("session");
		String phone = person.getStr("phone");
		String key = CryptTool.getPasswordOfNumber(6);
		String content = "【聚优支付】:您的验证码是：【" + key + "】。请不要把验证码泄露给其他人。如非本人操作，可不用理会！";
		// System.out.println(content);
		// String content = "【253云通讯】欢迎体验253云通讯产品验证码是2532536";

		try {
			String res = Yunsms.sms(phone, content);
			System.out.println("接收到的RES=：" + res);
			if (res.equals("100")) {
				setSessionAttr("sms", key);
				renderText(res);
			} else {
				renderText("短信获取失败，请联系客服");
			}
		} catch (Exception e) {
			renderText("短信获取失败，请联系工作人员");
			e.printStackTrace();
		}

	}

	/**
	 * 更改新手机时，获取新短信
	 */
	@Before(SmsInterceptor.class)
	public void getNewSms() {
		String newphone = getPara("newphone");
		String key = CryptTool.getPasswordOfNumber(6);
		String content = "【聚优支付】:您的验证码是：【" + key + "】。请不要把验证码泄露给其他人。如非本人操作，可不用理会！";
		try {
			String res = Yunsms.sms(newphone, content);
			if (res.equals("100")) {
				setSessionAttr("newsms", key);
				renderText(res);
			} else {
				renderText("短信获取失败，请联系工作人眼");
			}
		} catch (Exception e) {
			renderText("短信获取失败，请联系工作人眼");
			e.printStackTrace();
		}
	}

	/**
	 * 验证新手机短信
	 */
	public void newPhoneSms() {
		String keys = getPara("param");
		String key = getSessionAttr("newsms");
		if (keys.equals(key)) {
			renderText("y");
		} else {
			renderText("验证码错误");
		}
	}

	/**
	 * 判断原支付密码是否正确
	 */
	public void getPaymentboo() {
		String paypassw = getPara("param");
		Person person = getSessionAttr("session");
		String payment = "";
		try {
			payment = MD5Utils.createMD5(paypassw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean boo = UserService.userService.getPaymentboo(person.getInt("id"), payment);
		if (boo) {
			renderText("y");
		} else {
			renderText("原支付密码错误!");
		}
	}

	/**
	 * 判断原密码是否正确
	 */
	public void getPasswordboo() {
		String paword = getPara("param");
		Person person = getSessionAttr("session");
		String password = "";
		try {
			password = MD5Utils.createMD5(paword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean boo = UserService.userService.getPasswordboo(person.getInt("id"), password);
		if (boo) {
			renderText("y");
		} else {
			renderText("原密码错误!");
		}
	}

	/**
	 * 更换支付和登陆密码
	 */
	// @Before(UpdateInterceptor.class)
	public void upPassword() {
		String passwtype = getPara("passwtype");
		System.out.println("更改的类型是：" + passwtype);
		Person person = getSessionAttr("session");
		try {
			if (passwtype.equals("1")) {
				String password = getPara("password");
				password = MD5Utils.createMD5(password);
				Person.dao.findById(person.getInt("id")).set("password", password).update();
				renderJson("{\"info\":\"更改成功！\",\"status\":\"y\"}");
			} else if (passwtype.equals("2")) {
				String payment = getPara("payment");
				System.out.println("更改的支付密码是：" + payment);
				payment = MD5Utils.createMD5(payment);
				System.out.println("更改的支付密码加密后的值是：" + payment);
				System.out.println("用户的ID是==：" + person.getInt("id"));
				Payment.dao.findById(person.getInt("id")).set("payment", payment).update();
				// Payment.dao.find("UPDATE payment SET payment = '"+payment+"'
				// WHERE id="+person.getInt("id"));
				renderJson("{\"info\":\"更改成功！\",\"status\":\"y\"}");
			}
		} catch (Exception e) {
			renderJson("{\"info\":\"很遗憾，更改失败！\",\"status\":\"n\"}");
			e.printStackTrace();
		}
	}

	/**
	 * 更换手机号
	 */
	@Before(UpdateInterceptor.class)
	public void upPhone() {
		try {
			String newphone = getPara("newphone");
			Person person = getSessionAttr("session");
			Person.dao.findById(person.get("id")).set("phone", newphone).update();
			renderJson("{\"info\":\"更改成功！\",\"status\":\"y\"}");
		} catch (Exception e) {
			renderJson("{\"info\":\"更改失败！\",\"status\":\"n\"}");
			e.printStackTrace();
		}
	}

	/**
	 * 更换key值
	 */
	@Before(UpdateInterceptor.class)
	public void upKey() {
		try {
			String key = CryptTool.getPassword(32);
			Person person = getSessionAttr("session");
			Payment pay = Payment.dao.findById(person.getInt("id"));
			pay.set("key", key);
			pay.update();
			renderJson("{\"info\":\"更改成功！\",\"status\":\"y\"}");
		} catch (Exception e) {
			renderJson("{\"info\":\"更改失败！\",\"status\":\"n\"}");
			e.printStackTrace();
		}
	}

	public void findbytime() {
		System.out.println("findbytime()findbytime()findbytime()");
		try {
			Person per = getSessionAttr("session");
			String starttime = "";
			String endtime = "";
			String gateway = "";
			String dateType = "";
			BigDecimal rate = null;
			List<Order> list = new ArrayList<>();
			BigDecimal amount = new BigDecimal(0);
			BigDecimal result = new BigDecimal(0);
			int id = per.getInt("id");
			try {
				starttime = getPara("userstarttime");
				endtime = getPara("userendtime");
				gateway = getPara("payType");
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<Order> lists = null;
			// 支付宝查询
			if (gateway.equals("ALI")) {
				rate = Rate.dao.findById(id).getBigDecimal("mustali");
				//System.out.println("alipay rate=====" + rate);
				lists = UserService.userService.getResultALI(id, starttime, endtime);
				dateType = "ALI";
			}
			// 微信查询
			else if (gateway.equals("WX")) {
				rate = Rate.dao.findById(id).getBigDecimal("cibsm");
				//System.out.println("wxwap rate=====" + rate);
				lists = UserService.userService.getResultWX(id, starttime, endtime);
				dateType = "WX";
			}
			// 数据处理
			forEachList(per, rate, list, starttime, endtime, lists);
			//System.out.println("list.size============" + list.size());

			for (Order order : list) {
				//System.out.println("CreateTime=======" + order.get("CreateTime"));
				//System.out.println("name=======" + order.get("name"));
				//System.out.println("p3_Amt=======" + order.get("p3_Amt"));
				//System.out.println("result=======" + order.get("result"));
				amount = amount.add(order.getBigDecimal("p3_Amt"));
				result = result.add(order.getBigDecimal("result"));
			}
			//System.out.println("实际总额=======" + amount);
			//System.out.println("结算总额=======" + result);

			setAttr("rate", new BigDecimal(1).subtract(rate).multiply(new BigDecimal(100)));
			setAttr("amount", amount);
			setAttr("result", result);
			setAttr("list", list);
			setAttr("dateType",dateType);
			renderJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void forEachList(Person per, BigDecimal rate, List<Order> list, String starttime, String endtime,
			List<Order> lists) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 指定一个日期
		Date startdate = null;
		try {
			startdate = dateFormat.parse(starttime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date enddate = null;
		try {
			enddate = dateFormat.parse(endtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar startcalendar = Calendar.getInstance();
		Calendar endcalendar = Calendar.getInstance();
		startcalendar.setTime(startdate);
		endcalendar.setTime(enddate);
		// 获取年份
		int startyear = startcalendar.get(Calendar.YEAR);
		int endyear = endcalendar.get(Calendar.YEAR);
		if (startyear == endyear) {
			yearsScape(per, rate, list, lists, dateFormat, startcalendar, endcalendar, startyear);
		} else if (endyear - startyear == 1) {
			System.out.println("endyear - startyear == 1");
			Calendar endcalendar1 = Calendar.getInstance();
			Calendar startcalendar1 = Calendar.getInstance();
			
			endcalendar1.setTime(dateFormat.parse( startyear + ""+"-"+"12"+"-"+"31" ));
			yearsScape(per, rate, list, lists, dateFormat, startcalendar, endcalendar1, startyear);
			yearsScape_across(per, rate, list, lists, dateFormat, endcalendar1, endcalendar1, startyear);
			startcalendar1.setTime(dateFormat.parse(endyear + ""+"-"+ "1"+"-"+ "1" ));
			yearsScape(per, rate, list, lists, dateFormat, startcalendar1, endcalendar, startyear);
		} else {
			throw new Exception();
		}
	}

	private void yearsScape(Person per, BigDecimal rate, List<Order> list, List<Order> lists,
			SimpleDateFormat dateFormat, Calendar startcalendar, Calendar endcalendar, int startyear) {
		// 年份相等 通过当年第几天遍历
		int sday = startcalendar.get(Calendar.DAY_OF_YEAR);
		int eday = endcalendar.get(Calendar.DAY_OF_YEAR);
		System.out.println(sday);
		System.out.println(eday);
		for (; sday < eday; sday++) {
			BigDecimal suamo = new BigDecimal(0);
			BigDecimal sures = new BigDecimal(0);
			Order or = new Order();
			for (Order re : lists) {
				String data = "";
				data = re.get("ru_Trxtime").toString();
				Calendar calendar = Calendar.getInstance();
				Date datadate;
				try {
					datadate = dateFormat.parse(data);
					calendar.setTime(datadate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				// 数据当年所在天数
				int day = calendar.get(Calendar.DAY_OF_YEAR);
				if (day == sday) {
					BigDecimal amo = re.getBigDecimal("amt");
					suamo = suamo.add(amo);
				}
			}
			sures = suamo.multiply(rate);
			or.put("name", per.get("name"));
			// 将天转日期
			String createdata = "";
			int fan = 0;
			int temp = 0;
			int rsday = sday;
			lab2: for (; fan < 12; fan++) {
				if (rsday <= 0) {
					//System.out.println("rsday<0============" + rsday);
					break lab2;
				}
				// 1 3 5 78 10 12 是31天
				lab1: switch (fan) {
				case 0:
				case 2:
				case 4:
				case 6:
				case 7:
				case 9:
				case 11:
					temp = rsday;
					rsday = rsday - 31;
					break lab1;
				case 1:
					// 2月闰月处理
					if (startyear % 4 == 0) {
						temp = rsday;
						rsday = rsday - 29;
					} else {
						temp = rsday;
						rsday = rsday - 28;
					}
					break lab1;
				case 3:
				case 5:
				case 8:
				case 10:
					temp = rsday;
					rsday = rsday - 30;
					break lab1;
				}
			}
			createdata = startyear + ""+"-" +(fan) + ""+"-" + temp + "" ;
			or.set("CreateTime", createdata);
			or.set("p3_Amt", suamo);
			or.put("result", sures);
			list.add(or);
		}
	}

	// 2016-12-31到 2017-1-1之间处理
	private void yearsScape_across(Person per, BigDecimal rate, List<Order> list, List<Order> lists,
			SimpleDateFormat dateFormat, Calendar startcalendar, Calendar endcalendar, int startyear) {
		int sday = startcalendar.get(Calendar.DAY_OF_YEAR);
		int eday = endcalendar.get(Calendar.DAY_OF_YEAR);
		System.out.println(sday);
		System.out.println(eday);
		BigDecimal suamo = new BigDecimal(0);
		BigDecimal sures = new BigDecimal(0);
		Order or = new Order();
		for (Order re : lists) {
			String data = "";
			data = re.get("ru_Trxtime").toString();
			Calendar calendar = Calendar.getInstance();
			Date datadate;
			try {
				datadate = dateFormat.parse(data);
				calendar.setTime(datadate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// 数据当年所在天数
			int day = calendar.get(Calendar.DAY_OF_YEAR);
			if (day == sday) {
				BigDecimal amo = re.getBigDecimal("amt");
				suamo = suamo.add(amo);
			}
		}
		sures = suamo.multiply(rate);
		or.put("name", per.get("name"));
		// 将天转日期
		String createdata = "";
		createdata = startyear + ""+"-"+"12"+"-"+"31";
		or.set("CreateTime", createdata);
		or.set("p3_Amt", suamo);
		or.put("result", sures);
		list.add(or);
	}
	
	
	

	
	public void addPerson() {
		List<UploadFile> upfiles=getFiles();
		//System.out.println(upfiles.get(0).getParameterName());
		boolean bootoken = validateToken("addpersonToken");
		Person person = new Person();
		Payment pay = new Payment();
		String email = getPara("email");
		String phone = getPara("phone");
		boolean emailboo = RegisterService.service.getEmail(email);
		boolean phoneboo = RegisterService.service.getPhote(phone);
		if (!bootoken) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"请不要重新提交\"}");
		} else if (!emailboo) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"邮箱已存在\"}");
		} else if (!phoneboo) {
			renderJson("{\"statusCode\":\"300\", \"message\":\"手机号已存在\"}");
		} else {
			
			
			
			person.set("email", getPara("email"));
			String password = getPara("password");
			try {
				person.set("password", MD5Utils.createMD5(password));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String payment = getPara("payment");
			try {
				pay.set("payment", MD5Utils.createMD5(MD5Utils.createMD5(payment)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(StringUtil.isNotEmpty(getPara("webName"))){
				person.set("webName",getPara("webName"));
			}
			person.set("website","http://"+getPara("website"));
			person.set("herolist", getParaToInt("herolist"));
			person.set("huge", getParaToInt("huge"));
			int huge = getParaToInt("huge");
			
			if(StringUtil.isNotEmpty(getPara("name"))){
				person.set("name", getPara("name"));
			}
			if(StringUtil.isNotEmpty(getPara("contacts"))){
				person.set("contacts", getPara("contacts"));
			}
			String idcard = getPara("idcard");
			String licence = getPara("licence");
			
			if (huge == 0) {
				person.set("idcard", idcard);
			} else {
				person.set("idcard", licence);
			}
			
			if(StringUtil.isNotEmpty(getPara("address"))){
				person.set("address", getPara("address"));
			}
			if(StringUtil.isNotEmpty(getPara("phone"))){
				person.set("phone", getPara("phone"));
			}
			if(StringUtil.isNotEmpty(getPara("qq"))){
				person.set("qq", getPara("qq"));
			}
			
			if(StringUtil.isNotEmpty(getPara("isAgent"))){
				person.set("isAgent", getPara("isAgent"));
			}
			
			if(StringUtil.isNotEmpty(getPara("permission"))){
				
				person.set("permission", getPara("permission"));
			}
			
		
			Person session_person=(Person)getSessionAttr("session");
			person.set("superior", session_person.get("id"));
			
			person.set("time", new Date());
			person.set("iflogin", 2);
			person.set("iflock", 0);
			person.set("ifnet", 2);
			String key = CryptTool.getPassword(32);
			pay.set("key", key);
			
			String msg="操作失败！";
			
			try {
				
				boolean res = RegisterService.service.enroll(person, pay);
				if (res) {
					Employees employees = Employees.dao.findById(1);
					Employee_Person employee_Person = new Employee_Person();
					employee_Person.set("id", person.getInt("id"));
					employee_Person.set("employeeid", employees.getInt("employeeid"));
					employee_Person.save();
					
					/**
					 * 文件上传部分：
					 * 
					* 图片新保存的位置
					*/
					String path = getRequest().getSession().getServletContext().getRealPath("/");
					String newPath = "/upimg/check/"+person.get("phone")+"/";//自定义目录  用于存放图片
					/**
					* 没有则新建目录
					*/
					File floder = new File(path + newPath);
					if (!floder.exists()) {
						floder.mkdirs();
					}
					/**
					* 保存新文件
					*/
					Imgfile ifile=new Imgfile();
					ifile.set("phone", phone);
					
					FileInputStream fis = null;
					FileOutputStream fos = null;
					try{
						for (int i = 0; i < upfiles.size(); i++) {
							
							if(upfiles.get(i).getParameterName().equals("idcardFile1")){
								ifile.set("idcardfile1", upfiles.get(i).getFileName());
							}else if(upfiles.get(i).getParameterName().equals("idcardFile2")){
								ifile.set("idcardfile2", upfiles.get(i).getFileName());
							}else if(upfiles.get(i).getParameterName().equals("licenceFile")){
								ifile.set("licenceFile", upfiles.get(i).getFileName());
							}else if(upfiles.get(i).getParameterName().equals("openAccount")){
								ifile.set("openAccount", upfiles.get(i).getFileName());
							}else if(upfiles.get(i).getParameterName().equals("webCulture")){
								ifile.set("webCulture", upfiles.get(i).getFileName());
							}else if(upfiles.get(i).getParameterName().equals("domainName")){
								ifile.set("domainName", upfiles.get(i).getFileName());
							}
							
							File savePath = new File(path + newPath + upfiles.get(i).getFileName());
							if(!savePath.isDirectory()) savePath.createNewFile();
							fis = new FileInputStream(upfiles.get(i).getFile());
							fos = new FileOutputStream(savePath);
							byte[] bt = new byte[300];
							while (fis.read(bt, 0, 300) != -1) {
								fos.write(bt, 0, 300);
							}
							
						}
						
						
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						try{
							if(null!=fis) fis.close();
							if(null!=fos) fos.close();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					/**
					 * 文件上传结束
					 */
					
					
					Record record=new Record();
					record.setColumns(ifile);
					Db.save("imgfile", record);
					
					msg="操作成功！";
				}
			
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			setAttr("message",msg);
			//forwardAction("/user/agent");
			agent();
			
		}
	}
	
	
	public void getQueryCount(){
		
		Person person=getSessionAttr("session");
		
		String netstarttime = "";
		String netendtime = "";
		if(!getPara("netstarttime").equals("")){
			netstarttime = getPara("netstarttime");
		}
		if(!getPara("netendtime").equals("")){
			netendtime = getPara("netendtime");
		}
		
		int tote=0;      //订单比赛
		double amttote=0;	//充值支付总金额
		double amounttote=0;	//充值到账总金额
		double income=0;	//总收益
		
		List<Person> list=UserService.userService.getAll(person.getInt("id"),1);
		
		for (int i = 0; i < list.size(); i++) {
			
			//获取相关的收益率列表
			Rate rate=UserService.userService.getIncome(list.get(i).getInt("id"), person);
			//查询获取总的收益
			Record record = ExchangeService.exchangeService.getQueryOrdertotal20171031(list.get(i).getInt("id"), netstarttime, netendtime,"","",rate);
			
			list.get(i).put(record);
			
			
			tote+=list.get(i).getInt("tote");
			
			if(list.get(i).get("amttote")!=null){
				amttote+=list.get(i).getDouble("amttote");
			}
			
			if(list.get(i).get("amounttote")!=null){
				amounttote+=list.get(i).getDouble("amounttote");
			}
			
			if(list.get(i).get("income")!=null){
				income+=list.get(i).getDouble("income");
			}
			
			
			
		}
	
		Map map = new HashMap<String,Object>();
		map.put("list",list);
		
		map.put("tote", tote);
		map.put("amttote", amttote);
		map.put("amounttote", amounttote);
		map.put("income", income);
	
		renderJson(map);	
		
	}
	
}
