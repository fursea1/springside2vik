package org.vicalloy.quickstart.security.service;

import org.apache.commons.lang.StringUtils;
import org.springside.core.dao.extend.HibernateEntityExtendDao;
import org.springside.core.exception.BusinessException;
import org.vicalloy.quickstart.security.model.SubFunction;

public class SubFunctionManager extends HibernateEntityExtendDao<SubFunction> {
	@Override
	protected void onValid(SubFunction entity) {
		if (!isUnique(entity, "functionKey")) {
			throw new BusinessException("权限标识" + entity.getFunctionKey() + "重复");
		}
		String errMsg001 = "子权限的权限标识必须使用 主权限标识+英文字母的方式。如："
				+ entity.getMasterFunction().getFunctionKey() + "A";
		if (!entity.getFunctionKey().startsWith(
				entity.getMasterFunction().getFunctionKey())) {
			throw new BusinessException(errMsg001);
		}
		String subK = entity.getFunctionKey().substring(
				entity.getMasterFunction().getFunctionKey().length());
		if (StringUtils.isNumeric(subK)) {
			throw new BusinessException(errMsg001);
		}
	}
}