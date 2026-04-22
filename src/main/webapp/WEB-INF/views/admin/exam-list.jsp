<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exams</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>📊 Exams</h3>
        <a href="${pageContext.request.contextPath}/admin/exams?action=add"
           class="btn text-white" style="background:#1a3a5c;">+ Schedule Exam</a>
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
                <th>Course</th>
                <th>Type</th>
                <th>Total Marks</th>
                <th>Date</th>
                <th>Academic Year</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="e" items="${exams}" varStatus="st">
                <tr>
                    <td>${st.count}</td>
                    <td>${e.courseName}</td>
                    <td>
                        <span class="badge
                            <c:choose>
                                <c:when test="${e.examType == 'final'}">bg-danger</c:when>
                                <c:when test="${e.examType == 'mid'}">bg-warning text-dark</c:when>
                                <c:otherwise>bg-info text-dark</c:otherwise>
                            </c:choose>
                        ">
                            ${e.examType}
                        </span>
                    </td>
                    <td>${e.totalMarks}</td>
                    <td>${e.examDate}</td>
                    <td>${e.academicYear}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/marks?action=enter&examId=${e.examId}"
                           class="btn btn-sm btn-primary">Enter Marks</a>
                        <a href="${pageContext.request.contextPath}/admin/exams?action=delete&id=${e.examId}"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('Delete this exam?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty exams}">
                <tr>
                    <td colspan="7" class="text-center text-muted">
                        No exams scheduled yet.
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>