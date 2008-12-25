package org.vicalloy.quickstart.security.service;

import org.springside.core.dao.extend.HibernateEntityExtendDao;
import org.vicalloy.quickstart.security.model.Log;
import org.vicalloy.quickstart.security.model.User;


public class LogManager extends HibernateEntityExtendDao<Log> {

	/***************************************************************************
	 * 保存日志
	 * 
	 * @param userId
	 *            用户ID
	 * @param msg
	 *            消息 2007-6-1
	 */
	public void saveLog(User user, String msg) {
		Log log = new Log();
		log.setOperator(user);
		log.setMsg(msg);
		this.save(log);
	}

}
