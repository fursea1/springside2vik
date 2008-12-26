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
		<h1>权限信息（子权限）</h1>
		<html:form action="/d_security/subFunction.do" styleClass="form" onsubmit="return validateSubFunctionForm(this)">
			<input type="hidden" name="method" value="save"/>
			<html:hidden property="id"/>
			<table>
				<tr>
					<td><label>主权限</label></td>
					<td>
						<html:select property="masterFunctionId">
							<html:optionsCollection name="masterFunctions" value="value" label="label"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td><label>权限标识</label></td>
					<td>
						<html:text property="functionKey"/> 使用 主权限标识+字母 的格式。如：0001A
					</td>
				</tr>
				<tr>
					<td><label>描述</label></td>
					<td>
						<html:text property="descn"/>
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
<html:javascript formName="subFunctionForm" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
<%@ include file="/commons/disableFields.jsp" %>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>