package com.kkwrite.demo.shiro.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kkwrite.demo.shiro.dao.UserDao;
import com.kkwrite.demo.shiro.entity.User;
import com.kkwrite.demo.shiro.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	public User getByUserName(String username) {
		return userDao.getByUsername(username);
	}

	public Set<String> getRoles(String username) {
		return userDao.getRoles(username);
	}

	public Set<String> getPermissions(String username) {
		return userDao.getPermissions(username);
	}

}
