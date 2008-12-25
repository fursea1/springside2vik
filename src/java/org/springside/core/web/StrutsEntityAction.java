package org.springside.core.web;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springside.core.dao.EntityDao;
import org.springside.core.exception.BusinessException;
import org.springside.core.utils.BeanUtils;
import org.springside.core.utils.GenericsUtils;

/**
 * 负责管理单个Entity CRUD操作的Struts Action基类. <p/> 子类以以下方式声明,并实现将拥有默认的CRUD函数 <p/>
 * 
 * <pre>
 *    public class UserAction extends StrutsEntityAction&lt;User, UserManager&gt; {
 *    	private UserManager userManager;
 *  &lt;p/&gt;
 *    	public void setUserManager(UserManager userManager) {
 *    		this.userManager = userManager;
 *    	}
 *    }
 * </pre>
 * 
 * <p/> 此类仅演示一种封装的方式，大家可按自己的项目习惯进行重新封装 <p/> 目前封装了：<br/>
 * 1.index、list、create、edit、view、save、delete 七种action的流程封装；<br/>
 * 2.doListEntity、doGetEntity、doNewEntity、doSaveEntity(),doDeleteEntity
 * 五种业务函数调用，可在子类重载；<br/>
 * 3.initEntity、initForm两个FormBean与业务对象的初始函数及refrenceData,onInitForm,onInitEntity
 * 三个回调函数；<br/> 4.savedMessage、deletedMessage 两种业务成功信息，可在子类重载。<br/>
 * 
 * @author calvin
 */
