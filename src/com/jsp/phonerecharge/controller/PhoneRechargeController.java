package com.jsp.phonerecharge.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import com.jfinal.core.Controller;
import com.jsp.phonerecharge.service.PhoneRechargeTimer;
import com.pay.cardyee.server.util.EncodeUtils;
import com.pay.yeepay.server.Configuration;
import com.tool.CryptTool;
import com.vo.Gateway;
import com.vo.Onlineorder;

public class PhoneRechargeController extends Controller {

	public void node(){
		Gateway gateway = Gateway.dao.findById(11);
		String userid = gateway.getStr("gateway_merid");
		String KeyStr = gateway.getStr("gateway_key");
		String path = getRequest().getContextPath();
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+path;
		String cardnum = getPara("cardnum");
		String game_userid = getPara("tbtel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHMMSSmmm");
		String sporder_id = "ON" + sdf.format(new Date()) + CryptTool.getPasswordOfNumber(4) ;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String sporder_time = sdf1.format(new Date());
		String ret_url = basePath + "/phonerequest";
		String userpws = Configuration.getInstance().getValue("userpws");
		String md5_str = userid+userpws+"140101"+cardnum+sporder_id+sporder_time+ game_userid + KeyStr;
		String md5 = EncodeUtils.testDigest(md5_str);
		Onlineorder onlineorder = new Onlineorder();
		onlineorder.set("sporder_id", sporder_id);
		onlineorder.set("cardnum", cardnum);
		onlineorder.set("sporder_time", sporder_time);
		onlineorder.set("game_userid", game_userid);
		onlineorder.set("md5_str", md5);
		onlineorder.set("ret_code", "2");
		onlineorder.set("err_msg", "订单处理中");
		onlineorder.save();
		setAttr("userid", userid);
		setAttr("userpws", userpws);
		setAttr("cardnum", cardnum);
		setAttr("sporder_id", sporder_id);
		setAttr("sporder_time", sporder_time);
		setAttr("game_userid", game_userid);
		setAttr("md5_str", md5);
		setAttr("ret_url", ret_url);
		Timer timer = new Timer();
		timer.schedule(new PhoneRechargeTimer(sporder_id, timer), 15 * 60 * 1000);
		renderJsp("/WEB-INF/pay/phoneoufei/send.jsp");
	}
	
	public void phonerequest(){
		renderJsp("/WEB-INF/pay/phoneoufei/request.jsp");
	}
}
