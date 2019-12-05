package com.jt.sec.consumer;

import java.util.Date;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jt.sec.mapper.SeckillMapper;
import com.jt.sec.mapper.SuccessKilledMapper;
import com.jt.sec.pojo.SuccessKilled;

@Component
public class SecConsumer {
	@Autowired
	private SeckillMapper secMapper;
	@Autowired
	private SuccessKilledMapper skMapper;
	@RabbitListener(queues="sec-queue")
	public void process(String msg){
		//从消息拿到一条消息,
		/*1 seckillId和user信息截取
		 *2 入库,number-1 前提条件>0 update seckill set number=number-1 where
		 *number >0(线程安全,判断>0时有多个线程,-1操作多次,造成超卖,利用redis的list)
		 *3 利用返回值判断入库是否成功? trycatch判断入库成功,记录秒杀成功的信息
		 *到success_killed表格
		 */
		Long seckillId=Long.parseLong(msg.split("/")[0]);
		Long userPhone=Long.parseLong(msg.split("/")[1]);
		//执行更新商品数量
		try{
			int ok=secMapper.updateReduceNumber(seckillId, new Date());
			if(ok==0){
				System.out.println("当前用户:"+userPhone+"秒杀失败");
			}else{//秒杀入库成功
				//用户信息入库,将用户信息,和商品信息保存到成功的表格success_killed
				SuccessKilled sk=new SuccessKilled();
				sk.setCreateTime(new Date());
				sk.setSeckillId(seckillId);
				sk.setUserPhone(userPhone);
				sk.setState(0);
				skMapper.insertSk(sk);
			}
		}catch(Exception e){
			System.out.println("当前用户:"+userPhone+"秒杀失败");
		}
		
	}
}
