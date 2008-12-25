package org.vicalloy.codegen_demo.hello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.vicalloy.codegen.annotation.Descn__;
import org.vicalloy.codegen.annotation.MasterFunctionId__;

/*******************************************************************************
 * 演示用的用户类
 * 
 * @author vicalloy
 * 
 */
@Descn__(descn = "用户（演示）")
@MasterFunctionId__(masterFunctionId = "911")
@Entity
public class DemoUser {
	private Long id;// 主键

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