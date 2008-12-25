package org.vicalloy.quickstart.security.web;

import org.vicalloy.quickstart.Constants;
import org.vicalloy.quickstart.core.web.StrutsSecurityAction;
import org.vicalloy.quickstart.security.model.MasterFunction;
import org.vicalloy.quickstart.security.service.MasterFunctionManager;

@SuppressWarnings("unchecked")
public class MasterFunctionAction extends
		StrutsSecurityAction<MasterFunction, MasterFunctionManager> {

	public static final String FUNCTIONID = "000";
	{
		saveLog = false;
		functionId = FUNCTIONID;
		base_functionId = Constants.SUPER_FUNCTION_KEY;
		edit_functionId = Constants.SUPER_FUNCTION_KEY;

		functionDescn = "主权限";
		disabledField.add("functionKey");
	}

	@SuppressWarnings("unused")
	private MasterFunctionManager masterFunctionManager;

	public void setMasterFunctionManager(
			MasterFunctionManager masterFunctionManager) {
		this.masterFunctionManager = masterFunctionManager;
	}

}