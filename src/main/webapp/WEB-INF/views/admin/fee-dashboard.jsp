<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Fee Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>💰 Fee Management</h3>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/admin/fees?action=pending"
               class="btn btn-warning btn-sm">⚠️ Pending Only</a>
            <a href="${pageContext.request.contextPath}/admin/fees"
               class="btn btn-secondary btn-sm">All Fees</a>
            <a href="${pageContext.request.contextPath}/admin/fees?action=add"
               class="btn text-white btn-sm" style="background:#1a3a5c;">+ Add Fee</a>
        </div>
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

    <%-- Summary cards --%>
    <div class="row g-3 mb-4">
        <div class="col-md-3">
            <div class="card text-white" style="background:#1a3a5c;">
                <div class="card-body text-center">
                    <small>Total Collected</small>
                    <h4>₹ <fmt:formatNumber value="${totalCollected}"
                              maxFractionDigits="2"/></h4>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-danger">
                <div class="card-body text-center">
                    <small>Total Dues</small>
                    <h4>₹ <fmt:formatNumber value="${totalDues}"
                              maxFractionDigits="2"/></h4>
                </div>
            </div>
        </div>
    </div>

    <%-- Filter by student --%>
    <div class="card mb-3" style="max-width:400px;">
        <div class="card-body py-2">
            <form action="${pageContext.request.contextPath}/admin/fees"
                  method="get" class="d-flex gap-2">
                <input type="hidden" name="action" value="student">
                <select name="studentId" class="form-select form-select-sm">
                    <option value="">-- Filter by Student --</option>
                    <c:forEach var="s" items="${students}">
                        <option value="${s.studentId}">
                            ${s.rollNumber} — ${s.name}
                        </option>
                    </c:forEach>
                </select>
                <button type="submit"
                        class="btn btn-sm text-white"
                        style="background:#1a3a5c;">Go</button>
            </form>
        </div>
    </div>

    <%-- Fee table --%>
    <table class="table table-bordered table-hover">
        <thead style="background:#1a3a5c; color:white;">
            <tr>
                <th>#</th>
                <th>Student</th>
                <th>Roll No</th>
                <th>Amount</th>
                <th>Paid</th>
                <th>Due</th>
                <th>Due Date</th>
                <th>Semester</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="f" items="${fees}" varStatus="st">
                <tr>
                    <td>${st.count}</td>
                    <td>${f.studentName}</td>
                    <td>${f.rollNumber}</td>
                    <td>₹${f.amount}</td>
                    <td>₹${f.paidAmount}</td>
                    <td>₹${f.due}</td>
                    <td>${f.dueDate}</td>
                    <td>${f.semester}</td>
                    <td>
                        <c:choose>
                            <c:when test="${f.status == 'paid'}">
                                <span class="badge bg-success">Paid</span>
                            </c:when>
                            <c:when test="${f.status == 'partial'}">
                                <span class="badge bg-warning text-dark">Partial</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-danger">Pending</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${f.status != 'paid'}">
                            <a href="${pageContext.request.contextPath}/admin/fees?action=pay&feeId=${f.feeId}"
                               class="btn btn-sm btn-success">💳 Pay</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty fees}">
                <tr>
                    <td colspan="10" class="text-center text-muted">
                        No fee records found.
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>