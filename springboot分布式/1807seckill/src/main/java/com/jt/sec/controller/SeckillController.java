package com.jt.sec.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.sec.pojo.Seckill;
import com.jt.sec.service.SeckillService;

@Controller
public class SeckillController {
	@Autowired
	private SeckillService seckillService;
	//seckill/list
	@RequestMapping("seckill/list")
	public String goIndex(Model model){
		List<Seckill> sList=seckillService.queryAll();
		model.addAttribute("list", sList);
		return "list";
	}
	@RequestMapping("seckill/{id}/detail")
	public String detail(@PathVariable Long id,Model model){
		Seckill seckill=seckillService.getById(id);
		model.addAttribute("seckill",seckill);
		return "detail";
	}
	@RequestMapping("/seckill/time/now")
	@ResponseBody
	public Date getNow(){
		return new Date();
	}
	
	@RequestMapping("/seckill/{seckillId}")
	@ResponseBody
	public String start(@PathVariable Long seckillId){
		//没有sso没有拦截器,模拟海量访问
		for(int i=0;i<20;i++){//每次循环相当于有一个秒杀的请求都在秒杀本商品,
			//每次循环生成一个电话号
			int random=RandomUtils.nextInt(100, 999);//随机生成100-999的三位数字
			//表示电话的后三位
			String userPhone="18819221"+random;
			seckillService.start(seckillId,userPhone);
		}
		return "";
	}
	
}
