package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.manage.pojo.Product;



public interface ProductMapper {
	
	List<Product> queryProducts
	(@Param("start")int start,@Param("rows")Integer rows);
    //select * from t_product limit #{start},#{rows}

	int queryCount();

	Product findProductById(String id);

	int saveProduct(Product product);

	int updateProduct(Product product);
}
