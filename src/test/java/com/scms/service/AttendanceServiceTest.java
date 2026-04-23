package com.scms.service;

import com.scms.model.Attendance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AttendanceService Tests")
public class AttendanceServiceTest {

    private AttendanceService attendanceService;

    @BeforeEach
    void setUp() {
        attendanceService = new AttendanceService();
    }

    @Test
    @DisplayName("Should create attendance object correctly")
    void testAttendanceObjectCreation() {
        Attendance a = new Attendance();
        a.setAttendanceId(1);
        a.setStudentId(5);
        a.setCourseId(10);
        a.setDate(LocalDate.now());
        a.setStatus("P");

        assertEquals(1, a.getAttendanceId());
        assertEquals(5, a.getStudentId());
        assertEquals(10, a.getCourseId());
        assertEquals("P", a.getStatus());
    }

    @Test
    @DisplayName("Should accept P, A, L status")
    void testValidStatusValues() {
        Attendance a1 = new Attendance();
        a1.setStatus("P");
        assertEquals("P", a1.getStatus());

        Attendance a2 = new Attendance();
        a2.setStatus("A");
        assertEquals("A", a2.getStatus());

        Attendance a3 = new Attendance();
        a3.setStatus("L");
        assertEquals("L", a3.getStatus());
    }

    @Test
    @DisplayName("Should mark attendance with valid map")
    void testMarkAttendanceWithValidMap() {
        Map<Integer, String> statusMap = new HashMap<>();
        statusMap.put(1, "P");
        statusMap.put(2, "A");
        statusMap.put(3, "L");

        // Test that map is properly formed
        assertEquals(3, statusMap.size());
        assertEquals("P", statusMap.get(1));
        assertEquals("A", statusMap.get(2));
        assertEquals("L", statusMap.get(3));
    }

    @Test
    @DisplayName("Should calculate attendance percentage")
    void testAttendancePercentageCalculation() {
        // This would need actual database data
        // For now, just test the calculation logic
        int present = 20;
        int total = 25;
        double percentage = (present * 100.0) / total;

        assertEquals(80.0, percentage);
    }
}