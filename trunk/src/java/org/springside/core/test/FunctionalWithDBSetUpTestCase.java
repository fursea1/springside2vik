package org.springside.core.test;

import java.sql.Connection;
import java.util.Properties;

import org.springside.core.test.support.DatabaseUnitHelper;

/**
 * Selenium集成测试扩展基类,进行数据库管理方面的扩展.
 * <p>支持DbUnit的数据准备工作;支持重新指定数据库连接配置;支持每次Setup是否清空数据的选择.
 *
 * @author Anders.Lin
 */
public abstract class FunctionalWithDBSetUpTestCase extends FunctionalTestCase {

	protected static final DatabaseUnitHelper dbUnitHelper = new DatabaseUnitHelper();

	/**
	 * 返回包含初时话数据的Excel文件.
	 */
	protected abstract String getXlsPath();

	/**
	 * 如果不采用默认数据库连接属性配置文件，可以指定一个新的属性配置文件.
	 */
	protected String getPropertiesPath() {
		return "";
	}

	/**
	 * 如果不采用默认数据库连接属性，可以给定一个新的配置属性对象.
	 */
	protected Properties getProperties() {
		return null;
	}

	protected void doConfigConnection() {
		String path = getPropertiesPath();
		if (path != null && !path.trim().equals("")) {
			dbUnitHelper.doInit(path);
		}

		dbUnitHelper.doInit(getProperties());
	}

	protected Connection getConnection() throws Exception {
		return dbUnitHelper.getJdbcConnection();
	}

	/**
	 * 设定是否每次SetUp都清空数据.
	 */
	protected void doCleanInsertEachSetup(boolean flag) {
		dbUnitHelper.doCleanInsertEachSetup(flag);
	}

	@Override
	public void setUp() {
		super.setUp();
		doConfigConnection();
		dbUnitHelper.setXlsPath(getXlsPath());
		dbUnitHelper.setUp();
	}

	@Override
	public void tearDown() {
		super.tearDown();
		dbUnitHelper.tearDown();
	}
}
