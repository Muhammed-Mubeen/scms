package com.scms.util;

import com.scms.model.Mark;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.util.List;

public class PdfUtil {

    // PDFBox 3.x compatible fonts
    private static final PDFont fontBold =
            new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

    private static final PDFont fontNormal =
            new PDType1Font(Standard14Fonts.FontName.HELVETICA);

    private static final float fontSize = 10f;
    private static final float margin = 40f;

    // Generate result sheet PDF
    public static byte[] generateResultSheet(String studentName,
                                             String rollNo,
                                             double cgpa,
                                             List<Mark> marks)
            throws IOException {

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();

        try (PDPageContentStream contents =
                     new PDPageContentStream(doc, page)) {

            float y = 750;

            // Title
            contents.setFont(fontBold, 16);
            contents.beginText();
            contents.newLineAtOffset(margin, y);
            contents.showText("SCMS — Student Result Sheet");
            contents.endText();
            y -= 30;

            // Student info
            contents.setFont(fontBold, 11);
            contents.beginText();
            contents.newLineAtOffset(margin, y);
            contents.showText("Student: " + studentName + " (" + rollNo + ")");
            contents.endText();
            y -= 15;

            contents.setFont(fontNormal, 10);
            contents.beginText();
            contents.newLineAtOffset(margin, y);
            contents.showText("CGPA: " + cgpa);
            contents.endText();
            y -= 30;

            // Table header
            float[] colWidths = {80, 80, 60, 60, 40};
            drawTableHeader(contents, margin, y, colWidths);
            y -= 20;

            // Table rows
            contents.setFont(fontNormal, 9);

            for (Mark m : marks) {
                double pct = (m.getMarksObtained() / m.getTotalMarks()) * 100;

                drawTableRow(contents, margin, y, colWidths,
                        m.getCourseName(),
                        m.getExamType(),
                        String.valueOf(m.getMarksObtained()),
                        String.valueOf(m.getTotalMarks()),
                        String.format("%.0f%%", pct));

                y -= 18;

                if (y < 50) break; // simple page break
            }
        }

        doc.save(baos);
        doc.close();
        return baos.toByteArray();
    }

    // Generate fee receipt PDF
    public static byte[] generateFeeReceipt(String studentName,
                                            String rollNo,
                                            double amount,
                                            double paid,
                                            String date)
            throws IOException {

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();

        try (PDPageContentStream contents =
                     new PDPageContentStream(doc, page)) {

            float y = 750;

            // Header
            contents.setFont(fontBold, 14);
            contents.beginText();
            contents.newLineAtOffset(margin, y);
            contents.showText("SCMS — Fee Receipt");
            contents.endText();
            y -= 30;

            // Details
            contents.setFont(fontNormal, 11);

            String[] lines = {
                    "Receipt Date: " + date,
                    "Student Name: " + studentName,
                    "Roll Number: " + rollNo,
                    "",
                    "Total Fee:     ₹" + String.format("%.2f", amount),
                    "Paid Amount:   ₹" + String.format("%.2f", paid),
                    "Outstanding:   ₹" + String.format("%.2f", amount - paid)
            };

            for (String line : lines) {
                contents.beginText();
                contents.newLineAtOffset(margin, y);
                contents.showText(line);
                contents.endText();
                y -= 18;
            }

            // Footer
            y -= 20;
            contents.setFont(fontNormal, 9);
            contents.beginText();
            contents.newLineAtOffset(margin, y);
            contents.showText("Generated by SCMS — Smart College Management System");
            contents.endText();
        }

        doc.save(baos);
        doc.close();
        return baos.toByteArray();
    }

    // Table header
    private static void drawTableHeader(PDPageContentStream contents,
                                        float x, float y,
                                        float[] colWidths)
            throws IOException {

        String[] headers = {"Course", "Exam", "Marks", "Total", "Pct"};
        float xPos = x;

        for (int i = 0; i < headers.length; i++) {
            contents.beginText();
            contents.newLineAtOffset(xPos, y);
            contents.showText(headers[i]);
            contents.endText();
            xPos += colWidths[i];
        }

        contents.moveTo(x, y - 2);
        contents.lineTo(x + 320, y - 2);
        contents.stroke();
    }

    // Table row
    private static void drawTableRow(PDPageContentStream contents,
                                     float x, float y,
                                     float[] colWidths,
                                     String... values)
            throws IOException {

        float xPos = x;

        for (int i = 0; i < values.length; i++) {
            contents.beginText();
            contents.newLineAtOffset(xPos, y);
            contents.showText(values[i]);
            contents.endText();
            xPos += colWidths[i];
        }
    }
}