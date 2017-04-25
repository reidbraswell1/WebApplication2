<%@page isErrorPage="true" %>
<%@page import="java.io.*" %>
<%--
<%@page contentType="text/plain" %>
--%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/error.css">
    </head>
    <body>
        <h3 style="text-align: center;">Oops An Exception Has Occurred !</h3>
        <br/>
        <p><span class="exmsg">Exception Message:</span> <%=exception.getMessage()%> </p>

        <p><span class="exmsg">StackTrace:</span><br/>
<%
	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	exception.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();
	out.println(stackTrace.replace(System.getProperty("line.separator"), "<br/>\n"));
	printWriter.close();
	stringWriter.close();
%>
    </p>
    </body>
</html>