<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<%@ include file="/commons/meta.jsp" %>
	<title>站点名称</title>
</head>

<body>
<div id="page">
	<div id="header">
		<h1>模块名称</h1>
	</div>
	<%@ include file="/commons/messages.jsp" %>
	<div id="content">
		<h1>内容说明</h1>
		<html:form action="${entityInfo.springBeanActionPath}.do" styleClass="form" onsubmit="return validate${entityInfo.entity.simpleName}Form(this)">
			<input type="hidden" name="method" value="save"/>
			<html:hidden property="id"/>
			<table>
				<#list entityInfo.propertys! as f>
				<tr>
					<td><label>${f.name}</label></td>
					<td>
						<html:text property="${f.name}"/>
					</td>
				</tr>
				</#list>
			</table>
			<div>
				<html:submit property="saveBtn" styleClass="button">保存</html:submit>
				<html:cancel styleClass="button">取消</html:cancel>
			</div>
		</html:form>
	</div>
</div>
<html:javascript formName="${entityInfo.uncapitalizeClassName}Form" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
<script type="text/javascript" src="${r"${ctx}"}/scripts/validator.jsp"></script>
<%@ include file="/commons/disableFields.jsp" %>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>