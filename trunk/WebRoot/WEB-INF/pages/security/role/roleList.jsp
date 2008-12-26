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
		<h1>角色列表</h1>
		<%@ include file="/commons/messages.jsp" %>
		<ec:table items="roles" var="role"
				retrieveRowsCallback="limit" 
				sortRowsCallback="limit"  
				  action="${ctx}/d_role.do">
			<ec:exportXls fileName="UserList.xls" tooltip="Export Excel"/>
			<ec:row>
				<ec:column property="name" title="角色名称"  />
				<ec:column property="descn" title="描述"  />
				<vik:auth res="001B">
				<ec:column property="null" title="编辑" width="40" sortable="false" viewsAllowed="html">
					<a href="role.do?method=edit&id=${role.id}">编辑</a>
				</ec:column>
				<ec:column property="null" title="删除" width="40" sortable="false" viewsAllowed="html">
					<a href="role.do?method=delete&id=${role.id}">删除</a>
				</ec:column>
				</vik:auth>
			</ec:row>
		</ec:table>
	</div>

	<div>
		<button id="addbtn" onclick="location.href='role.do?method=create'">添加</button>
	</div>
</div>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>