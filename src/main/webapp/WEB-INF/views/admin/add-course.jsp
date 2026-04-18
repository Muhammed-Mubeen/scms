<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Course</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>➕ Add Course</h3>
        <a href="${pageContext.request.contextPath}/admin/courses"
           class="btn btn-outline-secondary">← Back</a>
    </div>

    <div class="card" style="max-width:600px;">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/courses" method="post">
                <input type="hidden" name="action" value="add">

                <div class="mb-3">
                    <label class="form-label fw-semibold">Course Code</label>
                    <input type="text" name="courseCode" class="form-control"
                           placeholder="e.g. CS301" required>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Course Name</label>
                    <input type="text" name="courseName" class="form-control" required>
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
                <div class="mb-3">
                    <label class="form-label fw-semibold">Faculty ID</label>
                    <input type="number" name="facultyId" class="form-control"
                           placeholder="Faculty ID (add faculty module later)" value="0">
                    <small class="text-muted">Enter 0 for now — faculty assignment coming later</small>
                </div>
                <div class="row">
                    <div class="col mb-3">
                        <label class="form-label fw-semibold">Credits</label>
                        <select name="credits" class="form-select" required>
                            <c:forEach begin="1" end="6" var="i">
                                <option value="${i}">${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col mb-3">
                        <label class="form-label fw-semibold">Semester</label>
                        <select name="semester" class="form-select" required>
                            <c:forEach begin="1" end="8" var="i">
                                <option value="${i}">${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <button type="submit" class="btn text-white w-100"
                        style="background:#1a3a5c;">Add Course</button>
            </form>
        </div>
    </div>

<%@ include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>