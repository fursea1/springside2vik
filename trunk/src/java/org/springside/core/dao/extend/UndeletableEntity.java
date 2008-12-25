package org.springside.core.dao.extend;

/**
 * 标识商业对象不能被删除,只能被设为无效的接口.
 *
 * @author calvin
 */
public interface UndeletableEntity {
	void setStatus(String status);
}
