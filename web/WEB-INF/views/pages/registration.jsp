<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>registration</title>
    <link href="${contextPath}/static/css/registration.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <form action="#registration" method="POST">
        <h2>Create your account</h2>
        <input type="text" name="username" placeholder="Username" required autofocus>
        <input type="password" name="password" placeholder="Password" required>
        <input type="password" name="confirmPassword" placeholder="Confirm your password" required>
        <input type="submit" value="S U B M I T">
    </form>
</div>
</body>
</html>
