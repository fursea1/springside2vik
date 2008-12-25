package org.vicalloy.quickstart.security.service;

import org.springside.core.dao.extend.HibernateEntityExtendDao;
import org.springside.core.exception.BusinessException;
import org.vicalloy.quickstart.security.model.User;

public class UserManager extends HibernateEntityExtendDao<User> {
	@Override
	protected void onValid(User entity) {
		if (!isUnique(entity, "loginName")) {
			throw new BusinessException("登陆名" + entity.getLoginName() + "重复");
		}
	}
}
