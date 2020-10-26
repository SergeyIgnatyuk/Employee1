<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<table class="users">
    <tr>
        <th>I D</th>
        <th>U S E R N A M E</th>
        <th>R O L E</th>
        <th>A C T I O N</th>
    </tr>
    <jsp:useBean id="users" scope="request" type="java.util.List"/>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.username}"/></td>
            <c:forEach items="${user.roles}" var="role">
                <td>
                    <c:out value="${role.name}"/>
                </td>
                <td>
                    <c:if test="${role.name.equals('ROLE_ADMIN')}">
                        <form id="editForm" action="/WebApp/users/${user.username}/edit" method="GET"></form>
                        <a class="edit" onclick="document.forms['editForm'].submit()">edit</a>
                    </c:if>
                    <c:if test="${!role.name.equals('ROLE_ADMIN')}">
                        <form id="deleteForm" action="/WebApp/users/${user.username}/delete" method="POST"></form>
                        <a class="delete" onclick="document.forms['deleteForm'].submit()">delete</a>
                    </c:if>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>