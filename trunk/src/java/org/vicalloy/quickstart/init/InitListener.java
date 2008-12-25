package org.vicalloy.quickstart.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class InitListener extends HttpServlet implements ServletContextListener {

	private static final long serialVersionUID = -827418805720009495L;

	private static final Logger logger = Logger.getLogger(InitListener.class);

	public void contextInitialized(ServletContextEvent arg0) {

	}

	public void contextDestroyed(ServletContextEvent arg0) {

	}

}
