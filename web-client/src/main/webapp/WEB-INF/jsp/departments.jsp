<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
	<title><spring:message code="departments"/></title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />" 
	</head>
	
	<body>
		<nav role="navigation" class="navbar navbar-default">
			<div id="navbarCollapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="<c:url value="/employees" />"><spring:message code="employees"/></a></li>
					<li class="active"><a href="<c:url value="/departments" />"><spring:message code="departments"/></a></li>
					<li><a href="<c:url value="/employees/add" />"><spring:message code="employee.new"/></a></li>
					<li><a href="<c:url value="/departments/add" />"><spring:message code="department.new"/></a></li>
					<li><a href="<c:url value="/departments/salary" />"><spring:message code="salary.average"/></a></li>
				</ul>
			</div>
		</nav>
	
		<div class="container">
			<div class="row">
				<div class="panel panel-default col-md-6 col-md-offset-3">
					<div class="panel-heading"><h3><spring:message code="departments"/></h3></div>
					<table class="table table-condensed table-hover"  >
						<thead>
							<tr class="success">
								<th><spring:message code="name"/></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<c:forEach items="${departments}" var="department" >
						<spring:url value="/departments/delete/${department.id}" var="delete" />
							<tr>
								<td><c:out value="${department.name}" /></td>
								<td><a class="btn btn-default" href="<c:url value="/employees/department/${department.id}" />" role="button"><spring:message code="employees"/></a></td>
								<td><a class="btn btn-default" href="<c:url value="/departments/edit/${department.id}" />" role="button"><spring:message code="edit"/></a></td>
								<td>
									<form action="${delete}" method="POST">
										<button class="btn btn-default"><spring:message code="delete"/></button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>
