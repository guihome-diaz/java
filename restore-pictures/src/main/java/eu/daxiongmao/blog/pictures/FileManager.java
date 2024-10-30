package eu.daxiongmao.blog.pictures;

import eu.daxiongmao.blog.pictures.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * To handle files operations.
 * @version 1.0 (2024/10)
 * @since 2024/10
 * @author Guillaume Diaz
 */
public class FileManager {

    protected static final Logger logger = LogManager.getLogger(Constants.LOGGER_NAME);

    /**
     * To ensure all given images exist.
     * It returns ONLY IMAGES THAT DO NOT exist.
     * @param images images to check
     * @return images that do not exist on hard drive.
     */
    public List<Image> ensureImagesExists(List<Image> images) {
        List<Image> missingImages = new ArrayList<>();
        for (Image image : images) {
            String imagePath = image.galleryPathOneDrive() + File.separator + image.imageName();
            if (Files.notExists(Paths.get(imagePath))) {
                System.err.println("Image does not exist. imagePath=" + imagePath);
                missingImages.add(image);
            }
        }
        // Sort results
        Collections.sort(missingImages);
        return missingImages;
    }

    /**
     * To delete all missing files or rename the ones that start with "1_" (remove prefix)
     * @return SQL script
     */
    public String createSqlQueries(List<Image> missingImages) {
        StringBuilder sqlQueries = new StringBuilder();
        for (Image image : missingImages) {
            sqlQueries.append("DELETE FROM `family_blog_ngg_pictures` WHERE galleryid=%s AND filename='%s';%n".formatted(image.galleryId(), image.imageName()));
        }
        return sqlQueries.toString();
    }

    /**
     * To copy images from OneDrive to new directory for blog restauration.
     * @param sourceImages list of images to copy
     * @param missingImages list of images that have been removed from the OneDrive
     * @param targetFolder directory where images should be copied
     * @return list of copied images
     * @throws IOException failed to copy images
     */
    public List<Path> copyImages(List<Image> sourceImages, List<Image> missingImages, Path targetFolder) throws IOException {
        List<Path> copies = new ArrayList<>();
        Path currentPath = null;
        for (Image source : sourceImages) {
            // skip missing images
            if (missingImages.contains(source)) {
                continue;
            }

            // Create gallery directory if it does not already exist
            String sourceGallerySrvPath = source.getServerGalleryName();
            if (currentPath == null || !currentPath.equals(sourceGallerySrvPath)) {
                currentPath = targetFolder.resolve(sourceGallerySrvPath);
                Files.createDirectories(currentPath);
            }
            // Copy file
            Path sourceFile = Paths.get(source.galleryPathOneDrive(), source.imageName());
            Path target = currentPath.resolve(source.imageName());
            Files.copy(sourceFile, target);
            copies.add(target);
        }
        return copies;
    }

    public List<Path> zipDirectories(Path targetFolder) throws IOException {
        // Build zip list
        final Map<String, List<Path>> directoriesToZip = new HashMap<>();
        AtomicInteger directoryCount = new AtomicInteger(0);
        Files.newDirectoryStream(targetFolder).forEach(directory -> {
            String dirName = directory.getFileName().toString();
            final int year = Integer.parseInt(dirName.substring(0, dirName.indexOf("_")));
            dirName = dirName.substring(dirName.indexOf("_") + 1);
            final int month = Integer.parseInt(dirName.substring(0, dirName.indexOf("_")));
            final String mapKey = "%4d_%02d.zip".formatted(year, month);

            if (directoriesToZip.containsKey(mapKey)) {
                directoriesToZip.get(mapKey).add(directory);
            } else {
                List<Path> directories = new ArrayList<>();
                directories.add(directory);
                directoriesToZip.put(mapKey, directories);
            }
            directoryCount.addAndGet(1);
        });

        System.out.println("Directories to zip: " + directoryCount.get());
        directoriesToZip.forEach((key, value) -> {
            System.out.println(key + " => " + value);
        });


        return new ArrayList<>();
    }
}
