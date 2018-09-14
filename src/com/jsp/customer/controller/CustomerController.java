package com.jsp.customer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.tool.MD5Utils;
import com.vo.Customer;
import com.vo.CustomerList;

public class CustomerController extends Controller {
	public void index(){
		//Person per = getSessionAttr("session");		
		renderJsp("/index_new/customer/login.jsp");
	}

	public void SaveMsg(){
		System.out.println("----------123-------------");
		Customer customer= new Customer();			
		String password ="";		
		try {
			password = MD5Utils.createMD5(getPara("password"));
			customer.set("password",password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String username= getPara("username");
		String realname = getPara("realname");
		String cardid = getPara("cardid");
		String address = getPara("address");
		String telno = getPara("telno");
		//Date date = new Date();
		System.out.println("22222222=:"+password+"----"+username+"------"+realname+"----"+cardid+"--------"+address+"--------"+telno);
		customer.set("username", username);
		customer.set("realname",realname);
		customer.set("cardid",cardid);		
		customer.set("address",address);
		customer.set("telno",telno);
		customer.set("time", new Date());
		customer.set("ifrock", 1);			
		if(customer.save()){			
			//renderJson("{\"info\":\"注册成功！\",\"status\":\"y\"}");
			renderJsp("/index_new/customer/login.jsp");
		}else{
			//System.out.println("222222222222222222222222222222=");
			renderJson("{\"info\":\"注册失败！\",\"status\":\"n\"}");
		}
	}
	
	public void SaveComplaint(){
		System.out.println("投诉资料保存");
		CustomerList customerlist= new CustomerList();		
		
		int user_id= getParaToInt("user_id");
		String orderid = getPara("orderid");
		String price = getPara("price");
		String cardid = getPara("cardid");
		String realname = getPara("realname");
		String img_cardid = getPara("pictureSrc");
		String img_orderid = getPara("pic_charge");
		String title = getPara("title");
		String content = getPara("content");
		String result_user = getPara("result_user");		
		Date record_date = new Date();
		
		customerlist.set("user_id", user_id);
		customerlist.set("orderid", orderid);
		customerlist.set("price", new BigDecimal(price));
		customerlist.set("realname",realname);
		customerlist.set("cardid",cardid);		
		customerlist.set("img_cardid",img_cardid);
		customerlist.set("img_orderid",img_orderid);
		customerlist.set("record_date", record_date);
		customerlist.set("title", title);
		customerlist.set("result_user", result_user);
		customerlist.set("content", content);
		customerlist.set("status_customer", "0");
		System.out.println("22222222=:"+user_id+"----"+orderid+"------"+cardid+"----"+title+"--------"+result_user+"--------"+price);
		if(customerlist.save()){			
			//renderJson("{\"info\":\"注册成功！\",\"status\":\"y\"}");
			renderJsp("/index_new/customer/login.jsp");
		}else{			
			renderJsp("/index_new/customer/index.jsp");
			renderJson("{\"info\":\"注册失败！\",\"status\":\"n\"}");
		}
	}
	
	public void ComplaintList(){
		System.out.println("开始读取数据");
		
		int pageNum = getParaToInt("pageNum");
		int numPerPage = getParaToInt("numPerPage");
		//System.out.println("pageNum、numPerPage："+pageNum+"----"+numPerPage);
		String starttime = "";
		String endtime = "";
		String id = "";
		int user_id=getParaToInt("user_id");
		if (!getPara("starttime").equals("")) {
			starttime = getPara("starttime");
		}
		if (!getPara("endtime").equals("")) {
			endtime = getPara("endtime");
		}
		if (!getPara("id").equals("")) {
			id = getPara("id");
		}		
		setAttr("starttime", starttime);
		setAttr("endtime", endtime);
		setAttr("id", id);
		Customer cus = Customer.dao.findById(user_id);		
		int ifrock=cus.getInt("ifrock");
		Page<CustomerList> orderpage = getCustomer(pageNum,numPerPage, starttime, endtime, id, user_id,ifrock);
		setAttr("pageNum", orderpage.getPageNumber());
		setAttr("numPerPage", orderpage.getPageSize());
		setAttr("totalCount", orderpage.getTotalRow());
		setAttr("pageNumShown", orderpage.getTotalPage());
		setAttr("perlist", orderpage.getList());
		renderJsp("/index_new/customer/complaint_list.jsp");
	}
	
	public Page<CustomerList> getCustomer(int pageNumber, int pageSize, String starttime, String endtime, String id,int user_id,int ifrock){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("SELECT p.*");
		frosql.append(" from customer_list p");			
		if(ifrock==2){
			frosql.append(" where 1=1");
		}else{			
			frosql.append(" WHERE p.user_id="+user_id+"");
		}
		if(!id.equals("")){
			frosql.append(" and p.id="+id);
		}		
		if(!starttime.equals("")&&!endtime.equals("")){
			frosql.append(" and p.time >= '"+starttime+"' and p.time < '"+endtime+"'");
		}
		frosql.append(" ORDER BY id DESC");
		Page<CustomerList> orderPage = CustomerList.dao.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
	
	public void showCustomer(){
		int id=getParaToInt("id");
		CustomerList customerlist = CustomerList.dao.findById(id);
		setAttr("id", id);
		setAttr("user_id", customerlist.getInt("user_id"));
		setAttr("orderid", customerlist.get("orderid"));
		setAttr("price", customerlist.get("price"));
		setAttr("cardid", customerlist.get("cardid"));
		setAttr("realname", customerlist.get("realname"));
		setAttr("img_cardid", customerlist.get("img_cardid"));
		setAttr("img_orderid", customerlist.get("img_orderid"));
		setAttr("title", customerlist.get("title"));
		setAttr("content", customerlist.get("content"));
		setAttr("result_user", customerlist.get("result_user"));
		setAttr("status_customer", customerlist.get("status_customer"));
		setAttr("status_manager", customerlist.get("status_manager"));
		setAttr("record_date", customerlist.get("record_date"));
		setAttr("result_date", customerlist.get("result_date"));
		renderJsp("/index_new/customer/showMsg.jsp");
	}
	
	
	public void showCustomerState(){
		int id=getParaToInt("id");
		CustomerList customerlist = CustomerList.dao.findById(id);
		setAttr("id", id);
		setAttr("user_id", customerlist.getInt("user_id"));
		setAttr("orderid", customerlist.get("orderid"));
		setAttr("price", customerlist.get("price"));
		setAttr("cardid", customerlist.get("cardid"));
		setAttr("realname", customerlist.get("realname"));
		setAttr("img_cardid", customerlist.get("img_cardid"));
		setAttr("img_orderid", customerlist.get("img_orderid"));
		setAttr("title", customerlist.get("title"));
		setAttr("content", customerlist.get("content"));
		setAttr("result_user", customerlist.get("result_user"));
		setAttr("result_sys", customerlist.get("result_sys"));
		setAttr("status_customer", customerlist.get("status_customer"));
		setAttr("status_manager", customerlist.get("status_manager"));
		setAttr("record_date", customerlist.get("record_date"));
		setAttr("result_date", customerlist.get("result_date"));
		renderJsp("/index_new/customer/showState.jsp");
	}
	
	public void UpdateComplaint(){
		int id=getParaToInt("id");
		String result_sys=getPara("result_sys");
		if(result_sys==null){
			result_sys = "";			
		}
		Date date = new Date();
		CustomerList cus = CustomerList.dao.findById(id);
		cus.set("result_sys", result_sys);
		cus.set("status_customer", "1");
		cus.set("result_date", date);
		if(cus.update()){
			setAttr("id", id);
			setAttr("user_id", cus.getInt("user_id"));
			setAttr("orderid", cus.get("orderid"));
			setAttr("cardid", cus.get("cardid"));
			setAttr("realname", cus.get("realname"));
			setAttr("img_cardid", cus.get("img_cardid"));
			setAttr("img_orderid", cus.get("img_orderid"));
			setAttr("title", cus.get("title"));
			setAttr("content", cus.get("content"));
			setAttr("result_user", cus.get("result_user"));
			setAttr("result_sys", cus.get("result_sys"));
			setAttr("status_customer", cus.get("status_customer"));
			setAttr("status_manager", cus.get("status_manager"));
			setAttr("record_date", cus.get("record_date"));
			setAttr("result_date", cus.get("result_date"));
			renderJsp("/index_new/customer/showState.jsp");
		}else{
			renderJsp("/index_new/customer/complaint.jsp");
		}
	}
	
	public void login() {
		boolean boo = false;
		String username = getPara("username");
		String password = getPara("password");
		
		try {
			password = MD5Utils.createMD5(getPara("password"));
			List<Customer> customer = Customer.dao.find("select p.username from customer p where p.username = '"+username+"' and p.password = '"+password+"'");
			if(customer.size()>0){
				boo = true;
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (boo) {
			List<Customer> cus = Customer.dao.find("select p.id,p.realname, p.cardid, p.address,p.telno,p.ifrock from customer p where p.username = '"+username+"'");
			Customer custom = cus.get(0);
			setSessionAttr("session", custom);			
			//renderJson("{\"info\":\"登陆成功！\",\"status\":\"y\"}");
			renderJsp("/index_new/customer/complaint.jsp");
		} else {
			renderJson("{\"info\":\"登陆失败！\",\"status\":\"n\"}");
		}
	}
	
	public void quit() {		
		removeSessionAttr("custom");
		removeSessionAttr("session");
		redirect("/index_new/customer/login.jsp");
	}
	
	public void imageUpload() {
		String flag = getPara("flag");
		if(flag.equals("1")){
			UploadFile uploadFile = getFile("imgFile1", PathKit.getWebRootPath()+ "/temp", 1 * 1024 * 1024, "utf-8"); // 最大上传1M的图片
		}else{
			UploadFile uploadFile = getFile("imgFile", PathKit.getWebRootPath()+ "/temp", 1 * 1024 * 1024, "utf-8"); // 最大上传1M的图片
		}
		
		// 异步上传时，无法通过uploadFile.getFileName()获取文件名
		String fileName = getPara("fileName");
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1); // 去掉路径
		System.out.println("--1111---"+fileName);
		// 异步上传时，无法通过File source = uploadFile.getFile();获取文件
		File source = new File(PathKit.getWebRootPath() + "/temp/" + fileName); // 获取临时文件对象

		String extension = fileName.substring(fileName.lastIndexOf("."));
		String savePath = PathKit.getWebRootPath() + "\\upload\\images\\"+ getCurrentDate();
		//System.out.println("--222222---"+extension);
		//System.out.println("---333333333--"+savePath);
		JSONObject json = new JSONObject();

		if (".png".equals(extension) || ".jpg".equals(extension)
				|| ".gif".equals(extension) || "jpeg".equals(extension)
				|| "bmp".equals(extension)) {
			fileName = getCurrentTime() + extension;
			//System.out.println("---444444444--"+fileName);
			try {
				FileInputStream fis = new FileInputStream(source);

				File targetDir = new File(savePath);
				if (!targetDir.exists()) {
					targetDir.mkdirs();
				}

				File target = new File(targetDir, fileName);
				if (!target.exists()) {
					target.createNewFile();
				}

				FileOutputStream fos = new FileOutputStream(target);
				byte[] bts = new byte[1024 * 20];
				while (fis.read(bts, 0, 1024 * 20) != -1) {
					fos.write(bts, 0, 1024 * 20);
				}

				fos.close();
				fis.close();
				json.put("error", 0);
				json.put("src", "/upload/images/" + getCurrentDate()
						+ "/" + fileName); // 相对地址，显示图片用
				source.delete();

			} catch (FileNotFoundException e) {
				json.put("error", 1);
				json.put("message", "上传出现错误，请稍后再上传");
			} catch (IOException e) {
				json.put("error", 1);
				json.put("message", "文件写入服务器出现错误，请稍后再上传");
			}
		} else {
			source.delete();
			json.put("error", 1);
			json.put("message", "只允许上传png,jpg,jpeg,gif,bmp类型的图片文件");
		}
		System.out.println(json.toJSONString());
		renderJson(json.toJSONString());
	}
	
	public static String getCurrentDate() {
		String defaultFormat = "yyyy-MM-dd HH:mm:ss";
		String format = "yyyy-MM-dd";
		Date date = new Date();
		date.setTime(System.currentTimeMillis());
		if (format == null || "".equals(format.trim())) {
			format = defaultFormat;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String getCurrentTime() {
		String defaultFormat = "yyyy-MM-dd HH:mm:ss";
		String format = "yyyyMMddHHmmss";
		Date date = new Date();
		date.setTime(System.currentTimeMillis());
		if (format == null || "".equals(format.trim())) {
			format = defaultFormat;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public void valiSms(){
		String keys = getPara("validate_id");
		String key = getSessionAttr("sms");
		if(keys.equals(key)){
			renderText("y");
		}else {
			renderText("验证码错误");
		}
	}
	
}
