<jsp:root version="2.1" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:display="urn:jsptld:http://displaytag.sf.net" >
  <jsp:directive.page import="org.displaytag.sample.ReportList"/>
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:output doctype-root-element="html" doctype-system=""/>
  <html>
  <head>
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/displaytag.css"/>
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/DailyTransactionSearchResults.css"/>
  </head>
  <body>
      <div>
  <!--
  <display:table name="test2" pagesize="25" keepStatus="true" id="rowID" >
  -->
    <display:table name="sessionScope.test2" pagesize="25" keepStatus="true" id="rowID" defaultorder="descending" requestURI="${pageContext.request.contextPath}/TransactionSearchResults">
    <display:setProperty name="paging.banner.placement" value="bottom"/>
    <display:caption>Daily Transaction Search Results</display:caption>
    <display:column title="ROW" sortable="true" headerClass="sortable">
      <c:out value="${rowID_rowNum}"/>
    </display:column>
    <display:column property="date" title="DATE" sortable="true" headerClass="sortable" format="{0,date,MM/dd/yyyy}">
      
    </display:column>
   <display:column property="time" title="TIME" format="{0,time,EEE hh:MM aa}">
      
    </display:column>
    <display:column property="amount" title="AMOUNT" format="{0,number,currency}">
      
    </display:column>
         <display:column property="account" title="ACCOUNT">
      
    </display:column>
    <display:column property="note" title="NOTE">
      
    </display:column>
        <display:footer>
      <tr>
        <td></td>
        <td></td>
      </tr>
    </display:footer>
  </display:table>
  </div>
      <form action="${pageContext.request.contextPath}/DailyTransactionSearch" method="get" enctype="application/x-www-form-urlencoded" >
          <div class="my_content_container">
              <input class="submits" type="submit" value="Return to Search" name="Submit"/>
          </div>
      </form>
  </body>
  </html>
</jsp:root>