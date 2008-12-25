package org.springside.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.springframework.util.CollectionUtils;
import org.springside.core.commons.support.CriteriaSetup;
import org.springside.core.dao.support.Page;
import org.springside.core.utils.GenericsUtils;

/**
 * 负责为单个Entity对象提供CRUD操作的Hibernate DAO基类. <p/> 子类只要在类定义时指定所管理Entity的Class,
 * 即拥有对单个Entity对象的CRUD操作.
 * 
 * <pre>
 * public class UserManager extends HibernateEntityDao&lt;User&gt; {
 * }
 * </pre>
 * 
 * @author calvin
 * @see HibernateGenericDao
 */
@SuppressWarnings("unchecked")
public class HibernateEntityDao<T> extends HibernateGenericDao implements
		EntityDao<T> {

	protected Class<T> entityClass;// DAO所管理的Entity类型.

	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 */
	public HibernateEntityDao() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 取得entityClass.JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 根据ID获取对象.
	 * 
	 * @see HibernateGenericDao#getId(Class,Object)
	 */
	public T get(Serializable id) {
		return get(getEntityClass(), id);
	}

	/**
	 * 获取全部对象
	 * 
	 * @see HibernateGenericDao#getAll(Class)
	 */
	public List<T> getAll() {
		return getAll(getEntityClass());
	}

	/**
	 * 获取全部对象,带排序参数.
	 * 
	 * @see HibernateGenericDao#getAll(Class,String,boolean)
	 */
	public List<T> getAll(String orderBy, boolean isAsc) {
		return getAll(getEntityClass(), orderBy, isAsc);
	}

	/**
	 * 根据ID移除对象.
	 * 
	 * @see HibernateGenericDao#removeById(Class,Serializable)
	 */
	public void removeById(Serializable id) {
		removeById(getEntityClass(), id);
	}

	/**
	 * 取得Entity的Criteria.
	 * 
	 * @see HibernateGenericDao#createCriteria(Class,Criterion[])
	 */
	public Criteria createCriteria(Criterion... criterions) {
		return createCriteria(getEntityClass(), criterions);
	}

	/**
	 * 取得Entity的Criteria,带排序参数.
	 * 
	 * @see HibernateGenericDao#createCriteria(Class,String,boolean,Criterion[])
	 */
	public Criteria createCriteria(String orderBy, boolean isAsc,
			Criterion... criterions) {
		return createCriteria(getEntityClass(), orderBy, isAsc, criterions);
	}

	/***************************************************************************
	 * 根据属性名和属性值查询对象。属性字段数组长度必须登录值数组
	 * 
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(String[] propertyNames, Object[] values) {
		return findBy(getEntityClass(), propertyNames, values);
	}

	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 * @see HibernateGenericDao#findBy(Class,String,Object)
	 */
	public List<T> findBy(String propertyName, Object value) {
		return findBy(getEntityClass(), propertyName, value);
	}

	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 * 
	 * @return 符合条件的对象列表
	 * @see HibernateGenericDao#findBy(Class,String,Object,String,boolean)
	 */
	public List<T> findBy(String propertyName, Object value, String orderBy,
			boolean isAsc) {
		return findBy(getEntityClass(), propertyName, value, orderBy, isAsc);
	}

	/**
	 * 根据属性名和属性值查询单个对象.
	 * 
	 * @return 符合条件的唯一对象 or null
	 * @see HibernateGenericDao#findUniqueBy(Class,String,Object)
	 */
	public T findUniqueBy(String propertyName, Object value) {
		return findUniqueBy(getEntityClass(), propertyName, value);
	}

	/**
	 * 判断对象某些属性的值在数据库中唯一.
	 * 
	 * @param uniquePropertyNames
	 *            在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 * @see HibernateGenericDao#isUnique(Class,Object,String)
	 */
	public boolean isUnique(Object entity, String uniquePropertyNames) {
		return isUnique(getEntityClass(), entity, uniquePropertyNames);
	}

	// -----------------------由springside1中复制过来，进行分页处理的代码-----------------------
	/**
	 * 根据Map中过滤条件和分页参数进行分页查询.
	 * 
	 * @param filterMap
	 *            过滤条件.
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            每页显示记录数.
	 */
	public Page findBy(Map filterMap, int pageNo, int pageSize) {
		return findBy(filterMap, null, pageNo, pageSize);
	}

	/**
	 * 根据Map中过滤条件,排序条件和分页参数进行分页查询.
	 * 
	 * @param filterMap
	 *            过滤条件.
	 * @param orderMap
	 *            排序条件.
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            每页显示记录数.
	 */
	public Page findBy(Map filterMap, Map orderMap, int pageNo, int pageSize,
			CriteriaSetup criteriaSetup) {
		Criteria criteria = getSession().createCriteria(getEntityClass());

		if (!CollectionUtils.isEmpty(filterMap)) {
			try {
				criteriaSetup.setup(criteria, filterMap);
			} catch (Exception e) {
			}
		}

		if (!CollectionUtils.isEmpty(orderMap))
			sortCriteria(criteria, orderMap, null);

		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);

		return pagedQuery(criteria, pageNo, pageSize);
	}

	public Page findBy(Map filterMap, Map orderMap, int pageNo, int pageSize) {
		return findBy(filterMap, orderMap, pageNo, pageSize,
				getDefaultCriteriaSetup());
	}
}
