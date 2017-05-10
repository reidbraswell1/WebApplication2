<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.lang.String"%>
<%@page import="java.util.Map"%>
<% final String CLASS_NAME = "com.webapps.servlets.DailyTransactionEntry"; %>
<% final String CLASS_NAME2 = "com.webapps.servlets.AbstractServlet"; %>
<% String message = (String)request.getAttribute(CLASS_NAME.concat(".message")); %>
<% Map<String, String> map = (Map<String,String>)request.getAttribute(CLASS_NAME.concat(".dropDownList")); %>
<% List<String> noteList = (List<String>)request.getAttribute(CLASS_NAME2.concat(".noteDropDownList")); %>
<% Map<String, String> mapP = (Map<String,String>)request.getAttribute(CLASS_NAME.concat(".debugParameters")); %>
<% Enumeration el = (Enumeration)request.getAttribute(CLASS_NAME.concat(".debugRequest")); %>
<% Enumeration e11 = (Enumeration)request.getAttribute(CLASS_NAME.concat(".debugSession")); %>
<% String debugJSP=request.getParameter("debugJSP"); %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/DailyTransactions.css">
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
        <%
        String paramString="";
        String headString="";
        if(debugJSP != null && debugJSP.equalsIgnoreCase("true")) {
           paramString="&debugJSP=True";
           headString="Debug";
        }//if//
        %>
        <h1 align="center">Daily Transaction Entry <%= headString %> </h1>
                
        <form action="<%= request.getContextPath() %>/DailyTransactionEntry?JSTL=False<%= paramString %>" method="post" enctype="application/x-www-form-urlencoded" >
            <div style="width: 770px;">
                <label>Date: </label>
                <input type="date" name="tran_date" required="required" placeholder="MM/DD/YYYY"/>
                <br />
                <label>Time: </label>
                <input type="time" name="tran_time" required="required" placeholder="HH:MM AM or PM"/>
                <br/>
                <label>Amount: </label>
                <input type="number" name="tran_amount" min=".01" max="9999.99" step=".01" required="required" placeholder="Amount .01-9999.99" />
                <br/>
                    <label>Account: </label>
                    <select id="selectAccount" required name="tran_account" onchange="unhideInput();">
        <% for (Map.Entry<String, String> entry : map.entrySet()) 
           {
        %>                        
                        <option value="<%= entry.getKey() %>"> <%= entry.getValue() %> </option>
        <%                
           }
        %>
                        <option value="Other">Other</option>
                        
                    </select>
                    <input style="display: inline; margin-left: 1%;" type="hidden" id="newAccount" name="tran_new_account" min="1" max="9999" maxlength="4" oninput="limitLength(this);" placeholder="Other Account Number" required="required"></input>                     
                <br/>
                    <label>Note: </label>
                    <select id="selectNote" required name="tran_note" onchange="unhideInputText();">
        <% Iterator<String> noteListIterator = noteList.iterator();
           String temp = null;
           while(noteListIterator.hasNext()) 
           {
               temp=noteListIterator.next();
        %>
                        <option value="<%= temp %>"> <%= temp %> </option>
        <%
           }//while//
        %>
                        <option value="Other">Other</option>
                    </select>
                    <input style="display: inline; margin-left: 1%;" type="hidden" id="newNote" name="tran_new_note" placeholder="Other Note" required="required"></input>
                    <!--    
                    <input type="text" name="tran_note" required="required" placeholder="Note-Required"/>
                    -->
            </div>
            <div class="my_content_container">
                <input class="submits" type="submit" value="Submit" name="Submit"/>
                <% if(debugJSP != null && debugJSP.equalsIgnoreCase("true")) {
                    out.println("<input class=\"submits\" type=\"submit\" value=\"Debug\" name=\"Debug\"/>");
                    out.print("<input class=\"submits\" type=\"submit\" value=\"Debug Error\" name=\"DebugError\"/>");
                   }
                %>
                <input class="submits" type="reset" onclick="hideInput()"/>
            </div>
            <div>
                <p class="message"> <%= message %> </p>
            </div>
        </form>
        <%@include file="/WEB-INF/webpages/DebugParameters.jsp" %>
    </body>
</html>
