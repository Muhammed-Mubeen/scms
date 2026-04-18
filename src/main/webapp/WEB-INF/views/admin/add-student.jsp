<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>➕ Add Student</h3>
        <a href="${pageContext.request.contextPath}/admin/students"
           class="btn btn-outline-secondary">← Back</a>
    </div>

    <div class="card" style="max-width:600px;">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/students" method="post">
                <input type="hidden" name="action" value="add">

                <div class="mb-3">
                    <label class="form-label fw-semibold">Roll Number</label>
                    <input type="text" name="rollNumber" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Full Name</label>
                    <input type="text" name="name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Email</label>
                    <input type="email" name="email" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Phone</label>
                    <input type="text" name="phone" class="form-control">
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Department</label>
                    <select name="departmentId" class="form-select" required>
                        <option value="">-- Select Department --</option>
                        <c:forEach var="dept" items="${departments}">
                            <option value="${dept.deptId}">${dept.deptName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="row">
                    <div class="col mb-3">
                        <label class="form-label fw-semibold">Semester</label>
                        <select name="semester" class="form-select" required>
                            <c:forEach begin="1" end="8" var="i">
                                <option value="${i}">${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col mb-3">
                        <label class="form-label fw-semibold">Batch Year</label>
                        <input type="number" name="batchYear" class="form-control"
                               placeholder="e.g. 2022" min="2000" max="2099" required>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Login Password</label>
                    <input type="password" name="password" class="form-control"
                           placeholder="Student's login password" required>
                    <small class="text-muted">Student will use Roll Number as username</small>
                </div>

                <button type="submit" class="btn text-white w-100"
                        style="background:#1a3a5c;">Add Student</button>
            </form>
        </div>
    </div>

<%@ include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>