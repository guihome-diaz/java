package eu.daxiongmao.blog.pictures;

import eu.daxiongmao.blog.pictures.model.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RestoreImage {

    public static void main(String[] args) {

    }

    /**
     * To restore blog galleries files on local disk.
     * @param excelFilePath excel file to parse
     * @param targetFolder folder where to recreate the blog NGG gallery directories and files. (Ex: E:/temp/Blog)
     */
    public void restoreImages(String excelFilePath, String targetFolder) throws IOException {
        // arg check
        Path targetDirectory = Paths.get("E:/Temp/Blog");
        if (Files.notExists(targetDirectory)) {
            throw new IllegalArgumentException("Cannot restore images, target folder does not exist. " + targetDirectory);
        }
        Path excelFile = Paths.get(excelFilePath);
        if (Files.notExists(excelFile)) {
            throw new IllegalArgumentException("Cannot restore images, excel file does not exist. " + excelFilePath);
        }

        // Parse excel file
        ExcelParser excelParser = new ExcelParser();
        List<Image> images = excelParser.readExcelFile(excelFilePath);

        // Ensure images are available
        FileManager fileManager = new FileManager();
        List<Image> missingImages = fileManager.ensureImagesExists(images);
        // Generate SQL script to delete missing images
        String sqlScript = fileManager.createSqlQueries(missingImages);
        System.out.println(sqlScript);
        // Copy files
        fileManager.copyImages(images, missingImages, targetDirectory);
        // ZIP files



    }

}
