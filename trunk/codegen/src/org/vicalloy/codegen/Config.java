package org.vicalloy.codegen;

import java.io.File;
import java.io.IOException;

import freemarker.template.Configuration;

/*******************************************************************************
 * 基础配置信息类
 * 
 * @author vicalloy
 * 
 */
public class Config {

	private String basePkg;

	/***************************************************************************
	 * 代码路径
	 */
	private String srcDir;
	{
		srcDir = getBasePath() + "/src/java";
	}

	private String resourcesDir;
	{
		resourcesDir = getBasePath() + "/src/resources";
	}

	/***************************************************************************
	 * web路径
	 */
	private String webDir;
	{
		webDir = getBasePath() + "/WebRoot";
	}

	/***************************************************************************
	 * 基础的jsp路径
	 */
	private String baseJspDir;
	{
		baseJspDir = getWebDir() + "/WEB-INF/pages";
	}

	private Configuration freemarkerCfg = null;

	private String encode = "UTF-8";

	// private String templateDir = "/";

	public Configuration getFreemarkerCfg() throws IOException {
		if (null == freemarkerCfg) {
			freemarkerCfg = new Configuration();
			freemarkerCfg.setDefaultEncoding(encode);
			freemarkerCfg
					.setDirectoryForTemplateLoading(new File(getFltPath()));
			// freemarkerCfg.setClassForTemplateLoading(this.getClass(),
			// templateDir);
		}
		return freemarkerCfg;
	}

	@SuppressWarnings("unchecked")
	public String getManageDir(Class entity) {
		String pk = entity.getPackage().getName();
		pk = pk.replace('.', '/');
		pk = pk.replace("/model", "/service");
		Utils.creatDirs(srcDir, pk);
		return srcDir + "/" + pk;
	}

	public String getBasePkgDir() {
		String pk = basePkg;
		pk = pk.replace('.', '/');
		// Utils.creatDirs(srcDir, pk);
		return srcDir + "/" + pk;
	}

	@SuppressWarnings("unchecked")
	public String getActionDir(Class entity) {
		String pk = entity.getPackage().getName();
		pk = pk.replace('.', '/');
		pk = pk.replace("/model", "/web");
		Utils.creatDirs(srcDir, pk);
		return srcDir + "/" + pk;
	}

	@SuppressWarnings("unchecked")
	public String getJspDir(EntityInfo e) {
		String pk = e.getSubPackage().replace('.', '/');
		String dir = baseJspDir + pk + "/" + e.getUncapitalizeClassName();
		Utils.creatDirs(dir);
		return dir;
	}

	/***************************************************************************
	 * 获取工程的基础路径
	 * 
	 * @return
	 */
	public String getBasePath() {
		String basePath = new File("").getAbsolutePath();
		return basePath;
	}

	/***************************************************************************
	 * 获取代码生成器的路径
	 * 
	 * @return
	 */
	public String getCodeGenBasePath() {
		return getBasePath() + "/codegen";
	}

	/***************************************************************************
	 * 获取模板路径
	 * 
	 * @return
	 */
	public String getFltPath() {
		return getCodeGenBasePath() + "/flt";
	}

	public String getActionFlt() {
		return "action.flt.java";
	}

	public String getIServiceFlt() {
		return "imanager.flt.java";
	}

	public String getServiceFlt() {
		return "manager.flt.java";
	}

	public String getAnnotatedClassesFlt() {
		return "annotated_classes.flt.xml";
	}

	public String getDataAccessContext_hibernate() {
		return resourcesDir + "/spring/dataAccessContext-hibernate.xml";
	}

	public String getSpringBeanServiceFlt() {
		return "spring_bean_service.flt.xml";
	}

	public String getServiceContext() {
		return resourcesDir + "/spring/serviceContext.xml";
	}

	public String getSpringBeanActionFlt() {
		return "spring_bean_action.flt.xml";
	}

	public String getSpringActionConfig() {
		return webDir + "/WEB-INF/modules/spring-config-action.xml";
	}

	public String getStrutsFormBeanFlt() {
		return "struts_form_bean_action.flt.xml";
	}

	public String getStrutsConfig() {
		return webDir + "/WEB-INF/modules/struts-config.xml";
	}

	public String getValidationFormFlt() {
		return "validation_form.flt.xml";
	}

	public String getValidationFormConfig() {
		return webDir + "/WEB-INF/modules/validation.xml";
	}

	public String getListJspFlt() {
		return "list.flt.jsp";
	}

	public String getFormJspFlt() {
		return "form.flt.jsp";
	}

	public String getEncode() {
		return encode;
	}

	public static void main(String args[]) throws IOException {

	}

	public String getBasePkg() {
		return basePkg;
	}

	public void setBasePkg(String basePkg) {
		this.basePkg = basePkg;
	}

	public String getWebDir() {
		return webDir;
	}

	public String getBaseJspDir() {
		return baseJspDir;
	}
}
