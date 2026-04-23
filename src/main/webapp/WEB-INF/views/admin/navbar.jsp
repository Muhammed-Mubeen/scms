<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<style>
    .nav-link {
        transition: all 0.3s ease;
        border-radius: 6px;
    }

    .nav-link:hover {
        background-color: rgba(255, 255, 255, 0.1) !important;
        transform: translateX(4px);
    }

    #mainContent {
        background: #f8fafc;
        padding: 20px;
    }

    .alert {
        border-radius: 8px;
        border: none;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    }

    .card {
        border: none;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        border-radius: 8px;
    }

    .btn {
        border-radius: 6px;
        font-weight: 500;
        transition: all 0.2s;
    }

    .btn:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    }

    .table {
        border-radius: 8px;
        overflow: hidden;
    }

    .table thead {
        font-weight: 600;
        letter-spacing: 0.5px;
    }
</style>
<nav class="navbar navbar-expand-lg navbar-dark" style="background: linear-gradient(135deg, #1a3a5c 0%, #2563eb 100%);">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold fs-5" href="${pageContext.request.contextPath}/admin/dashboard">
            🎓 SCMS
        </a>
        <span class="navbar-text text-white small">Smart College Management System</span>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto gap-2">
                <li class="nav-item">
                    <span class="nav-link text-white-50">
                        👤 ${sessionScope.loggedUser.username}
                    </span>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-outline-light btn-sm"
                       href="${pageContext.request.contextPath}/logout">
                        🚪 Logout
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Sidebar -->
<div class="d-flex">
    <div style="width: 220px; min-height: 100vh; background: linear-gradient(180deg, #1e3a5f 0%, #1a3a5c 100%);"
         class="p-3 position-fixed" id="sidebar">
        <ul class="nav flex-column gap-2">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/dashboard"
                   class="nav-link text-white rounded px-3 py-2 transition"
                   style="transition: background 0.3s;">
                   🏠 Dashboard
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/departments"
                   class="nav-link text-white rounded px-3 py-2">
                   🏛 Departments
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/students"
                   class="nav-link text-white rounded px-3 py-2">
                   👤 Students
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/courses"
                   class="nav-link text-white rounded px-3 py-2">
                   📚 Courses
                </a>
            </li>
            <li class="nav-item border-top border-secondary mt-3 pt-3">
                <a href="${pageContext.request.contextPath}/admin/attendance"
                   class="nav-link text-white rounded px-3 py-2">
                   📋 Attendance
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/exams"
                   class="nav-link text-white rounded px-3 py-2">
                   📊 Exams
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/marks"
                   class="nav-link text-white rounded px-3 py-2">
                   📝 Marks
                </a>
            </li>
            <li class="nav-item border-top border-secondary mt-3 pt-3">
                <a href="${pageContext.request.contextPath}/admin/fees"
                   class="nav-link text-white rounded px-3 py-2">
                   💰 Fees
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/notices"
                   class="nav-link text-white rounded px-3 py-2">
                   🔔 Notices
                </a>
            </li>
        </ul>
    </div>

    <div class="flex-grow-1" style="margin-left: 220px;" id="mainContent">