package com.jt.sso.pojo;

import java.io.Serializable;

public class User implements Serializable{
	private String userId;
	private String userName;
	private String userPassword;
	private String userNickname;
	private String userEmail;
	private Integer userType;//ҵ���߼��ϲ�û��ʹ��type
	//һ�����������,�п���������ʱ,����nullָ���쳣,1 ��ʾ�����û�,2 ͭ���û�,3 �����û�,4 �����û�,5 ��ʯ�û�,6 �׽��û�7
	//Ĭ������ʱ,ȫ��0

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	

}
