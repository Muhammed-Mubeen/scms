<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Attendance</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>📋 Attendance History</h3>
        <a href="${pageContext.request.contextPath}/admin/attendance"
           class="btn btn-outline-secondary">← Back</a>
    </div>

    <%-- Percentage summary cards --%>
    <h5 class="mb-3">Attendance Percentage by Course</h5>
    <div class="row g-3 mb-4">
        <c:forEach var="entry" items="${percentageMap}">
            <div class="col-md-3">
                <div class="card text-center">
                    <div class="card-body">
                        <h6 class="card-title">${entry.key}</h6>
                        <h3 class="
                            <c:choose>
                                <c:when test="${entry.value >= 75}">text-success</c:when>
                                <c:when test="${entry.value >= 50}">text-warning</c:when>
                                <c:otherwise>text-danger</c:otherwise>
                            </c:choose>
                        ">
                            ${entry.value}%
                        </h3>
                        <small class="text-muted">
                            <c:choose>
                                <c:when test="${entry.value >= 75}">✅ Good</c:when>
                                <c:when test="${entry.value >= 50}">⚠️ Low</c:when>
                                <c:otherwise>❌ Critical</c:otherwise>
                            </c:choose>
                        </small>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <%-- Attendance detail table --%>
    <h5 class="mb-3">Detailed Records</h5>
    <table class="table table-bordered table-hover">
        <thead style="background:#1a3a5c; color:white;">
            <tr>
                <th>#</th>
                <th>Date</th>
                <th>Course</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="a" items="${attendanceList}" varStatus="st">
                <tr>
                    <td>${st.count}</td>
                    <td>${a.date}</td>
                    <td>${a.courseName}</td>
                    <td>
                        <c:choose>
                            <c:when test="${a.status == 'P'}">
                                <span class="badge bg-success">Present</span>
                            </c:when>
                            <c:when test="${a.status == 'A'}">
                                <span class="badge bg-danger">Absent</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-warning text-dark">Leave</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty attendanceList}">
                <tr>
                    <td colspan="4" class="text-center text-muted">
                        No attendance records found.
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>