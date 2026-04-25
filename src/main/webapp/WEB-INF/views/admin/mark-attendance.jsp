<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Mark Attendance</title>
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
            </c:choose>

            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3>📋 Mark Attendance</h3>
                <a href="${pageContext.request.contextPath}/admin/attendance" class="btn btn-outline-secondary">←
                    Back</a>
            </div>

            <c:if test="${not empty students}">
                <div class="card mb-3" style="max-width:200px;">
                    <div class="card-body py-2 text-center">
                        <small class="text-muted">Date</small>
                        <div class="fw-bold">${date}</div>
                    </div>
                </div>

                <form action="${pageContext.request.contextPath}/admin/attendance" method="post">
                    <input type="hidden" name="courseId" value="${courseId}">
                    <input type="hidden" name="date" value="${date}">

                    <%-- Quick mark all buttons --%>
                        <div class="mb-3">
                            <button type="button" class="btn btn-sm btn-success" onclick="markAll('P')">✅ Mark All
                                Present</button>
                            <button type="button" class="btn btn-sm btn-danger ms-2" onclick="markAll('A')">❌ Mark All
                                Absent</button>
                        </div>

                        <table class="table table-bordered table-hover">
                            <thead style="background:#1a3a5c; color:white;">
                                <tr>
                                    <th>#</th>
                                    <th>Roll No</th>
                                    <th>Name</th>
                                    <th>Present</th>
                                    <th>Absent</th>
                                    <th>Leave</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="s" items="${students}" varStatus="st">
                                    <input type="hidden" name="studentIds" value="${s.studentId}">
                                    <tr>
                                        <td>${st.count}</td>
                                        <td>${s.rollNumber}</td>
                                        <td>${s.name}</td>
                                        <td class="text-center">
                                            <input type="radio" name="status_${s.studentId}" value="P"
                                                class="form-check-input status-radio" ${existingMap[s.studentId]=='P'
                                                ? 'checked' : '' } ${empty existingMap[s.studentId] ? 'checked' : '' }>
                                        </td>
                                        <td class="text-center">
                                            <input type="radio" name="status_${s.studentId}" value="A"
                                                class="form-check-input status-radio" ${existingMap[s.studentId]=='A'
                                                ? 'checked' : '' }>
                                        </td>
                                        <td class="text-center">
                                            <input type="radio" name="status_${s.studentId}" value="L"
                                                class="form-check-input status-radio" ${existingMap[s.studentId]=='L'
                                                ? 'checked' : '' }>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <button type="submit" class="btn text-white px-5" style="background:#1a3a5c;">💾 Save
                            Attendance</button>
                </form>
            </c:if>

            <c:if test="${empty students}">
                <div class="alert alert-info">
                    Select a course and date above to mark attendance.
                </div>
            </c:if>

            <%@ include file="footer.jsp" %>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
                <script>
                    function markAll(status) {
                        document.querySelectorAll('.status-radio').forEach(radio => {
                            if (radio.value === status) radio.checked = true;
                        });
                    }
                </script>
        </body>

        </html>