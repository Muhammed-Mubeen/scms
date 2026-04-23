<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Post Notice</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>📢 Post New Notice</h3>
        <a href="${pageContext.request.contextPath}/admin/notices"
           class="btn btn-outline-secondary">← Back</a>
    </div>

    <div class="card" style="max-width:600px;">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/notices"
                  method="post">

                <div class="mb-3">
                    <label class="form-label fw-semibold">Title</label>
                    <input type="text" name="title"
                           class="form-control"
                           placeholder="Notice title" required>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Content</label>
                    <textarea name="content" class="form-control"
                              rows="5"
                              placeholder="Write notice content here..."
                              required></textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">
                        Department
                        <small class="text-muted fw-normal">
                            (leave blank to broadcast to all)
                        </small>
                    </label>
                    <select name="departmentId" class="form-select">
                        <option value="">-- All Departments --</option>
                        <c:forEach var="dept" items="${departments}">
                            <option value="${dept.deptId}">
                                ${dept.deptName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit"
                        class="btn text-white w-100"
                        style="background:#1a3a5c;">
                    📢 Post Notice
                </button>
            </form>
        </div>
    </div>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>