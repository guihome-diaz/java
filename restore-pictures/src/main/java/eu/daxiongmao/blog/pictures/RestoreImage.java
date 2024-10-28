package eu.daxiongmao.blog.pictures;

import eu.daxiongmao.blog.pictures.model.Image;

import java.util.Set;

public class RestoreImage {

    public static void main(String[] args) {

    }

    public void restoreImages(String excelFilePath) {
        // Parse excel file
        ExcelParser excelParser = new ExcelParser();
        Set<Image> images = excelParser.readExcelFile(excelFilePath);

        // Ensure images are available
        FileManager fileManager = new FileManager();
        Set<Image> missingImages = fileManager.ensureImagesExists(images);
        if (!missingImages.isEmpty()) {
            throw new IllegalStateException("You must fix the missing images");
        }
    }

}
