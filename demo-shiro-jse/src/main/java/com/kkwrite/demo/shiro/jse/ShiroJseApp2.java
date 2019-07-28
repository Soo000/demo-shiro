package com.kkwrite.demo.shiro.jse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;

public class ShiroJseApp2 {

	public static void main(String[] args) {
        // 1.根据配置文件创建SecurityManagerFactory
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 2.通过工厂获取SecurityManager
        SecurityManager securityManager = factory.getInstance();
        // 3.将SecurityManager绑定到当前运行环境
        SecurityUtils.setSecurityManager(securityManager);
        // 4.从当前运行环境中构造subject
        Subject subject = SecurityUtils.getSubject();
        // 5.构造shiro登录的数据
        String username = "zhangsan";
        String password = "1234561";
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        // 6.主体登陆
        subject.login(token);
        // 7.验证用户是否登录成功
        System.out.println("用户是否登录成功=" + subject.isAuthenticated());
        // 8.获取登录成功的数据
        System.out.println(subject.getPrincipal());
        
        // 9.授权检验，判断当前登录用户是否具有操作权限，是否具有某个角色
        System.out.println(subject.hasRole("role1"));
        System.out.println(subject.isPermitted("user:save"));
	}
}
