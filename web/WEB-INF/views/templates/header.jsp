<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="logout">
    <p>${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></p>
</div>

<div class="logo">
    <h1>E M P L O Y E E<br></h1>
    <p>simple web project</p>
</div>
<nav>
    <ul class="main-menu">
        <li><a href="/WebApp/">Home</a></li>
        <li><a href="/WebApp/employees">Employees</a></li>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <li><a href="/WebApp/users">Users</a></li>
        </security:authorize>
        <li><a href="/WebApp/contacts">Contacts</a></li>
    </ul>
</nav>

<form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="POST">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>