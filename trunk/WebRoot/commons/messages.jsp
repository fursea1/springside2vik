<%-- Error Messages --%>
<%@ include file="/commons/taglibs.jsp" %>
<table width="95%">
  <tr  align="center">
    <td>
		 <logic:messagesPresent>
			<div class="error">
				<html:messages id="error">
					${error}<br/>
				</html:messages>
			</div>
		</logic:messagesPresent>
		
		<%-- Success Messages --%>
		<logic:messagesPresent message="true">
			<div class="message">
				<html:messages id="message" message="true">
					${message}<br/>
				</html:messages>
			</div>
		</logic:messagesPresent>   
    </td>
  </tr>
</table>


