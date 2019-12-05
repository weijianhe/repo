package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.Page;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Product;
import com.jt.web.service.ProductService;


@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@RequestMapping("/product/page")
	public String queryProducts(
			Integer currentPage,Integer rows,Model model) throws Exception{
		List<Product> pList=productService.
			queryProducts(currentPage,rows);
		Integer totalPage=productService.queryTotalPage(rows);
		Page page=new Page();
		page.setProducts(pList);
		page.setCurrentPage(currentPage);
		page.setTotalPage(totalPage);
		model.addAttribute("page", page);
		return "product_list";
	}
	
	@RequestMapping("/product/findProductById/{id}")
	public String findProductById(@PathVariable String id,
			Model model) throws Exception{
		Product product=productService.findProductById(id);
		model.addAttribute("product",product);
		return "product_info";
	}
	

	@RequestMapping("/product/save")
	@ResponseBody
	public SysResult saveProduct(Product product){
		SysResult result=new SysResult();
		try{
		int success=productService.saveProduct(product);
		if(success==1){
			
			result.setStatus(200);
			result.setMsg("成功");
			return result;
		}else{
			result.setStatus(201);
			result.setMsg("失败");
			return result;}
		}catch(Exception e){
			result.setStatus(201);
			result.setMsg(e.getMessage());
			return result;
		}
	}
	
	@RequestMapping("/product/query")
	@ResponseBody
	public EasyUIResult queryManageProducts(
			Integer page,Integer rows) throws Exception{
	List<Product> pList=productService.queryProducts
			(page, rows);
	int total=productService.queryTotal();
	
	EasyUIResult result=new EasyUIResult();
	result.setTotal(total);
	result.setRows(pList);
	return result;
	}
	
	
	@RequestMapping("/product/update")
	@ResponseBody
	public SysResult updateProduct(Product product) throws Exception{
		SysResult result=new SysResult();
		
		int success=productService.updateProduct(product);
		if(success==1){
			result.setStatus(200);
			result.setMsg("ok");
			return result;
		}else{
			result.setStatus(201);
			return result;
		}
	}
	
}




