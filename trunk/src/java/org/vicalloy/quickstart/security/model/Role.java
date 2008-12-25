package org.vicalloy.quickstart.security.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/*******************************************************************************
 * 角色
 * 
 * @author vicalloy
 * 
 */
@Entity
public class Role implements Serializable {
	private static final long serialVersionUID = -4573814027149051091L;

	private Long id;

	private String name;// 名称

	private String descn;// 描述

	private Set<SubFunction> functions;// 该角色所具备的资源

	@Column(length = 255)
	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany()
	public Set<SubFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<SubFunction> functions) {
		this.functions = functions;
	}
}