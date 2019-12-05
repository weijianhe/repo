package com.jt.sec.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecConfig {
	//声明交换机
	@Bean
	public DirectExchange getEx(){
		return new DirectExchange("sec-ex");
	}
	@Bean
	public Queue queue(){
		return new Queue("sec-queue");
	}
	@Bean//绑定交换机和队列
	public Binding bind(){
		return BindingBuilder.bind(queue()).
				to(getEx()).with("seckill");
	}
	
}
