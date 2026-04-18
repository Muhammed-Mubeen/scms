<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Faculty Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>🧑‍🏫 Faculty Dashboard</h2>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger">Logout</a>
    </div>
    <p>Welcome, <strong>${sessionScope.loggedUser.username}</strong>!</p>
</body>
</html>
