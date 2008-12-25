package org.vicalloy.codegen.ext;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.LazyDynaBean;
import org.vicalloy.codegen.annotation.Descn__;
import org.vicalloy.codegen.annotation.MasterFunctionId__;

/*******************************************************************************
 * 模块的扩展信息。用于包装由断言获取到的信息，方便模板的使用。
 * 
 * @author vicalloy
 * 
 */
public class ModelExt extends LazyDynaBean {
	private static final long serialVersionUID = 282060668161552854L;
	@SuppressWarnings("unchecked")
	private Class entityClass;

	@SuppressWarnings("unchecked")
	protected static final Map exchangeMap = new HashMap();// 转换map，用于设置初始值
	static {

	}

	@SuppressWarnings("unchecked")
	public ModelExt(Class entityClass) {
		this.entityClass = entityClass;
		init();
	}

	@SuppressWarnings("unchecked")
	protected void init() {
		if (entityClass.isAnnotationPresent(Descn__.class)) {
			Descn__ anno = (Descn__) entityClass.getAnnotation(Descn__.class);
			String descn = anno.descn();
			this.set("__descn", descn);
		}
		if (entityClass.isAnnotationPresent(MasterFunctionId__.class)) {
			MasterFunctionId__ anno = (MasterFunctionId__) entityClass
					.getAnnotation(MasterFunctionId__.class);
			String masterFunctionId = anno.masterFunctionId();
			this.set("__masterFunctionId", masterFunctionId);
		}
	}

	/***************************************************************************
	 * 重载get方法，在遇到异常的时候直接返回空字符
	 */
	@Override
	public Object get(String name) {
		Object ret = null;
		try {
			ret = super.get(name);
		} catch (Exception e) {
			ret = exchangeMap.get(name);
		}
		if (ret == null) {
			return "";
		}
		return ret;
	}
}
