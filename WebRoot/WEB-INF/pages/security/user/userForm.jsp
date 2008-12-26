<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<%@ include file="/commons/meta.jsp" %>
	<script src="${ctx}/scripts/prototype.js" type="text/javascript"></script>
	<title>SpringSide--++</title>
	<%
	request.setAttribute("id", request.getParameter("id"));
	%>
</head>

<body>
<div id="page">
	<div id="header">
		<h1>权限管理</h1>
	</div>
	<%@ include file="/commons/messages.jsp" %>
	<div id="content">
		<h1>用户信息</h1>
		<html:form action="/d_security/user.do" focus="loginName" styleClass="form" onsubmit="return validateForm(this);">
			<input type="hidden" name="method" value="save"/>
			<html:hidden property="id"/>
			<table>
				<tr>
					<td><label>登录名</label></td>
					<td>
						<html:text property="loginName"/>
					</td>
				</tr>
				<tr>
					<td><label>密码</label></td>
					<td>
                        <c:if test="${not empty id}">
                        	<a href="#" onclick="modpsw(this);">修改密码</a>
                        </c:if>
                        <div id="pswddiv" 
                        <c:if test="${not empty id}">style="display: none;"</c:if>
                        >
                            输入密码&nbsp;
                            <input type="password" name="pswd" size="26" value=""/>
                            <br>
                            再次输入&nbsp;
                            <input type="password" name="repeatpswd" size="26" value=""/>
                        </div>
					</td>
				</tr>
				<tr>
					<td><label>角色</label></td>
					<td>
					<html:select property="roleId">
						<html:optionsCollection name="roles" value="id" label="name"/>
					</html:select>
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
<html:javascript formName="userForm" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
<%@ include file="/commons/disableFields.jsp" %>
<%@ include file="/commons/footer.jsp" %>
<script language="javascript">
    function modpsw(item) {
        if (item.innerText == '修改密码') {
            item.innerText = '取消修改';
            $('pswddiv').show();
        } else {
            item.innerText = '修改密码';
            $('pswddiv').hide();
        }
    }
    function validateForm(form) {
    	if (bCancel) {//取消操作不做验证
    		return true;
    	}
    	var is_create=true;
        <c:if test="${not empty id}">
        is_create=false;
        </c:if>
    	if (!validateForm_Ext(form, is_create))
    		return false;
    	if (!validateUserForm(form))
            return false;
        return true;
    }
    function validateForm_Ext(form, is_create) {
        if ($('pswddiv').visible() || is_create) {
            var pswd = document.getElementsByName('pswd')[0].value;
            var repeatpswd = document.getElementsByName('repeatpswd')[0].value;
            if (pswd == '' || repeatpswd == '') {
                alert('密码不能为空!');
                return false;
            } else if (pswd != repeatpswd) {
                alert('两次密码输入不一致!');
                return false;
            } else {
                return true;
            }
        } else {
            document.getElementsByName('pswd')[0].value = '';
            document.getElementsByName('repeatpswd')[0].value = '';
            return true;
        }
    }
</script>
</body>
</html>