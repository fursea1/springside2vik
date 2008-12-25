package org.springside.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springside.core.dao.support.Page;

/**
 * 针对单个Entity对象的操作定义.不依赖于具体ORM实现方案.
 * 
 * @author calvin
 */
public interface EntityDao<T> {

	T get(Serializable id);

	List<T> getAll();

	Page findBy(Map filterMap, Map orderMap, int pageNo, int pageSize);

	void save(Object o);

	void remove(Object o);

	void removeById(Serializable id);

	/**
	 * 获取Entity对象的主键名.
	 */
	String getIdName(Class clazz);
}
