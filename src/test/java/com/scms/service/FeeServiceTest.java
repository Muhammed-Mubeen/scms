package com.scms.service;

import com.scms.model.Fee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FeeService Tests")
public class FeeServiceTest {

    private FeeService feeService;

    @BeforeEach
    void setUp() {
        feeService = new FeeService();
    }

    @Test
    @DisplayName("Should throw exception for negative amount")
    void testAddFeeNegativeAmount() {
        Fee fee = new Fee();
        fee.setStudentId(1);
        fee.setAmount(-1000);
        fee.setDueDate(LocalDate.now().plusMonths(1));
        fee.setAcademicYear("2024-25");

        assertThrows(IllegalArgumentException.class, () -> {
            feeService.addFee(fee);
        });
    }

    @Test
    @DisplayName("Should throw exception for missing due date")
    void testAddFeeMissingDueDate() {
        Fee fee = new Fee();
        fee.setStudentId(1);
        fee.setAmount(25000);
        fee.setAcademicYear("2024-25");

        assertThrows(IllegalArgumentException.class, () -> {
            feeService.addFee(fee);
        });
    }

    @Test
    @DisplayName("Should throw exception for zero amount")
    void testAddFeeZeroAmount() {
        Fee fee = new Fee();
        fee.setStudentId(1);
        fee.setAmount(0);
        fee.setDueDate(LocalDate.now().plusMonths(1));
        fee.setAcademicYear("2024-25");

        assertThrows(IllegalArgumentException.class, () -> {
            feeService.addFee(fee);
        });
    }

    @Test
    @DisplayName("Should throw exception for negative payment")
    void testRecordPaymentNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            feeService.recordPayment(1, -500);
        });
    }

    @Test
    @DisplayName("Should throw exception for zero payment")
    void testRecordPaymentZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            feeService.recordPayment(1, 0);
        });
    }
}