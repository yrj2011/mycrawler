package org.byron4j.java2Thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


/**
 *	@author  	Byron.Y.Y
 *  @optDate 	2016年11月15日
 *  This class is for ...
 */
public class ThymeleafAppUtil {

	private static TemplateEngine templateEngine;
	
	/**
	 * static代码块，加载初始模板设置：/WEB-INF/templates/**.html文件
	 */
    static {
        ServletContextTemplateResolver templateResolver = 
                new ServletContextTemplateResolver();
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }
    
    public static TemplateEngine getTemplateEngine() {
	 return templateEngine;
    }

}
