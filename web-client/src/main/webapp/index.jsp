<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<title><spring:message code="hello"/></title>
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />" 
	</head>
	<body>
		
	
		<div class="container">
			<div class="row">
				<div class="panel panel-default col-md-8 col-md-offset-2">
					<div class="panel-heading" align="center"><h3><spring:message code="index.title"/></h3>
					<spring:message code="index.text"/>
						</div>
					<div class="col-md-8 col-md-offset-2" align="center">
						<a class="btn btn-success" href="<c:url value="/employees/" />" role="button"><spring:message code="start"/></a>
							</div>
				</div>
			</div>
		</div>
	</body>
</html>
