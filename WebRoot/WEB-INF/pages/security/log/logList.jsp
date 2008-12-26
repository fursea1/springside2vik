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
		<h1>日志列表</h1>
		<%@ include file="/commons/messages.jsp" %>

<div id="filterDiv" style="text-align: left;">
		<html:form action="/d_security/log.do">
		<html:hidden property="ec_crd"/>
            操作内容: <html:text property="search_like_msg"/>
            <!-- 角色: -->
            &nbsp;
          <html:submit property="searchBtn" styleClass="button">搜索</html:submit>
		</html:form>
</div>

		<ec:table items="logs" var="log"
				retrieveRowsCallback="limit" 
				sortRowsCallback="limit"  
				action="${ctx}/d_security/log.do">
			<ec:exportXls fileName="UserList.xls" tooltip="Export Excel"/>
			<ec:row>
				<ec:column property="msg" title="操作内容"  />
				<ec:column property="operator.loginName" title="操作员"  sortable="false" />
				<ec:column property="operateDate" cell="date" format="yyyy-MM-dd hh:mm:ss" title="操作日志" />
			</ec:row>
		</ec:table>
	</div>
</div>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>