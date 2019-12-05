package com.jt.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface SuccessKilledMapper {
	
	//插入购买明细，可过滤重复
	public int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") String userPhone);

	public List queryUserPhone(@Param("seckillId") long seckillId);
	
}
