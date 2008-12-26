<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<%@ include file="/commons/meta.jsp" %>
	<link href="${ctx}/widgets/extremecomponents/extremecomponents.css" type="text/css" rel="stylesheet">
	<title>SpringSide--++</title>
</head>

<body>
<div id="page">
	<div id="header">
		<h1>权限管理</h1>
	</div>

	<div id="content">
		<h1>权限列表（子权限）</h1>
		<%@ include file="/commons/messages.jsp" %>
		<ec:table items="subFunctions" var="subFunction"
				retrieveRowsCallback="limit" 
				sortRowsCallback="limit"  
				  action="${ctx}/d_security/subFunction.do">
			<ec:exportXls fileName="UserList.xls" tooltip="Export Excel"/>
			<ec:row>
				<ec:column property="masterFunction.descn" title="主权限"  />
				<ec:column property="functionKey" title="权限标识"  />
				<ec:column property="descn" title="描述"  />
				<ec:column property="null" title="编辑" width="40" sortable="false" viewsAllowed="html">
					<a href="subFunction.do?method=edit&id=${subFunction.id}">编辑</a>
				</ec:column>
				<ec:column property="null" title="删除" width="40" sortable="false" viewsAllowed="html">
					<a href="subFunction.do?method=delete&id=${subFunction.id}">删除</a>
				</ec:column>
			</ec:row>
		</ec:table>
	</div>

	<div>
		<button id="addbtn" onclick="location.href='subFunction.do?method=create'">添加</button>
	</div>
</div>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>