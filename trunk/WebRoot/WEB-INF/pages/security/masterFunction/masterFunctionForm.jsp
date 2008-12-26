<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<%@ include file="/commons/meta.jsp" %>
	<title>SpringSide--++</title>
</head>

<body>
<div id="page">
	<div id="header">
		<h1>权限管理</h1>
	</div>
	<%@ include file="/commons/messages.jsp" %>
	<div id="content">
		<h1>权限信息（主权限）</h1>
		<html:form action="/d_security/masterFunction.do" styleClass="form" onsubmit="return validateMasterFunctionForm(this)">
			<input type="hidden" name="method" value="save"/>
			<html:hidden property="id"/>
			<table>
				<tr>
					<td><label>权限标识</label></td>
					<td>
						<html:text property="functionKey"/> 使用数字进行标识，如001
					</td>
				</tr>
				<tr>
					<td><label>描述</label></td>
					<td>
						<html:textarea property="descn" rows="10" cols="40"/>
					</td>
				</tr>
			</table>
			<div>
				<html:submit property="saveBtn" styleClass="button">保存</html:submit>
				<html:cancel styleClass="button">取消</html:cancel>
			</div>
		</html:form>
	</div>
</div>
<html:javascript formName="masterFunctionForm" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
<%@ include file="/commons/disableFields.jsp" %>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>