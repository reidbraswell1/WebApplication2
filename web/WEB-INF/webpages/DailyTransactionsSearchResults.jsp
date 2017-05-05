<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
                        xmlns:c="http://java.sun.com/jsp/jstl/core"
                        xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
                        xmlns:fn="http://java.sun.com/jsp/jstl/functions"
                        xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <!--
  <jsp:directive.page import="org.displaytag.sample.ReportList"/>
  -->
  
  <jsp:directive.page import="java.util.Enumeration"/>
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:output doctype-root-element="html" doctype-system=""/>
  
  <c:set var="message"      value="${sessionScope['com.webapps.servlets.DailyTransactionSearchServlet.message']}"/>
  <c:set var="itemsPage"    scope="request" value="${sessionScope['com.webapps.servlets.DailyTransactionSearchServlet.tranItemsPage']}"/>
  <c:set var="debugRequest" value="${requestScope['com.webapps.servlets.DailyTransactionSearchServlet.debugRequest']}"/>
  <c:set var="debugSession" value="${requestScope['com.webapps.servlets.DailyTransactionSearchServlet.debugSession']}"/>
  <c:set var="resultSet"    scope="page" value="${sessionScope['com.webapps.servlets.DailyTransactionSearchServlet.resultSet']}"/>
  
  <fmt:parseNumber var="pgSizeInt" value="${itemsPage}"/>
  
  
  <html>
  <head>
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/displaytag.css"/>
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/DailyTransactionSearchResults.css"/>
      <title>Daily Transaction Search Results</title>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
  </head>
  <body>
      <c:set var="paramString" scope="page" value=""/>
      <c:set var="headString" scope="page" value=""/>
      <c:if test="${param['debugJSP'] != null and fn:toLowerCase(param['debugJSP']) eq 'true'}">
          <c:set var="paramString" scope="page" value="debugJSP=True"/>
          <c:set var="headString" scope="page" value="Debug"/>
      </c:if>
      <h1 class="h1"> Daily Transaction Search Results <c:out value="${headString}"/> </h1>

      <!--
      <h3> itemsPage=${itemsPage} Page size=${pgSizeInt} </h3>
      -->
      
      <h4 class="h4">Search Parameters: ${message}</h4>
  <!--
  <jsp:scriptlet> request.setAttribute( "test", new ReportList(10) ); </jsp:scriptlet>
  -->
  <div>
  <display:table name="pageScope.resultSet" pagesize="${pageScope.pgSizeInt}" varTotals="myTotals" keepStatus="true" id="rowID" requestURI="${pageContext.request.contextPath}/TransactionSearchResults" decorator="org.displaytag.decorator.TotalTableDecorator" >
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:caption>Daily Transaction Search Results</display:caption>
    <display:column title="ROW">
      <c:out value="${rowID_rowNum}"/>
    </display:column>
    <display:column property="date" title="DATE" sortable="true" format="{0,date,MM/dd/yyyy}">
      
    </display:column>
   <display:column property="time" title="TIME" format="{0,time,EEE hh:MM aa}">
      
    </display:column>
    <display:column property="amount" title="AMOUNT" format="{0,number,currency}" total="true">
      
    </display:column>
         <display:column property="account" title="ACCOUNT">
      
    </display:column>
    <display:column property="note" title="NOTE">

    </display:column>
    <display:footer>    
      <tr>
          <td>Total</td>
          <td></td>
          <td></td>
          <td><fmt:setLocale value="en_US"/> <fmt:formatNumber value="${myTotals.column4}" type="currency"/></td>
      </tr>
    </display:footer>
    
  </display:table>
  </div>
    
    <form action="${pageContext.request.contextPath}/DailyTransactionSearch" method="get" enctype="application/x-www-form-urlencoded" >
        <div class="my_content_container">
            <input class="submits" type="submit" value="Return To Search" name="Submit"/>
                <c:if test="${param['debugJSP'] != null and fn:toLowerCase(param['debugJSP']) eq 'true'}">
                    <input type="hidden" name="debugJSP" value="True"/>
                </c:if>
        </div>
    </form>
  
  <jsp:include page="/WEB-INF/webpages/DebugParametersJSTL_XML.jsp" />

  </body>
  </html>
</jsp:root>
