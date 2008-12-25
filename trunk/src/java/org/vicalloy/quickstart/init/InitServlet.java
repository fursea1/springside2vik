package org.vicalloy.quickstart.init;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import org.vicalloy.quickstart.init.InitServlet;

/*******************************************************************************
 * 启动初始化
 */
public class InitServlet extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {

	private static final long serialVersionUID = -3585970875094386687L;

	private static final Logger logger = Logger.getLogger(InitServlet.class);

	private static ApplicationContext applicationContext;

	public InitServlet() {
		super();
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		// TODO 启动初始化工作
	}
}