package com.kkwrite.demo.shiro.service;

import java.util.Set;

import com.kkwrite.demo.shiro.entity.User;


public interface UserService {

	/**
	 * 通过用户名查询用户
	 * @param userName
	 * @return
	 */
	User getByUserName(String userName);
	
	/**
	 * 通过用户名查询角色信息
	 * @param userName
	 * @return
	 */
	Set<String> getRoles(String userName);
	
	/**
	 * 通过用户名查询权限信息
	 * @param userName
	 * @return
	 */
	Set<String> getPermissions(String userName);
}
