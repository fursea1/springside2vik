package org.springside.core;

/**
 * @author calvin
 */
public class Constants {
	/**
	 * 测试用例使用的定义Spring Context 文件集合的字符串.
	 */
	public static final String DEFAULT_CONTEXT = "classpath*:spring/*.xml";

	/**
	 * 测试用例使用的定义Spring在测试时特别设置的Context 文件集合的字符串.
	 */
	public static final String DEFAULT_TEST_CONTEXT = "classpath*:spring/test/*.xml";
}
