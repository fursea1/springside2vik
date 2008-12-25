package org.vicalloy.quickstart.security.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.vicalloy.quickstart.security.model.User;


/**
 * 日志对象
 * 
 * @auth vicalloy
 */
@Entity
public class Log implements Serializable {

	private static final long serialVersionUID = 2115550268201299256L;

	private Long id;

	/***************************************************************************
	 * 消息
	 */
	private String msg;

	/***************************************************************************
	 * 操作时间
	 */
	private Timestamp operateDate = new Timestamp(new Date().getTime());

	/***************************************************************************
	 * 操作员
	 */
	private User operator;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 255)
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	 
	public Timestamp getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Timestamp operateDate) {
		this.operateDate = operateDate;
	}

	@ManyToOne()
	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

}
