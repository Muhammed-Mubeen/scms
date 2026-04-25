<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Faculty Dashboard</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>

            <nav class="navbar navbar-expand-lg navbar-dark"
                style="background: linear-gradient(135deg, #059669 0%, #10b981 100%);">
                <div class="container-fluid">
                    <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/faculty/dashboard">
                        🎓 SCMS — Faculty Portal
                    </a>
                    <div class="ms-auto"> <a href="${pageContext.request.contextPath}/admin/notices"
                            class="btn btn-outline-light btn-sm me-3">
                            ðŸ”” Notices
                        </a> <span class="text-white me-3">👤 ${sessionScope.loggedUser.username}</span>
                        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">
                            🚪 Logout
                        </a>
                    </div>
                </div>
            </nav>

            <div class="container-fluid py-4" style="background: #f8fafc; min-height: 100vh;">

                <%-- Error message --%>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <h2 class="mb-4">Welcome, Faculty Member! 👋</h2>

                    <%-- Quick Actions --%>
                        <div class="row g-4 mb-4">
                            <div class="col-md-4">
                                <div class="card text-white"
                                    style="background: linear-gradient(135deg, #059669 0%, #10b981 100%);">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">📚 My Courses</h5>
                                        <h2>${courseCount}</h2>
                                        <small>Courses assigned this semester</small>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="card text-white"
                                    style="background: linear-gradient(135deg, #2563eb 0%, #3b82f6 100%);">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">📋 Mark Attendance</h5>
                                        <p class="mt-3">
                                            <a href="${pageContext.request.contextPath}/admin/attendance"
                                                class="btn btn-light btn-sm">Go →</a>
                                        </p>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="card text-white"
                                    style="background: linear-gradient(135deg, #dc2626 0%, #ef4444 100%);">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">📝 Enter Marks</h5>
                                        <p class="mt-3">
                                            <a href="${pageContext.request.contextPath}/admin/marks"
                                                class="btn btn-light btn-sm">Go →</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <%-- Courses Table --%>
                            <div class="card">
                                <div class="card-header fw-bold" style="background: #059669; color: white;">
                                    📚 Assigned Courses
                                </div>
                                <div class="card-body">
                                    <c:if test="${empty courses}">
                                        <p class="text-muted">No courses assigned yet.</p>
                                    </c:if>
                                    <c:if test="${not empty courses}">
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Code</th>
                                                        <th>Course Name</th>
                                                        <th>Department</th>
                                                        <th>Credits</th>
                                                        <th>Semester</th>
                                                        <th>Students</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="course" items="${courses}">
                                                        <tr>
                                                            <td><strong>${course.courseCode}</strong></td>
                                                            <td>${course.courseName}</td>
                                                            <td>${course.departmentName}</td>
                                                            <td>${course.credits}</td>
                                                            <td>${course.semester}</td>
                                                            <td>
                                                                <span class="badge bg-info">View</span>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>