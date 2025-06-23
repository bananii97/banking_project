package org.example.bankproject.contract;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenerateAccountOpeningAgreementPdfTest {

    private GenerateAccountOpeningAgreementPdf contractGenerator = new  GenerateAccountOpeningAgreementPdf();

    @Test
    void shouldCreatePdfFile_whenValidDataAndTemplateProvided() {
        //given
        Map<String, String> data = new HashMap<>();
        data.put("imię", "Jan ");
        data.put("nazwisko", "Kowalski");
        data.put("pesel","12345678914");
        data.put("dokument","ASD123123");
        data.put("data", "12-12-2024");
        data.put("miasto", "warszawa");
        File outputFolder = new File("target/output");
        File pdfFile = new File(outputFolder, "Umowa bankowa.pdf");

        //when
        contractGenerator.generateContractPdf(data);

        //then
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }
}
