<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-dark bg-dark justify-content-center">
        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/groups">Groups</a>
        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/students">Students</a>
    </nav>
    <div class="row">
        <div class="col-12">
            <div class="card mt-2">
                <div class="card-header">
                    <h4>New group</h4>
                </div>
                <div class="card-body">
                    <c:if test="${message != ''}">
                        <strong><c:out value="${message}"/></strong>
                    </c:if>
                    <form:form action = "${pageContext.request.contextPath}/groups/new" method = "post" modelAttribute="group">
                          <label for="name">name: </label>
                          <form:input path = "name" class="form-control"/>
                          <input type = "submit" value = "Submit" class="btn btn-primary mt-5"/>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>