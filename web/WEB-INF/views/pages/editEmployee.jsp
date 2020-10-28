<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <%--@elvariable id="employee" type="com.model.Employee"--%>
    <form:form action="/WebApp/employees/${employee.id}/edit" method="POST" modelAttribute="employee">
        <h3>E D I T &nbsp; E M P L O Y E E</h3>
        <form:input type="text" name="firstName" placeholder="First Name" path="firstName" readonly="true"/>
        <form:input type="text" name="lastName" placeholder="Last Name" path="lastName" readonly="true"/>
        <form:input type="text" name="departmentId" placeholder="Department ID" path="departmentId" autofocus="true"/>
        <form:errors cssClass="has-error" path="departmentId"/>
        <form:input type="text" name="jobTitle" placeholder="Job Title" path="jobTitle"/>
        <form:errors cssClass="has-error" path="jobTitle"/>
        <form:input type="text" name="gender" placeholder="Gender" path="gender" readonly="true"/>
        <form:input type="text" name="dateOfBirth" placeholder="Date Of Birth" path="dateOfBirth" readonly="true"/>
        <input type="submit" value="S U B M I T">
    </form:form>
</div>