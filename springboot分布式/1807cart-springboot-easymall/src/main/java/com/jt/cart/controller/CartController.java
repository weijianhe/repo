package com.jt.cart.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.common.util.ObjectUtil;

@RestController
public class CartController {
	@Autowired
	private CartMapper cartMapper;
	//查询我的购物车
	/*
	 *利用userId查询我的购物车
	请求url:http://cart.jt.com/cart/mycart/{userId}
	请求方式:get
	请求参数:路径传参 String userId
	返回数据:cartList的json字符串
	 */
	@RequestMapping("cart/mycart/{userId}")
	public List<Cart> queryMycart(@PathVariable String userId){
		return cartMapper.queryMycart(userId);
	}
	/*
	 * 	请求url:http://cart.jt.com/cart/save/{productId}/{userId}/{num}
	请求方式:post
	请求参数: cart对象的json字符串, productImage,productName,productPrice,发起http请求利用id查询product将数据封装到cart
	返回数据:1成功0失败
	 */
	@RequestMapping("cart/save")
	public String saveCart(@RequestBody String cartJson) throws Exception{
		//转化数据为对象
		Cart cart=ObjectUtil.mapper.readValue(cartJson, Cart.class);
		int success = cartMapper.addCart(cart);
		return success+"";
	}
	@RequestMapping("cart/updateNum/{userId}/{productId}/{num}")
	public void updateCart(@PathVariable String userId,
			@PathVariable String productId,
			@PathVariable Integer num){
		Cart exists=new Cart();
		exists.setNum(num);
		exists.setUserId(userId);
		exists.setProductId(productId);
		cartMapper.updateCartNum(exists);
	}
	
	
	
	
	
	
	
	
	
	
	
}
