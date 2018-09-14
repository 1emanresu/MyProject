package com.admin.cluster.dao;

import java.util.List;

import com.vo.ClusterGateway;
import com.vo.Gateway;

public class AdClusterDao {
	public List<Gateway> findWXGateways(){
		StringBuffer sql = new StringBuffer();
		sql.append("  select g.gateway_name,g.gateway_id from gateway g where wxwap = 'y' or weixin = 'y' ");
		return Gateway.dao.find(sql.toString());
	}
	
	/**
	 * @description 保存集群信息<br/>
	 * @methodName saveClusterGateway<br/>
	 * @author jack<br/>
	 * @createTime 2017年10月19日下午3:54:38<br/>
	 * @param cluster_id
	 * @param gateway_id
	 * @param amount_interval
	 * @return
	 */
	public boolean saveClusterGateway(String cluster_id,String gateway_id,String amount_interval,String cluster_type){
		ClusterGateway cluster = new ClusterGateway();
		cluster.set("cluster_id", cluster_id);
		cluster.set("gateway_id", gateway_id);
		cluster.set("amount_interval", amount_interval);
		cluster.set("cluster_type", cluster_type);
		return cluster.save();
	}
}
