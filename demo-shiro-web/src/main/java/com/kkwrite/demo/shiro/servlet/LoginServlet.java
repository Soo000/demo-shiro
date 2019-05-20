package com.kkwrite.demo.shiro.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.kkwrite.demo.shiro.util.CryptographyUtil;

/** 
 * 类说明 
 *
 * @author Soosky Wang
 * @date 2018年9月20日 下午2:15:51 
 * @version 1.0.0
 */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 4017282184883228099L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("jsp/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		password = CryptographyUtil.md5(password, "1a2b3c");
		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			
			Session session = subject.getSession();
			String sessionId = session.getId().toString();
			System.out.println("sessionId = " + sessionId);
			
			System.out.println(session.getHost());
			
			System.out.println(session.getTimeout());
			
			resp.sendRedirect("jsp/main.jsp");
		} catch (AuthenticationException e) {
			e.printStackTrace();
			req.setAttribute("msg", "用户名或密码错误");
			req.getRequestDispatcher("jsp/login.jsp").forward(req, resp);
		}
	}
	
}
