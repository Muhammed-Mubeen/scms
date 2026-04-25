<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Attendance</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>

            <c:choose>
                <c:when test="${sessionScope.role == 'admin'}">
                    <%@ include file="navbar.jsp" %>
                </c:when>
                <c:when test="${sessionScope.role == 'faculty'}">
                    <nav class="navbar navbar-expand-lg navbar-dark"
                        style="background: linear-gradient(135deg, #059669 0%, #10b981 100%);">
                        <div class="container-fluid">
                            <a class="navbar-brand fw-bold"
                                href="${pageContext.request.contextPath}/faculty/dashboard">ðŸŽ“ SCMS â€” Faculty
                                Portal</a>
                            <div class="ms-auto">
                                <a href="${pageContext.request.contextPath}/faculty/dashboard"
                                    class="btn btn-outline-light btn-sm me-2">Go to Dashboard</a>
                                <a href="${pageContext.request.contextPath}/admin/notices"
                                    class="btn btn-outline-light btn-sm me-2">ðŸ”” Notices</a>
                                <a href="${pageContext.request.contextPath}/logout"
                                    class="btn btn-outline-light btn-sm">Logout</a>
                            </div>
                        </div>
                    </nav>
                    <div class="container mt-4">
                </c:when>
                <c:when test="${sessionScope.role == 'student'}">
                    <nav class="navbar navbar-expand-lg navbar-dark"
                        style="background: linear-gradient(135deg, #0ea5e9 0%, #06b6d4 100%);">
                        <div class="container-fluid">
                            <a class="navbar-brand fw-bold"
                                href="${pageContext.request.contextPath}/student/dashboard">ðŸŽ“ SCMS â€” Student
                                Portal</a>
                            <div class="ms-auto">
                                <a href="${pageContext.request.contextPath}/student/dashboard"
                                    class="btn btn-outline-light btn-sm me-2">Go to Dashboard</a>
                                <a href="${pageContext.request.contextPath}/logout"
                                    class="btn btn-outline-light btn-sm">Logout</a>
                            </div>
                        </div>
                    </nav>
                    <div class="container mt-4">
                </c:when>
            </c:choose>

            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3>📋 Attendance</h3>
            </div>

            <%-- Flash messages --%>
                <c:if test="${not empty sessionScope.successMsg}">
                    <div class="alert alert-success">${sessionScope.successMsg}</div>
                    <c:remove var="successMsg" scope="session" />
                </c:if>
                <c:if test="${not empty sessionScope.errorMsg}">
                    <div class="alert alert-danger">${sessionScope.errorMsg}</div>
                    <c:remove var="errorMsg" scope="session" />
                </c:if>

                <%-- Mark attendance form --%>
                    <div class="card mb-4" style="max-width:500px;">
                        <div class="card-header fw-bold" style="background:#1a3a5c; color:white;">
                            Mark Attendance
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/admin/attendance" method="get">
                                <input type="hidden" name="action" value="mark">
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Select Course</label>
                                    <select name="courseId" class="form-select" required>
                                        <option value="">-- Select Course --</option>
                                        <c:forEach var="course" items="${courses}">
                                            <option value="${course.courseId}">
                                                ${course.courseCode} — ${course.courseName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Date</label>
                                    <input type="date" name="date" class="form-control" value="${today}" required>
                                </div>
                                <button type="submit" class="btn text-white w-100" style="background:#1a3a5c;">Go
                                    →</button>
                            </form>
                        </div>
                    </div>

                    <%@ include file="footer.jsp" %>
                        <script
                            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>