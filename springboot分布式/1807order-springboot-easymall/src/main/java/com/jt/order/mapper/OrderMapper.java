package com.jt.order.mapper;

import java.util.Date;
import java.util.List;

import com.jt.order.pojo.Order;

public interface OrderMapper {

	void saveOrder(Order order);

	List<Order> queryMyorder(String userId);

	void deleteOrder(String orderId);

	void deleteOTOrders(Date lastDay);

}
