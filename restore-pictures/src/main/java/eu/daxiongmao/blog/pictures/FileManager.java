package eu.daxiongmao.blog.pictures;

import eu.daxiongmao.blog.pictures.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    public List<Image> ensureImagesExists(Set<Image> images) {
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

}
