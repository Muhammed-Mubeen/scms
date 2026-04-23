package com.scms.service;

import com.scms.dao.FeeDAO;
import com.scms.dao.StudentDAO;
import com.scms.model.Fee;
import com.scms.model.Student;
import com.scms.util.LogUtil;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class FeeService {

    private final FeeDAO     feeDAO     = new FeeDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private static final Logger logger = LogUtil.getLogger(FeeService.class);

    public List<Fee> getAllFees() {
        logger.debug("Retrieving all fees");
        return feeDAO.findAll();
    }

    public List<Fee> getPendingFees() {
        logger.debug("Retrieving pending fees");
        return feeDAO.findPending();
    }

    public List<Fee> getFeesByStudent(int studentId) {
        logger.debug("Retrieving fees for student ID: {}", studentId);
        return feeDAO.findByStudent(studentId);
    }

    public Fee getFeeById(int feeId) {
        logger.debug("Retrieving fee with ID: {}", feeId);
        return feeDAO.findById(feeId);
    }

    public boolean addFee(Fee fee) {
        if (fee.getAmount() <= 0) {
            logger.warn("Invalid fee amount: {}", fee.getAmount());
            throw new IllegalArgumentException("Fee amount must be greater than 0.");
        }
        if (fee.getDueDate() == null) {
            logger.warn("Due date is null");
            throw new IllegalArgumentException("Due date is required.");
        }
        if (fee.getAcademicYear() == null || fee.getAcademicYear().isBlank()) {
            logger.warn("Academic year is blank");
            throw new IllegalArgumentException("Academic year is required.");
        }

        fee.setPaidAmount(0.0);
        fee.setStatus("pending");
        boolean result = feeDAO.save(fee);

        if (result) {
            logger.info("Fee record added for student ID: {} - Amount: ₹{}",
                    fee.getStudentId(), fee.getAmount());
        }
        return result;
    }

    public boolean recordPayment(int feeId, double paymentAmount) {
        if (paymentAmount <= 0) {
            logger.warn("Invalid payment amount: {}", paymentAmount);
            throw new IllegalArgumentException("Payment amount must be greater than 0.");
        }

        Fee fee = feeDAO.findById(feeId);
        if (fee == null) {
            logger.warn("Fee record not found with ID: {}", feeId);
            throw new IllegalArgumentException("Fee record not found.");
        }

        if ("paid".equals(fee.getStatus())) {
            logger.warn("Attempt to pay already fully paid fee ID: {}", feeId);
            throw new IllegalArgumentException("This fee is already fully paid.");
        }

        boolean result = feeDAO.recordPayment(feeId, paymentAmount);

        if (result) {
            logger.info("Payment recorded - Fee ID: {}, Amount: ₹{}, New Status: {}",
                    feeId, paymentAmount, fee.getStatus());
        }
        return result;
    }

    public double getTotalDues() {
        double total = feeDAO.findPending().stream()
                .mapToDouble(Fee::getDue)
                .sum();
        logger.debug("Total dues calculated: ₹{}", total);
        return total;
    }

    public double getTotalCollected() {
        double total = feeDAO.findAll().stream()
                .mapToDouble(Fee::getPaidAmount)
                .sum();
        logger.debug("Total collected calculated: ₹{}", total);
        return total;
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }
}