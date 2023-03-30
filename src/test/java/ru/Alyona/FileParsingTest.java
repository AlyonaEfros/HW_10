package ru.Alyona;

import com.codeborne.pdftest.PDF;

import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.InvalidArgumentException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParsingTest {

    private final ClassLoader cl = FileParsingTest.class.getClassLoader();

    private ZipInputStream getStreamFromArchive(String filename) throws IOException {
        ZipEntry entry;
        ZipInputStream zis;
        InputStream is = cl.getResourceAsStream("files.zip");
        assert is != null;
        zis = new ZipInputStream(is);
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().endsWith(filename)) return zis;
        }
        is.close();
        zis.close();
        throw new InvalidArgumentException("ERROR: File " + filename + " was not found in the archive" + "files.zip" + "\n");
    }


    @Test
    void pdfTest() throws Exception {
        try (InputStream inputStream = getStreamFromArchive(".pdf")) {
            PDF pdf = new PDF(inputStream);
            Assertions.assertTrue(pdf.text.contains("The functioning of the human community at all stages of its development was"));
        }
    }

    @Test
    void xlsxTest() throws Exception {
        try (InputStream inputStream = getStreamFromArchive(".xlsx")) {
            XLS xls = new XLS(inputStream);
            Assertions.assertEquals(xls.excel.getSheetAt(0).getRow(7).getCell(0)
                    .getStringCellValue(), "Юго-Вост. Азия");
        }
    }
    @Test
    void csvTest() throws Exception {
        try (InputStream inputStream = getStreamFromArchive(".csv")) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
            List<String[]> content = csvReader.readAll();
            Assertions.assertArrayEquals(new String[]{"household appliances", " beauty and health"}, content.get(0));
        }
    }


}

