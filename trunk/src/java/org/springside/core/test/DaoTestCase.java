package org.springside.core.test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springside.core.Constants;

/**
 * Dao测试基类,继承于AbstractTransactionalDataSourceSpringContextTests.
 * <p/>
 * 该类带OpenSessionInTest与事务默认回滚能力,并带有一个jdbcTemplate变量可在同一事务内检查数据库变化.
 * 实现了基类的getConfigLocations()函数,设置默认的applicationContext, 在子类可重载此函数以减少载入的applicaitonContext.mxl,加快测试速度.
 * 设置AUTOWIRE_BY_NAME ,因为Spring的测试基类默认为BY_TYPE,在有多个相同类型的Bean时冲突.
 *
 * @author calvin
 * @see AbstractTransactionalDataSourceSpringContextTests
 */
public abstract class DaoTestCase extends AbstractTransactionalDataSourceSpringContextTests {
	/**
	 * @see AbstractTransactionalDataSourceSpringContextTests#getConfigLocations()
	 */
	@Override
	protected String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		return new String[]{Constants.DEFAULT_CONTEXT, Constants.DEFAULT_TEST_CONTEXT};
	}
}
