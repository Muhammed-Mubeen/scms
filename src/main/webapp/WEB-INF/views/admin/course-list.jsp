<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Courses</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>📚 Courses</h3>
        <a href="${pageContext.request.contextPath}/admin/courses?action=add"
           class="btn text-white" style="background:#1a3a5c;">+ Add Course</a>
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
                <th>Code</th>
                <th>Course Name</th>
                <th>Department</th>
                <th>Faculty</th>
                <th>Credits</th>
                <th>Semester</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="c" items="${courses}" varStatus="st">
                <tr>
                    <td>${st.count}</td>
                    <td><span class="badge" style="background:#1a3a5c;">${c.courseCode}</span></td>
                    <td>${c.courseName}</td>
                    <td>${c.departmentName}</td>
                    <td>${c.facultyName}</td>
                    <td>${c.credits}</td>
                    <td>${c.semester}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/courses?action=edit&id=${c.courseId}"
                           class="btn btn-sm btn-warning">Edit</a>
                        <a href="${pageContext.request.contextPath}/admin/courses?action=delete&id=${c.courseId}"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('Delete this course?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty courses}">
                <tr><td colspan="8" class="text-center text-muted">No courses found.</td></tr>
            </c:if>
        </tbody>
    </table>

<%@ include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>