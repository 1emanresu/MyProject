package com.admin.cluster.controller;

import com.admin.cluster.service.AdClusterService;
import com.interceptor.AdLoginSeInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.tool.StringUtil;
@Before(AdLoginSeInterceptor.class)
public class AdClusterController extends Controller{
	public void index(){
		setAttr("wxgateway",AdClusterService.service.findWXGateways());
		renderJsp("/WEB-INF/admin/jsp/cluster/cluster.jsp");
	}
	
	@Clear
	public void saveClusterData(){
		try {
			String cluster0 = getPara("cluster0");
			String cluster1 = getPara("cluster1");
			String cluster2 = getPara("cluster2");
			String gatewayid0 = getPara("gatewayid0");
			String gatewayid1 = getPara("gatewayid1");
			String gatewayid2 = getPara("gatewayid2");
			String money0 = getPara("money0");
			String money1 = getPara("money1");
			String money2 = getPara("money2");
			String cluster_type = getPara("cluster_type");
			boolean saveResult = false;
			//三条通道一起达到3000
			if(StringUtil.isNotEmpty(cluster0) && StringUtil.isNotEmpty(cluster1) && StringUtil.isNotEmpty(cluster2)){
				if(cluster0.equals(cluster1) && cluster1.equals(cluster2)){
					AdClusterService.service.saveClusterGateway(cluster0, gatewayid0, money0,cluster_type);
					AdClusterService.service.saveClusterGateway(cluster0, gatewayid1, money1,cluster_type);
					AdClusterService.service.saveClusterGateway(cluster0, gatewayid2, money2,cluster_type);
					saveResult = true;
				}else{
					renderJson("{\"message\":\"对不起，您不能同时保存两个不同的集群\"}");
				}
			}else if(StringUtil.isNotEmpty(cluster0) && StringUtil.isNotEmpty(cluster1)){
				if(cluster0.equals(cluster1)){
					AdClusterService.service.saveClusterGateway(cluster0, gatewayid0, money0,cluster_type);
					AdClusterService.service.saveClusterGateway(cluster0, gatewayid1, money1,cluster_type);
					saveResult = true;
				}else{
					renderJson("{\"message\":\"对不起，您不能同时保存两个不同的集群\"}");
				}
			}else if(StringUtil.isNotEmpty(cluster0)){
				AdClusterService.service.saveClusterGateway(cluster0, gatewayid0, money0,cluster_type);
				saveResult = true;
			}
			if(saveResult)renderJson("{\"message\":\"恭喜您！保存成功\"}");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
