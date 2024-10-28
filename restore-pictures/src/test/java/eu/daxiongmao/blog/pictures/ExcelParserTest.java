package eu.daxiongmao.blog.pictures;

import eu.daxiongmao.blog.pictures.model.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class ExcelParserTest {

    @Test
    void readExcelFile() {
        // Given
        Path excelFile = Paths.get("src", "test", "resources", "family_blog_ngg_gallery.xlsx");
        ExcelParser excelParser = new ExcelParser();
        // Do
        Set<Image> images = excelParser.readExcelFile(excelFile.toString());
        // Checks
        Assertions.assertNotNull(images);
        Assertions.assertFalse(images.isEmpty());
        Assertions.assertEquals(1957, images.size());
    }
}
