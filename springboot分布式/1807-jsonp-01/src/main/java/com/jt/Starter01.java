package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.AMQP.Connection.Start;

@SpringBootApplication
public class Starter01 {
	public static void main(String[] args) {
		SpringApplication.run(Starter01.class, args);
	}
}
