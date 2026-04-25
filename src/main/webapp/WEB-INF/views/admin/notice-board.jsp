<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Notice Board</title>
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
                <h3>ðŸ”” Notice Board</h3>
                <c:if test="${sessionScope.role == 'admin'}">
                    <a href="${pageContext.request.contextPath}/admin/notices?action=add" class="btn text-white"
                        style="background:#1a3a5c;">+ Post Notice</a>
                </c:if>
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

                <%-- Notice cards --%>
                    <c:if test="${empty notices}">
                        <div class="alert alert-info">No notices posted yet.</div>
                    </c:if>

                    <c:forEach var="n" items="${notices}">
                        <div class="card mb-3">
                            <div class="card-header d-flex justify-content-between
                        align-items-center" style="background:#f0f4f8;">
                                <div>
                                    <span class="fw-bold fs-6">${n.title}</span>
                                    <c:if test="${not empty n.departmentName}">
                                        <span class="badge ms-2" style="background:#1a3a5c;">
                                            ${n.departmentName}
                                        </span>
                                    </c:if>
                                    <c:if test="${empty n.departmentName}">
                                        <span class="badge bg-secondary ms-2">
                                            All Departments
                                        </span>
                                    </c:if>
                                </div>
                                <div class="d-flex align-items-center gap-3">
                                    <small class="text-muted">${n.createdAt}</small>
                                    <small class="text-muted">by ${n.postedByName}</small>
                                    <c:if test="${sessionScope.role == 'admin'}">
                                        <a href="${pageContext.request.contextPath}/admin/notices?action=delete&id=${n.noticeId}"
                                            class="btn btn-sm btn-outline-danger"
                                            onclick="return confirm('Delete this notice?')">
                                            ðŸ—‘
                                        </a>
                                    </c:if>
                                </div>
                            </div>
                            <div class="card-body">
                                <p class="mb-0">${n.content}</p>
                            </div>
                        </div>
                    </c:forEach>

                    <%@ include file="footer.jsp" %>
                        <script
                            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>