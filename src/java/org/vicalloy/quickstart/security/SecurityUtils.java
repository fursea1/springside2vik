package org.vicalloy.quickstart.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.vicalloy.quickstart.Constants;
import org.vicalloy.quickstart.security.model.SubFunction;
import org.vicalloy.quickstart.security.model.User;

public class SecurityUtils {
	/***************************************************************************
	 * 是否有权限
	 * 
	 * @param request
	 * @param functionId
	 *            权限ID
	 * @return
	 */
	public static boolean hasRight(HttpServletRequest request, String functionId) {
		return hasRight(request.getSession(), functionId);
	}

	public static boolean hasRight(HttpSession session, String functionId) {
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		return hasRight(user, functionId);
	}

	/***************************************************************************
	 * 是否有权限
	 * 
	 * @param user
	 *            被检查的用户
	 * @param functionId
	 *            权限ID
	 * @return
	 */
	public static boolean hasRight(User user, String functionId) {
		/***********************************************************************
		 * <pre>
		 * if (true) {// 测试用
		 * 	return true;
		 * }
		 * </pre>
		 */

		try {
			if (user == null)
				return false;
			for (SubFunction f : user.getRole().getFunctions()) {
				if (f.getFunctionKey().equals(functionId)
						|| f.getFunctionKey().equals("000A")) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

}
