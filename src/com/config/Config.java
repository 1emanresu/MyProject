package com.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import redis.clients.util.SafeEncoder;

import com.admin.caifutong.controller.AdCaifutongController;
import com.admin.card.controller.AdCardController;
import com.admin.circlip.controller.AdCirclipController;
import com.admin.cluster.controller.AdClusterController;
import com.admin.customerlog.controller.AdCustomerLogController;
import com.admin.dopersons.controller.DopresonsController;
import com.admin.dynamic.controller.AdDynamicController;
import com.admin.employee.controller.AdEmployeeController;
import com.admin.failinfo.controller.FailinfoController;
import com.admin.gateway.controller.AdGatewayController;
import com.admin.gatewaylog.controller.AdGatewayLogController;
import com.admin.index.controller.AdIndexController;
import com.admin.login.controller.AdLoginController;
import com.admin.net.controller.AdNetController;
import com.admin.orderrepair.controller.AdOrderRepairController;
import com.admin.person.controller.AdPersonController;
import com.admin.phonerecharge.controller.AdPhoneRechargeController;
import com.admin.power.controller.AdPowerController;
import com.admin.recharge.controller.AdRechargeController;
import com.admin.refund.controller.AdRefundController;
import com.admin.statement.controller.AdStatementController;
import com.admin.statistics.controller.AdStatisticsController;
import com.admin.system.controller.AdSystemController;
import com.admin.transfer.controller.AdTransferController;
import com.admin.userrequestlog.controller.AdUserRequestLogController;
import com.admin.withdraw.controller.AdWithdrawController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.plugin.redis.serializer.ISerializer;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.jsp.index;
import com.jsp.dynamic.controller.DynamicController;
import com.jsp.exchange.controller.ExchangeController;
import com.jsp.indexcard.controller.IndexCardController;
import com.jsp.login.controller.LoginController;
import com.jsp.payment.controller.PaymentController;
import com.jsp.phonerecharge.controller.PhoneRechargeController;
import com.jsp.register.controller.Register;
import com.jsp.safetysecurity.controller.SafetySecurityController;
import com.jsp.settlement.controller.SettlementController;
import com.jsp.statement.controller.StatementController;
import com.jsp.transfer.controller.TransferController;
import com.jsp.user.controller.UserController;
import com.manager.rolemanager.controller.RoleController;
import com.manager.usermanager.controller.ManagerController;
import com.pay.hspay;
import com.pay.jyypay;
import com.pay.alipay.client.controller.Alipay;
import com.pay.allinpay.client.controller.Allinpay;
import com.pay.baopay.client.controller.Baopay;
import com.pay.caifutong.client.controller.CaiFuTong;
import com.pay.cardyee.client.controller.Cardyee;
import com.pay.cib.client.controller.Cib;
import com.pay.dinpay.client.controller.Dinpay;
import com.pay.ease.client.controller.Ease;
import com.pay.ecpss.client.controller.Ecpss;
import com.pay.fastmoney.client.controller.FastMoney;
import com.pay.ips.client.controller.IpsController;
import com.pay.w95epay.client.controller.W95epay;
import com.pay.wapalipay.client.controller.WapAlipay;
import com.pay.yeepay.client.controller.Yeepay;
import com.pay.yeepay.server.Configuration;
import com.vo.Addedamount;
import com.vo.Asynchronous;
import com.vo.Balance;
import com.vo.Caifutong;
import com.vo.CaifutongOrder;
import com.vo.CardCode;
import com.vo.CardOrder;
import com.vo.Circlip;
import com.vo.ClusterGateway;
import com.vo.CustomerBalanceLog;
import com.vo.DayReport;
import com.vo.Dynamic;
import com.vo.Employee_Person;
import com.vo.Employees;
import com.vo.Gateway;
import com.vo.GatewayLog;
import com.vo.GatewayRate;
import com.vo.Logrecord;
import com.vo.Navigation;
import com.vo.Onlineorder;
import com.vo.Order;
import com.vo.Participate;
import com.vo.Payment;
import com.vo.Person;
import com.vo.PersonGateway;
import com.vo.Power;
import com.vo.Powerid_Navid;
import com.vo.Rate;
import com.vo.Recharge;
import com.vo.Refund;
import com.vo.Role;
import com.vo.Roleperm;
import com.vo.SettlementAccount;
import com.vo.SingleCard;
import com.vo.System_interface;
import com.vo.System_qq;
import com.vo.Systemss;
import com.vo.Transfer;
import com.vo.UserRequestLog;
import com.vo.Userperm;
import com.vo.UsreRole;

