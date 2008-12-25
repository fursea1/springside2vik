package org.vicalloy.quickstart.security.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MasterFunction {
	private Long id;
	
	private String functionKey;// 主权限ID，使用数字组成 如：001

	private Set<SubFunction> subFunctions;// 属于这个分类的分类

	private String descn;// 描述

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(mappedBy = "masterFunction")
	public Set<SubFunction> getSubFunctions() {
		return subFunctions;
	}

	public void setSubFunctions(Set<SubFunction> subFunctions) {
		this.subFunctions = subFunctions;
	}

	@Column(nullable = false, length = 255)
	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(nullable = false, length = 20)
	public String getFunctionKey() {
		return functionKey;
	}

	public void setFunctionKey(String functionKey) {
		this.functionKey = functionKey;
	}
}
