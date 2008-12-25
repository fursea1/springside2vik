<%@ page contentType="text/html;charset=UTF-8" %>
            <div class="pkg">
            	<h3>自定义</h3>
                <div class="pkg-body">
                    <a class="ex" href="${ctx}/welcome.jsp">欢迎页面</a>
                </div>
            </div>
            <div class="pkg">
            	<h3>权限管理</h3>
                <div class="pkg-body">
                    <vik:auth res="002A"><a class="ex" href="${ctx}/d_security/user.do">用户管理</a></vik:auth>
                    <vik:auth res="001A"><a class="ex" href="${ctx}/d_security/role.do">角色管理</a></vik:auth>
                    <vik:auth res="003A"><a class="ex" href="${ctx}/d_security/log.do">日志管理</a></vik:auth>
                    <vik:auth res="000A"><a class="ex" href="${ctx}/d_security/masterFunction.do">权限管理（主权限）</a></vik:auth>
                    <vik:auth res="000A"><a class="ex" href="${ctx}/d_security/subFunction.do">权限管理（子权限）</a></vik:auth>
                </div>
            </div>