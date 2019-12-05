package com.jt.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.ObjectUtil;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Product;

@Service
public class CartService {
	@Autowired
	private HttpClientService client;
	public List<Cart> queryMycart(String userId) throws Exception {
		String url="http://cart.jt.com/cart/mycart/"+userId;
		List<Cart> cList=null;
		try{
			String jsonData=client.doGet(url);//cartList的json;
			
			JsonNode data=ObjectUtil.mapper.readTree(jsonData);
			if(data.isArray()&&data.size()>0){
				cList = ObjectUtil.mapper.readValue(data.traverse(),
			    ObjectUtil.mapper.getTypeFactory().
			    constructCollectionType(List.class, Cart.class));
			}
		}catch(NullPointerException e){
			return cList;
		}
		return cList;
		
	}
	public int addCart(String userId, String productId, Integer num) throws Exception {
		//id查询商品
		String url="http://manage.jt.com/product/queryById/"+productId;
		String productJson=client.doGet(url);
		Product p=ObjectUtil.mapper.
				readValue(productJson, Product.class);
		Cart cart=new Cart();
		cart.setNum(num);
		cart.setProductId(productId);
		cart.setUserId(userId);
		cart.setProductId(p.getProductId());
		cart.setProductName(p.getProductName());
		cart.setProductPrice(p.getProductPrice());
		String cartJson=ObjectUtil.mapper.writeValueAsString(cart);
		url="http://cart.jt.com/cart/save";
		String success=client.doPostJson(url, cartJson);
		return Integer.parseInt(success);
	}
	public void updateCartNum(String userId, String productId, Integer num) throws Exception {
		String url="http://cart.jt.com/cart/updateNum/"
				+userId+"/"+productId+"/"+num;
		client.doGet(url);
	}
	
	
}
