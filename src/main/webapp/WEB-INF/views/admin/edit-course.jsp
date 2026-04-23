<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Edit Course</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>

            <%@ include file="navbar.jsp" %>

                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h3>✏️ Edit Course</h3>
                    <a href="${pageContext.request.contextPath}/admin/courses" class="btn btn-outline-secondary">←
                        Back</a>
                </div>

                <div class="card" style="max-width:600px;">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/admin/courses" method="post">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="courseId" value="${course.courseId}">

                            <div class="mb-3">
                                <label class="form-label fw-semibold">Course Code</label>
                                <input type="text" name="courseCode" class="form-control" value="${course.courseCode}"
                                    required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Course Name</label>
                                <input type="text" name="courseName" class="form-control" value="${course.courseName}"
                                    required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Department</label>
                                <select name="departmentId" class="form-select" required>
                                    <c:forEach var="dept" items="${departments}">
                                        <option value="${dept.deptId}" ${dept.deptId==course.departmentId ? 'selected'
                                            : '' }>
                                            ${dept.deptName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Faculty ID (Optional)</label>
                                <input type="number" name="facultyId" class="form-control"
                                    value="${course.facultyId == 0 ? '' : course.facultyId}">
                            </div>
                            <div class="row">
                                <div class="col mb-3">
                                    <label class="form-label fw-semibold">Credits</label>
                                    <input type="number" name="credits" class="form-control" min="1" max="6"
                                        value="${course.credits}" required>
                                </div>
                                <div class="col mb-3">
                                    <label class="form-label fw-semibold">Semester</label>
                                    <select name="semester" class="form-select" required>
                                        <c:forEach begin="1" end="8" var="i">
                                            <option value="${i}" ${i==course.semester ? 'selected' : '' }>${i}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <button type="submit" class="btn text-white w-100" style="background:#1a3a5c;">Update
                                Course</button>
                        </form>
                    </div>
                </div>

                <%@ include file="footer.jsp" %>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>