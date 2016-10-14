<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<title><spring:message code="employees"/></title>
		<script src="/web-client/resources/js/script.js"></script>
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />" 
	</head>
	<body onload="hide()">
	<spring:url value="/employees/search" var="actionURL" />
		
		<nav role="navigation" class="navbar navbar-default">
			<div id="navbarCollapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="<spring:url value="/employees" />"><spring:message code="employees"/></a></li>
					<li><a href="<c:url value="/departments" />"><spring:message code="departments"/></a></li>
					<li><a href="<c:url value="/employees/add" />"><spring:message code="employee.new"/></a></li>
					<li><a href="<c:url value="/departments/add" />"><spring:message code="department.new"/></a></li>
					<li><a href="<c:url value="/departments/salary" />"><spring:message code="salary.average"/></a></li>
				</ul>
			</div>
		</nav>


		<div class="container">
			<c:if test="${action == showResults}">
			<div class="row">
				<nav role="navigation" class="navbar navbar-default col-md-10 col-md-offset-1">
					<form:form method="post" modelAttribute="searchDateDTO" action="${actionURL}" cssClass="navbar-form" role="search">
						<div class="form-group">
							<label for="certainDate" id="certainLable" cssClass="col-md-2"></label>
							<form:input path="certainDate" type="date" id="certainDate" cssClass="form-control" maxlength="30"/>
						</div>
						<div class="form-group">
							<label for="startDate" id="startLable" cssClass="col-md-2"><spring:message code="from"/></label>
							<form:input path="startDate" type="date" id="startDate" cssClass="form-control" maxlength="30"/>
						</div>
						<div class="form-group">
							<label for="endDate" id="endLable" cssClass="col-md-2"><spring:message code="to"/></label>
							<form:input path="endDate" type="date" id="endDate" cssClass="form-control" maxlength="30" />
						</div>
						<div class="form-group ">
							<button type="submit" class="btn btn-default"><spring:message code="search"/></button>
						</div>
						<div class="radio-inline navbar-right">
							<label class="col-md-1">
							<input type="radio" name="optionsRadios" id="intervalRadio" onchange="hide()" >
							<spring:message code="date.interval"/>
							</label>
						</div>
						<div class="radio-inline navbar-right">
							<label class="col-md-1">
							<input type="radio" name="optionsRadios" id="certainRadio" onchange="hide()" checked>
							<spring:message code="date.certain"/>
							</label>
						</div>
					</form:form>
				</nav>
			</div>
			</c:if>
	
			<div class="row">
				<div class="col-md-1"></div>
					<div class="panel panel-default col-md-10">
						<div class="panel-heading"><h3><spring:message code="employees"/></h3></div>
						<table class="table table-condensed table-hover">
							<thead>
								<tr class="success">
									<th><spring:message code="fullname"/></th>
									<th><spring:message code="dob"/></th>
									<th><spring:message code="salary"/></th>
									<th><spring:message code="department"/></th>
									<th></th>
									<th></th>
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
							<spring:url value="/employees/delete/${employee.id}" var="delete" />
								<tr>
									<td><c:out value="${employee.fullName}" /></td>
									<td><c:out value="${formattedDate}" /></td>
									<td><c:out value="${employee.salary}" /></td>
									<td><c:out value="${employee.departmentName}" /></td>
									<c:if test="${action == showResults}">
									<td><a class="btn btn-default" href="<c:url value="/employees/edit/${employee.id}" />" role="button"><spring:message code="edit"/></a></td>
									<td>
										<form action="${delete}" method="POST">
											<button class="btn btn-default"><spring:message code="delete"/></button>
										</form>
									</td>
									</c:if>
								</tr>
							</c:forEach>
						</table>
					</div>
			</div>
		</div>
	</body>
</html>
