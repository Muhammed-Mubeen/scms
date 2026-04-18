<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar navbar-dark" style="background:#1a3a5c;">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="#">🎓 SCMS Admin</a>
        <div class="d-flex align-items-center gap-3">
            <span class="text-white">👤 ${sessionScope.loggedUser.username}</span>
            <a href="${pageContext.request.contextPath}/logout"
               class="btn btn-outline-light btn-sm">Logout</a>
        </div>
    </div>
</nav>

<!-- Sidebar links -->
<div class="d-flex">
    <div style="width:220px; min-height:100vh; background:#1e3a5f;" class="p-3">
        <ul class="nav flex-column gap-1">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/dashboard"
                   class="nav-link text-white">🏠 Dashboard</a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/departments"
                   class="nav-link text-white">🏛 Departments</a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/students"
                   class="nav-link text-white">👤 Students</a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/courses"
                   class="nav-link text-white">📚 Courses</a>
            </li>
        </ul>
    </div>
    <div class="flex-grow-1 p-4" id="mainContent">