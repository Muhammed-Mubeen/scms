<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Student Dashboard</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>

            <nav class="navbar navbar-expand-lg navbar-dark"
                style="background: linear-gradient(135deg, #0ea5e9 0%, #06b6d4 100%);">
                <div class="container-fluid">
                    <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/student/dashboard">
                        🎓 SCMS — Student Portal
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

                <%-- Flash messages --%>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <div class="row mb-4">
                        <div class="col-md-8">
                            <h2>Welcome, ${student.name}! 👋</h2>
                            <p class="text-muted">Roll Number: <strong>${student.rollNumber}</strong> |
                                Department: <strong>${student.departmentName}</strong> |
                                Semester: <strong>${student.semester}</strong></p>
                        </div>
                    </div>

                    <%-- KPI Cards --%>
                        <div class="row g-4 mb-4">
                            <div class="col-md-3">
                                <div class="card text-white"
                                    style="background: linear-gradient(135deg, #0ea5e9 0%, #06b6d4 100%);">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">📋 Attendance</h5>
                                        <h2>${attendance}%</h2>
                                        <small>
                                            <c:choose>
                                                <c:when test="${attendanceValue >= 75}">✅ Good</c:when>
                                                <c:when test="${attendanceValue >= 60}">⚠️ Satisfactory</c:when>
                                                <c:otherwise>❌ Low</c:otherwise>
                                            </c:choose>
                                        </small>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="card text-white bg-success">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">🎯 CGPA</h5>
                                        <h2>${cgpa}</h2>
                                        <small>Out of 10</small>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="card text-white bg-warning">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">📚 Courses</h5>
                                        <h2>${marks.size()}</h2>
                                        <small>Enrolled</small>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="card text-white ${totalDue > 0 ? 'bg-danger' : 'bg-success'}">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">💰 Fees</h5>
                                        <h2>
                                            <c:choose>
                                                <c:when test="${totalDue > 0}">₹${totalDue}</c:when>
                                                <c:otherwise>No Dues</c:otherwise>
                                            </c:choose>
                                        </h2>
                                        <small>
                                            <c:choose>
                                                <c:when test="${totalDue > 0}">Pending Due</c:when>
                                                <c:otherwise>Fully Paid</c:otherwise>
                                            </c:choose>
                                        </small>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <%-- Course Marks --%>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card">
                                        <div class="card-header fw-bold" style="background: #0ea5e9; color: white;">
                                            📊 My Exam Results
                                        </div>
                                        <div class="card-body">
                                            <c:if test="${empty marks}">
                                                <p class="text-muted">No exam results yet.</p>
                                            </c:if>
                                            <c:if test="${not empty marks}">
                                                <div class="table-responsive">
                                                    <table class="table table-hover">
                                                        <thead>
                                                            <tr>
                                                                <th>Course</th>
                                                                <th>Exam Type</th>
                                                                <th>Marks</th>
                                                                <th>Grade</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="m" items="${marks}">
                                                                <tr>
                                                                    <td><strong>${m.courseName}</strong></td>
                                                                    <td>
                                                                        <span class="badge
                                                    <c:choose>
                                                        <c:when test=" ${m.examType=='final' }">bg-danger</c:when>
                                                                            <c:when test="${m.examType == 'mid'}">
                                                                                bg-warning text-dark</c:when>
                                                                            <c:otherwise>bg-secondary</c:otherwise>
                                                                            </c:choose>
                                                                            ">
                                                                            ${m.examType}
                                                                        </span>
                                                                    </td>
                                                                    <td>${m.marksObtained}/${m.totalMarks}</td>
                                                                    <td>
                                                                        <span class="badge
                                                    <c:choose>
                                                        <c:when test=" ${m.grade=='O' || m.grade=='A+' }">bg-success
                                                                            </c:when>
                                                                            <c:when test="${m.grade == 'F'}">bg-danger
                                                                            </c:when>
                                                                            <c:otherwise>bg-primary</c:otherwise>
                                                                            </c:choose>
                                                                            ">
                                                                            ${m.grade}
                                                                        </span>
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
                            </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>