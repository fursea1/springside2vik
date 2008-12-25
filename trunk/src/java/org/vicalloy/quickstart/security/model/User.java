package org.vicalloy.quickstart.security.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/*******************************************************************************
 * 操作员
 * 
 * @author vicalloy
 */
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 8090935304682303867L;

	private Long id;// 主键

	private Role role;// 所属角色

	private String loginName;// 登陆名

	private String pswd;// 密码

	private String descn;// 描述

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 255)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 255)
	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@ManyToOne()
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(nullable = false, length = 50)
	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	@Column(nullable = false, length = 50)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}