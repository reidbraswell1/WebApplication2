<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
                        xmlns:c="http://java.sun.com/jsp/jstl/core"
                        xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
                        xmlns:fn="http://java.sun.com/jsp/jstl/functions">
<c:if test="${param['debugJSP'] != null and fn:toLowerCase(param['debugJSP']) eq 'true'}">
    <h4>Debug Request Parameters</h4>
    <ol>
    <c:forEach varStatus="loopStatus" var="par" items="${paramValues}">
        <c:choose>
            <c:when test="${loopStatus.index == 0}">
                <c:set var="parameterString" scope="page" value="?${par.key}=${par.value[0]}"/>
            </c:when>
            <c:otherwise>
                <c:set var="parameterString" scope="page" value="${parameterString}&amp;${par.key}=${par.value[0]}"/>
            </c:otherwise>
        </c:choose>
        <li> <c:out value="${par.key}=${par.value[0]}"/> </li>
    </c:forEach>
    </ol>
    <h5>Parameter String = <c:out value="${parameterString}"/> </h5>

    <h4>Debug Request Attributes</h4>
        <ol>
            <c:forEach var="reqName" items="${requestScope}">
                <li> <c:out value="${reqName.key}"/> </li>
            </c:forEach>
        </ol>

    <h4>Debug Page Attributes</h4>
    <ol>
    <c:forEach var="pageName" items="${pageScope}">
        <li> <c:out value="${pageName.key}"/> </li>
    </c:forEach>
    </ol>                      

    <h4>Debug Session Attributes</h4>
    <ol>
    <c:forEach var="sesName" items="${sessionScope}">
        <li> <c:out value="${sesName.key}"/> </li>
    </c:forEach>
    </ol>
    
        <h4>Session Status = <span style="color:red"> <c:out value="${pageScope.session}"/> </span> </h4>
</c:if>
</jsp:root>
