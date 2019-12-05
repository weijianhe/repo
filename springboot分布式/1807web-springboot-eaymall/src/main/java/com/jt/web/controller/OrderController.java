package com.jt.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Order;
import com.jt.web.service.CartService;
import com.jt.web.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	private CartService cartService;
	
	@RequestMapping("order/order-cart")
	public String collectOrderInfo(HttpServletRequest request,
			Model model) throws Exception{
		//String userId=(String)session.getAttribute("userId");
		String userId=(String)request.getAttribute("userId");
		List<Cart> cartList = cartService.queryMycart(userId);
		model.addAttribute("cartList",cartList);
		return "order_add";
	}
	@Autowired
	private OrderService orderService;
	@RequestMapping("order/addOrder")
	public String saveOrder(Order order,HttpServletRequest request) throws Exception{
		order.setUserId(request.getAttribute("userId")+"");
		orderService.saveOrder(order);
		return "index";
	}
	
	
	@RequestMapping("order/list")
	public String queryMyorder(HttpServletRequest request,Model model) throws Exception{
		//String userId=(String)session.getAttribute("userId");
		String userId=(String)request.getAttribute("userId");
		List<Order> orderList=orderService.queryMyorder(userId);
		model.addAttribute("orderList", orderList);
		return "order_list";
	}
	
	@RequestMapping("order/deleteOrder/{orderId}")
	public String deleteOrder(@PathVariable String orderId) throws Exception{
		orderService.deleteOrder(orderId);
		return "redirect:/order/list";
	}
}













