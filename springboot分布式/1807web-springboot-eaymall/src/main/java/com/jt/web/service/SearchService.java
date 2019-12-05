package com.jt.web.service;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.web.pojo.Product;

@Service
public class SearchService {
	@Autowired
	private TransportClient client;

	public List<Product> search(Integer page, String q) {
		//构造查询query对象 matchquery
		QueryBuilder query=QueryBuilders.
				matchQuery("product_name", q);
		int start=(page-1)*5;
		//调用查询方法,获取response,解析数据
		SearchResponse response = client.prepareSearch("easymall").
		setQuery(query).setFrom(start).setSize(5).get();
		//解析resonse的数据
		SearchHits hits = response.getHits();
		System.out.println("共搜到:"+hits.getTotalHits());
		//准备一个返回的List对象
		List<Product> plist=new ArrayList<Product>();
		for (SearchHit hit : hits) {
			Product p=new Product();
			p.setProductId(hit.getSource().get("product_id")+"");
			p.setProductImgurl(hit.getSource().get("product_imgurl")+"");
			p.setProductPrice(Double.parseDouble(
					hit.getSource().get("product_price")+""));
			p.setProductName(hit.getSource().get("product_name")+"");
			plist.add(p);
		}
		return plist;
	}
}
