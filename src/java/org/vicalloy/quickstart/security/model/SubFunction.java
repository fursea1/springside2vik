package org.vicalloy.quickstart.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SubFunction {
	private Long id;

	private String functionKey;// 权限ID,组成规则为主权限ID+字母。如主权限ID为 001，则子权限ID为： 001A
	// 001B

	private MasterFunction masterFunction;

	private String descn;// 描述

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne()
	public MasterFunction getMasterFunction() {
		return masterFunction;
	}

	public void setMasterFunction(MasterFunction masterFunction) {
		this.masterFunction = masterFunction;
	}

	@Column(nullable = false, length = 20)
	public String getFunctionKey() {
		return functionKey;
	}

	public void setFunctionKey(String functionKey) {
		this.functionKey = functionKey;
	}

	@Column(nullable = false, length = 255)
	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	/***************************************************************************
	 * 重载该方法，只使用functionKey做对象唯一性判断
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((functionKey == null) ? 0 : functionKey.hashCode());
		return result;
	}

	/***************************************************************************
	 * 重载该方法，只使用functionKey做对象唯一性判断
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SubFunction other = (SubFunction) obj;
		if (functionKey == null) {
			if (other.functionKey != null)
				return false;
		} else if (!functionKey.equals(other.functionKey))
			return false;
		return true;
	}
}
