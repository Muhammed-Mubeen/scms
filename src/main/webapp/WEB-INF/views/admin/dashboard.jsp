<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <h3 class="mb-4">Dashboard</h3>

    <%-- Success / Error flash messages --%>
    <c:if test="${not empty sessionScope.successMsg}">
        <div class="alert alert-success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.errorMsg}">
        <div class="alert alert-danger">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
    </c:if>

    <div class="row g-3">
        <div class="col-md-3">
            <div class="card text-white" style="background:#1a3a5c;">
                <div class="card-body text-center">
                    <h5>👤 Students</h5>
                    <a href="${pageContext.request.contextPath}/admin/students"
                       class="btn btn-light btn-sm mt-2">Manage</a>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white" style="background:#2563eb;">
                <div class="card-body text-center">
                    <h5>📚 Courses</h5>
                    <a href="${pageContext.request.contextPath}/admin/courses"
                       class="btn btn-light btn-sm mt-2">Manage</a>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white" style="background:#1e40af;">
                <div class="card-body text-center">
                    <h5>🏛 Departments</h5>
                    <a href="${pageContext.request.contextPath}/admin/departments"
                       class="btn btn-light btn-sm mt-2">Manage</a>
                </div>
            </div>
        </div>
    </div>

<%@ include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>