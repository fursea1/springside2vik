package org.vicalloy.quickstart.core.web;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springside.core.dao.EntityDao;
import org.vicalloy.quickstart.security.SecurityUtils;

/*******************************************************************************
 * 权限控制类，提供基础的权限控制。同时记录基本的操作日志。
 * 
 * @author vicalloy
 * 
 * @param <T>
 * @param <M>
 */
@SuppressWarnings("unchecked")
public class StrutsSecurityAction<T, M extends EntityDao<T>> extends
		StrutsECEntityAction {

	/***************************************************************************
	 * 主权限ID
	 */
	protected String functionId;

	protected String functionDescn;// = "角色管理";

	/***************************************************************************
	 * 是否记录日志
	 */
	protected boolean saveLog = true;

	/***************************************************************************
	 * 基础权限ID
	 */
	protected String base_functionId;

	/***************************************************************************
	 * 编辑权限ID
	 */
	protected String edit_functionId;

	/***************************************************************************
	 * 没有权限返回的view
	 */
	protected String NORIGHT = "noright";

	/***************************************************************************
	 * 白名单。白名单中的方法，在权限基类中不做权限验证。
	 */
	protected Set<String> whiteMethod = new HashSet<String>();

	/***************************************************************************
	 * 黑名单。屏蔽黑名单中的方法，当遇到黑名单时候，直接返回无权限。
	 */
	protected Set<String> blackMethod = new HashSet<String>();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 虽然在父类中会初始化session_user，但在没有初始化前就已经要使用session_user了，因此需要在execute方法的最前面调用一次
		if (base_functionId == null)
			base_functionId = functionId + "A";
		if (edit_functionId == null)
			edit_functionId = functionId + "B";
		// 白名单，在白名单中的函数不需要进行权限判断
		if (!whiteMethod.contains(request.getParameter("method"))) {
			ActionForward f = hasBaseRight(mapping, form, request, response);
			// 判断是否有权限
			if (f != null) {
				// 返回无权限的提示页面
				return f;
			}
		}
		// 由于默认继承就会有完整的添加/删除/修改操作。这里增加黑名单，如果遇到方法在黑名单中就不执行
		if (blackMethod.contains(request.getParameter("method"))) {
			return mapping.findForward(NORIGHT);
		}
		return super.execute(mapping, form, request, response);
	}

	/***************************************************************************
	 * 是否存在编辑权限。如果认证通过返回null，失败返回出错页面。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	protected ActionForward hasBaseRight(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SecurityUtils.hasRight(getSessionUser(request), base_functionId)) {
			// 返回无权限的提示页面
			return mapping.findForward(NORIGHT);
		}
		return null;
	}

	/***************************************************************************
	 * 是否存在编辑权限。如果认证通过返回null，失败返回出错页面。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	protected ActionForward hasEditRight(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SecurityUtils.hasRight(getSessionUser(request), edit_functionId)) {
			// 返回无权限的提示页面
			return mapping.findForward(NORIGHT);
		}
		return null;
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward f = hasEditRight(mapping, form, request, response);
		// 判断是否有权限
		if (f != null) {
			// 返回无权限的提示页面
			return f;
		}
		return super.edit(mapping, form, request, response);
	}

	/***************************************************************************
	 * 记录 编辑/新建 的日志
	 */
	@Override
	protected void logSaveLog(HttpServletRequest request, Object object) {
		// FIXME 重载后无法使用泛型
		if (saveLog)
			logManager.saveLog(getSessionUser(request), functionDescn + " 编辑/新建");
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward f = hasEditRight(mapping, form, request, response);
		// 判断是否有权限
		if (f != null) {
			// 返回无权限的提示页面
			return f;
		}
		f = super.save(mapping, form, request, response);
		return f;
	}

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward f = hasEditRight(mapping, form, request, response);
		// 判断是否有权限
		if (f != null) {
			// 返回无权限的提示页面
			return f;
		}
		return super.create(mapping, form, request, response);
	}

	/***************************************************************************
	 * 是否存在删除权限。如果认证通过返回null，失败返回出错页面。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	protected ActionForward hasDeleteRight(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return hasEditRight(mapping, form, request, response);
	}

	/***************************************************************************
	 * 记录删除日志
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@Override
	protected void logDeleteLog(HttpServletRequest request) {
		// FIXME 重载后无法使用泛型
		if (saveLog)
			logManager.saveLog(getSessionUser(request), functionDescn + " 删除 "
					+ getEntityId(request));
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward f = hasDeleteRight(mapping, form, request, response);
		// 判断是否有权限
		if (f != null) {
			// 返回无权限的提示页面
			return f;
		}
		f = super.delete(mapping, form, request, response);
		return f;
	}

	/***************************************************************************
	 * 将不可编辑字段加如过滤列表，防止用户修改提交表单进行攻击
	 * 
	 * @return
	 */
	@Override
	protected Set<String> getNotBindFields(ActionForm form,
			HttpServletRequest request) {
		Set<String> notBindFields = super.getNotBindFields(form, request);
		if (StringUtils.isNotEmpty(request.getParameter(idName))) {
			notBindFields.addAll(disabledField);
		}
		return notBindFields;
	}

}
