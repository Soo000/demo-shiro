package com.kkwrite.demo.shiro.entity;

/** 
 * 类说明 
 *
 * @author Soosky Wang
 * @date 2018年9月20日 下午3:55:11 
 * @version 1.0.0
 */
public class User {

	private Long id;
	private String username;
	private String password;
	
	public User() {
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User(Long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
