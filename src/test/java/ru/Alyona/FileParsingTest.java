package ru.Alyona;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParsingTest {

    private final ClassLoader cl = FileParsingTest.class.getClassLoader();

    @Test
    void pdfTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("files.zip")) {
            try (ZipInputStream zs = new ZipInputStream(Objects.requireNonNull(is))) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".pdf")) {
                        PDF pdf = new PDF(zs);
                        Assertions.assertTrue(pdf.text.contains("The functioning of the human community at all stages of its development was"));
                    }
                }
            }
        }
    }

    @Test
    void xlsxTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("files.zip")) {
            try (ZipInputStream zs = new ZipInputStream(Objects.requireNonNull(is))) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".xlsx")) {
                        XLS xls = new XLS(zs);
                        Assertions.assertEquals(xls.excel.getSheetAt(0).getRow(7).getCell(0)
                                .getStringCellValue(), "Юго-Вост. Азия");
                    }
                }
            }
        }
    }
    @Test
    void csvTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("files.zip")) {
            try (ZipInputStream zs = new ZipInputStream(Objects.requireNonNull(is))) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".csv")){
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zs));
                        List<String[]> content = csvReader.readAll();
                        Assertions.assertArrayEquals(new String[]{"household appliances", " beauty and health"}, content.get(0));
                    }
                }
            }
        }
    }


}

