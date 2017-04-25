<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="java.util.Map"%>
<c:set var="message" value="${requestScope['com.webapps.servlets.DailyTransactionEntry.message']}"/>
<c:set var="dropDownList" value="${requestScope['com.webapps.servlets.DailyTransactionEntry.dropDownList']}"/>
<c:set var="session" scope="page" value="${sessionScope['com.webapps.servlets.DailyTransactionEntry.session']}"/>
<!DOCTYPE HTML>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/DailyTransactionsConfirm.css">    
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>        
        </style>
    </head>
    <body>
        <c:set var="paramString" scope="page" value=""/>
        <c:set var="headString" scope="page" value=""/>
        <c:if test="${param['debugJSP'] != null && fn:toLowerCase(param['debugJSP']) eq "true"}">
            <c:set var="paramString" scope="page" value="&debugJSP=True"/>
            <c:set var="headString" scope="page" value="Debug"/>
        </c:if>        
        <h1>Confirm Output JSTL <c:out value="${headString}"/> </h1>
        <div>
            <p>
                <h3>Output</h3
                <p>
                    <span class="message"> ${pageScope['message']} </span>
                </p>
            </p>
        </div>
        <div class="my_content_container">
            <form action="<%= request.getContextPath() %>/DailyTransactionEntry?JSTL=True<c:out value="${paramString}"/>" method="post" enctype="application/x-www-form-urlencoded">
                <input type="submit" value="Confirm" name="Confirm"/>
                <input type="submit" value="Cancel" name="Cancel"/>
                <input type="hidden" value="true" name="JSTL"/>
                
                <%--
                <input type="hidden" name="tran_date" value="<%= request.getAttribute("tran_date") %>" />
                <input type="hidden" name="tran_date_time_format" value="<%= request.getAttribute("tran_date_time_format") %>" />
                <input type="hidden" name="tran_time" value="<%= request.getAttribute("tran_time") %>" />
                <input type="hidden" name="tran_amount" value="<%= request.getAttribute("tran_amount") %>" />
                <input type="hidden" name="tran_account" value="<%= request.getAttribute("tran_account") %>" />
                <input type="hidden" name="tran_note" value="<%= request.getAttribute("tran_note") %>" />
                <input type="hidden" name="JSTL" value="True" />
                --%>
            </form>
            <%@include file="/WEB-INF/webpages/DebugParametersJSTL.jsp" %>
            <c:if test="${pageScope['debugRequest'] != null}">
                <h4>Debug Request Attributes</h4>
                <ol>
                <c:forEach var="requestName" items="${pageScope['debugRequest']}">
                    <li>${requestName}</li>
                </c:forEach>
                </ol>
            </c:if>
        </div>
</body>
</html>
