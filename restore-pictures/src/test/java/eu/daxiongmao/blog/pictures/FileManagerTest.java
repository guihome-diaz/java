package eu.daxiongmao.blog.pictures;

import eu.daxiongmao.blog.pictures.model.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class FileManagerTest {

    @Test
    void lookForMissingFiles() {
        // Given
        Path excelFile = Paths.get("src", "test", "resources", "family_blog_ngg_gallery.xlsx");
        ExcelParser excelParser = new ExcelParser();
        FileManager fileManager = new FileManager();
        // Do
        List<Image> images = excelParser.readExcelFile(excelFile.toString());
        List<Image> missingImages = fileManager.ensureImagesExists(images);
        Assertions.assertNotNull(missingImages);
        // Generate SQL script
        String sqlScript = fileManager.createSqlQueries(missingImages);
        Assertions.assertNotNull(sqlScript);
        Assertions.assertFalse(sqlScript.trim().isEmpty());
        System.out.println(sqlScript);
    }

    @Test
    void createDirectoriesAndCopyFiles() throws IOException {
        // Given
        Path targetFolder = Paths.get("E:/Temp/Blog");
        Path excelFile = Paths.get("src", "test", "resources", "family_blog_ngg_gallery.xlsx");
        // Do
        ExcelParser excelParser = new ExcelParser();
        FileManager fileManager = new FileManager();
        List<Image> images = excelParser.readExcelFile(excelFile.toString());
        List<Image> missingImages = fileManager.ensureImagesExists(images);
        fileManager.copyImages(images, missingImages, targetFolder);
    }

    @Test
    void zipDirectories() throws IOException {
        Path targetFolder = Paths.get("E:/Temp/Blog");
        FileManager fileManager = new FileManager();
        List<Path> zipFiles = fileManager.zipDirectories(targetFolder);
        Assertions.assertNotNull(zipFiles);
    }
}
