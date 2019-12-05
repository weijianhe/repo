package com.jt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

@Controller
public class UserController {
	
	
	@Autowired
	private UserService userService;
	@RequestMapping("/user_ajax/checkUserName")
	@ResponseBody
	public SysResult checkUserName(String username) throws Exception{
		//exists=0表示可用,1表示不可用
		int exists=userService.checkUserName(username);
		SysResult result=new SysResult();
		result.setStatus(exists);
		return result;
	}
	 
	@RequestMapping("/user_ajax/regist")
	@ResponseBody
	public SysResult regist(User user) throws Exception{
		SysResult result =new SysResult();
		
		int success=userService.regist(user);
		result.setStatus(success);
		if(success==1){
		
			String msg="用户:"+user.getUserName()+"注册成功";
			result.setMsg(msg);
		}else{
			String msg="用户:"+user.getUserName()+"注册失败";
			result.setMsg(msg);
		}
		return result;
	}
	
	//user_ajax/login
	@RequestMapping("user_ajax/login")
	@ResponseBody
	public SysResult login(User user,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		//userName  userPassword参数
		SysResult result=new SysResult();
		String ticket=userService.login(user);
		//登录在sso失败,返回的是"",如果成功,返回的是ticket值
		if(StringUtils.isNotEmpty(ticket)){
			//表示登录成功;ticket值放入cookie中Cookie的类
			CookieUtils.setCookie(request, response, 
					"JT_TICKET", ticket);
			//登录成功转向首页
			result.setStatus(1);
			return result;
		}else{
			result.setStatus(0);
			return result;
		}
		
		
	}
	/*
	@RequestMapping("/user_ajax/logout")
	public String logout(HttpSession session){
		//���session�е�����
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		return "redirect:/";
	}*/
}

















































