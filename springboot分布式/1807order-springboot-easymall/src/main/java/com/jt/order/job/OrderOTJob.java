package com.jt.order.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.jt.order.mapper.OrderMapper;

public class OrderOTJob extends QuartzJobBean{
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		ApplicationContext applicationContext=(ApplicationContext)
				context.getJobDetail().getJobDataMap().
				get("applicationContext");
		System.out.println("执行前");
		Date lastDay=new Date(new Date().getTime()-1000*60*60*24);
		applicationContext.getBean(OrderMapper.class).
		deleteOTOrders(lastDay);
		System.out.println("执行后");
	}

	
	
	
	
	
	
}
