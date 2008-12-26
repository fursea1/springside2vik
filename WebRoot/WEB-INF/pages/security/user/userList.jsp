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
		<h1>用户信息</h1>
		<%@ include file="/commons/messages.jsp" %>

<div id="filterDiv" style="text-align: left;">
		<html:form action="/d_security/user.do">
		<html:hidden property="ec_crd"/>
            登录名: <html:text property="search_loginName"/>
            描述: <html:text property="search_like_descn"/>
            <!-- 角色: -->
            &nbsp;
          <html:submit property="searchBtn" styleClass="button">搜索</html:submit>
		</html:form>
</div>

		<ec:table items="users" var="user"
				retrieveRowsCallback="limit" 
				sortRowsCallback="limit"  
				  action="user.do">
			<ec:exportXls fileName="UserList.xls" tooltip="Export Excel"/>
			<ec:row>
				<ec:column property="loginName" title="登录名"  />
				<ec:column property="role.name" title="角色名称" sortable="false" />
				<ec:column property="descn" title="描述"  />
				<vik:auth res="002B">
				<ec:column property="null" title="编辑" width="40" sortable="false" viewsAllowed="html">
					<a href="user.do?method=edit&id=${user.id}">编辑</a>
				</ec:column>
				<ec:column property="null" title="删除" width="40" sortable="false" viewsAllowed="html">
					<a href="user.do?method=delete&id=${user.id}">删除</a>
				</ec:column>
				</vik:auth>
			</ec:row>
		</ec:table>
	</div>

	<div>
		<button id="addbtn" onclick="location.href='user.do?method=create'">添加</button>
	</div>
</div>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>