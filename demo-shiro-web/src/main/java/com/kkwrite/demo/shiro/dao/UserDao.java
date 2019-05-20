package com.kkwrite.demo.shiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import com.kkwrite.demo.shiro.entity.User;
import com.kkwrite.demo.shiro.util.DbUtil;

/** 
 * 类说明 
 *
 * @author Soosky Wang
 * @date 2018年9月20日 下午3:54:19 
 * @version 1.0.0
 */
public class UserDao extends Dao {

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User getByUsername(String username) throws Exception {
		User user = null;
		String sql = "select * from sys_user where username = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs, ps, conn);
		}
		return user;
	}

	/**
	 * 根据用户名查询用户角色
	 * @param username
	 * @return
	 */
	public Set<String> getRoles(String username) {
		Set<String> roles = new HashSet<String>();
		String sql = "select role from sys_user a, sys_role b where a.username = ? and a.role_ids = b.id";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				roles.add(rs.getString("role"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs, ps, conn);
		}
		return roles;
	}

	/**
	 * 根据用户名查询用户权限
	 * @param username
	 * @return
	 */
	public Set<String> getPermissions(String username) {
		Set<String> permissions = new HashSet<String>();
		String sql = "select c.permission from sys_user a, sys_role b, roles_permissions c " + 
				"where a.username = ? and a.role_ids = b.id and b.role = c.role_name";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				permissions.add(rs.getString("permission"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs, ps, conn);
		}
		return permissions;
	}
	
}
