package com.jt.sec.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.sec.mapper.SeckillMapper;
import com.jt.sec.pojo.Seckill;

@Service
public class SeckillService {
	@Autowired
	private SeckillMapper seckillMapper;
	public List<Seckill> queryAll() {
		
		return seckillMapper.queryAll();
	}
	public Seckill getById(Long id) {
		
		return seckillMapper.queryById(id);
	}
	//使用rabbitTemplate将消息封装携带到队列中
	@Autowired
	private RabbitTemplate rabbit;
	public void start(Long seckillId, String userPhone) {
		//生成消息,seckillId,userPhone
		String msg=seckillId+"/"+userPhone;
		rabbit.convertAndSend("sec-ex", "seckill", msg);
	}

}
