package org.springside.core.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.springframework.util.ReflectionUtils;
import org.springside.core.utils.BeanUtils;
import org.springside.core.web.support.DateConverter;
import org.springside.core.web.support.StringConverter;
import org.vicalloy.quickstart.Constants;
import org.vicalloy.quickstart.security.model.User;
import org.vicalloy.quickstart.security.service.LogManager;

/**
 * 简单封装Struts DispatchAction的基类. 提供一些基本的简化函数,将不断增强.
 * 
 * @author calvin
 */
public class StrutsAction extends DispatchAction {

	// 不知道springside中为什么在这里添加成功失败的view定义。现改为在代码中将view写死。为防止歧义，特注释掉 ---vicalloy
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";

	public static final String DIRECTLY_MESSAGE_KEY = "message";

	@SuppressWarnings("unused")
	protected LogManager logManager;

	static {
		registConverter();
	}

	/**
	 * 设置Struts 中数字<->字符串转换，字符串为空值时,数字默认为null，而不是0.
	 * 也可以在web.xml中设置struts的参数达到相同效果，在这里设置可以防止用户漏设web.xml.
	 */
	public static void registConverter() {
		ConvertUtils.register(new StringConverter(), String.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new FloatConverter(null), Float.class);
		ConvertUtils.register(new DoubleConverter(null), Double.class);
		ConvertUtils.register(new DateConverter("yyyy-MM-dd"), Date.class);
	}

	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

	/***************************************************************************
	 * 将session中的用户信息取出
	 */
	public static User getSessionUser(HttpServletRequest request) {
		User session_user = (User) request.getSession().getAttribute(
				Constants.SESSION_USER);
		return session_user;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return super.execute(mapping, form, request, response);
	}

	/**
	 * 将FormBean中的内容通过BeanUtils的copyProperties()绑定到Object中.
	 * 因为BeanUtils中两个参数的顺序很容易搞错，因此封装此函数.
	 */
	protected void bindEntity(ActionForm form, Object object,
			String[] ignoreProperties) {
		if (form != null) {
			try {
				BeanUtils.copyProperties(object, form, ignoreProperties);
				// 换成spring的，增加过滤列表的支持(不知道为什么，这个在这里无法生效)
				// org.springframework.beans.BeanUtils.copyProperties(form,
				// object, ignoreProperties);
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
			}
		}
	}

	/**
	 * 将Object内容通过BeanUtils的copyProperties 复制到FormBean中.
	 * 因为BeanUtils中两个参数的顺序很容易搞错，因此封装此函数。
	 */
	protected void bindForm(ActionForm form, Object object) {
		if (object != null) {
			try {
				BeanUtils.copyProperties(form, object);
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
			}
		}
	}

	/**
	 * 保存单条信息到Message的简化函数.
	 */
	protected void saveMessage(HttpServletRequest request, String key,
			String... values) {
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key, values));
		saveMessages(request.getSession(), msgs);
	}

	/**
	 * 直接保存文本信息(非i18n)到messages.
	 * 
	 * @param message
	 *            直接的文本信息
	 */
	protected void saveDirectlyMessage(HttpServletRequest request,
			String message) {
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				DIRECTLY_MESSAGE_KEY, message));
		saveMessages(request.getSession(), msgs);
	}

	/**
	 * 保存单条信息到Error的简化函数.
	 */
	protected void saveError(HttpServletRequest request, String key,
			String... values) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage(key, values));
		saveErrors(request.getSession(), errors);
	}

	/**
	 * 直接保存文本信息(非i18n)errors.
	 * 
	 * @param message
	 *            直接的文本信息
	 */
	protected void saveDirectlyError(HttpServletRequest request, String message) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				DIRECTLY_MESSAGE_KEY, message));
		saveErrors(request.getSession(), errors);
	}

	/**
	 * 直接输出.
	 * 
	 * @param contentType
	 *            内容的类型.html,text,xml的值见后，json为"text/x-json;charset=UTF-8"
	 */
	protected void render(HttpServletResponse response, String text,
			String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出纯字符串.
	 */
	protected void renderText(HttpServletResponse response, String text) {
		render(response, text, "text/plain;charset=UTF-8");
	}

	/**
	 * 直接输出纯HTML.
	 */
	protected void renderHtml(HttpServletResponse response, String text) {
		render(response, text, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出纯XML.
	 */
	protected void renderXML(HttpServletResponse response, String text) {
		render(response, text, "text/xml;charset=UTF-8");
	}
}
