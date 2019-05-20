package com.kkwrite.demo.shiro.jse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroJdbcApp {

	public static void main(String[] args) {
		// 通过 SecurityUtils 将 securityManager 绑定到上下文中
		SecurityUtils.setSecurityManager(getIniSecurityManger());
		
		// 主体。从上下文中获取主体
		Subject subject = SecurityUtils.getSubject();
		
		String username = "admin";
		String password = "admin";
		UsernamePasswordToken usernameAndPasswordToken = new UsernamePasswordToken(username, password);
		
		try {
			System.out.println("登录前是否认证：" + subject.isAuthenticated());
			// 登录，登录中包含了认证信息
			subject.login(usernameAndPasswordToken);
			System.out.println("登录后是否认证：" + subject.isAuthenticated());
			
			// 判断用户是否有某个角色
			if (subject.hasRole("admin")) {
				System.out.println("用户 【" + username + "】 有 admin 角色");
			} else {
				System.out.println("用户 【" + username + "】 没有 admin 角色");
			}
			
			// 判断用户是否有某个权限
			if (subject.isPermitted("add")) {
				System.out.println("用户 【" + username + "】 有 add 权限");
			} else {
				System.out.println("用户 【" + username + "】 没有 add 权限");
			}
		} catch (AuthenticationException e) {
			System.out.println("用户名或密码错误！");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * IniSecurityManagerFactory 工厂生成的 SecurityManager
	 */
	private static SecurityManager getIniSecurityManger() {
		// SecurityManager 工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-jdbc.ini");	
		// 从 SecurityManager 工厂生成其实例
		SecurityManager securityManager = factory.getInstance();
		
		return securityManager;
	}
	
}
