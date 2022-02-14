<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    Students in group ${groupName}
                </div>
                <div class="card-body">
                    <table class="table table-sm table-hover">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Age</th>
                            <th>Group</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${studentsInGroup}">
                            <tr>
                                <td>${student.id}</td>
                                <td>${student.name}</td>
                                <td>${student.age}</td>
                                <td>${student.group.name}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/groups/${id}/students/to-update/${student.getId()}" type="button" class="btn btn-warning">edit</a>
                                    <a href="${pageContext.request.contextPath}/groups/${id}/students/delete/${student.getId()}" type="button" class="btn btn-danger">delete</a>
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
                            <a href="${pageContext.request.contextPath}/groups/${id}/students/to-new" class="btn btn-primary">Add new one</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
