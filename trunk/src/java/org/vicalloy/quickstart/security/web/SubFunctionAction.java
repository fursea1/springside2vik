package org.vicalloy.quickstart.security.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;
import org.vicalloy.quickstart.Constants;
import org.vicalloy.quickstart.core.web.StrutsSecurityAction;
import org.vicalloy.quickstart.security.model.MasterFunction;
import org.vicalloy.quickstart.security.model.SubFunction;
import org.vicalloy.quickstart.security.service.MasterFunctionManager;
import org.vicalloy.quickstart.security.service.SubFunctionManager;

@SuppressWarnings("unchecked")
public class SubFunctionAction extends
		StrutsSecurityAction<SubFunction, SubFunctionManager> {
	// FIXME 使用了masterFunction.descn的方式后会导致排序失败。需要修改EC？

	public static final String FUNCTIONID = "000";
	{
		functionId = FUNCTIONID;
		base_functionId = Constants.SUPER_FUNCTION_KEY;
		edit_functionId = Constants.SUPER_FUNCTION_KEY;
		functionDescn = "子权限";
		disabledField.add("masterFunction");
		disabledField.add("masterFunctionId");
	}

	@SuppressWarnings("unused")
	private SubFunctionManager subFunctionManager;
	private MasterFunctionManager masterFunctionManager;

	public void setSubFunctionManager(SubFunctionManager subFunctionManager) {
		this.subFunctionManager = subFunctionManager;
	}

	public void setMasterFunctionManager(
			MasterFunctionManager masterFunctionManager) {
		this.masterFunctionManager = masterFunctionManager;
	}

	@Override
	protected void refrenceData(HttpServletRequest request) {
		List<MasterFunction> t_masterFunctions = masterFunctionManager.getAll();
		List<LabelValueBean> masterFunctions = new ArrayList<LabelValueBean>();
		for (MasterFunction masterFunction : t_masterFunctions) {
			LabelValueBean lvb = new LabelValueBean();
			lvb.setValue(masterFunction.getId().toString());
			lvb.setLabel(masterFunction.getFunctionKey() + ":"
					+ masterFunction.getDescn());
			masterFunctions.add(lvb);
		}
		request.setAttribute("masterFunctions", masterFunctions);
		super.refrenceData(request);
	}

	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request,
			Object object) {
		// FIXME 为什么object无法使用泛型了？
		String masterFunctionId = request.getParameter("masterFunctionId");
		SubFunction sf = (SubFunction) object;
		sf.setMasterFunction(masterFunctionManager.get(Long
				.valueOf(masterFunctionId)));
		super.onInitEntity(form, request, object);
	}

	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request,
			Object object) {
		SubFunction sf = (SubFunction) object;
		if (sf != null && sf.getMasterFunction() != null) {
			request.setAttribute("masterFunctionId", sf.getMasterFunction()
					.getId());
		}
		super.onInitForm(form, request, object);
	}

}