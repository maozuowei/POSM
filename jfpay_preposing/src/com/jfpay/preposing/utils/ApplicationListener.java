package com.jfpay.preposing.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jfpay.preposing.properties.DataDictInitialize;

/**
 * 监听application，在application启动的同时将相关线程启动；
 * @author Rex_xu
 *
 */
public class ApplicationListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent servletContext) {
		System.out.println("系统正在退出");
		DataDictInitialize.destory();
		System.out.println("系统正常退出");
//		System.exit(1);
	}

	public void contextInitialized(ServletContextEvent servletContext) {
		//加载log4j配置文件；
//		System.out.println("AAA"+this.getClass().getClassLoader().getResource("/").getPath());
//		servletContext.getServletContext().getResourceAsStream(servletContext.getServletContext().getInitParameter("log4jConfigLocation"));
//		PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("/").getPath().replaceAll("^\\/", "") + servletContext.getServletContext().getInitParameter("log4jConfigLocation"));
		DataDictInitialize.getInstance().init();
        System.out.println("系统正常启动");
	}
}
