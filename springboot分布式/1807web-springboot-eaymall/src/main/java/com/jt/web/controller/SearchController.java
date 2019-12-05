package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jt.web.pojo.Product;
import com.jt.web.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searcher;
	//搜索提供的controller
	@RequestMapping("search")
	public String search(@RequestParam(defaultValue="1")Integer page,
			String q,Model model){
		List<Product> pList=searcher.search(page,q);//写死了查询productName
		model.addAttribute("products", pList);
		model.addAttribute("query", q);
		return "search";
	}
}
