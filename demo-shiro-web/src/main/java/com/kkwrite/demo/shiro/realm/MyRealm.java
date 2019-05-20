package com.kkwrite.demo.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.kkwrite.demo.shiro.dao.UserDao;
import com.kkwrite.demo.shiro.entity.User;

/** 
 * 类说明 
 *
 * @author Soosky Wang
 * @date 2018年9月20日 下午3:46:53 
 * @version 1.0.0
 */
public class MyRealm extends AuthorizingRealm {
	
	private UserDao userDao = new UserDao();

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		try {
			User user = userDao.getByUsername(username);
			if (user != null) {
				AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), "xx");
				return authcInfo; 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 授权（授予角色和权限）
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(); 
		// 设置角色
		authorizationInfo.setRoles(userDao.getRoles(username));
		// 设置权限
		authorizationInfo.setStringPermissions(userDao.getPermissions(username));
		
		return authorizationInfo;
	}

}
