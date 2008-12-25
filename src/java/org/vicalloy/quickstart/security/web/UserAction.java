package org.vicalloy.quickstart.security.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;
import org.vicalloy.quickstart.Constants;
import org.vicalloy.quickstart.core.web.StrutsSecurityAction;
import org.vicalloy.quickstart.security.model.Role;
import org.vicalloy.quickstart.security.model.User;
import org.vicalloy.quickstart.security.service.RoleManager;
import org.vicalloy.quickstart.security.service.UserManager;

@SuppressWarnings("unchecked")
public class UserAction extends StrutsSecurityAction<User, UserManager> {

	protected static final String LOGIN_SUCC = "/main.jsp";
	public static final String FUNCTIONID = "002";
	{
		functionId = FUNCTIONID;
		functionDescn = "用户";
		disabledField.add("loginName");
		whiteMethod.add("login");
		whiteMethod.add("logout");
	}

	@SuppressWarnings("unused")
	private UserManager userManager;
	private RoleManager roleManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	@Override
	protected void refrenceData(HttpServletRequest request) {
		List<Role> roles = roleManager.getAll();
		request.setAttribute("roles", roles);
		super.refrenceData(request);
	}

	@Override
	protected Set<String> getNotBindFields(ActionForm form,
			HttpServletRequest request) {
		Set<String> notBindFields = super.getNotBindFields(form, request);
		if (StringUtils.isNotEmpty(request.getParameter(idName))) {// 如果是修改（添加的时候，密码不能为空）
			notBindFields.add("pswd");
		}
		return notBindFields;
	}

	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request,
			Object object) {
		// FIXME 为什么object无法使用泛型了？
		String roleId = request.getParameter("roleId");
		User new_object = (User) object;
		new_object.setRole(roleManager.get(Long.valueOf(roleId)));
		super.onInitEntity(form, request, object);
	}

	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request,
			Object object) {
		User new_object = (User) object;
		if (new_object != null && new_object.getRole() != null) {
			request.setAttribute("roleId", new_object.getRole().getId());
		}
		super.onInitForm(form, request, object);
	}

	/**
	 * 用户登陆
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String loginName = (String) request.getParameter("loginName");
		String pswd = (String) request.getParameter("pswd");
		User user = userManager.findUniqueBy("loginName", loginName);

		if (user != null && user.getPswd().equals(pswd)) {
			// 强制加载
			Hibernate.initialize(user);
			Hibernate.initialize(user.getRole());
			Hibernate.initialize(user.getRole().getFunctions());
			request.getSession().setAttribute(Constants.SESSION_USER, user);
			logManager.saveLog(user, "用户登陆:" + user.getLoginName() + " IP:"
					+ request.getLocalAddr());
			return new ActionForward(LOGIN_SUCC, true);
		} else {
			saveDirectlyError(request, "登录失败：用户名/密码错误");
		}
		return new ActionForward("/", true);
	}

	/**
	 * 用户登出
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "index";
		request.getSession().invalidate();
		return mapping.findForward(forward);
	}

}