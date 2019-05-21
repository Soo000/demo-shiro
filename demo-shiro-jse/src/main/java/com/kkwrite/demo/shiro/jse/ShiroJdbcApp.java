package com.kkwrite.demo.shiro.jse;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.kkwrite.demo.shiro.jse.realms.MyJdbcRealm;

public class ShiroJdbcApp {

	public static void main(String[] args) {
		// Spring DataSource
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.1.118:4306/demo-shiro-db");
		dataSource.setUsername("root");
		dataSource.setPassword("1qa@WS");

		// JdbcTemplate
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setDataSource(dataSource);
		
		// 自定义 jdbcRealm
		Realm realm = new MyJdbcRealm(jdbcTemplate);
		// 通过 SecurityUtils 将 securityManager 绑定到上下文中
		SecurityManager securityManager = getDefaultSecurityManager(realm); 
		SecurityUtils.setSecurityManager(securityManager);
		
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
			if (subject.isPermitted("/user/list")) {
				System.out.println("用户 【" + username + "】 有 /user/list 权限");
			} else {
				System.out.println("用户 【" + username + "】 没有 /user/list 权限");
			}
		} catch (AuthenticationException e) {
			System.out.println("用户名或密码错误！");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 默认实现的 DefaultSecurityManager
	 * @return
	 */
	private static SecurityManager getDefaultSecurityManager(Realm realm) {
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
		// 给授权器设置 realm - TODO 这部有没有必要？
		Collection<Realm> realms = new ArrayList<>();
		realms.add(realm);
		authorizer.setRealms(realms);
		// 给 securityManger 设置授权器
		defautlSecurityManger.setAuthorizer(authorizer);
		
		// 给 securityManger realm
		defautlSecurityManger.setRealm(realm);
		
		return defautlSecurityManger;
	}
	
}
