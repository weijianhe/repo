package com.jt.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.common.service.HttpClientService;
import com.jt.common.util.CookieUtils;
import com.jt.common.util.ObjectUtil;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

public class OrderInterceptor implements HandlerInterceptor{
	@Autowired
	private HttpClientService client;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取request中的ticket 如果有值说明曾经登录,没值直接登录界面
		String ticket=CookieUtils.getCookieValue(request, "JT_TICKET");
		if(StringUtils.isNotEmpty(ticket)){//曾经登录过
			//到sso连接redis获取userJson
			String url="http://sso.jt.com/user/query/"+ticket;
			String jsonData=client.doGet(url);//获取sysresult的json
			SysResult result=ObjectUtil.mapper.readValue(jsonData, SysResult.class);
			String userJson=(String) result.getData();
			if(userJson!=null&&userJson!=""&&userJson!="null"){//真正登录是正常的
				//userNode解析userId,放到request域中
				User user=ObjectUtil.mapper.readValue(userJson, User.class);
				String userId=user.getUserId();
				request.setAttribute("userId", userId);
				return true;//放行,后续的controller才能获取userId
			}
		}
		//登录是失效的
		response.sendRedirect("/page/login");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
