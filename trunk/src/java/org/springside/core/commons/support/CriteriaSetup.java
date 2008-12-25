package org.springside.core.commons.support;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author calvin
 */
public class CriteriaSetup {

	public static final String EXT_RESTRICTIONS = "ext_restrictions";

	@SuppressWarnings("unchecked")
	public void setup(Criteria criteria, Map filter) {
		if (filter != null && !filter.isEmpty()) {
			for (Entry<String, Object> e : (Set<Entry<String, Object>>) filter
					.entrySet()) {
				Object value = e.getValue();
				String key = e.getKey();
				setupColumn(key, value, criteria);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void setupColumn(String key, Object value, Criteria criteria) {
		// 添加手动拼装的查询条件
		if ("ext_restrictions".equals(key)) {
			Collection<Criterion> criterions = (Collection<Criterion>) value;
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			return;
		}
		boolean must = false;
		if (key.startsWith("must_")) {
			must = true;
			key = key.substring("must_".length());
		}
		// 如果字符串为空，同时非必须字段，直接返回
		if (!isNotBlankObj(value) && !must)
			return;
		if (key.startsWith("like_")) {
			key = key.substring("like_".length());
			criteria.add(Restrictions.like(key, "%" + value + "%"));
			return;
		}
		if (key.startsWith("le_")) {
			key = key.substring("le_".length());
			criteria.add(Restrictions.le(key, value));
			return;
		}
		if (key.startsWith("ge_")) {
			key = key.substring("ge_".length());
			criteria.add(Restrictions.ge(key, value));
			return;
		}
		criteria.add(Restrictions.eq(key, value));
	}

	protected static boolean isNotBlankObj(Object o) {
		if (o == null) {
			return false;
		}
		return StringUtils.isNotBlank(o.toString());
	}

}