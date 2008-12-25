package org.vicalloy.codegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;
import org.springframework.util.Assert;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/*******************************************************************************
 * 代码生成器入口类
 * 
 * @author vicalloy
 * 
 */
public class Generator {

	@SuppressWarnings( { "unchecked", "unused" })
	private Class entity;

	private Config config;

	private EntityInfo entityInfo;

	@SuppressWarnings("unchecked")
	private Map valueMap = new HashMap();

	@SuppressWarnings("unchecked")
	public Generator(Config config, Class entity) {
		this.config = config;
		this.entity = entity;
		this.entityInfo = new EntityInfo(entity, config);
		valueMap.put("entityInfo", entityInfo);
		Assert.notNull(config.getBasePkg(), "必须设置basePkg");
		valueMap.put("config", config);
	}

	/***************************************************************************
	 * 生成业务类
	 */
	@SuppressWarnings("unchecked")
	public void genManage() throws IOException, TemplateException {
		Template t = config.getFreemarkerCfg().getTemplate(
				config.getServiceFlt());
		File afile = new File(config.getManageDir(entity) + "/"
				+ entityInfo.getEntity().getSimpleName() + "Manager.java");
		if (afile.exists()) {
			return;
		}
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(afile), config.getEncode()));
		t.process(valueMap, out);
	}

	/***************************************************************************
	 * 生成action类
	 */
	@SuppressWarnings("unchecked")
	public void genAction() throws IOException, TemplateException {
		Template t = config.getFreemarkerCfg().getTemplate(
				config.getActionFlt());
		File afile = new File(config.getActionDir(entity) + "/"
				+ entityInfo.getEntity().getSimpleName() + "Action.java");
		if (afile.exists()) {
			return;
		}
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(afile), config.getEncode()));
		t.process(valueMap, out);
	}

	/***************************************************************************
	 * 将对象添加到hibernate的模型映射
	 */
	@SuppressWarnings("unchecked")
	public void addAnnotatedClassesConfig() throws IOException,
			TemplateException, DocumentException {
		if (Utils.hasNode(config.getDataAccessContext_hibernate(),
				"/beans//bean/property[@name='annotatedClasses']/list/value[text()='"
						+ entityInfo.getEntity().getName() + "']")) {
			return;
		}
		Template t = config.getFreemarkerCfg().getTemplate(
				config.getAnnotatedClassesFlt());
		String s = Utils.processTemplate(t, config.getEncode(), valueMap);
		Utils.replaceFileStr(config.getDataAccessContext_hibernate(), config
				.getEncode(), "<!-- new_annotatedClasses -->", s);
	}

	/***************************************************************************
	 * 添加service配置
	 */
	@SuppressWarnings("unchecked")
	public void addServiceContextConfig() throws IOException,
			TemplateException, DocumentException {
		if (Utils.hasNode(config.getServiceContext(), "/beans//bean[@id='"
				+ entityInfo.getUncapitalizeServiceName() + "']")) {
			return;
		}
		Template t = config.getFreemarkerCfg().getTemplate(
				config.getSpringBeanServiceFlt());
		String s = Utils.processTemplate(t, config.getEncode(), valueMap);
		Utils.replaceFileStr(config.getServiceContext(), config.getEncode(),
				"<!-- new_bean -->", s);
	}

	/***************************************************************************
	 * 添加service配置
	 */
	@SuppressWarnings("unchecked")
	public void addActionContextConfig() throws IOException, TemplateException,
			DocumentException {
		if (Utils.hasNode(config.getSpringActionConfig(), "/beans/bean[@name='"
				+ entityInfo.getSpringBeanActionPath() + "']")) {
			return;
		}
		Template t = config.getFreemarkerCfg().getTemplate(
				config.getSpringBeanActionFlt());
		String s = Utils.processTemplate(t, config.getEncode(), valueMap);
		Utils.replaceFileStr(config.getSpringActionConfig(),
				config.getEncode(), "<!-- new_bean -->", s);
	}

	/***************************************************************************
	 * 添加service配置
	 */
	@SuppressWarnings("unchecked")
	public void addStrutsFormBeanConfig() throws IOException,
			TemplateException, DocumentException {
		if (Utils.hasNode(config.getStrutsConfig(),
				"/struts-config/form-beans/form-bean[@name='"
						+ entityInfo.getStrutsFormName() + "']")) {
			return;
		}
		Template t = config.getFreemarkerCfg().getTemplate(
				config.getStrutsFormBeanFlt());
		String s = Utils.processTemplate(t, config.getEncode(), valueMap);
		Utils.replaceFileStr(config.getStrutsConfig(), config.getEncode(),
				"<!-- new_form_bean -->", s);
	}

	/***************************************************************************
	 * 添加验证form的配置
	 */
	public void addValidationFormConfig() throws IOException,
			TemplateException, DocumentException {
		if (Utils.hasNode(config.getValidationFormConfig(),
				"/form-validation/formset/form[@name='"
						+ entityInfo.getStrutsFormName() + "']")) {
			return;
		}
		Template t = config.getFreemarkerCfg().getTemplate(
				config.getValidationFormFlt());
		String s = Utils.processTemplate(t, config.getEncode(), valueMap);
		Utils.replaceFileStr(config.getValidationFormConfig(), config
				.getEncode(), "<!-- new_form -->", s);
	}

	/***************************************************************************
	 * 生成查看列表的jsp
	 */
	@SuppressWarnings("unchecked")
	public void genListJsp() throws IOException, TemplateException {
		Template t = config.getFreemarkerCfg().getTemplate(
				config.getListJspFlt());
		File afile = new File(config.getJspDir(entityInfo) + "/"
				+ entityInfo.getUncapitalizeClassName() + "List.jsp");
		if (afile.exists()) {
			return;
		}
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(afile), config.getEncode()));
		t.process(valueMap, out);
	}

	/***************************************************************************
	 * 生成创建/编辑的jsp
	 */
	@SuppressWarnings("unchecked")
	public void genFormJsp() throws IOException, TemplateException {
		Template t = config.getFreemarkerCfg().getTemplate(
				config.getFormJspFlt());
		File afile = new File(config.getJspDir(entityInfo) + "/"
				+ entityInfo.getUncapitalizeClassName() + "Form.jsp");
		if (afile.exists()) {
			return;
		}
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(afile), config.getEncode()));
		t.process(valueMap, out);
	}

	public void genAll() throws IOException, TemplateException,
			DocumentException {
		this.genManage();
		this.genAction();
		this.addAnnotatedClassesConfig();
		this.addServiceContextConfig();
		this.addActionContextConfig();
		this.addStrutsFormBeanConfig();
		this.addValidationFormConfig();
		this.genFormJsp();
		this.genListJsp();
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException,
			TemplateException {
	}
}
