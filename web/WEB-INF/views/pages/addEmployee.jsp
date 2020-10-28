<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <%--@elvariable id="employee" type="com.model.Employee"--%>
    <form:form action="/WebApp/employees/addEmployee" method="POST" modelAttribute="employee">
        <h3>N E W &nbsp; E M P L O Y E E</h3>
        <form:input type="text" name="firstName" placeholder="First Name" path="firstName" autofocus="true"/>
        <form:errors cssClass="has-error" path="firstName"/>
        <form:input type="text" name="lastName" placeholder="Last Name" path="lastName"/>
        <form:errors cssClass="has-error" path="lastName"/>
        <form:input type="text" name="departmentId" placeholder="Department ID" path="departmentId"/>
        <form:errors cssClass="has-error" path="departmentId"/>
        <form:input type="text" name="jobTitle" placeholder="Job Title" path="jobTitle"/>
        <form:errors cssClass="has-error" path="jobTitle"/>
        <form:input type="text" name="gender" placeholder="Gender" path="gender"/>
        <form:errors cssClass="has-error" path="gender"/>
        <form:input type="text" name="dateOfBirth" placeholder="Date Of Birth" path="dateOfBirth"/>
        <form:errors cssClass="has-error" path="dateOfBirth"/>
        <input type="submit" value="S U B M I T">
    </form:form>
</div>
