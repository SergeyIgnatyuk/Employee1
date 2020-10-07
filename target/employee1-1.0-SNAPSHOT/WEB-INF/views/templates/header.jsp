

<p class="logout">${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></p>

<div class="logo">
    <h1>E M P L O Y E E<br></h1>
    <p>simple web project</p>
</div>
<nav>
    <ul class="main-menu">
        <li><a href="#home">Home</a></li>
        <li><a href="#employees">Employees</a></li>
        <li><a href="#departments">Departments</a></li>
        <li><a href="#users">Users</a></li>
        <li><a href="#contacts">Contacts</a></li>
    </ul>
</nav>

<form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="POST" >
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>