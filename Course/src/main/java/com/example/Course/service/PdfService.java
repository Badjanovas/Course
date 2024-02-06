package com.example.Course.service;

import com.example.Course.model.Student;
import com.example.Course.model.Teacher;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PdfService {
    Integer invoiceCounter = 1000000;

    public void pdfGenerator(Teacher teacher, Student student) throws FileNotFoundException, MalformedURLException {

        String path = "invoice.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);


        Document document = new Document(pdfDocument);
        String imagePath = "C:\\Users\\Geimantronas\\Downloads\\Course\\Course\\src\\main\\java\\AndriusTM_converted_resized.jpg";
        ImageData imageData = ImageDataFactory.create(imagePath);
        Image image = new Image(imageData);

        float x = pdfDocument.getDefaultPageSize().getWidth()/2;
        float y = pdfDocument.getDefaultPageSize().getHeight()/2;
        image.setFixedPosition(x+200,y-430);
        image.setOpacity(0.1f);
        document.add(image);


        float threecol = 190f;
        float twocol = 285f;
        float twocol150 = twocol + 150f;
        float twocolumWidth[] = {twocol150, twocol};
        float threeColumnWidth[] = {threecol, threecol, threecol};
        float fullWidth[] = {threecol * 3};
        Paragraph onesp = new Paragraph("\n");

        Table table = new Table(twocolumWidth);
        table.addCell(new Cell().add("Invoice").setFontSize(20F).setBorder(Border.NO_BORDER).setBold());
        Table nestedTable = new Table(new float[]{twocol / 2, twocol / 2});
        nestedTable.addCell(getHeaderTextCell("Invoice No."));
        addOneToInvoiceCounter();
        nestedTable.addCell(getHeaderTextValue(String.valueOf(invoiceCounter)));
        nestedTable.addCell(getHeaderTextCell("Invoice Date"));
        nestedTable.addCell(getHeaderTextValue(getCurrentDate()));


        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));

        Border gb = new SolidBorder(Color.GRAY, 2f);
        Table divider = new Table(fullWidth);
        divider.setBorder(gb);
        document.add(table);
        document.add(onesp);
        document.add(divider);
        document.add(onesp);

        Table twoColTable = new Table(twocolumWidth);
        twoColTable.addCell(getTeacherAndStudentCell("Teacher Information:"));
        twoColTable.addCell(getTeacherAndStudentCell("Student Information:"));
        document.add(twoColTable.setMarginBottom(12f));

        Table twoColTable2 = new Table(twocolumWidth);
        twoColTable2.addCell(getCell10fLeft("Name:", true));
        twoColTable2.addCell(getCell10fLeft("Name:", true));
        twoColTable2.addCell(getCell10fLeft(teacher.getFirstName() + " " + teacher.getLastName(), false));
        twoColTable2.addCell(getCell10fLeft(student.getFirstName() + " " + student.getLastName(), false));
        twoColTable2.addCell(getCell10fLeft("Phone Number:", true));
        twoColTable2.addCell(getCell10fLeft("Phone Number:", true));
        twoColTable2.addCell(getCell10fLeft(teacher.getPhoneNumber(), false));
        twoColTable2.addCell(getCell10fLeft(student.getPhoneNumber(), false));
        twoColTable2.addCell(getCell10fLeft("Email:", true));
        twoColTable2.addCell(getCell10fLeft("Email", true));
        twoColTable2.addCell(getCell10fLeft(teacher.getEmail(), false));
        twoColTable2.addCell(getCell10fLeft(student.getEmail(), false));
        document.add(twoColTable2);

        Table twoColTable3 = new Table(twocolumWidth);
        twoColTable2.addCell(getCell10fLeft("Name:", true));
        twoColTable2.addCell(getCell10fLeft("Name:", true));
        twoColTable2.addCell(getCell10fLeft(teacher.getFirstName() + " " + teacher.getLastName(), false));
        twoColTable2.addCell(getCell10fLeft(student.getFirstName() + " " + student.getLastName(), false));
        twoColTable2.addCell(getCell10fLeft("Phone Number:", true));
        twoColTable2.addCell(getCell10fLeft("Phone Number:", true));
        twoColTable2.addCell(getCell10fLeft(teacher.getPhoneNumber(), false));
        twoColTable2.addCell(getCell10fLeft(student.getPhoneNumber(), false));
        twoColTable2.addCell(getCell10fLeft("Email:", true));
        twoColTable2.addCell(getCell10fLeft("Email", true));
        twoColTable2.addCell(getCell10fLeft(teacher.getEmail(), false));
        twoColTable2.addCell(getCell10fLeft(student.getEmail(), false));
        document.add(twoColTable3);

        float oneColumnWidth[] = {twocol150};

        document.add(onesp);
        document.add(onesp);
        Table oneColTable1 = new Table(oneColumnWidth);
        oneColTable1.addCell(getCell10fLeft("Course name:", true));
        oneColTable1.addCell(getCell10fLeft("Spring-Boot", false));
        document.add(oneColTable1.setMarginBottom(10f));

        Table tableDivider2 = new Table(fullWidth);
        Border dgb = new DashedBorder(Color.GRAY, 0.5f);
        document.add(tableDivider2.setBorder(dgb));
        Paragraph pricingPara = new Paragraph("Price:");

        document.add(onesp);
        document.add(pricingPara.setBold());
        Table threeColTable1 = new Table(threeColumnWidth);
        threeColTable1.setBackgroundColor(Color.BLACK, 0.7f);

        threeColTable1.addCell(new Cell().add("Description").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Quantity").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Price").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
        document.add(threeColTable1);

        Table threeColTable2 = new Table(threeColumnWidth);
        threeColTable2.addCell(new Cell().add("Spring-Boot course").setBorder(Border.NO_BORDER).setMargin(10f));
        threeColTable2.addCell(new Cell().add("1").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable2.addCell(new Cell().add("500").setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);

        threeColTable2.addCell(new Cell().add("VAT").setBorder(Border.NO_BORDER).setMargin(10f));
        threeColTable2.addCell(new Cell().add("20%").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable2.addCell(new Cell().add("100").setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
        document.add(threeColTable2.setMarginBottom(20f));

        float onetwo[] = {threecol + 125f, threecol * 2};
        Table threeColTable4 = new Table(onetwo);
        threeColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        threeColTable4.addCell(new Cell().add(tableDivider2).setBorder(Border.NO_BORDER));
        document.add(threeColTable4);

        Table threeColTable3 = new Table(threeColumnWidth);
        threeColTable3.addCell(new Cell().add("").setBorder(Border.NO_BORDER)).setMarginLeft(10f);
        threeColTable3.addCell(new Cell().add("Total:").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable3.addCell(new Cell().add("600").setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
        document.add(threeColTable3);
        document.add(tableDivider2);
        document.add(new Paragraph("\n"));
        document.add(divider.setBorder(new SolidBorder(Color.GRAY, 1f)).setMarginBottom(15f));

        Table tb = new Table(fullWidth);
        tb.addCell(new Cell().add("TERMS AND CONDITIONS\n").setBold().setBorder(Border.NO_BORDER));
        List<String> tncList = new ArrayList<>();
        tncList.add("1. The course provider's liability for any claims related to the course shall not exceed the fees paid by the participant.");
        tncList.add("2. The course is provided \"as is\" without any warranties, express or implied. The course provider does not guarantee employment or proficiency upon completion of the course.");

        for (String s : tncList) {
            tb.addCell(new Cell().add(s).setBorder(Border.NO_BORDER));
        }
        document.add(tb);

        document.close();
    }

    private Cell getHeaderTextCell(String textValue) {
        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }

    private Cell getHeaderTextValue(String textValue) {
        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    private Cell getTeacherAndStudentCell(String textValue) {
        return new Cell().add(textValue).setFontSize(12.f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    private Cell getCell10fLeft(String textValue, Boolean isBold) {
        Cell myCell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ? myCell.setBold() : myCell;
    }

    private String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        return currentDate.format(formatter);
    }

    private Integer addOneToInvoiceCounter(){
        return invoiceCounter++;
    }

}
