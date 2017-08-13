package org.byron4j.java2Thymeleaf;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *	@author  	Byron.Y.Y
 *  @optDate 	2016年11月15日
 *  使用servlet3注解，注册一个servlet，并在容器启动时加载
 */
@WebServlet(urlPatterns = "/welcome", loadOnStartup = 1)
public class WelcomeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	//POST请求
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
	        doGet(request,response);
	}
	
	//GET请求
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		WelcomeApplication application = new WelcomeApplication();
		application.process(request, response);
	}
} 