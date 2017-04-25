<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/DailyTransactionsDebug.css">
        <title>Daily Transaction Search Debug</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
        </style>

    </head>
    <body>
        <h2 align="center">Daily Transaction Search Debug at <%= request.getContextPath() %> </h2>         
        <div>
            <h3>Request Parameters:</h3>
            <p><ol>
        <% Map<String, String[]> map = request.getParameterMap(); %>
        <% for (Map.Entry<String, String[]> entry : map.entrySet()) 
           {
        %>
         <li> <%= entry.getKey() + "=" + entry.getValue()[0] %> </li>
         <%
           }
         %>
            </ol></p>
            <h3>Request Attributes: </h3>
            <p><ol>
         <% Enumeration<String> e1 = request.getAttributeNames(); %>
         <% while(e1.hasMoreElements()) {
         %>
         <li> <%= e1.nextElement().toString() %>
         <%
             }
         %>      
            </ol></p>
            <h3>Servlet Context Attributes: </h3>
            <p><ol>
         <% Enumeration<String> e2 = request.getServletContext().getAttributeNames(); %>
         <% while(e2.hasMoreElements()) {
         %>
         <li> <%= e2.nextElement().toString() %>
         <%
             }
         %>      
            </ol></p>
          
            <h3>Request Headers:</h3>
            <p><ol>
        <% Enumeration<String> enumeration = request.getHeaderNames(); %>
        <%
        while (enumeration.hasMoreElements()) 
           {
               String header = enumeration.nextElement();
               String value  =  request.getHeader(header);
        %>
        <li> <%=header + "=" + value %> </li>
        <%
           }
        %>
            </ol></p>
    </div>
        
  
    </body>
</html>