package com.jt.sec.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.sec.pojo.Seckill;



public interface SeckillMapper {
	
	//根据偏移量查询秒杀商品列表
	public List<Seckill> queryAll();
	
	//根据id查询秒杀对象
	public Seckill queryById(long id);
	
	//减库存操作
	public int updateReduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);
	
}
