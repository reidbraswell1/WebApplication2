<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.String"%>
<%@page import="java.util.Map"%>
<c:set var="message" scope="page" value="${requestScope['com.webapps.servlets.DailyTransactionSearchServlet.message']}"/>
<c:set var="accountDropDownList" scope="page" value="${requestScope['com.webapps.servlets.AbstractServlet.accountDropDownList']}"/>
<c:set var="noteDropDownList" scope="page" value="${requestScope['com.webapps.servlets.AbstractServlet.noteDropDownList']}"/>
<c:set var="session" scope="page" value="${sessionScope['com.webapps.servlets.DailyTransactionSearchServlet.session']}"/>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/DailyTransactionSearch.css">
        <%--
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath/WEB-INF/webpages/DailyTransactions.css}">
        --%>
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
            <c:set var="paramString" scope="page" value="?debugJSP=True"/>
            <c:set var="headString" scope="page" value="Debug"/>
        </c:if>        
        <h1 align="center">Daily Transaction Search ${headString} </h1> 
        
        <form action="${pageContext.servletContext.contextPath}/DailyTransactionSearch${paramString}" method="post" enctype="application/x-www-form-urlencoded" >
            <div style="width: 550px">
                <label class="inlineBlock" for="startDate">Start Date: </label>
                <input type="date" id="startDate" name="start_date" placeholder="MM/DD/YYYY"/>
                <br />
                <label class="inlineBlock" for="endDate">End Date: </label>
                <input type="date" id="endDate" name="end_date" placeholder="MM/DD/YYYY"/>
                <br/>
                <label class="inlineBlock">Account: </label>
                <div class="tooltip">
                <select name="tran_account">
                    <c:forEach var="accountItem" items="${pageScope.accountDropDownList}">
                        <option value ="${accountItem}">${accountItem}</option>
                    </c:forEach>
                </select>
                <span class="tooltiptext">Select an account.</span>
                </div>
                <br/>
                <label class="inlineBlock" for="tranNote">Note: </label>
                <div class="tooltip">
                <select id="tranNote" name="tran_note">
                    <c:forEach var="noteItem" items="${pageScope.noteDropDownList}">
                        <option value="${noteItem}">${noteItem}</option>
                    </c:forEach>
                </select>
                <span class="tooltiptext">Select a note.</span>
                </div>
                <div class="tooltip">
                <input id="inputLike" type="hidden" name="searchLikeInput" placeholder="Enter your note"/>
                <span class="tooltiptext">Enter a partial string to search for.</span>
                </div>
                <br>

                <label class="inlineBlock" for="searchEqual">Equal</label>
                <input type="radio" id="searchEqual" name="tran_search" value="tran_searchEqual" checked="checked" onclick="hideInput2('inputLike','tranNote');"/>
                <label class="inline" for="searchLike">Like</label>
                <input type="radio" id="searchLike" name="tran_search" value="tran_searchLike" onclick="unhideInput2('inputLike','tranNote');"/>
                
                <br/>
                <label class="inlineBlock">Items / Page: </label>
                <select name="tran_items_page">
                   <c:forEach begin="0" end="30" step="5" var="i">
                       <option value="${i}"> ${i} </option>
                   </c:forEach>
                </select>                
            </div>
            <div style="width: 415px; border: 1px solid red;">
                <p style="font-weight: bold; text-align: center;">Search By</p>
                <label class="inlineBlock" for="cbox1">Date ?</label>
                <input type="checkbox" id="cbox1" name="date_search" value="true" onclick="setRequiredDate('startDate','endDate');">
                <label class="inline" for="cbox2">Account ?</label>
                <input type="checkbox" id="cbox2" name="account_search" value="true">
                <label class="inline" for="cbox3">Note ?</label>
                <input type="checkbox" id="cbox3" name="note_search" value="true" onclick="setRequiredNote('inputLike');">
            </div>
            <div class="my_content_container">
                <input class="submits" type="submit" value="Submit" name="Submit"/>
                <c:if test="${param['debugJSP'] != null && fn:toLowerCase(param['debugJSP']) eq 'true'}">
                    <input class="submits" type="submit" value="Debug" name="Debug"/>
                    <input class="submits" type="submit" value="Debug Error" name="DebugError"/>
                </c:if>
                <input class="submits" type="reset" onclick="hideInput2('inputLike','tranNote')"/>
            </div>
            <div>
                <span class="message"> ${pageScope.message} </span>
            </div>
        </form>
        <%@include file="/WEB-INF/webpages/DebugParametersJSTL.jsp" %>
    </body>
</html>
