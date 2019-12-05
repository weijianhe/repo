package com.jt.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.ObjectUtil;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Order;

@Service	
public class OrderService {
	@Autowired
	private HttpClientService client;
	public void saveOrder(Order order) throws Exception {
		String url="http://order.jt.com/order/save";
		String orderJson=ObjectUtil.mapper.writeValueAsString(order);
		client.doPostJson(url, orderJson);
	}
	public List<Order> queryMyorder(String userId) throws Exception {
		String url="http://order.jt.com/order/list/"+userId;
		String orderListJson=client.doGet(url);//[{}{}]
		List<Order> orderList=null;
		JsonNode data=ObjectUtil.mapper.readTree(orderListJson);
		if(data.isArray()&&data.size()>0){
			orderList = ObjectUtil.mapper.readValue(data.traverse(),
		    ObjectUtil.mapper.getTypeFactory().
		    constructCollectionType(List.class, Order.class));
		}
		return orderList;
	}
	public void deleteOrder(String orderId) throws Exception {
		String url="http://order.jt.com/order/delete/"+orderId;
		client.doGet(url);
	}
}
