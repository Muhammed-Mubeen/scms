<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Fee</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>➕ Add Fee Record</h3>
        <a href="${pageContext.request.contextPath}/admin/fees"
           class="btn btn-outline-secondary">← Back</a>
    </div>

    <div class="card" style="max-width:500px;">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/fees"
                  method="post">
                <input type="hidden" name="action" value="add">

                <div class="mb-3">
                    <label class="form-label fw-semibold">Student</label>
                    <select name="studentId" class="form-select" required>
                        <option value="">-- Select Student --</option>
                        <c:forEach var="s" items="${students}">
                            <option value="${s.studentId}">
                                ${s.rollNumber} — ${s.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Fee Amount (₹)</label>
                    <input type="number" name="amount" class="form-control"
                           placeholder="e.g. 25000" min="1"
                           step="0.01" required>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Due Date</label>
                    <input type="date" name="dueDate" class="form-control" required>
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
                        <label class="form-label fw-semibold">Academic Year</label>
                        <input type="text" name="academicYear"
                               class="form-control"
                               placeholder="e.g. 2024-25" required>
                    </div>
                </div>

                <button type="submit" class="btn text-white w-100"
                        style="background:#1a3a5c;">Add Fee Record</button>
            </form>
        </div>
    </div>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>