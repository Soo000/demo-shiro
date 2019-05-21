package com.kkwrite.demo.shiro.jse.realms;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JdbcRealm
 * @author Soosky
 *
 */
public class MyJdbcRealm extends AuthorizingRealm {
	
	private JdbcTemplate jdbcTemplate;

	public MyJdbcRealm(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 用户名
		String username = (String) principals.getPrimaryPrincipal();
		
		// 查询用户角色
		String sql = "select role_code from t_user_role where username = ?";
		List<String> roles = jdbcTemplate.queryForList(sql, String.class, username);
		
		// 查询角色权限
		List<String> permissions = new ArrayList<>();
		for (String role: roles) {
			String rolePermissionSql = "SELECT b.permission_value " +
										"FROM t_role_permission a " + 
										"LEFT JOIN t_permission b ON a.permission_id = b.permission_id " + 
										"WHERE a.role_code = ?";
			List<String> perms = jdbcTemplate.queryForList(rolePermissionSql, String.class, role);
			if (!perms.isEmpty()) {
				permissions.addAll(perms);
			}
		}
		
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		
		// 添加角色
		simpleAuthorizationInfo.addRoles(roles);
		// 添加权限
		simpleAuthorizationInfo.addStringPermissions(permissions);
		
		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String sql = "select password from t_user where username = ?";
		String password = jdbcTemplate.queryForObject(sql, String.class, username);
		
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, null, getName());		
		return simpleAuthenticationInfo;
	}

}
