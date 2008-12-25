package org.vicalloy.quickstart.common;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/*******************************************************************************
 * 工具方法
 * 
 * @author vicalloy
 * 
 */
public class Utils {
	/***************************************************************************
	 * sql增加查询参数的辅助方法
	 * 
	 * @param sql
	 * @param params
	 * @param value
	 * @param nullObj
	 * @return
	 */
	public static String sqlAddParam(String sql, List<Object> params,
			String extSql, Object value, Object nullObj) {
		// 如果查询参数为空则返回
		if (value == null || StringUtils.isEmpty(value.toString())) {
			return sql;
		}
		// 如果查询参数为通配符，则不添加查询条件
		if (nullObj == null || nullObj.equals(value)) {
			return sql;
		}
		sql += extSql;
		params.add(value);
		return sql;
	}

	/***************************************************************************
	 * sql增加查询参数的辅助方法，专门针对like进行扩展，给查询参数加是%%
	 * 
	 * @param sql
	 * @param params
	 * @param extSql
	 * @param value
	 * @return
	 */
	public static String sqlAddLikeParam(String sql, List<Object> params,
			String extSql, Object value) {
		if (value == null || StringUtils.isEmpty(value.toString())) {
			return sql;
		}
		sql += extSql;
		params.add("%" + value + "%");
		return sql;
	}
}
