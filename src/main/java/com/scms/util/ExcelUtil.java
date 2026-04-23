package com.scms.util;

import com.scms.model.Student;
import com.scms.model.Fee;
import com.scms.model.Attendance;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {

    // Export students to Excel
    public static byte[] exportStudents(List<Student> students)
            throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        // Header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Roll No", "Name", "Email",
                "Phone", "Department", "Semester", "Batch"};

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Data rows
        int rowNum = 1;
        for (Student s : students) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(s.getRollNumber());
            row.createCell(1).setCellValue(s.getName());
            row.createCell(2).setCellValue(s.getEmail());
            row.createCell(3).setCellValue(s.getPhone());
            row.createCell(4).setCellValue(s.getDepartmentName());
            row.createCell(5).setCellValue(s.getSemester());
            row.createCell(6).setCellValue(s.getBatchYear());
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();
        return baos.toByteArray();
    }

    // Export fees to Excel
    public static byte[] exportFees(List<Fee> fees)
            throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Fees");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Student", "Roll No", "Amount",
                "Paid", "Due", "Due Date", "Status"};

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Fee f : fees) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(f.getStudentName());
            row.createCell(1).setCellValue(f.getRollNumber());
            row.createCell(2).setCellValue(f.getAmount());
            row.createCell(3).setCellValue(f.getPaidAmount());
            row.createCell(4).setCellValue(f.getDue());
            row.createCell(5).setCellValue(f.getDueDate().toString());
            row.createCell(6).setCellValue(f.getStatus());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();
        return baos.toByteArray();
    }

    // Export attendance to Excel
    public static byte[] exportAttendance(List<Attendance> records)
            throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Roll No", "Name", "Date",
                "Course", "Status"};

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Attendance a : records) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(a.getRollNumber());
            row.createCell(1).setCellValue(a.getStudentName());
            row.createCell(2).setCellValue(a.getDate().toString());
            row.createCell(3).setCellValue(a.getCourseName());
            row.createCell(4).setCellValue(a.getStatus());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();
        return baos.toByteArray();
    }
}