package org.vicalloy.quickstart.security.web;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.vicalloy.quickstart.core.web.StrutsSecurityAction;
import org.vicalloy.quickstart.security.model.Role;
import org.vicalloy.quickstart.security.model.SubFunction;
import org.vicalloy.quickstart.security.service.RoleManager;
import org.vicalloy.quickstart.security.service.SubFunctionManager;

@SuppressWarnings("unchecked")
public class RoleAction extends StrutsSecurityAction<Role, RoleManager> {

	public static final String FUNCTIONID = "001";
	{
		functionId = FUNCTIONID;
		functionDescn = "角色";
	}

	@SuppressWarnings("unused")
	private RoleManager roleManager;
	private SubFunctionManager subFunctionManager;

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	public void setSubFunctionManager(SubFunctionManager subFunctionManager) {
		this.subFunctionManager = subFunctionManager;
	}

	@Override
	protected void refrenceData(HttpServletRequest request) {
		request.setAttribute("functions", roleManager.getFunctions());
		super.refrenceData(request);
	}

	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request,
			Object object) {
		// 获取权限列表，并保存到角色中。
		Role new_object = (Role) object;
		String[] funcs = request.getParameterValues("funcs");
		new_object.setFunctions(new HashSet<SubFunction>());
		for (String f : funcs) {
			SubFunction sfunc = subFunctionManager.get(new Long(f));
			new_object.getFunctions().add(sfunc);
		}

		super.onInitEntity(form, request, object);
	}

	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request,
			Object object) {
		Role new_object = (Role) object;
		super.onInitForm(form, request, object);
	}

}