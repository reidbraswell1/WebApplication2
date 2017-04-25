<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.String"%>
<%@page import="java.util.Map"%>
<% Map<String, String> map = (Map<String,String>)request.getAttribute("dropDownValues"); %>
<% List<String> noteList = (List<String>)request.getAttribute("noteDropDownList"); %>
<% List<String> accountList = (List<String>)request.getAttribute("accountDropDownList"); %>
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

    </head>
    <body>
        <c:set var="paramString" scope="page" value=""/>
        <c:set var="headString" scope="page" value=""/>
        <c:if test="${param['debugJSP'] != null && fn:toLowerCase(param['debugJSP']) eq 'true'}">
            <c:set var="paramString" scope="page" value="?debugJSP=True"/>
            <c:set var="headString" scope="page" value="Debug"/>
        </c:if>        
        <h1 align="center">Daily Transaction Search <c:out value="${headString}"/> </h1> 
        
        <form action="<c:out value="${pageContext.servletContext.contextPath}"/>/DailyTransactionSearch<c:out value="${paramString}"/>" method="post" enctype="application/x-www-form-urlencoded" >
            <div style="width: 550px">
                <label>Start Date: </label>
                <input type="date" name="start_date" placeholder="MM/DD/YYYY"/>
                <br />
                <label>End Date: </label>
                <input type="date" name="end_date" placeholder="MM/DD/YYYY"/>
                <br/>
                <label>Account: </label>
                <select name="tran_account">
                    <c:forEach var="accountItem" items="${requestScope.accountDropDownList}">
                        <option value ="${accountItem}">${accountItem}</option>
                    </c:forEach>
                </select>
                <br/>
                <label>Note: </label>
                <select name="tran_note">
                    <c:forEach var="noteItem" items="${requestScope.noteDropDownList}">
                        <option value="${noteItem}">${noteItem}</option>
                    </c:forEach>
                </select>
                <br/>
                <label>Items / Page: </label>
                <select name="tran_items_page">
                   <c:forEach begin="0" end="30" step="5" var="i">
                       <option value="<c:out value="${i}"/>"> <c:out value="${i}"/> </option>
                   </c:forEach>
                </select>                
            </div>
            <div style="width: 415px; border: 1px solid red;">
                <p style="font-weight: bold; text-align: center;">Search By</p>
                <label for="cbox1">Date ?</label>
                <input type="checkbox" id="cbox1" name="date_search" value="true">
                <label for="cbox2">Account ?</label>
                <input type="checkbox" id="cbox2" name="account_search" value="true">
                <label for="cbox3">Note ?</label>
                <input type="checkbox" id="cbox3" name="note_search" value="true">
            </div>
            <div class="my_content_container">
                <input class="submits" type="submit" value="Submit" name="Submit"/>
                <c:if test="${param['debugJSP'] != null && fn:toLowerCase(param['debugJSP']) eq 'true'}">
                    <input class="submits" type="submit" value="Debug" name="Debug"/>
                    <input class="submits" type="submit" value="Debug Error" name="DebugError"/>
                </c:if>
            </div>
            <div>
                <span class="message"> <c:out value="${requestScope.message}"/> </span>
            </div>
        </form>
        <%@include file="/WEB-INF/webpages/DebugParametersJSTL.jsp" %>
    </body>
</html>