public class Config extends JFinalConfig {
	private Timer timer = null;

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		// boolean boo = new Backstage().getBackstage();
		// me.setDevMode(true);
		boolean boo = true;
		if (boo) {
			loadPropertyFile("../a_little_config.txt");
			System.out.println("授权通过");
		} else {
			System.out.println("授权失败");
		}
		me.setViewType(ViewType.JSP);
	}

	@Override
	public void configRoute(Routes me) {
		String adminurl = Configuration.getInstance().getValue("adminurl");

		String neturl = Configuration.getInstance().getValue("neturl");
		String cardurl = Configuration.getInstance().getValue("cardurl");
		me.setBaseViewPath("/");
		// TODO Auto-generated method stub
		me.add("/", index.class);
		me.add("/register", Register.class);
		// me.add("/customer", CustomerController.class);
		me.add("/user", UserController.class);
		me.add("/login", LoginController.class);
		me.add("/dynamic", DynamicController.class);
		me.add("/safetysecurity", SafetySecurityController.class);
		me.add("/indexcard", IndexCardController.class);
		me.add("/exchange", ExchangeController.class);
		me.add(neturl, hspay.class);
		me.add("/pay", jyypay.class);
		me.add("/yeepay", Yeepay.class);
		me.add("/fastmoney", FastMoney.class);
		me.add("/baopay", Baopay.class);
		me.add("/caifutong", CaiFuTong.class);
		me.add("/alipay", Alipay.class);
		me.add("/dinpay", Dinpay.class);
		me.add("/ease", Ease.class);
		me.add("/allinpay", Allinpay.class);
		me.add("/wapalipay", WapAlipay.class);
		me.add("/ecpss", Ecpss.class);
		me.add("/ips", IpsController.class);
		me.add("/w95epay", W95epay.class);
		me.add(cardurl, Cardyee.class);
		me.add("/statement", StatementController.class);
		me.add("/payment", PaymentController.class);
		me.add("/settlement", SettlementController.class);
		me.add("/phonerecharge", PhoneRechargeController.class);
		me.add("/transfer", TransferController.class);
		me.add(adminurl, AdIndexController.class);
		me.add(adminurl + "/login", AdLoginController.class);
		me.add(adminurl + "/net", AdNetController.class);
		me.add(adminurl + "/card", AdCardController.class);
		me.add(adminurl + "/dynamic", AdDynamicController.class);
		me.add(adminurl + "/person", AdPersonController.class);
		me.add(adminurl + "/employee", AdEmployeeController.class);
		me.add(adminurl + "/power", AdPowerController.class);
		me.add(adminurl + "/refund", AdRefundController.class);
		me.add(adminurl + "/statistics", AdStatisticsController.class);
		me.add(adminurl + "/recharge", AdRechargeController.class);
		me.add(adminurl + "/circlip", AdCirclipController.class);
		me.add(adminurl + "/gateway", AdGatewayController.class);
		me.add(adminurl + "/caifutong", AdCaifutongController.class);
		me.add(adminurl + "/system", AdSystemController.class);

		me.add(adminurl + "/phonerecharge", AdPhoneRechargeController.class);
		me.add(adminurl + "/transfer", AdTransferController.class);
		me.add("/cibpay", Cib.class);
		me.add(adminurl + "/failinfo", FailinfoController.class);
		me.add(adminurl + "/withdraw", AdWithdrawController.class);
		me.add(adminurl + "/dopersons", DopresonsController.class);
		me.add(adminurl+"/rolemanager", RoleController.class);
		me.add(adminurl+"/usermanager", ManagerController.class);
		me.add(adminurl+"/statement", AdStatementController.class);
		me.add(adminurl+"/cluster", AdClusterController.class);
		me.add(adminurl+"/orderrepair", AdOrderRepairController.class);
		me.add(adminurl+"/userRequestLog", AdUserRequestLogController.class);
		me.add(adminurl+"/customerLog", AdCustomerLogController.class);
		me.add(adminurl+"/gatewayLog", AdGatewayLogController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		C3p0Plugin cp = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		System.out.println(getProperty("jdbcUrl") + ";" + getProperty("user") + ";" + getProperty("password"));
		me.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp);
		
		//缓存user模块 到 redis
        RedisPlugin redisPlugin=new RedisPlugin("netpayCache","192.168.31.123","wxredis123");
        redisPlugin.setSerializer(new ISerializer() {
            @Override
            public byte[] keyToBytes(String key) {
                return SafeEncoder.encode(key);
            }
            @Override
            public String keyFromBytes(byte[] bytes) {
                return SafeEncoder.encode(bytes);
            }
            @Override
            public byte[] fieldToBytes(Object field) {
                return valueToBytes(field);
            }
            @Override
            public Object fieldFromBytes(byte[] bytes) {
                return valueFromBytes(bytes);
            }
            @Override			
            public byte[] valueToBytes(Object value) {
                return SafeEncoder.encode(value.toString());
            }
            @Override
            public Object valueFromBytes(byte[] bytes) {
                if(bytes == null || bytes.length == 0)
                    return null;
                return SafeEncoder.encode(bytes);
            }
        });
        me.add(redisPlugin);
		
		arp.addMapping("person", "id", Person.class);
		// arp.addMapping("customer", "id", Customer.class);
		// arp.addMapping("customer_list", "id", CustomerList.class);
		arp.addMapping("payment", "id", Payment.class);
		arp.addMapping("balance", "id", Balance.class);
		arp.addMapping("logrecord", "logid", Logrecord.class);
		arp.addMapping("participate", "codeid", Participate.class);
		arp.addMapping("orders", "orderid", Order.class);
		arp.addMapping("cardorder", "orderid", CardOrder.class);
		arp.addMapping("singlecard", "orderno", SingleCard.class);
		arp.addMapping("cardcode", "codeid", CardCode.class);
		arp.addMapping("employee", "employeeid", Employees.class);
		arp.addMapping("power", "powerid", Power.class);
		arp.addMapping("navigation", "navid", Navigation.class);
		arp.addMapping("powerid_navid", Powerid_Navid.class);
		arp.addMapping("dynamic", "dynamicid", Dynamic.class);
		arp.addMapping("rate", "id", Rate.class);
		arp.addMapping("recharge", "rechargeid", Recharge.class);
		arp.addMapping("circlip", "circlipid", Circlip.class);
		arp.addMapping("settlement_account","primary_id", SettlementAccount.class);
		arp.addMapping("refund", "refund_id", Refund.class);
		arp.addMapping("gateway", "gateway_id", Gateway.class);
		arp.addMapping("person_gateway", "id", PersonGateway.class);
		arp.addMapping("addedamount", "addedamount_id", Addedamount.class);
		arp.addMapping("employee_person", "id", Employee_Person.class);
		arp.addMapping("caifutong", "id", Caifutong.class);
		arp.addMapping("caifutongorder", "sp_billno", CaifutongOrder.class);
		arp.addMapping("system", "system_id", Systemss.class);
		arp.addMapping("system_interface", "system_interface_id", System_interface.class);
		arp.addMapping("system_qq", "qq_id", System_qq.class);
		arp.addMapping("onlineorder", "sporder_id", Onlineorder.class);
		arp.addMapping("transfer", "transfer_id", Transfer.class);
		arp.addMapping("asynchronous", "orderid", Asynchronous.class);
		arp.addMapping("navigation", "navid", Navigation.class);
		arp.addMapping("role", "role_id", Role.class);
		arp.addMapping("role_user", "role_id", UsreRole.class);
		arp.addMapping("role_perm", "role_id", Roleperm.class);
		arp.addMapping("user_perm", "employee_id", Userperm.class);
		arp.addMapping("day_report", "id",DayReport.class);
		arp.addMapping("cluster_gateway","id",ClusterGateway.class);
		arp.addMapping("user_request_log","id",UserRequestLog.class);
		arp.addMapping("customer_balance_log","id",CustomerBalanceLog.class);
		arp.addMapping("gateway_rate","gateway_id",GatewayRate.class);
		arp.addMapping("gateway_log","id",GatewayLog.class);
	}

	@Override
	public void configInterceptor(Interceptors me) {

	}

	@Override
	public void configHandler(Handlers me) {
		// me.add(new ContextPathHandler("contextPath")); // 设置上下文路径
		// TODO Auto-generated method stub
		// me.add(new UrlConvertHandler());
	}

	/**
	 * Call back after JFinal start
	 */
	public void afterJFinalStart() {
		// 一天的毫秒数
		final long daySpan = 24 * 60 * 60 * 1000;

		// 规定的每天时间00:00:00运行
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd '00:30:00'");
		// 首次运行时间
		Date startTime = null;
		Date asynTime = null;
		// Date alarmTime = null;
		try {
			startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
			asynTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
			// alarmTime = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss").parse(sdf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ThreadsTimer threatimer = new ThreadsTimer();
		AsynchronousTimer asyntimer = new AsynchronousTimer();
		// AlarmThreadsTimer alarm = new AlarmThreadsTimer();
		timer = new Timer();
		System.out.println("计时器开启");
		long delay = Power.dao.findById(1).getLong("MySetTime");
		// long delay2 = Power.dao.findById(1).getLong("AlarmTime");
		// 如果今天的已经过了 首次运行时间就改为明天
		if (System.currentTimeMillis() > startTime.getTime()) {
			System.out.println("明天运行了===");
			startTime = new Date(startTime.getTime() + daySpan);
		}
		// if (System.currentTimeMillis() > asynTime.getTime()){
		// asynTime = new Date(asynTime.getTime() + delay);
		// }
		// if (System.currentTimeMillis() > alarmTime.getTime()){
		// alarmTime = new Date(alarmTime.getTime() + delay2);
		// }

		timer.scheduleAtFixedRate(threatimer, startTime, daySpan);
//		timer.scheduleAtFixedRate(asyntimer, asynTime, delay);
		// timer.scheduleAtFixedRate(alarm, alarmTime, delay2);
		// new Backstage().setEmp();
	}

	/**
	 * Call back before JFinal stop
	 */
	public void beforeJFinalStop() {
		timer.cancel();
		System.out.println("计时器关闭");
		// new Backstage().exit();
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		
	}
}
