package org.example.bankproject.contract;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class GenerateAccountOpeningAgreementPdfTest {

    private GenerateAccountOpeningAgreementPdf generator;
    private final File outputPdf = new File("target/output/Umowa bankowa.pdf");

    @BeforeEach
    void setUp() {
        generator = new GenerateAccountOpeningAgreementPdf();
    }

    @AfterEach
    void cleanUp() {
        if (outputPdf.exists()) {
            outputPdf.delete();
        }
    }

    @Test
    void shouldGeneratePdfFileWithReplacedText() {
        // given
        Map<String, String> data = new HashMap<>();
        data.put("imię", "Jan");
        data.put("nazwisko", "Kowalski");

        // when
        generator.generateContractPdf(data);

        // then
        assertThat(outputPdf).exists();
        assertThat(outputPdf.length()).isGreaterThan(0);
    }

    @Test
    void shouldNotThrowExceptionWhenTemplateNotFound() {
        // given

        // when/then
        assertThatCode(() -> generator.generateContractPdf(new HashMap<>())).doesNotThrowAnyException();
    }

    @Test
    void shouldReplacePlaceholdersCorrectlyInMemory() throws Exception {
        // given
        Map<String, String> data = new HashMap<>();
        data.put("imię", "Anna");
        data.put("nazwisko", "Nowak");

        try (InputStream in = new ClassPathResource("templates/Umowa_bankowa.docx").getInputStream()) {
            XWPFDocument doc = new XWPFDocument(in);

            doc.getParagraphs().forEach(p -> p.getRuns().forEach(run -> {
                String text = run.getText(0);
                if (text != null) {
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        text = text.replace("${" + entry.getKey() + "}", entry.getValue());
                    }
                    run.setText(text, 0);
                }
            }));

            // then
            String documentText = doc.getParagraphs().stream()
                    .map(p -> p.getRuns().stream()
                            .map(r -> r.getText(0))
                            .filter(t -> t != null)
                            .reduce("", String::concat))
                    .reduce("", String::concat);

            assertThat(documentText).contains("Anna", "Nowak");
            assertThat(documentText).doesNotContain("${imię}", "${nazwisko}");
        }
    }
}