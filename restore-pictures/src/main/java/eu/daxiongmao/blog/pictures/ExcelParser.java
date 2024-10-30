package eu.daxiongmao.blog.pictures;

import eu.daxiongmao.blog.pictures.model.Image;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * To read an Excel file and extract data.
 * @version 1.0 (2024/10)
 * @since 2024/10
 * @author Guillaume Diaz
 */
public class ExcelParser {

    /**
     * To read an Excel file.
     * The public method handles exceptions.
     * @param filePath Excel file to read
     * @return excel file content
     */
    public List<Image> readExcelFile(String filePath) {
        final Path excelFile = Paths.get(filePath);
        if (Files.notExists(excelFile)) {
            throw new IllegalArgumentException("File not found. filePath=" + filePath);
        }

        // Read Excel file
        try (FileInputStream excelFis = new FileInputStream(excelFile.toFile())) {
            return doReadExcelFile(excelFis);
        } catch (FileNotFoundException e) {
            // should never occur
            throw new IllegalArgumentException("Requested file does not exists. filePath=" + filePath, e);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read file. It may not be an Excel document. filePath=" + filePath, e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read excel file.", e);
        }
    }

    private List<Image> doReadExcelFile(FileInputStream excelFis) throws IOException {
        List<Image> images = new ArrayList<>();
        // Open Excel file
        XSSFWorkbook workbook = new XSSFWorkbook(excelFis);
        // Get first sheet
        XSSFSheet sheet = workbook.getSheetAt(0);
        // Iterate through each rows one by one, extract data
        for (Row row : sheet) {
            // Skip headers
            if (row.getRowNum() == 0) { continue; }
            double galleryId = row.getCell(0).getNumericCellValue();
            String galleryName = row.getCell(1).getStringCellValue();
            String galleryPathServer = row.getCell(2).getStringCellValue();
            String fileName = row.getCell(3).getStringCellValue();
            String galleryPathOneDrive = row.getCell(4).getStringCellValue();
            images.add(new Image(Double.valueOf(galleryId).intValue(), galleryName, galleryPathServer, galleryPathOneDrive, fileName));
        }

        Collections.sort(images);
        return images;
    }
}
