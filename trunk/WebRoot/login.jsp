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
		<h1>SpringSide--++</h1>
	</div>

	<div id="content">
		<h1>登录</h1>
		<%@ include file="/commons/messages.jsp" %>

<div id="filterDiv" style="text-align: left;">
		<html:form action="/d_security/user.do">
		<input type="hidden" name="method" value="login"/>
            登录名: <html:text property="loginName"/>
           密码: <input type="password" name="pswd" value=""/>
            &nbsp;
          <html:submit property="loginBtn" styleClass="button">登录</html:submit>
          <br/>管理员(admin/admin)，普通用户(user/user)
		</html:form>
</div>

	</div>

</div>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>