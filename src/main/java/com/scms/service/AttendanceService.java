package com.scms.service;

import com.scms.dao.AttendanceDAO;
import com.scms.dao.StudentDAO;
import com.scms.model.Attendance;
import com.scms.model.Student;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AttendanceService {

    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final StudentDAO    studentDAO    = new StudentDAO();

    // Mark attendance for entire class — bulk insert
    public boolean markAttendance(int courseId, LocalDate date,
                                  Map<Integer, String> studentStatusMap, int markedBy) {
        LinkedList<Attendance> records = new LinkedList<>();

        for (Map.Entry<Integer, String> entry : studentStatusMap.entrySet()) {
            Attendance a = new Attendance();
            a.setStudentId(entry.getKey());
            a.setCourseId(courseId);
            a.setDate(date);
            a.setStatus(entry.getValue()); // P, A, L
            a.setMarkedBy(markedBy);
            records.add(a);
        }

        return attendanceDAO.bulkInsert(records);
    }

    // Get attendance for a course on a date
    public List<Attendance> getAttendanceByCoursAndDate(int courseId, LocalDate date) {
        return attendanceDAO.findByCourseAndDate(courseId, date);
    }

    // Get attendance history for a student
    public List<Attendance> getStudentAttendance(int studentId) {
        return attendanceDAO.findByStudent(studentId);
    }

    // Calculate attendance percentage per course for a student
    // Returns HashMap<CourseName, Percentage>
    public Map<String, Double> getAttendancePercentage(int studentId) {
        List<Attendance> records = attendanceDAO.findByStudent(studentId);
        Map<String, Integer> totalMap   = new HashMap<>();
        Map<String, Integer> presentMap = new HashMap<>();

        for (Attendance a : records) {
            String course = a.getCourseName();
            totalMap.put(course, totalMap.getOrDefault(course, 0) + 1);
            if ("P".equals(a.getStatus())) {
                presentMap.put(course, presentMap.getOrDefault(course, 0) + 1);
            }
        }

        Map<String, Double> percentageMap = new HashMap<>();
        for (String course : totalMap.keySet()) {
            int total   = totalMap.get(course);
            int present = presentMap.getOrDefault(course, 0);
            double pct  = (total == 0) ? 0.0 : (present * 100.0 / total);
            percentageMap.put(course, Math.round(pct * 100.0) / 100.0);
        }

        return percentageMap;
    }

    // Get all students for a course (to show checkbox grid)
    public List<Student> getStudentsForCourse(int courseId) {
        return studentDAO.findAll(); // filtered by course in future
    }
}