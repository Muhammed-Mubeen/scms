<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Record Payment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp" %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>💳 Record Payment</h3>
        <a href="${pageContext.request.contextPath}/admin/fees"
           class="btn btn-outline-secondary">← Back</a>
    </div>

    <div class="card" style="max-width:500px;">
        <div class="card-body">

            <%-- Fee summary --%>
            <table class="table table-sm mb-4">
                <tr>
                    <td class="fw-semibold">Student</td>
                    <td>${fee.studentName} (${fee.rollNumber})</td>
                </tr>
                <tr>
                    <td class="fw-semibold">Total Fee</td>
                    <td>₹${fee.amount}</td>
                </tr>
                <tr>
                    <td class="fw-semibold">Already Paid</td>
                    <td class="text-success">₹${fee.paidAmount}</td>
                </tr>
                <tr>
                    <td class="fw-semibold">Outstanding Due</td>
                    <td class="text-danger fw-bold">₹${fee.due}</td>
                </tr>
                <tr>
                    <td class="fw-semibold">Status</td>
                    <td>
                        <c:choose>
                            <c:when test="${fee.status == 'partial'}">
                                <span class="badge bg-warning text-dark">Partial</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-danger">Pending</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>

            <%-- Payment form --%>
            <form action="${pageContext.request.contextPath}/admin/fees"
                  method="post">
                <input type="hidden" name="action" value="pay">
                <input type="hidden" name="feeId"  value="${fee.feeId}">

                <div class="mb-3">
                    <label class="form-label fw-semibold">
                        Payment Amount (₹)
                    </label>
                    <input type="number" name="paymentAmount"
                           class="form-control"
                           placeholder="Enter amount to pay"
                           min="1" max="${fee.due}"
                           step="0.01" required>
                    <small class="text-muted">
                        Maximum payable: ₹${fee.due}
                    </small>
                </div>

                <button type="submit"
                        class="btn btn-success w-100 fw-bold">
                    💳 Confirm Payment
                </button>
            </form>
        </div>
    </div>

<%@ include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>