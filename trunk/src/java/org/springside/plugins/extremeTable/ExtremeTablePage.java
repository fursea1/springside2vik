package org.springside.plugins.extremeTable;

import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.limit.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 辅助ExtremeTable获取分页信息的Util类
 *
 * @author calvin
 */
public class ExtremeTablePage {
	
	public static final Integer DEFAULT_PAGE_SIZE = 15; 


    static public Limit getLimit(HttpServletRequest request) {
        return getLimit(request, DEFAULT_PAGE_SIZE);
    }

    /**
     * 从request构造Limit对象实例.
     * Limit的构造流程比较不合理，为了照顾export Excel时忽略信息分页，导出全部数据
     * 因此流程为程序先获得total count, 再使用total count 构造Limit，再使用limit中的分页数据查询分页数据
     * 而SS的page函数是在同一步的，无法拆分，再考虑到首先获得的totalCount
     */
    static public Limit getLimit(HttpServletRequest request, int defautPageSize) {
        Context context = new HttpServletRequestContext(request);
        LimitFactory limitFactory = new TableLimitFactory(context);
        TableLimit limit = new TableLimit(limitFactory);
        limit.setRowAttributes(1000000000, defautPageSize);
        return limit;
    }

    /**
     * 将Limit中的排序信息转化为Map{columnName,升序/降序}
     */
    @SuppressWarnings("unchecked")
	static public Map getSort(Limit limit) {
        Map sortMap = new HashMap();
        if (limit != null) {
            Sort sort = limit.getSort();
            if (sort != null && sort.isSorted()) {
                sortMap.put(sort.getProperty(), sort.getSortOrder());
            }
        }
        return sortMap;
    }
}
