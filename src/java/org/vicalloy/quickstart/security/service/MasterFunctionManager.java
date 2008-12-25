package org.vicalloy.quickstart.security.service;

import org.springside.core.dao.extend.HibernateEntityExtendDao;
import org.springside.core.exception.BusinessException;
import org.vicalloy.quickstart.security.model.MasterFunction;

public class MasterFunctionManager extends HibernateEntityExtendDao<MasterFunction> {
	@Override
	protected void onValid(MasterFunction entity) {
		if (!isUnique(entity, "functionKey")) {
			throw new BusinessException("权限标识" + entity.getFunctionKey() + "重复");
		}
	}
}