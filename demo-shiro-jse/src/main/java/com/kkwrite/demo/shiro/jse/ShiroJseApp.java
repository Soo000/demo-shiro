package com.kkwrite.demo.shiro.jse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import com.kkwrite.demo.shiro.jse.realms.MyRealm;

public class ShiroJseApp {

	public static void main(String[] args) {
		// 通过 SecurityUtils 将 securityManager 绑定到上下文中
		//SecurityUtils.setSecurityManager(getIniSecurityManger());
		SecurityUtils.setSecurityManager(getDefaultSecurityManager());
		
		// 主体。从上下文中获取主体
		Subject subject = SecurityUtils.getSubject();
		
		String username = "root";
		String password = "123456";
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
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");	
		// 从 SecurityManager 工厂生成其实例
		SecurityManager securityManager = factory.getInstance();
		
		return securityManager;
	}
	
	/**
	 * 默认实现的 DefaultSecurityManager
	 * @return
	 */
	private static SecurityManager getDefaultSecurityManager() {
		// 默认实现的 securityManager
		DefaultSecurityManager defautlSecurityManger = new DefaultSecurityManager();
		
		// 认证器 
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		// 给认证器设置认证策略（AtLeastOneSuccessfulStrategy）
		authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		// 给 securityManger 设置认证器
		defautlSecurityManger.setAuthenticator(authenticator);
		
		// 授权器
		ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
		// 授权器设置授权方案
		authorizer.setPermissionResolver(new WildcardPermissionResolver());
		// 设置 realm
		defautlSecurityManger.setRealm(new MyRealm());
		
		// 给 securityManger 设置授权器
		defautlSecurityManger.setAuthorizer(authorizer);
		
		
		return defautlSecurityManger;
	}
	
}
