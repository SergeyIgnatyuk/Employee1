<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<table class="employees">
    <caption><h2>E M P L O Y E E S</h2> &nbsp; table</caption>
    <tr>
        <th>I D</th>
        <th>F i r s t &nbsp; N a m e</th>
        <th>L a s t &nbsp; N a m e</th>
        <th>D e p a r t m e n t &nbsp; I D</th>
        <th>J o b &nbsp; T i t l e</th>
        <th>G e n d e r</th>
        <th>D a t e &nbsp; o f &nbsp; B i r t h</th>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <th>A c t i o n</th>
        </security:authorize>
    </tr>
    <jsp:useBean id="employees" scope="request" type="java.util.List"/>
    <c:forEach items="${employees}" var="employee">
        <tr>
            <td><c:out value="${employee.id}"/></td>
            <td><c:out value="${employee.firstName}"/></td>
            <td><c:out value="${employee.lastName}"/></td>
            <td><c:out value="${employee.departmentId}"/></td>
            <td><c:out value="${employee.jobTitle}"/></td>
            <td><c:out value="${employee.gender}"/></td>
            <td><c:out value="${employee.dateOfBirth}"/></td>
            <td>
                <form:form id="editForm" action="/WebApp/employees/${employee.id}/edit" method="GET"
                           modelAttribute="employee"/>
                <form:form id="deleteForm" action="/WebApp/employees/${employee.id}/delete" method="POST"
                           modelAttribute="employee"/>
                <p>
                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <a class="edit" onclick="document.forms['editForm'].submit()">edit</a> | <a class="delete"
                                                                                                    onclick="document.forms['deleteForm'].submit()">delete</a>
                    </security:authorize>
                </p>
            </td>
        </tr>
    </c:forEach>
</table>