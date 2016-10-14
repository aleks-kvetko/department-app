<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
	<title><spring:message code="employees"/></title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />" 
	</head>
	<body>
		<nav role="navigation" class="navbar navbar-default">
			<div id="navbarCollapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="<c:url value="/employees" />"><spring:message code="employees"/></a></li>
					<li><a href="<c:url value="/departments" />"><spring:message code="departments"/></a></li>
					<li><a href="<c:url value="/employees/add" />"><spring:message code="employee.new"/></a></li>
					<li><a href="<c:url value="/departments/add" />"><spring:message code="department.new"/></a></li>
					<li><a href="<c:url value="/departments/salary" />"><spring:message code="salary.average"/></a></li>
				</ul>
			</div>
		</nav>

		<div class="container">
			<div class="row">
				<div class="panel panel-default col-md-10 col-md-offset-1">
					<div class="panel-heading"><h3>${department.name}</h3></div>
					<table class="table table-condensed table-hover"  >
						<thead>
							<tr class="success">
								<th><spring:message code="fullname"/></th>
								<th><spring:message code="dob"/></th>
								<th><spring:message code="salary"/></th>
							</tr>
						</thead>
						<c:if test="${empty employees}">
								<tr>
									<td><h3><spring:message code="notfound"/></h3></td>
								<tr>
						</c:if>
						<c:forEach items="${employees}" var="employee" >
							<c:set var="formattedDate">
							<fmt:formatDate value="${employee.dateOfBirth}" pattern="dd-MM-yyyy" />
							</c:set>
							<tr>
								<td><c:out value="${employee.fullName}" /></td>
								<td><c:out value="${formattedDate}" /></td>
								<td><c:out value="${employee.salary}" /></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>
