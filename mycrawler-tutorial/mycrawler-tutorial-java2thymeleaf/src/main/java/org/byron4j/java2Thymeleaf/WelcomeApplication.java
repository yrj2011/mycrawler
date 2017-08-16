package org.byron4j.java2Thymeleaf;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;

public class WelcomeApplication {
    public void process(HttpServletRequest request, HttpServletResponse response) 
   		 throws IOException {
	    WebContext ctx = new WebContext(request, response, request.getServletContext(),
	    		request.getLocale());
	    ctx.setVariable("currentDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	    
	    /**
	     * 使用Thymeleaf引擎加载模板文件welcome.html
	     */
	    ThymeleafAppUtil.getTemplateEngine().process("welcome", ctx, response.getWriter());
    }
}
