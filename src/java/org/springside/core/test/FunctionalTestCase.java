package org.springside.core.test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import junit.framework.TestCase;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.server.SeleniumServer;

/**
 * Selenium集成测试基类.
 * <p/>
 * 负责启动和关闭selenium服务。 可以在selenium.properties 设置Explorer类型 和 待测网站的BaseURL
 * 如果没有设置,Explorer默认为"*iexplore", BaseURL默认为"http://localhost:8080"
 *
 * @author calvin
 */
public abstract class FunctionalTestCase extends TestCase {

	protected static final String DEFAULT_EXPLORER = "*iexplore";

	protected static final String DEFAULT_BASEURL = "http://localhost:8080";

	protected static final String DEFAULT_TIME = "30000";

	private static PropertiesConfiguration config = new PropertiesConfiguration();

	static {
		try {
			config.load("selenium.properties");
		} catch (ConfigurationException e) {
			// 客户没有自定义selenium.properties属正常情况
		}
	}

	protected Selenium user; // Selenium变量,命名为user,子类使用时可读性较高.

	@Override
	public void setUp() {
		user = new DefaultSelenium("localhost", SeleniumServer.DEFAULT_PORT, getExplorer(), getBaseURL());
		user.start();
	}

	@Override
	public void tearDown() {
		user.stop();
	}

	/**
	 * 返回模拟的浏览器类型. 先从selenium.properties中读取selenium.explorer,如果未设定则使用默认值.
	 */
	public static String getExplorer() {
		return config.getString("selenium.explorer", DEFAULT_EXPLORER);
	}

	/**
	 * 返回网站基本URL. 先从selenium.properties中读取selenium.baseurl,如果未设定则使用默认值.
	 */
	public static String getBaseURL() {
		return config.getString("selenium.baseurl", DEFAULT_BASEURL);
	}
}
