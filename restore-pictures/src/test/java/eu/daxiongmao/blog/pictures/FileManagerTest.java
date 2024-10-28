package eu.daxiongmao.blog.pictures;

import eu.daxiongmao.blog.pictures.model.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class FileManagerTest {

    @Test
    void lookForMissingFiles() {
        // Given
        Path excelFile = Paths.get("src", "test", "resources", "family_blog_ngg_gallery.xlsx");
        ExcelParser excelParser = new ExcelParser();
        FileManager fileManager = new FileManager();
        // Do
        Set<Image> images = excelParser.readExcelFile(excelFile.toString());
        List<Image> missingImages = fileManager.ensureImagesExists(images);
        Assertions.assertNotNull(missingImages);
        Assertions.assertTrue(missingImages.isEmpty());
    }
}
