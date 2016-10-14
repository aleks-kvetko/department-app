<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<title><spring:message code="form"/></title>
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />" 
	</head>
	<body>
	
	<c:url value="/employees/" var="actionURL" />
	
	<c:set var="formattedDate">
	<fmt:formatDate value="${employee.dateOfBirth}" pattern="dd-MM-yyyy" />
	</c:set>
	
		<nav role="navigation" class="navbar navbar-default">
			<div id="navbarCollapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="<c:url value="/employees" />"><spring:message code="employees"/></a></li>
					<li><a href="<c:url value="/departments" />"><spring:message code="departments"/></a></li>
					<li class="active"><a href="<c:url value="/employees/add" />"><spring:message code="employee.new"/></a></li>
					<li><a href="<c:url value="/departments/add" />"><spring:message code="department.new"/></a></li>
					<li><a href="<c:url value="/departments/salary" />"><spring:message code="salary.average"/></a></li>
				</ul>
			</div>
		</nav>
	
		<div class="container">
			<div class="row">
				<div class="panel panel-default col-md-8 col-md-offset-2">
					<div class="panel-body">
						<form:form role="form" class="form-horizontal" method="post" modelAttribute="employee" action="${actionURL}">
							<form:hidden path="id" />
							<div class="form-group">
								<label for="fullName" class="col-md-2 control-label"><spring:message code="fullname"/></label>
								<div class="controls col-md-6">
									<form:input path="fullName" type="text" class="form-control" id="fullName" value="${employee.fullName}" />
								</div>
								<div class="controls col-md-4">
									<form:errors path="fullName" cssClass="error text-danger"  />
								</div>
							</div>
							<div class="form-group">
								<label for="dateOfBirth" class="col-md-2 control-label"><spring:message code="dob"/></label>
								<div class="controls col-md-6">
									<form:input path="dateOfBirth" type="text" class="form-control" id="dateOfBirth" value="${formattedDate}" placeholder="dd-mm-yyyy" />
								</div>
								<div class="controls col-md-4">
									<form:errors path="dateOfBirth" cssClass="error text-danger"  />
								</div>
							</div>
							<div class="form-group">
								<label for="salary" class="col-md-2 control-label"><spring:message code="salary"/></label>
								<div class="controls col-md-6">
									<form:input path="salary" type="text" class="form-control" id="salary" value="${employee.salary}" />
								</div>
								<div class="controls col-md-4">
									<form:errors path="salary" cssClass="error text-danger"  />
								</div>
							</div>
							<div class="form-group">
								<label for="department" class="col-md-2 control-label"><spring:message code="department"/></label>
								<div class="controls col-md-6">
									<form:select path="departmentId" class="form-control">
										<c:forEach items="${departments}" var="department"  >
											<c:choose>
												<c:when test="${employee.departmentId eq department.id}">
													<form:option value="${department.id}" label="${department.name}" selected="true" />
												</c:when>
												<c:otherwise>
													<form:option value="${department.id}" label="${department.name}" />
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-offset-5 col-md-1">
									<c:choose>
										<c:when test="${employee.id == null}">
											<button type="submit" class="btn btn-default"><spring:message code="add"/></button>
										</c:when>
										<c:otherwise>
											<button type="submit" class="btn btn-default"><spring:message code="edit"/></button>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</form:form>
					</div>
				</div>						
			</div>
		</div>
	</body>
</html>
