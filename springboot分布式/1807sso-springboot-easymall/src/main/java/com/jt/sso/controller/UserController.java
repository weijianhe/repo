package com.jt.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jt.common.service.RedisService;
import com.jt.common.util.MD5Util;
import com.jt.common.util.ObjectUtil;
import com.jt.common.util.UUIDUtil;
import com.jt.common.vo.SysResult;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

@RestController
public class UserController {
	
	/*
	 * 	请求url:http://sso.jt.com/user/check/{username}
	请求方式:get
	请求参数:路径传参RestFul格式 String username
返回数据:1表示不可用,0表示可用
	 */
	@Autowired
	private UserMapper userMapper;
	@RequestMapping("user/check/{username}")
	public String checkUsername(@PathVariable String username){
		String exists=userMapper.checkUserName(username)+"";
		
		return exists; 
	}
	
	/*
	 * 用户注册接口文件
	请求url:http://sso.jt.com/user/register
	请求方式:post
	请求参数:
	String userName;
	String userPassword;
	String userNickname;
	String userEmail;
	SSO单点登录可以用User对象来接收
	封装到post方法的map参数对象中,以key=value的形式在请求体中存在
	返回数据:1 表示成功, 0 表示新增注册失败
	 */
	
	@RequestMapping("user/register")
	public String doRgister(User user){
		//password是明文,需要加密注册
		user.setUserPassword(MD5Util.md5(user.getUserPassword()));
		//user的id为UUID
		user.setUserId(UUIDUtil.getUUID());
		return userMapper.insertUser(user)+"";
	}
	/*
	 * 用户登录的接口文件
	请求url:http:sso.jt.com/user/login
	请求参数:String u(用户名),String p(密码)
	注意:数据利用登录的username和password传递给后端服务,过程占用带宽,而且登录的次数非常多,节省带宽
	请求方式:post
返回数据:String ticket值;
	 */
	@Autowired
	private RedisService redis;
	@RequestMapping("user/login")
	public String doLogin(@RequestParam(value="u")
	String userName,@RequestParam(value="p")
	String userPassword) throws Exception{
		String ticket="";//存储在redis中数据的key值
		//判断登录成功和失败
		User _user=new User();
		_user.setUserName(userName);
		_user.setUserPassword(MD5Util.md5(userPassword));
		User user = userMapper.checkLogin(_user);
		if(user==null){//登录失败,提供的用户名密码不对
			return ticket;
		}else{//登录成功,密码用户名正确
		/* 生成ticket "JT_LOGIN"+currentTime+username md5加密;
		 * user对象转化成json,存入redis,
		 */
		//先获取username的ticketvalue
		ticket=redis.get(userName);
		if(StringUtils.isNotEmpty(ticket)){//说明登录过了
			//删除原有的值,覆盖新的登录值
			redis.del(ticket);
			ticket=MD5Util.md5("JT_LOGIN"+
					 System.currentTimeMillis()+userName);
			redis.set(userName, ticket);
			String userJson=ObjectUtil.mapper.
					writeValueAsString(user);
			redis.set(ticket, userJson,60*30);
			return ticket;
		}else{
			//说明ticket为空,第一次设置
			ticket=MD5Util.md5("JT_LOGIN"+
					 System.currentTimeMillis()+userName);
			redis.set(userName, ticket);
			String userJson=ObjectUtil.mapper.
					writeValueAsString(user);
			redis.set(ticket, userJson,60*30);
			return ticket;
		}
		
		}

	}
	/*
	 * 用户登录信息的校验(使用ticket获取userJson)
	请求url:"http://sso.jt.com/user/query/{ticket}"
	请求参数:String ticket 隐藏参数String callback
	请求方式:get
	返回数据:
		如果callback==""或者null
		返回SysResult的json字符串(data属性,携带从redis获取的userJson),给拦截器用的
		如果callback!=null或者""
		返回jsonp格式的数据
		callback(sysresult的就送字符串)
	 */
	
	@RequestMapping("user/query/{ticket}")
	public String queryTicket(@PathVariable String ticket,
			String callback) throws Exception{
		//根据callback判断返回的数据格式
		SysResult result=new SysResult();
		String userJson=redis.get(ticket);
		if(userJson!=null){//登录成功
			//判断时间是否剩余少于5分钟
			Long left = redis.getTiem(ticket);
			if(left<=60*5){
				int time=(int)(left+60*5);
				redis.set(ticket, userJson,time);
			}
		}
		result.setData(userJson);
		result.setStatus(200);
		result.setMsg("ok");
		String jsonData=ObjectUtil.mapper.
				writeValueAsString(result);
		if(callback==null){//说明需要的是json字符串数据
			return jsonData;
			//{"":"","":""}
		}else{
			return callback+"("+jsonData+")";//加入callback=test
			//test({"":"","":""})--->jsonp
		}
		/*SysResult result=new SysResult();
		String userJson=redis.get(ticket);//一定有userJson?
		result.setData(userJson);
		result.setStatus(200);
		result.setMsg("ok");
		String jsonData=ObjectUtil.mapper.
				writeValueAsString(result);
		if(callback==null){//说明需要的是json字符串数据
			return jsonData;
			//{"":"","":""}
		}else{
			return callback+"("+jsonData+")";//加入callback=test
			//test({"":"","":""})--->jsonp
		}*/
	}	
}
