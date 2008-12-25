package org.vicalloy.quickstart.security;

import org.springframework.web.util.ExpressionEvaluationUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 页面权限管理
 * 
 * @author vicalloy
 */
public class AuthorizeTag extends TagSupport {

	private static final long serialVersionUID = -3033383954871613431L;

	/***************************************************************************
	 * functionId
	 */
	private String res = "";

	// ~ Methods
	// ========================================================================================================

	public int doStartTag() throws JspException {
		final String resource = ExpressionEvaluationUtils.evaluateString("res",
				res, pageContext);
		if ((SecurityUtils.hasRight(pageContext.getSession(), resource))) {
			return Tag.EVAL_BODY_INCLUDE;
		}
		return Tag.SKIP_BODY;
	}

	public void setRes(String res) {
		this.res = res;
	}

}
