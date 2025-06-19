package org.example.bankproject.contract;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

@Component
public class ContractGenerator {

    public void GeneratedContractPdf(Map<String,String> data){
        try (InputStream in = new FileInputStream("C:\\Users\\kryst\\Desktop\\Umowa bankowa.docx")) {
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

            convertToPdf(doc, "C:\\Users\\kryst\\Desktop\\Umowa_bankowa.pdf");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void convertToPdf(XWPFDocument doc, String outputPdfPath) {
        try (OutputStream out = new FileOutputStream(outputPdfPath)) {
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
