package com.kkwrite.demo.shiro.jse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

/**
 * 自定义 realm
 * @author Soosky
 *
 */
public class MyRealm implements Realm {

	/**
	 * 自定义 realm 的名字
	 */
	@Override
	public String getName() {
		return "myRealm";
	}

	/**
	 * 支持那种类型的 token
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		if (token instanceof UsernamePasswordToken) {
			return true;
		}
		return false;
	}

	/**
	 * 认证
	 */
	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) 
			throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String password = String.valueOf((char[]) token.getCredentials());
		
		if (!"root".equals(username)) {
			throw new UnknownAccountException();
		}
		
		if (!"123456".equals(password)) {
			throw new IncorrectCredentialsException();
		}
		
		AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
		return authenticationInfo;
	}

}
