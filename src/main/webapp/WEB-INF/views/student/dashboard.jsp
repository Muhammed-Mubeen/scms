<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark" style="background: linear-gradient(135deg, #0ea5e9 0%, #06b6d4 100%);">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/student/dashboard">
            🎓 SCMS — Student Portal
        </a>
        <div class="ms-auto">
            <span class="text-white me-3">👤 ${sessionScope.loggedUser.username}</span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">
                🚪 Logout
            </a>
        </div>
    </div>
</nav>

<div class="container-fluid py-5">
    <h2 class="mb-4">📚 Student Dashboard</h2>

    <div class="row g-4">
        <div class="col-md-3">
            <div class="card text-white" style="background: linear-gradient(135deg, #0ea5e9 0%, #06b6d4 100%);">
                <div class="card-body text-center">
                    <h5 class="card-title">Attendance</h5>
                    <h2>${attendance}</h2>
                    <small>Overall attendance</small>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card text-white bg-success">
                <div class="card-body text-center">
                    <h5 class="card-title">CGPA</h5>
                    <h2>${cgpa}</h2>
                    <small>Cumulative grade point</small>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card text-white bg-warning">
                <div class="card-body text-center">
                    <h5 class="card-title">Fees Status</h5>
                    <p class="mt-3">
                        <span class="badge bg-danger">${feesStatus}</span>
                    </p>
                    <small>Payment pending</small>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card text-white bg-info">
                <div class="card-body text-center">
                    <h5 class="card-title">Courses</h5>
                    <h2>5</h2>
                    <small>Enrolled this semester</small>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4 mt-4">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header fw-bold" style="background: #0ea5e9; color: white;">
                    📋 My Courses
                </div>
                <div class="card-body">
                    <ul class="list-group">
                        <li class="list-group-item">📚 Data Structures</li>
                        <li class="list-group-item">💻 Web Development</li>
                        <li class="list-group-item">🔐 Database Systems</li>
                        <li class="list-group-item">🤖 AI & ML Basics</li>
                        <li class="list-group-item">🎨 UI/UX Design</li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card">
                <div class="card-header fw-bold" style="background: #0ea5e9; color: white;">
                    📊 Quick Links
                </div>
                <div class="card-body">
                    <div class="d-grid gap-2">
                        <a href="#" class="btn btn-outline-primary">📋 View Attendance</a>
                        <a href="#" class="btn btn-outline-success">📝 View Marks</a>
                        <a href="#" class="btn btn-outline-warning">💰 Pay Fees</a>
                        <a href="#" class="btn btn-outline-info">📢 View Notices</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>