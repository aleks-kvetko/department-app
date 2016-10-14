<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<title><spring:message code="form"/></title>
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />" 
	</head>
	<body>
	
	<c:url value="/departments/" var="actionURL" />
	
		<nav role="navigation" class="navbar navbar-default">
			<div id="navbarCollapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="<c:url value="/employees" />"><spring:message code="employees"/></a></li>
					<li><a href="<c:url value="/departments" />"><spring:message code="departments"/></a></li>
					<li><a href="<c:url value="/employees/add" />"><spring:message code="employee.new"/></a></li>
					<li class="active"><a href="<c:url value="/departments/add" />"><spring:message code="department.new"/></a></li>
					<li><a href="<c:url value="/departments/salary" />"><spring:message code="salary.average"/></a></li>
				</ul>
			</div>
		</nav>
	
		<div class="container">
			<div class="row">
				<div class="panel panel-default col-md-6 col-md-offset-3">
					<div class="panel-body">
						<form:form role="form" class="form-horizontal" method="post" modelAttribute="department" action="${actionURL}">
							<form:hidden path="id" />
							<div class="form-group">
								<label for="name" class="col-md-1 control-label"><spring:message code="name"/></label>
								<div class="controls col-md-7">
									<form:input path="name" type="text" class="form-control" id="name" value="${department.name}" />
								</div>
								<div class="controls col-md-4">
									<form:errors path="name" cssClass="error text-danger"  />
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-offset-5 col-md-1">
									<c:choose>
										<c:when test="${department.id == null}">
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
