package org.vicalloy.quickstart.security.web;

import org.vicalloy.quickstart.core.web.StrutsSecurityAction;
import org.vicalloy.quickstart.security.model.Log;
import org.vicalloy.quickstart.security.service.LogManager;

@SuppressWarnings("unchecked")
public class LogAction extends StrutsSecurityAction<Log, LogManager> {

	public static final String FUNCTIONID = "003";
	{
		functionId = FUNCTIONID;
		functionDescn = "日志";
		saveLog=false;
	}

	@SuppressWarnings("unused")
	private LogManager logManager;

	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

}