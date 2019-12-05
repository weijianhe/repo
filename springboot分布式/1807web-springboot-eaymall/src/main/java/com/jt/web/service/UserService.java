package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.HttpClientService;
import com.jt.common.vo.HttpResult;
import com.jt.web.pojo.User;
@Service
public class UserService {
	@Autowired
	private HttpClientService client;
	public int checkUserName(String username) throws Exception {
		String url="http://sso.jt.com/user/check/"+username;
		String exists = client.doGet(url);//"1";"0"
		return Integer.parseInt(exists);
	}
	public int regist(User user) throws Exception {
		
		String url="http://sso.jt.com/user/register";
		//封装一个map对象的参数
		Map<String,Object> map=new HashMap<String,Object>();
		//key=value,
		map.put("userName", user.getUserName());
		map.put("userPassword", user.getUserPassword());
		map.put("userNickname", user.getUserNickname());
		map.put("userEmail", user.getUserEmail());
		//请求体中 userName=**&userPassword=**&userNickname=**&
		HttpResult result=client.doPost(url, map);//封装了响应体
		//获取响应体内容
		String success=result.getBody();//1成功,0失败
		return Integer.parseInt(success);
	}
	public String login(User user) throws Exception {
		String url="http://sso.jt.com/user/login";
		//参数
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("u", user.getUserName());
		map.put("p", user.getUserPassword());
		HttpResult result=client.doPost(url,map);
		//获取响应体的ticket值
		String ticket=result.getBody();//"","1233JK321GK2L1BI12"
		return ticket;
	}

}






























