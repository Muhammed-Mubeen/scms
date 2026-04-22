<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Marks</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <h3 class="mb-4">📝 Marks & Results</h3>

    <%-- Flash messages --%>
    <c:if test="${not empty sessionScope.successMsg}">
        <div class="alert alert-success">${sessionScope.successMsg}</div>
        <c:remove var="successMsg" scope="session"/>
    </c:if>

    <div class="row g-4">

        <%-- Enter marks card --%>
        <div class="col-md-6">
            <div class="card h-100">
                <div class="card-header fw-bold" style="background:#1a3a5c; color:white;">
                    Enter Marks for Exam
                </div>
                <div class="card-body">
                    <p class="text-muted">Select an exam to enter student marks.</p>
                    <a href="${pageContext.request.contextPath}/admin/exams"
                       class="btn text-white" style="background:#1a3a5c;">
                        Go to Exams →
                    </a>
                </div>
            </div>
        </div>

        <%-- View results card --%>
        <div class="col-md-6">
            <div class="card h-100">
                <div class="card-header fw-bold" style="background:#2563eb; color:white;">
                    View Student Results
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/marks" method="get">
                        <input type="hidden" name="action" value="results">
                        <select name="studentId" class="form-select mb-3" required>
                            <option value="">-- Select Student --</option>
                            <c:forEach var="s" items="${students}">
                                <option value="${s.studentId}">
                                    ${s.rollNumber} — ${s.name}
                                </option>
                            </c:forEach>
                        </select>
                        <button type="submit" class="btn text-white w-100"
                                style="background:#2563eb;">View Results</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>