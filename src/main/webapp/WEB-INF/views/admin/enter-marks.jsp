<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Enter Marks</title>
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
                <h3>📝 Enter Marks — ${exam.courseName} (${exam.examType})</h3>
                <a href="${pageContext.request.contextPath}/admin/exams" class="btn btn-outline-secondary">← Back</a>
            </div>

            <div class="alert alert-info">
                Total Marks: <strong>${exam.totalMarks}</strong> |
                Date: <strong>${exam.examDate}</strong> |
                Academic Year: <strong>${exam.academicYear}</strong>
            </div>

            <form action="${pageContext.request.contextPath}/admin/marks" method="post">
                <input type="hidden" name="examId" value="${exam.examId}">

                <table class="table table-bordered table-hover">
                    <thead style="background:#1a3a5c; color:white;">
                        <tr>
                            <th>#</th>
                            <th>Roll No</th>
                            <th>Name</th>
                            <th>Marks Obtained (max: ${exam.totalMarks})</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${students}" varStatus="st">
                            <input type="hidden" name="studentIds" value="${s.studentId}">
                            <tr>
                                <td>${st.count}</td>
                                <td>${s.rollNumber}</td>
                                <td>${s.name}</td>
                                <td>
                                    <input type="number" name="marks_${s.studentId}"
                                        class="form-control form-control-sm" style="max-width:120px;" min="0"
                                        max="${exam.totalMarks}" step="0.5" value="${existingMap[s.studentId]}">
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <button type="submit" class="btn text-white px-5" style="background:#1a3a5c;">💾 Save Marks</button>
            </form>

            <%@ include file="footer.jsp" %>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>