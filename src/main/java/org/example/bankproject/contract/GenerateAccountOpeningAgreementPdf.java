package org.example.bankproject.contract;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;

@Component
public class GenerateAccountOpeningAgreementPdf {

    public void generateContractPdf(Map<String, String> data) {
        try (InputStream in = new ClassPathResource("templates/Umowa_bankowa.docx").getInputStream()) {
            XWPFDocument doc = new XWPFDocument(in);

            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        for (Map.Entry<String, String> entry : data.entrySet()) {
                            text = text.replace("${" + entry.getKey() + "}", entry.getValue());
                        }
                        run.setText(text, 0);
                    }
                }
            }

            File outputFolder = new File("target/output");
            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }

            File pdfFile = new File(outputFolder, "Umowa bankowa.pdf");
            convertToPdf(doc, pdfFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void convertToPdf(XWPFDocument doc, File outputPdfFile) {
        try (OutputStream out = new FileOutputStream(outputPdfFile)) {
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, out);
            pdf.open();

            for (XWPFParagraph para : doc.getParagraphs()) {
                pdf.add(new Paragraph(para.getText()));
            }

            pdf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
