package com.jt.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;


@Controller
public class CartContrller{
	
	
	@Autowired
	private CartService cartService;
	@RequestMapping("cart/mycart")
	public String showMyCart(HttpServletRequest request,Model model) throws Exception{
		//String userId=(String)session.getAttribute("userId");
		String userId=(String)request.getAttribute("userId");
		List<Cart> cartList=cartService.queryMycart(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	@RequestMapping("cart/addCart/{productId}/{num}")
	public String addCart(@PathVariable String productId,
			@PathVariable Integer num,HttpServletRequest request) throws Exception{
		//String userId=(String)session.getAttribute("userId");
		String userId=(String)request.getAttribute("userId");
		int success=cartService.addCart(userId,productId,num);
		if(success==1){
			return "redirect:/cart/mycart";
		}else{
			return "index";
		}}
	
	
	@RequestMapping("cart/editCart/{productId}/{num}")
	public String updateCartNum(@PathVariable String productId,
			@PathVariable Integer num,HttpServletRequest request) throws Exception{

		//String userId=(String)session.getAttribute("userId");
		String userId=(String)request.getAttribute("userId");
		cartService.updateCartNum(userId,productId,num);
		return "redirect:/cart/mycart";
	}
}









