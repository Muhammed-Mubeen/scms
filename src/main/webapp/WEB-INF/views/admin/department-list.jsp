<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Departments</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>🏛 Departments</h3>
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

    <%-- Add department form --%>
    <div class="card mb-4" style="max-width:400px;">
        <div class="card-body">
            <h6 class="card-title">Add New Department</h6>
            <form action="${pageContext.request.contextPath}/admin/departments" method="post">
                <div class="input-group">
                    <input type="text" name="deptName" class="form-control"
                           placeholder="e.g. CSE" required>
                    <button class="btn text-white" style="background:#1a3a5c;">Add</button>
                </div>
            </form>
        </div>
    </div>

    <%-- Department table --%>
    <table class="table table-bordered table-hover" style="max-width:500px;">
        <thead style="background:#1a3a5c; color:white;">
            <tr>
                <th>#</th>
                <th>Department Name</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="dept" items="${departments}" varStatus="s">
                <tr>
                    <td>${s.count}</td>
                    <td>${dept.deptName}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty departments}">
                <tr><td colspan="2" class="text-center text-muted">No departments yet.</td></tr>
            </c:if>
        </tbody>
    </table>

<%@ include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>