<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Students</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>👤 Students</h3>
        <a href="${pageContext.request.contextPath}/admin/students?action=add"
           class="btn text-white" style="background:#1a3a5c;">+ Add Student</a>
    </div>

    <%-- Flash messages --%>
    <c:if test="${not empty sessionScope.successMsg}">
        <div class="alert alert-success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.errorMsg}">
        <div class="alert alert-danger">${sessionScope.errorMsg}</div>
        <c:remove var="errorMsg" scope="session"/>
    </c:if>

    <table class="table table-bordered table-hover">
        <thead style="background:#1a3a5c; color:white;">
            <tr>
                <th>#</th>
                <th>Roll No</th>
                <th>Name</th>
                <th>Email</th>
                <th>Department</th>
                <th>Semester</th>
                <th>Batch</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="s" items="${students}" varStatus="st">
                <tr>
                    <td>${st.count}</td>
                    <td>${s.rollNumber}</td>
                    <td>${s.name}</td>
                    <td>${s.email}</td>
                    <td>${s.departmentName}</td>
                    <td>${s.semester}</td>
                    <td>${s.batchYear}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/students?action=edit&id=${s.studentId}"
                           class="btn btn-sm btn-warning">Edit</a>
                        <a href="${pageContext.request.contextPath}/admin/students?action=delete&id=${s.studentId}"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('Delete this student?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty students}">
                <tr><td colspan="8" class="text-center text-muted">No students found.</td></tr>
            </c:if>
        </tbody>
    </table>

<%@ include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>