package com.jt.sso.mapper;

import com.jt.sso.pojo.User;

public interface UserMapper {

	int checkUserName(String userName);

	int insertUser(User user);

	User checkLogin(User user);

}
