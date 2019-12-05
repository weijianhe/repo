package com.jt.manage.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jt.common.util.ObjectUtil;
import com.jt.common.util.UUIDUtil;
import com.jt.manage.mapper.ProductMapper;
import com.jt.manage.pojo.Product;

@RestController
public class ProductController {
	@Autowired
	private ProductMapper productMapper;
	//查询分页数据
	@RequestMapping("product/list")
	public List<Product> queryList(Integer page,Integer rows){
		//判断page是否为空
		if(page==null){page=1;}
		int start=(page-1)*rows;
		return productMapper.queryProducts(start, rows);
	}
	
	@RequestMapping("product/total")
	public String queryTotal(){
		String total=productMapper.queryCount()+"";
		return total;
	}
	
	/*根据商品id查询商品
	请求url:http://manage.jt.com/product/queryById/{id}
	请求参数:String productId
	请求方式:get
	返回数据:Product的对象的json*/
	@RequestMapping("product/queryById/{id}")
	public Product queryById(@PathVariable String id){
		return productMapper.findProductById(id);}
	
	/*
	 * 新增商品
	请求url:http://manage.jt.com/product/save
	请求参数:Product 的json字符串 没有	Id
	请求方式:post
	返回数据:空
	 */
	@RequestMapping("product/save")
	public void saveProduct(@RequestBody String jsonData) throws Exception{
		//RequestBody 接收json格式字符串
		//如果是key=value的最好不要使用 name=haha&age=19&id=5
		//解析json为product对象,新增没id
		Product product=ObjectUtil.mapper.
				readValue(jsonData, Product.class);
		product.setProductId(UUIDUtil.getUUID());
		productMapper.saveProduct(product);
	}
	@RequestMapping("product/update")
	public void updateProduct(@RequestBody String jsonData) throws Exception{
		//RequestBody 接收json格式字符串
		//如果是key=value的最好不要使用 name=haha&age=19&id=5
		//解析json为product对象,更新有id
		Product product=ObjectUtil.mapper.
				readValue(jsonData, Product.class);
		productMapper.updateProduct(product);
	}
}









