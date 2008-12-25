<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<%@ include file="/commons/meta.jsp" %>
	<link href="${r"${ctx}"}/widgets/extremecomponents/extremecomponents.css" type="text/css" rel="stylesheet">
	<title>站点名称</title>
</head>

<body>
<div id="page">
	<div id="header">
		<h1>模块名称</h1>
	</div>

	<div id="content">
		<h1>内容说明</h1>
		<%@ include file="/commons/messages.jsp" %>
		
<div id="filterDiv" style="text-align: left;">
		<html:form action="${entityInfo.springBeanActionPath}.do">
		<html:hidden property="ec_crd"/>
            Id: <html:text property="search_id"/>
            &nbsp;
          <html:submit property="searchBtn" styleClass="button">搜索</html:submit>
		</html:form>
</div>
		
		<ec:table items="${entityInfo.uncapitalizeClassName}s" var="${entityInfo.uncapitalizeClassName}"
				retrieveRowsCallback="limit" 
				sortRowsCallback="limit"  
				action="${entityInfo.uncapitalizeClassName}.do">
			<ec:exportXls fileName="UserList.xls" tooltip="Export Excel"/>
			<ec:row>
				<#list entityInfo.propertys! as f>
				<ec:column property="${f.name}" title="${f.name}" <#if f.propertyType.simpleName == "Date">cell="date"</#if> />
				</#list>
				<vik:auth res="${entityInfo.entityExt.__masterFunctionId}B">
				<ec:column property="null" title="编辑" width="40" sortable="false" viewsAllowed="html">
					<a href="${entityInfo.uncapitalizeClassName}.do?method=edit&id=${r"${"}${entityInfo.uncapitalizeClassName}.id}">编辑</a>
				</ec:column>
				<ec:column property="null" title="删除" width="40" sortable="false" viewsAllowed="html">
					<a href="${entityInfo.uncapitalizeClassName}.do?method=delete&id=${r"${"}${entityInfo.uncapitalizeClassName}.id}">删除</a>
				</ec:column>
				</vik:auth>
			</ec:row>
		</ec:table>
	</div>

	<div>
		<button id="addbtn" onclick="location.href='${entityInfo.uncapitalizeClassName}.do?method=create'">添加</button>
	</div>
</div>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>