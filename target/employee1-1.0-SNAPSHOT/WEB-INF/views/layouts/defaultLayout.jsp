<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><tiles:getAsString name="title"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/addEmployee.css">
</head>
<body>
    <header class="header">
        <tiles:insertAttribute name="header"/>
    </header>
    <main class="content">
        <tiles:insertAttribute name="main"/>
    </main>
    <footer>
        <tiles:insertAttribute name="footer"/>
    </footer>
</body>
</html>
