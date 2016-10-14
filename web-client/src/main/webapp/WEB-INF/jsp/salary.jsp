<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<title><spring:message code="salary.average"/></title>
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
					<li class="active"><a href="<c:url value="/departments/salary" />"><spring:message code="salary.average"/></a></li>
				</ul>
		</nav>
	
		<div class="container">
			<div class="row">
				<div class="panel panel-default col-md-6 col-md-offset-3">
					<div class="panel-heading"><h3><spring:message code="salary.average"/></h3></div>
					<table class="table table-condensed table-hover"  >
						<thead>
							<tr class="success">
								<th><spring:message code="department"/></th>
								<th><spring:message code="salary.average"/></th>
							</tr>
						</thead>
						<c:forEach items="${salaryView}" var="salaryView" >
							<tr>
								<td><c:out value="${salaryView.departmentName}" /></td>
								<td><c:out value="${salaryView.averageSalary}" /></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>
