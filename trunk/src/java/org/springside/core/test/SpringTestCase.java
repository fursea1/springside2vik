package org.springside.core.test;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springside.core.Constants;

/**
 * Spring环境下的测试基类,继承于AbstractDependencyInjectionSpringContextTests.
 * <p/>
 * 该类带Spring的IOC能力,子类只要设置Setter即可获得依赖注入.
 * 实现了基类的getConfigLocations()函数,设置默认的applicationContext,在子类可重载该函数，减少载文件,加快速度. 设置AUTOWIRE_BY_NAME
 * ,因为Spring的测试基类默认为BY_TYPE,在有多个相同类型的Bean时冲突.
 *
 * @author calvin
 * @see AbstractDependencyInjectionSpringContextTests
 */
public abstract class SpringTestCase extends AbstractDependencyInjectionSpringContextTests {

	@Override
	protected String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		return new String[]{Constants.DEFAULT_CONTEXT, Constants.DEFAULT_TEST_CONTEXT};
	}
}
