<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<% final String CLASS_NAME = "com.webapps.servlets.DailyTransactionEntry"; %>
<% String debugJSP=request.getParameter("debugJSP"); %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/DailyTransactionsConfirm.css">    
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>        
        </style>
    </head>
    <body>
        <%
        String paramString="";
        String headString="";
        if(debugJSP != null && debugJSP.equalsIgnoreCase("true")) {
           paramString="&debugJSP=True";
           headString="Debug";
        }//if//
        %>        
        <h1>Confirm Output <%= headString %> </h1>
        <div>
            <p>
                <h3>Output</h3
                <p>
                    <span class="message"> <%= request.getAttribute(CLASS_NAME.concat(".message")) %> </span>
                </p>
            </p>
        </div>
        <div class="my_content_container">
            <form action="<%= request.getContextPath() %>/DailyTransactionEntry?JSTL=False<%= paramString %>" method="post" enctype="application/x-www-form-urlencoded">
                <input type="submit" value="Confirm" name="Confirm"/>
                <input type="submit" value="Cancel" name="Cancel"/>
            </form>
        </div>
        <%@include file="/WEB-INF/webpages/DebugParameters.jsp" %>
</body>
</html>