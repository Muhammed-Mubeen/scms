<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Results</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>📊 Results — ${student.name}</h3>
        <a href="${pageContext.request.contextPath}/admin/marks"
           class="btn btn-outline-secondary">← Back</a>
    </div>

    <%-- CGPA card --%>
    <div class="card mb-4" style="max-width:250px;">
        <div class="card-body text-center">
            <small class="text-muted">Overall CGPA</small>
            <h1 class="
                <c:choose>
                    <c:when test="${cgpa >= 8.0}">text-success</c:when>
                    <c:when test="${cgpa >= 6.0}">text-warning</c:when>
                    <c:otherwise>text-danger</c:otherwise>
                </c:choose>
            ">
                ${cgpa}
            </h1>
            <small class="text-muted">${student.rollNumber}</small>
        </div>
    </div>

    <%-- Marks table --%>
    <table class="table table-bordered table-hover">
        <thead style="background:#1a3a5c; color:white;">
            <tr>
                <th>#</th>
                <th>Course</th>
                <th>Exam Type</th>
                <th>Marks Obtained</th>
                <th>Total Marks</th>
                <th>Percentage</th>
                <th>Grade</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="m" items="${marks}" varStatus="st">
                <c:set var="pct"
                       value="${m.totalMarks > 0 ? (m.marksObtained * 100 / m.totalMarks) : 0}"/>
                <tr>
                    <td>${st.count}</td>
                    <td>${m.courseName}</td>
                    <td>
                        <span class="badge
                            <c:choose>
                                <c:when test="${m.examType == 'final'}">bg-danger</c:when>
                                <c:when test="${m.examType == 'mid'}">bg-warning text-dark</c:when>
                                <c:otherwise>bg-info text-dark</c:otherwise>
                            </c:choose>
                        ">
                            ${m.examType}
                        </span>
                    </td>
                    <td>${m.marksObtained}</td>
                    <td>${m.totalMarks}</td>
                    <td>
                        <span class="
                            <c:choose>
                                <c:when test="${pct >= 60}">text-success fw-bold</c:when>
                                <c:when test="${pct >= 40}">text-warning fw-bold</c:when>
                                <c:otherwise>text-danger fw-bold</c:otherwise>
                            </c:choose>
                        ">
                            ${pct}%
                        </span>
                    </td>
                    <td>
                        <span class="badge
                            <c:choose>
                                <c:when test="${m.grade == 'O' or m.grade == 'A+'}">bg-success</c:when>
                                <c:when test="${m.grade == 'F'}">bg-danger</c:when>
                                <c:otherwise>bg-primary</c:otherwise>
                            </c:choose>
                        ">
                            ${m.grade}
                        </span>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty marks}">
                <tr>
                    <td colspan="7" class="text-center text-muted">
                        No marks found for this student.
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>