package com.scan.studentformbackend.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.scan.studentformbackend.model.Student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;

@Component
public class PdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PdfGenerator.class);

    public void generateStudentPdf(Student student, String outputPath) throws FileNotFoundException {
        try {
            PdfWriter writer = new PdfWriter(outputPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Title
            Paragraph title = new Paragraph("Student Registration Form")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // Student Details
            document.add(createLabelValueParagraph("Customer Name:", student.getCustomerName()));
            document.add(createLabelValueParagraph("ID Number:", student.getIdNumber()));
            document.add(createLabelValueParagraph("Email:", student.getEmail()));
            document.add(createLabelValueParagraph("Phone Number:", student.getPhoneNumber()));
            document.add(createLabelValueParagraph("PAN File:", student.getPanFilePath()));
            document.add(createLabelValueParagraph("Aadhaar File:", student.getAadhaarFilePath()));

            // Submission timestamp
            document.add(new Paragraph("\n"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            document.add(createLabelValueParagraph("Submitted At:",
                    student.getCreatedAt().format(formatter)));

            // Footer
            document.add(new Paragraph("\n\n"));
            Paragraph footer = new Paragraph("This is an auto-generated document.")
                    .setFontSize(10)
                    .setItalic()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(footer);

            document.close();
            logger.info("PDF generated successfully at: {}", outputPath);

        } catch (Exception e) {
            logger.error("Error generating PDF: {}", e.getMessage());
            throw e;
        }
    }

    private Paragraph createLabelValueParagraph(String label, String value) {
        Paragraph paragraph = new Paragraph();
        Text labelText = new Text(label + " ").setBold();
        Text valueText = new Text(value != null ? value : "N/A");
        paragraph.add(labelText);
        paragraph.add(valueText);
        return paragraph;
    }
}
