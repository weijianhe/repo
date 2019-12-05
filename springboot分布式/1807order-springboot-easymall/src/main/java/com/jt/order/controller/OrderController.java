package com.jt.order.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.util.ObjectUtil;
import com.jt.common.util.UUIDUtil;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.pojo.Order;

@RestController
public class OrderController {
/*
 * 	接口文件
		新增订单
		请求url:http://order.jt.com/order/save
		请求参数:String orderJson
		请求方式:post
		返回结果:空*/
	@Autowired
	private OrderMapper orderMapper;
	@RequestMapping("order/save")
	public void saveOrder(@RequestBody String orderJson) throws Exception{
		//转化对象
		//paystate 0 orderid uuid orderTime
		
		Order order=ObjectUtil.mapper.readValue(orderJson, Order.class);
		order.setOrderId(UUIDUtil.getUUID());
		order.setOrderTime(new Date());
		order.setOrderPaystate(0);
		orderMapper.saveOrder(order);
	}
	@RequestMapping("order/list/{userId}")
	public List<Order> queryList(@PathVariable String userId){
		return orderMapper.queryMyorder(userId);
	}
	@RequestMapping("order/delete/{orderId}")
	public void deleteOrder(@PathVariable String orderId){
		orderMapper.deleteOrder(orderId);
	}
		/**
	查询订单列表
		请求url:http://order.jt.com/order/list/{userId}
		请求方式:get
		请求参数:路径传参 String userId
		返回数据:List<Order>
	
	删除订单
		请求url:http://order.jt.com/order/delete/{orderId}
		请求参数:路径String orderId
		请求方式:get
返回数据:空
 */
}
