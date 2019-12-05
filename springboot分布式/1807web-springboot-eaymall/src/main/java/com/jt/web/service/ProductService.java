package com.jt.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.ObjectUtil;
import com.jt.web.pojo.Product;

@Service
public class ProductService {
	@Autowired
	private HttpClientService client;
	public List<Product> queryProducts(Integer currentPage, 
			Integer rows) throws Exception {
		//生成接口定义的url
		String url="http://manage.jt.com/product/list?page="
		+currentPage+"&rows="+rows;
		/*CloseableHttpClient client=HttpClients.createDefault();
		HttpGet get=new HttpGet(url);
		CloseableHttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		String jsonData=EntityUtils.toByteArray(entity);*/
		String jsonData = client.doGet(url);
		//TODO解析listjson编程对象List<Product>
		List<Product> pList=null;
		JsonNode data=ObjectUtil.mapper.readTree(jsonData);
		if(data.isArray()&&data.size()>0){
			pList = ObjectUtil.mapper.readValue(data.traverse(),
		    ObjectUtil.mapper.getTypeFactory().
		    constructCollectionType(List.class, Product.class));
		}
		return pList;
	}

	public Integer queryTotalPage(Integer rows) throws Exception {
		//发起想后台的请求,查询商品的总数
		String url="http://manage.jt.com/product/total";
		String totalS=client.doGet(url);
		Integer total=Integer.parseInt(totalS);
		//业务逻辑将total与rows做业务计算得到totalpage
		return (total%rows)==0? total/rows:((total/rows)+1);
	}

	public Product findProductById(String id) throws Exception {
		
		String url="http://manage.jt.com/product/queryById/"
											+id;
		String jsonData=client.doGet(url);
		Product product=ObjectUtil.mapper.
				readValue(jsonData, Product.class);
		return product;
	}

	public int saveProduct(Product product) throws Exception{
		//利用httpclient doPostJson的方法,将对象转化成json字符串
		String jsonData=ObjectUtil.mapper.
				writeValueAsString(product);
		String url="http://manage.jt.com/product/save";
		try{
			client.doPostJson(url, jsonData);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public int queryTotal() throws Exception {
		String url="http://manage.jt.com/product/total";
		String totalS=client.doGet(url);
		Integer total=Integer.parseInt(totalS);
		return total;
	}

	public int updateProduct(Product product) throws Exception {
		String url="http://manage.jt.com/product/update";
		String json=ObjectUtil.mapper.
				writeValueAsString(product);
		try{
			client.doPostJson(url, json);
			return 1;
		}catch(Exception e){
			e.printStackTrace();;
			return 0;
		}
	}

}
