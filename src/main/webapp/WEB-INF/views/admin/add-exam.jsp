<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Schedule Exam</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>➕ Schedule Exam</h3>
        <a href="${pageContext.request.contextPath}/admin/exams"
           class="btn btn-outline-secondary">← Back</a>
    </div>

    <div class="card" style="max-width:500px;">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/exams" method="post">

                <div class="mb-3">
                    <label class="form-label fw-semibold">Course</label>
                    <select name="courseId" class="form-select" required>
                        <option value="">-- Select Course --</option>
                        <c:forEach var="course" items="${courses}">
                            <option value="${course.courseId}">
                                ${course.courseCode} — ${course.courseName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Exam Type</label>
                    <select name="examType" class="form-select" required>
                        <option value="mid">Mid Term</option>
                        <option value="final">Final</option>
                        <option value="quiz">Quiz</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Total Marks</label>
                    <input type="number" name="totalMarks" class="form-control"
                           placeholder="e.g. 100" min="1" required>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Exam Date</label>
                    <input type="date" name="examDate" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Academic Year</label>
                    <input type="text" name="academicYear" class="form-control"
                           placeholder="e.g. 2024-25" required>
                </div>

                <button type="submit" class="btn text-white w-100"
                        style="background:#1a3a5c;">Schedule Exam</button>
            </form>
        </div>
    </div>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>