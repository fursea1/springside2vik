package ${entityInfo.actionPkg};

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.vicalloy.quickstart.core.web.StrutsSecurityAction;
import ${entityInfo.entity.name};
import ${entityInfo.serviceName};

@SuppressWarnings("unchecked")
public class ${entityInfo.entity.simpleName}Action extends StrutsSecurityAction<${entityInfo.entity.simpleName}, ${entityInfo.simpleServiceName}> {

	public static final String FUNCTIONID = "${entityInfo.entityExt.__masterFunctionId}";
	{
		functionId = FUNCTIONID;
		functionDescn = "${entityInfo.entityExt.__descn}";
	}

	@SuppressWarnings("unused")
	private ${entityInfo.simpleServiceName} ${entityInfo.uncapitalizeServiceName};

	public void set${entityInfo.simpleServiceName}(${entityInfo.simpleServiceName} ${entityInfo.uncapitalizeServiceName}) {
		this.${entityInfo.uncapitalizeServiceName} = ${entityInfo.uncapitalizeServiceName};
	}
	
	@Override
	protected void refrenceData(HttpServletRequest request) {
		super.refrenceData(request);
	}

	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request,
			Object object) {
		${entityInfo.entity.simpleName} new_object = (${entityInfo.entity.simpleName}) object;
		super.onInitEntity(form, request, object);
	}

	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request,
			Object object) {
		${entityInfo.entity.simpleName} new_object = (${entityInfo.entity.simpleName}) object;
		super.onInitForm(form, request, object);
	}

}