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
            <div class="card mb-2 mt-2">
                <div class="card-header">
                    All groups
                </div>
                <div class="card-body">
                    <table class="table table-sm table-hover">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="group" items="${groups}">
                            <tr>
                                <td>${group.id}</td>
                                <td>${group.name}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/groups/${group.getId()}/students" type="button" class="btn btn-primary">students</a>
                                    <a href="${pageContext.request.contextPath}/groups/to-update/${group.getId()}" type="button" class="btn btn-warning">edit</a>
                                    <a href="${pageContext.request.contextPath}/groups/delete/${group.getId()}" type="button" class="btn btn-danger">delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    </table>
                    </div>
                </div>
            </div>
            <div class="col-12">
                <div class="card mt-2 mb-2">
                    <div class="card-header">
                        <div>
                            <a href="${pageContext.request.contextPath}/groups/to-new" class="btn btn-primary">Add new one</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
