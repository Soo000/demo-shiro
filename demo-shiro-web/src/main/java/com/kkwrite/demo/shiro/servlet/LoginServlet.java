package com.kkwrite.demo.shiro.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * 登录 Servlet
 *
 * @author Soosky Wang
 */
@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 4017282184883228099L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		try {
			subject.login(token);
			req.getRequestDispatcher("WEB-INF/pages/main.jsp").forward(req, resp);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			req.setAttribute("msg", "用户名或密码错误");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}

}