@SuppressWarnings( { "unchecked", "hiding" })
public abstract class StrutsEntityAction<T, M extends EntityDao<T>> extends
		StrutsAction implements InitializingBean {

	protected static final String LIST = "list";

	protected static final String EDIT = "edit";

	protected static final String VIEW = "view";

	protected Class<T> entityClass; // Action所管理的Entity类型.

	protected Class idClass; // Action所管理的Entity的主键类型.

	protected Set<String> disabledField = new HashSet<String>();// 修改时不可编辑字段

	protected String idName; // Action所管理的Entity的主键名.

	private M entityManager; // Action管理Entity所用的manager.

	/**
	 * 取得entityClass的函数. JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 获得EntityManager类进行CRUD操作,可以在子类重载.
	 */
	protected M getEntityManager() {
		Assert.notNull(entityManager, "Manager未能成功初始化");
		return entityManager;
	}

	/**
	 * Init回调函数,初始化一系列泛型参数.
	 */
	public void afterPropertiesSet() {
		// 根据T,反射获得entityClass
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());

		// 根据M,反射获得符合M类型的manager
		List<Field> fields = BeanUtils.getFieldsByType(this, GenericsUtils
				.getSuperClassGenricType(getClass(), 1));
		Assert.isTrue(fields.size() == 1,
				"subclass's has not only one entity manager property.");
		try {
			entityManager = (M) BeanUtils.forceGetProperty(this, fields.get(0)
					.getName());
			Assert.notNull(entityManager,
					"subclass not inject manager to action sucessful.");
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}

		// 反射获得entity的主键类型
		try {
			idName = entityManager.getIdName(entityClass);
			idClass = BeanUtils.getPropertyType(entityClass, idName);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
	}

	/**
	 * 显示Welcome页的Action函数. 默认跳转到{@link #list(ActionMapping,ActionForm,HttpServletRequest,HttpServletResponse) }
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return list(mapping, form, request, response);
	}

	/**
	 * 列出所有对象的Action函数.
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(getEntityListName(), doListEntity());
		return mapping.findForward(LIST);
	}

	/**
	 * 显示新建对象Form的Action函数. 默认跳到{@link #edit(ActionMapping,ActionForm,HttpServletRequest,HttpServletResponse)}}
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return edit(mapping, form, request, response);
	}

	/**
	 * 显示修改对象Form的Action函数.
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// 防止重复提交的token
		saveToken(request);
		T object = null;

		// 如果是修改操作，id!=null
		if (request.getParameter(idName) != null) {
			object = doGetEntity(form, request);
			if (object == null) {
				saveError(request, "entity.missing");
				return mapping.findForward(LIST);
			}
		} else {
			try {
				object = entityClass.newInstance();
			} catch (InstantiationException e) {
				log.error(e);
			} catch (IllegalAccessException e) {
				log.error(e);
			}
		}

		initForm(form, request, object);
		refrenceData(request);
		return mapping.findForward(EDIT);
	}

	/**
	 * 查看业务对象（不能修改）的Action函数.
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		T object = doGetEntity(form, request);
		if (object == null) {
			saveError(request, "entity.missing");
			return mapping.findForward("list");
		}
		request.setAttribute(getEntityName(), object);
		refrenceData(request);
		return mapping.findForward(VIEW);
	}

	/**
	 * 保存对象的Action函数.
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (isCancelled(request))
			return list(mapping, form, request, response);
		if (!isTokenValid(request)) {
			saveDirectlyError(request, "重复提交");
			return list(mapping, form, request, response);
			//return mapping.findForward(LIST);
		}
		resetToken(request);

		// run validation rules on this form
		ActionMessages errors = form.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			refrenceData(request);
			return mapping.findForward(EDIT);
		}

		T object;
		// 如果是修改操作，id is not blank
		if (StringUtils.isNotBlank(request.getParameter(idName))
				&& StringUtils.isBlank(request.getParameter("create__"))) {
			object = doGetEntity(form, request);
			if (object == null) {
				saveError(request, "entity.missing");
				//return mapping.findForward(LIST);
				return list(mapping, form, request, response);
			}
		} else { // 否则为新增操作
			object = doNewEntity(form, request);
		}
		try {
			// 将lazyform内容绑定到object
			initEntity(form, request, object);
			doSaveEntity(form, request, object);
			savedMessage(request, object);
			logSaveLog(request, object);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			saveDirectlyError(request, e.getMessage());
			initForm(form, request, object);
			refrenceData(request);
			// 防止重复提交的token
			saveToken(request);
			return mapping.findForward(EDIT);
		}
		return mapping.findForward(SUCCESS);
	}

	/***************************************************************************
	 * 更具id，获取对象的描述
	 * 
	 * @param id
	 * @return
	 */
	protected String getEntityDescn(Serializable id) {
		return id.toString();
	}

	/**
	 * 删除单个对象的Action函数.
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			doDeleteEntity(form, request);
			deletedMessage(request);
			logDeleteLog(request);
		} catch (DataIntegrityViolationException e) {
			saveDirectlyError(request, "当前数据〈"
					+ getEntityDescn(getEntityId(request)) + "〉与其他数据关联，删除失败");
		} catch (Exception e) {
			saveDirectlyError(request, e.getMessage());
		}
		return mapping.findForward(SUCCESS);
	}

	/**
	 * url参数未定义method时的默认Action函数. 默认为index Action.
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return index(mapping, form, request, response);
	}

	/***************************************************************************
	 * 在绑定对象前调用，可以为form设置一些初始值等。可以将不需要复制的字段设置成null。
	 */
	protected void beforInitEntity(ActionForm form, HttpServletRequest request,
			T object) {

	}

	/***************************************************************************
	 * 获取不需要进行绑定的熟悉字段
	 * 
	 * @param form
	 * @param request
	 * @return
	 */
	protected Set<String> getNotBindFields(ActionForm form,
			HttpServletRequest request) {
		return new HashSet<String>();
	}

	/**
	 * 保存Form表单时,初始化Entity对象的属性.
	 */
	protected void initEntity(ActionForm form, HttpServletRequest request,
			T object) {
		beforInitEntity(form, request, object);
		String[] notBindFields = getNotBindFields(form, request).toArray(
				new String[] {});
		bindEntity(form, object, notBindFields);
		onInitEntity(form, request, object);
	}

	/**
	 * 显示Form表单时，初始化Form对象的属性.
	 */
	protected void initForm(ActionForm form, HttpServletRequest request,
			T object) {
		bindForm(form, object);
		onInitForm(form, request, object);
	}

	/**
	 * 获取业务对象列表的函数.
	 */
	protected List<T> doListEntity() {
		return getEntityManager().getAll();
	}

	/**
	 * 新建业务对象的函数.
	 */
	protected T doNewEntity(ActionForm form, HttpServletRequest request) {
		T object = null;
		try {
			object = getEntityClass().newInstance();
		} catch (Exception e) {
			log.error("Can't new Instance of entity.", e);
		}
		return object;
	}

	/**
	 * 从数据库获取业务对象的函数.
	 */
	protected T doGetEntity(ActionForm form, HttpServletRequest request) {
		Serializable id = getEntityId(request);
		return getEntityManager().get(id);
	}

	/**
	 * 保存业务对象的函数.
	 */
	protected void doSaveEntity(ActionForm form, HttpServletRequest request,
			T object) {
		getEntityManager().save(object);
	}

	/**
	 * 删除业务对象的函数.
	 */
	protected void doDeleteEntity(ActionForm form, HttpServletRequest request) {
		Serializable id = getEntityId(request);
		getEntityManager().removeById(id);
	}

	/**
	 * form与list界面所需的参考对象注入.如categoryList,在子类重载.
	 */
	protected void refrenceData(HttpServletRequest request) {
		request.setAttribute("disabledField", disabledField);
	}

	/**
	 * 显示Form表单时的回调函数.为Form对象添加更多属性,在子类重载.
	 */
	protected void onInitForm(ActionForm form, HttpServletRequest request,
			T object) {
		request.setAttribute(getEntityName(), object);
	}

	/**
	 * 保存Form表单时的回调函数.为业务对象添加更多属性，在子类重载.
	 */
	protected void onInitEntity(ActionForm form, HttpServletRequest request,
			T object) {
	}

	/***************************************************************************
	 * 记录 编辑/新建 的日志
	 * 
	 * @param request
	 * @param object
	 */
	protected void logSaveLog(HttpServletRequest request, T object) {

	}

	/**
	 * 生成保存成功的信息.
	 */
	protected void savedMessage(HttpServletRequest request, T object) {
		saveMessage(request, "entity.saved");
	}

	/***************************************************************************
	 * 记录 删除 的日志
	 * 
	 * @param request
	 * @param object
	 */
	protected void logDeleteLog(HttpServletRequest request) {

	}

	/**
	 * 生成删除成功的信息.
	 */
	protected void deletedMessage(HttpServletRequest request) {
		saveMessage(request, "entity.deleted");
	}

	/**
	 * 获取所管理的对象名. 首字母小写，如"user".
	 */
	protected String getEntityName() {
		return StringUtils.uncapitalize(ClassUtils
				.getShortName(getEntityClass()));
	}

	/**
	 * 获取所管理的对象列表名. 首字母小写，如"users".
	 */
	protected String getEntityListName() {
		return StringUtils.uncapitalize(ClassUtils
				.getShortName(getEntityClass()))
				+ "s";
	}

	/**
	 * 从request中获得Entity的id，并判断其有效性.
	 */
	protected Serializable getEntityId(HttpServletRequest request) {
		String idString = request.getParameter(idName);
		try {
			return (Serializable) ConvertUtils.convert(idString, idClass);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Wrong when get id from request");
		}
	}
}
