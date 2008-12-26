<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<%@ include file="/commons/meta.jsp" %>
	<title>SpringSide--++</title>
<script type="text/javascript">
 function selectAll(name, s) {
 	es=document.getElementsByName(name);
 	for (i=0;i<es.length;i++){
 		es[i].checked=s;
 	}
 }
</script>
</head>

<body>
<div id="page">
	<div id="header">
		<h1>权限管理</h1>
	</div>
	<%@ include file="/commons/messages.jsp" %>
	<div id="content">
		<h1>角色信息</h1>
		<html:form action="/d_security/role.do" focus="name" styleClass="form" onsubmit="return validateRoleForm(this)">
			<input type="hidden" name="method" value="save"/>
			<html:hidden property="id"/>
			<table width="100%">
				<tr>
					<td><label>角色名称</label></td>
					<td>
						<html:text property="name"/>
					</td>
				</tr>
				<tr>
					<td><label>描述</label></td>
					<td>
						<html:text property="descn"/>
					</td>
				</tr>
				<tr>
					<td><label>权限</label></td>
					<td>
<table width="100%" style="border:1px solid #CCCCCC;">
  <tr>
    <td width="40%">
      <input type="button" value="全选" onclick="selectAll('funcs', 'checked')" />
      <input type="button" value="取消所有" onclick="selectAll('funcs', '')"/>
    </td>
    <td width="60%">
    </td>
  </tr>
<%@ page import="org.vicalloy.quickstart.security.model.MasterFunction" %>
<%@ page import="org.vicalloy.quickstart.security.model.SubFunction" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<% 
List<Map> funcs = (List<Map>) request.getAttribute("functions");
for (Map m: funcs) {
	MasterFunction mf = (MasterFunction)m.get("master");
	request.setAttribute("t_mf", mf);
	List<SubFunction> sfs = (List<SubFunction>)m.get("subs");
	request.setAttribute("t_sfs", sfs);
%>
  <tr>
    <td width="40%">${t_mf.descn}/${t_mf.functionKey}</td>
    <td width="60%">
      <c:forEach var="t_sf" items="${t_sfs}">
        <input name="funcs" id="f_${t_sf.id}" value="${t_sf.id}" type="checkbox" />${t_sf.descn}/${t_sf.functionKey}<br/> 
      </c:forEach>
    </td>
  </tr>
<%}%>
</table>
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
<html:javascript formName="roleForm" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
<%@ include file="/commons/disableFields.jsp" %>
<%@ include file="/commons/footer.jsp" %>
<script type="text/javascript">
 function ck(id) {
 	x=document.getElementById(id);
 	x.checked='checked';
 }
 <c:forEach var="func" items="${role.functions}">
 ck('f_${func.id}');
 </c:forEach>
</script>
</body>
</html>