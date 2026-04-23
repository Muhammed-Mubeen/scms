<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Faculty Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark" style="background: linear-gradient(135deg, #059669 0%, #10b981 100%);">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/faculty/dashboard">
            🎓 SCMS — Faculty Portal
        </a>
        <div class="ms-auto">
            <span class="text-white me-3">👤 ${sessionScope.loggedUser.username}</span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">
                🚪 Logout
            </a>
        </div>
    </div>
</nav>

<div class="container-fluid py-5">
    <h2 class="mb-4">📊 Faculty Dashboard</h2>

    <div class="row g-4">
        <div class="col-md-4">
            <div class="card text-white" style="background: linear-gradient(135deg, #059669 0%, #10b981 100%);">
                <div class="card-body text-center">
                    <h5 class="card-title">My Courses</h5>
                    <h2>${courses.size()}</h2>
                    <small>Active courses assigned</small>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card text-white bg-primary">
                <div class="card-body text-center">
                    <h5 class="card-title">Mark Attendance</h5>
                    <p class="mt-3">
                        <a href="${pageContext.request.contextPath}/faculty/attendance"
                           class="btn btn-light btn-sm">Go →</a>
                    </p>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card text-white bg-info">
                <div class="card-body text-center">
                    <h5 class="card-title">Enter Marks</h5>
                    <p class="mt-3">
                        <a href="${pageContext.request.contextPath}/faculty/marks"
                           class="btn btn-light btn-sm">Go →</a>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <h4 class="mt-5">My Assigned Courses</h4>
    <div class="table-responsive">
        <table class="table table-hover">
            <thead style="background: #059669; color: white;">
                <tr>
                    <th>Course Code</th>
                    <th>Course Name</th>
                    <th>Department</th>
                    <th>Credits</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="course" items="${courses}">
                    <tr>
                        <td><strong>${course.courseCode}</strong></td>
                        <td>${course.courseName}</td>
                        <td>${course.departmentName}</td>
                        <td>${course.credits}</td>
                        <td>
                            <a href="#" class="btn btn-sm btn-outline-primary">View Students</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>