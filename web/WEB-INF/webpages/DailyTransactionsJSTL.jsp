<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="message" value="${requestScope['com.webapps.servlets.DailyTransactionEntry.message']}"/>
<c:set var="dropDownList" value="${requestScope['com.webapps.servlets.DailyTransactionEntry.dropDownList']}"/>
<c:set var="noteDropDownList" value="${requestScope['com.webapps.servlets.DailyTransactionEntry.noteDropDownList']}"/>
<c:set var="session" scope="page" value="${sessionScope['com.webapps.servlets.DailyTransactionEntry.session']}"/>
<%--
<c:set var="debugParameters" value="${requestScope['com.webapps.servlets.DailyTransactionEntry.debugParameters']}"/>
<c:set var="debugRequest" value="${requestScope['com.webapps.servlets.DailyTransactionEntry.debugRequest']}"/>
<c:set var="debugSession" value="${requestScope['com.webapps.servlets.DailyTransactionEntry.debugSession']}"/>
--%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <!--
        <link rel="stylesheet" type="text/css" href="css/DailyTransactions.css">
        -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/DailyTransactions.css">
        
        <title>Daily Transactions</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
        </style>

        <script src="${pageContext.request.contextPath}/includes/js/DailyTransactions.js" type="text/javascript">
        </script>
    </head>
    <body>
        <c:set var="paramString" scope="page" value=""/>
        <c:set var="headString" scope="page" value=""/>
        <c:if test="${param['debugJSP'] != null && fn:toLowerCase(param['debugJSP']) eq 'true'}">
            <c:set var="paramString" scope="page" value="&debugJSP=True"/>
            <c:set var="headString" scope="page" value="Debug"/>
        </c:if>
        <h1 align="center">Daily Transaction Entry JSTL ${headString} </h1>
        <form action="<%= request.getContextPath() %>/DailyTransactionEntry?JSTL=True${paramString}" method="post" enctype="application/x-www-form-urlencoded" >
            <div style="width: 500px">
                <label for="tranDate" >Date: </label>
                <input type="date" id="tranDate" name="tran_date" required="required" placeholder="MM/DD/YYYY"/>
                <br />
                <label for="tranTime">Time: </label>
                <input type="time" id="tranTime" name="tran_time" required="required" placeholder="HH:MM AM or PM"/>
                <br/>
                <label for="tranAmount">Amount: </label>
                <input type="number" id="tranAmount" name="tran_amount" min=".01" max="9999.99" step=".01" required="required" placeholder="Amount .01-9999.99" />
                <br/>
                    <label for="selectAccount">Account: </label>
                    <select id="selectAccount" required name="tran_account" onchange="unhideInput();">
                        <c:forEach var="entry" items="${pageScope['dropDownList']}">
                            <option value="${entry.key}">${entry.value}</option>
                            <%--
                            <c:out escapeXml="<>" value="<option value=\"${entry.key}\">${entry.value}</option>"></c:out>
                            --%>
                        </c:forEach>
                            <option value="Other">Other</option>
                    </select>
                    <input style="display: inline; margin-left: 1%;" type="hidden" id="newAccount" name="tran_new_account" min="1" max="9999" maxlength="4" oninput="limitLength(this);" placeholder="Other Account Number" required="required"></input>
                <br/>
                    <label for="tranNote">Note: </label>
                    <select id="selectNote" required name="tran_note" onchange="unhideInputText();">
                        <c:forEach var="noteItem" items="${noteDropDownList}">
                            <option value="${noteItem}"> ${noteItem} </option>
                        </c:forEach>
                            <option value="Other">Other</option>
                    </select>
                    <input style="display: inline; margin-left: 1%;" type="hidden" id="newNote" name="tran_new_note" placeholder="Other Note" required="required"></input>
                    <!--
                    <input type="text" id="tranNote" name="tran_note" required="required" placeholder="Note-Required"/>
                    -->
            </div>
            <div class="my_content_container">
                <input class="submits" type="submit" value="Submit" name="Submit"/>
                <c:if test="${param['debugJSP'] != null && fn:toLowerCase(param['debugJSP']) eq 'true'}">
                    <input class="submits" type="submit" value="Debug" name="Debug"/>
                    <input class="submits" type="submit" value="Debug Error" name="DebugError"/>
                </c:if>
                <input class="submits" type="reset" onclick="hideInput()"/>
            </div>
            <div>
                <p class="message"> ${pageScope['message']} </p>
            </div>
        </form>
        <%@include file="/WEB-INF/webpages/DebugParametersJSTL.jsp" %> 
    </body>
</html>
