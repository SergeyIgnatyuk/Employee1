<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<table class="users">
    <caption><h2>U S E R S</h2> &nbsp; table</caption>
    <tr>
        <th>I D</th>
        <th>U S E R N A M E</th>
        <th>R O L E</th>
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
            </c:forEach>
        </tr>
    </c:forEach>
</table>