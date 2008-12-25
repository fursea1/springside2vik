package org.vicalloy.quickstart.core.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.extremecomponents.table.limit.Limit;
import org.springframework.web.util.WebUtils;
import org.springside.core.dao.EntityDao;
import org.springside.core.dao.support.Page;
import org.springside.core.utils.BeanUtils;
import org.springside.core.web.StrutsEntityAction;
import org.springside.plugins.extremeTable.ExtremeTablePage;
import org.vicalloy.quickstart.Constants;

/*******************************************************************************
 * 加入springside1中的翻页代码，需要EC的支持。
 * 
 * @author vicalloy
 * 
 */
@SuppressWarnings("unchecked")
public class StrutsECEntityAction<T, M extends EntityDao<T>> extends
		StrutsEntityAction {

	protected Integer defaultPageSize = Constants.DEFAULT_PAGE_SIZE;

	/***************************************************************************
	 * 添加扩展过滤条件
	 * 
	 * @param filter
	 */
	@SuppressWarnings("unchecked")
	protected void doAddExtFilter(Map filter, HttpServletRequest request) {
		// 在这里添加扩展过滤条件
	}

	/***************************************************************************
	 * 如果是以prefix开头，则替去掉
	 * 
	 * @param s
	 * @param prefix
	 * @return
	 */
	private static String replace_start(String s, String prefix) {
		if (s.startsWith(prefix)) {
			return s.substring(prefix.length());
		}
		return s;
	}

	/***************************************************************************
	 * 根据POJO对查询条件进行类型转换
	 * 
	 * @param filter
	 */
	@SuppressWarnings("unchecked")
	protected void doCoverFilter(Map filter) {
		Map tmpMap = new HashMap();
		for (Entry e : (Set<Entry>) filter.entrySet()) {
			String key = (String) e.getKey();
			String name = key;
			// 去掉修饰符
			name = replace_start(name, "must_");
			name = replace_start(name, "like_");
			name = replace_start(name, "ge_");
			name = replace_start(name, "le_");
			try {
				Object value = ConvertUtils.convert((String) e.getValue(),
						BeanUtils.getPropertyType(entityClass, name));
				tmpMap.put(key, value);
			} catch (Exception ex) {
				log.debug(ex);
				// 可能没有和查询条件对应的参数，该异常不用处理。
				// 为了防止那个讨厌的语法检查错误标记，debug的时候记录日志。
			}
		}
		filter.putAll(tmpMap);
	}

	protected Page doListEntity(HttpServletRequest request) {
		Limit limit = ExtremeTablePage.getLimit(request, defaultPageSize);
		Map filterMap = WebUtils.getParametersStartingWith(request, "search_");
		doCoverFilter(filterMap);
		doAddExtFilter(filterMap, request);
		Page page = getEntityManager().findBy(filterMap,
				ExtremeTablePage.getSort(limit), limit.getPage(),
				limit.getCurrentRowsDisplayed());
		return page;
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Page page = doListEntity(request);
		request.setAttribute(getEntityListName(), page.getResult());
		request.setAttribute("totalRows", page.getTotalCount());
		return mapping.findForward(LIST);
	}
}
