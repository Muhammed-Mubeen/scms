package com.scms.service;

import com.scms.model.Mark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ResultService Tests")
public class ResultServiceTest {

    private ResultService resultService;

    @BeforeEach
    void setUp() {
        resultService = new ResultService();
    }

    @Test
    @DisplayName("Should compute grade O for 90%+")
    void testComputeGradeO() {
        String grade = resultService.computeGrade(95, 100);
        assertEquals("O", grade);
    }

    @Test
    @DisplayName("Should compute grade A+ for 80-89%")
    void testComputeGradeAPlus() {
        String grade = resultService.computeGrade(85, 100);
        assertEquals("A+", grade);
    }

    @Test
    @DisplayName("Should compute grade A for 70-79%")
    void testComputeGradeA() {
        String grade = resultService.computeGrade(75, 100);
        assertEquals("A", grade);
    }

    @Test
    @DisplayName("Should compute grade B+ for 60-69%")
    void testComputeGradeBPlus() {
        String grade = resultService.computeGrade(65, 100);
        assertEquals("B+", grade);
    }

    @Test
    @DisplayName("Should compute grade F for <40%")
    void testComputeGradeF() {
        String grade = resultService.computeGrade(35, 100);
        assertEquals("F", grade);
    }

    @Test
    @DisplayName("Should return F for zero total marks")
    void testComputeGradeZeroTotal() {
        String grade = resultService.computeGrade(50, 0);
        assertEquals("F", grade);
    }
}