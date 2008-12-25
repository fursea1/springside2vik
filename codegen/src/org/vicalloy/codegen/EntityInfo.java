package org.vicalloy.codegen;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.vicalloy.codegen.ext.ModelExt;

/*******************************************************************************
 * 具体模型对象的相关信息
 * 
 * @author vicalloy
 * 
 */
public class EntityInfo {
	private Config config;

	/***************************************************************************
	 * 基础的实体对象类
	 */
	@SuppressWarnings("unchecked")
	private Class entity;
	
	private ModelExt entityExt;

	@SuppressWarnings("unchecked")
	public EntityInfo(Class entity, Config config) {
		this.entity = entity;
		this.config = config;
		this.entityExt = new ModelExt(entity);
	}

	public List<PropertyDescriptor> getPropertys() {
		Set<String> filter = new HashSet<String>();
		filter.add("class");
		filter.add("id");
		List<PropertyDescriptor> ret = new ArrayList<PropertyDescriptor>();
		PropertyDescriptor[] ps = BeanUtils.getPropertyDescriptors(entity);
		for (PropertyDescriptor p : ps) {
			if (!filter.contains(p.getName()))
				ret.add(p);
		}
		return ret;
	}

	/***************************************************************************
	 * 获取基础包名
	 * 
	 * @return
	 */
	public String getBasePackage() {
		return entity.getPackage().getName().replaceAll("\\.model", "");
	}

	/***************************************************************************
	 * 获取减去基础包名的包名以.开头，如.demo
	 * 
	 * @return
	 */
	public String getSubPackage() {
		String s = getBasePackage().replace(config.getBasePkg(), "");
		return s;
	}

	@SuppressWarnings("unchecked")
	public Class getEntity() {
		return entity;
	}

	/***************************************************************************
	 * 获得小写首字母的简单类名（不带包名）
	 * 
	 * @return
	 */
	public String getUncapitalizeClassName() {
		return StringUtils.uncapitalize(entity.getSimpleName());
	}

	public String getStrutsFormName() {
		return getUncapitalizeClassName() + "Form";
	}

	/***************************************************************************
	 * 获取struts对应的action路径
	 * 
	 * @return
	 */
	public String getSpringBeanActionPath() {
		String path = "/d_" + getSubPackage().substring(1).replace('.', '/')
				+ "/" + getUncapitalizeClassName();
		return path;
	}

	// --------------service--------------//
	/***************************************************************************
	 * 简单service名
	 */
	public String getSimpleServiceName() {
		return entity.getSimpleName() + "Manager";
	}

	/***************************************************************************
	 * 首字母小写的service名
	 * 
	 * @return
	 */
	public String getUncapitalizeServiceName() {
		return StringUtils.uncapitalize(this.getSimpleServiceName());
	}

	/***************************************************************************
	 * service所在的包
	 * 
	 * @return
	 */
	public String getServicePkg() {
		return getBasePackage() + ".service";
	}

	/***************************************************************************
	 * service的全名
	 * 
	 * @return
	 */
	public String getServiceName() {
		return getServicePkg() + "." + getSimpleServiceName();
	}

	// --------------action--------------//
	/***************************************************************************
	 * 简单action名
	 */
	public String getSimpleActionName() {
		return entity.getSimpleName() + "Action";
	}

	/***************************************************************************
	 * 首字母小写的action名
	 * 
	 * @return
	 */
	public String getUncapitalizeActionName() {
		return StringUtils.uncapitalize(this.getSimpleActionName());
	}

	/***************************************************************************
	 * action所在的包
	 * 
	 * @return
	 */
	public String getActionPkg() {
		return getBasePackage() + ".web";
	}

	/***************************************************************************
	 * action的全名
	 * 
	 * @return
	 */
	public String getActionName() {
		return getActionPkg() + "." + getSimpleActionName();
	}

	public ModelExt getEntityExt() {
		return entityExt;
	}
}